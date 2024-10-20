package com.example.italiandreambackend.Services;

import com.example.italiandreambackend.Entity.Client;
import com.example.italiandreambackend.Entity.Paiement;
import com.example.italiandreambackend.Request.LoginRequest;
import com.example.italiandreambackend.Repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService{

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PaiementService paiementService;
    @Autowired
    PasswordUtil passwordUtil;

    @Override
    public ResponseEntity<String> createClient(Client client) {
        // Check if the client with the given clientId, cin, or email already exists
        Optional<Client> existingClientById = clientRepository.findById(client.getClientId());
        Optional<Client> existingClientByCin = clientRepository.findByCin(client.getCin());
        Optional<Client> existingClientByEmail = clientRepository.findByEmail(client.getEmail());

        if (existingClientById.isPresent()) {
            return new ResponseEntity<>("Client with this ID already exists", HttpStatus.FOUND); // 302 FOUND
        }

        if (existingClientByCin.isPresent()) {
            return new ResponseEntity<>("Client with this CIN already exists", HttpStatus.CONFLICT); // 409 CONFLICT
        }

        if (existingClientByEmail.isPresent()) {
            return new ResponseEntity<>("Client with this email already exists", HttpStatus.CONFLICT); // 409 CONFLICT
        }

        try {
            // Encrypt the password
            client.setPassword(passwordUtil.encryptPassword(client.getPassword()));
            // Save the client
            List<Paiement> paiements = paiementService.createPayment(client);
            client.setPayments(paiements);
            Client registered = clientRepository.save(client);

            if (registered != null) {
                return new ResponseEntity<>("Client registered successfully", HttpStatus.OK); // 200 OK
            } else {
                return new ResponseEntity<>("Client registration failed for some reason", HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Client registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
        }
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<Client> optionalClient = clientRepository.findById(loginRequest.getClientId());

        if (!optionalClient.isPresent()) {
            return new ResponseEntity<>("Client doesn't exist", HttpStatus.NOT_FOUND);
        }

        Client client = optionalClient.get();

        String currentPassword = client.getPassword();

        if (passwordUtil.matchPassword(loginRequest.getPassword(), currentPassword)) {
            return new ResponseEntity<>(client.getClientId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong Password", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public ResponseEntity<?> getAllClients() {
        List<Client> clients = clientRepository.findAll();

        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clients);
    }

    @Override
    public ResponseEntity<?> verifyClientByEmail(String email) {
        Optional<Client> optionalClient = clientRepository.findClientByEmail(email);
        if(optionalClient.isPresent()){
            return new ResponseEntity<>("Client found",HttpStatus.OK);
        }
        return new ResponseEntity<>("Client not found",HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> resetPassword(String email, String password) {
        Optional<Client> optionalClient = clientRepository.findClientByEmail(email);

        if (optionalClient.isPresent()) {
            Client c = optionalClient.get();
            c.setPassword(passwordUtil.encryptPassword(password));
            clientRepository.save(c);
            return new ResponseEntity<>("Password changed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
    }


}
