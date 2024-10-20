package com.example.italiandreambackend.Services;

import com.example.italiandreambackend.Entity.Client;
import com.example.italiandreambackend.Entity.Paiement;
import com.example.italiandreambackend.Repository.ClientRepository;
import com.example.italiandreambackend.Repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementService implements IPaiementService{

    @Autowired
    PaiementRepository paiementRepository;

    @Autowired
    ClientRepository clientRepository ;

    @Override
    public List<Paiement> createPayment(Client client) {

        List<Paiement> paiements = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 1);
        Paiement tranche1 = new Paiement(
                null,
                "Tranche 1",
                1500.0,
                0.0,
                1500.0,
                calendar.getTime(),
                false,
                client
        );
        paiements.add(tranche1);

        calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 3);
        Paiement tranche2 = new Paiement(
                null,
                "Tranche 2",
                2000.0,
                0.0,
                2000.0,
                calendar.getTime(),
                false,
                client
        );
        paiements.add(tranche2);

        calendar = Calendar.getInstance();

        // Tranche 3
        calendar.add(Calendar.MONTH, 5);
        Paiement tranche3 = new Paiement(
                null,
                "Tranche 3",
                2000.0,
                0.0,
                2000.0,
                calendar.getTime(),
                false,
                client
        );
        paiements.add(tranche3);

        calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 7);
        Paiement tranche4 = new Paiement(
                null,
                "Tranche 4",
                1000.0,
                0.0,
                1000.0,
                calendar.getTime(),
                false,
                client
        );
        paiements.add(tranche4);

        return paiements;
    }

    @Override
    public ResponseEntity<?> updatePayment(String client_id, List<Paiement> updatedPayments) {
        Optional<Client> optionalClient = clientRepository.findById(client_id);
        if (!optionalClient.isPresent()) {
            return new ResponseEntity<>("Client doesn't exist", HttpStatus.NOT_FOUND);
        }

        Client client = optionalClient.get();

        // Iterate over the list of updated payments and update the client's payments
        for (Paiement updatedPayment : updatedPayments) {
            Paiement paymentToUpdate = client.getPayments().stream()
                    .filter(p -> p.getPaiementId().equals(updatedPayment.getPaiementId()))
                    .findFirst()
                    .orElse(null);

            if (paymentToUpdate == null) {
                return new ResponseEntity<>("One or more payments not found", HttpStatus.NOT_FOUND);
            }

            paymentToUpdate.setMontantPaye(updatedPayment.getMontantPaye());
            paymentToUpdate.setMontantRestant(updatedPayment.getMontantRestant());
            paymentToUpdate.setDateLimite(updatedPayment.getDateLimite());
            paymentToUpdate.setPaid(updatedPayment.getPaid());
        }

        clientRepository.save(client);
        return new ResponseEntity<>("Payments updated successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPayments(String client_id) {
        Optional<Client> optionalClient = clientRepository.findById(client_id);
        if(!optionalClient.isPresent()){
            return new ResponseEntity<>("Client doesn't exist", HttpStatus.NOT_FOUND);
        }
        Client client = optionalClient.get();
        return new ResponseEntity<>(client.getPayments(),HttpStatus.OK);
    }
}
