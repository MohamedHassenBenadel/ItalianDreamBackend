package com.example.italiandreambackend.Services;

import com.example.italiandreambackend.Entity.*;
import com.example.italiandreambackend.Request.LoginRequest;
import com.example.italiandreambackend.Repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService{

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PaiementService paiementService;

    @Autowired
    DocumentService documentService;
    @Autowired
    PasswordUtil passwordUtil;

    @Autowired
    ClientDTOMapper clientDTOMapper;
    @Autowired
    EmailService emailService;

    @Override
    public ResponseEntity<Map<String, Object>> createClient(Client client) {
        Map<String, Object> response = new HashMap<>();
        Optional<Client> existingClientById = clientRepository.findById(client.getClientId());
        Optional<Client> existingClientByCin = clientRepository.findByCin(client.getCin());
        Optional<Client> existingClientByEmail = clientRepository.findByEmail(client.getEmail());

        if (existingClientById.isPresent()) {
            response.put("message", "Client with this ID already exists");
            return new ResponseEntity<>(response, HttpStatus.FOUND); // 302 FOUND
        }

        if (existingClientByCin.isPresent()) {
            response.put("message", "Client with this CIN already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 CONFLICT
        }

        if (existingClientByEmail.isPresent()) {
            response.put("message", "Client with this email already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 CONFLICT
        }

        try {
            // Encrypt the password
            client.setPassword(passwordUtil.encryptPassword(client.getPassword()));

            // Save the client initially without documents
            Client registered = clientRepository.save(client);

            // Create and assign initial payments
            List<Paiement> paiements = paiementService.createPayment(client);
            registered.setPayments(paiements);

            // Initialize and save documents
            documentService.initializeDocuments(registered);

            // Save the client again with documents
            clientRepository.save(registered);

            // Send welcome email to the new client
            emailService.sendWelcomeEmail(registered.getEmail(), registered.getClientId(), client.getCin().toString());

            response.put("message", "Client registered successfully");
            response.put("clientId", registered.getClientId());
            return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            response.put("message", "Client registration failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
        }
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<Client> optionalClient = clientRepository.findById(loginRequest.getClientId());

        if (!optionalClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Client doesn't exist"));
        }

        Client client = optionalClient.get();
        String currentPassword = client.getPassword();

        if (passwordUtil.matchPassword(loginRequest.getPassword(), currentPassword)) {
            return ResponseEntity.ok(Collections.singletonMap("clientId", client.getClientId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Wrong Password"));
        }
    }

    @Override
    public ResponseEntity<?> getAllClients() {
        List<Client> clients = clientRepository.findAll();

        // Filter clients to include only those with Role.User
        List<ClientDTO> clientDTOs = clients.stream()
                .filter(client -> client.getType() == Role.User)
                .map(clientDTOMapper)
                .collect(Collectors.toList());

        if (clientDTOs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clientDTOs);
    }

    @Override
    public ResponseEntity<?> verifyClientByEmail(String email) {
        Optional<Client> optionalClient = clientRepository.findClientByEmail(email);
        if(optionalClient.isPresent()){
            Random random = new Random();
            int min = 1000;
            int max = 9999;
            int code = random.nextInt(max - min + 1) + min;
            Client c = optionalClient.get();
            c.setCode(code);
            clientRepository.save(c);
            String subject = "Password Reset Code";
            String body = String.format(
                    "Bonjour %s,\n\n" +
                            "Nous avons reçu une demande pour réinitialiser votre mot de passe. Veuillez utiliser le code suivant pour procéder à la réinitialisation de votre mot de passe :\n\n" +
                            "       < %d >\n\n" +
                            "Si vous n'avez pas fait cette demande, veuillez ignorer cet e-mail. Votre compte est en sécurité.\n\n" +
                            "Merci,\n" +
                            "L'équipe Italian Dream", c.getPrenom(), code);

            emailService.sendEmail(c.getEmail(), subject, body);

            return new ResponseEntity<>(Map.of("message", "Client found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("message", "Client not found"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> verifyClientByCode(String email, Integer code) {

        Optional<Client> optionalClient = clientRepository.findClientByEmail(email);
        if(optionalClient.isPresent()){
            Client c = optionalClient.get();
            Integer dbCode = c.getCode();
            if (dbCode != null && dbCode.equals(code)) {
                return new ResponseEntity<>(Map.of("message", "Code vérifié avec succès"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("message", "Code incorrect"), HttpStatus.UNAUTHORIZED);
            }
        }

        return new ResponseEntity<>(Map.of("message", "Client non trouvé"), HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<?> resetPassword(String email, String password) {
        Optional<Client> optionalClient = clientRepository.findClientByEmail(email);

        Map<String, Object> response = new HashMap<>();

        if (optionalClient.isPresent()) {
            Client c = optionalClient.get();
            c.setPassword(passwordUtil.encryptPassword(password));
            clientRepository.save(c);
            response.put("message", "Password changed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Client not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> ChangePassword(String clientId, String password) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);

        Map<String, Object> response = new HashMap<>();

        if (optionalClient.isPresent()) {
            Client c = optionalClient.get();
            c.setPassword(passwordUtil.encryptPassword(password));
            clientRepository.save(c);
            response.put("message", "Password changed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Client not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getClientProfile(String clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            ClientDTO clientDTO = clientDTOMapper.apply(client);
            return new ResponseEntity<>(clientDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Client not found"), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> BanClient(String clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Map<String, Object> response = new HashMap<>();

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            if (client.getBanned()) {
                response.put("error", "This client is already banned");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            client.setBanned(true);
            clientRepository.save(client);

            response.put("message", "Client banned successfully");
            response.put("clientId", clientId);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Client not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Override
    public ResponseEntity<?> UnbanClient(String clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Map<String, Object> response = new HashMap<>();

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            if (!client.getBanned()) {
                response.put("error", "This client is already unbanned");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            client.setBanned(false);
            clientRepository.save(client);

            response.put("message", "Client unbanned successfully");
            response.put("clientId", clientId);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Client not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


}
