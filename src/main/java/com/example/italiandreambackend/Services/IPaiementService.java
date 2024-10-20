package com.example.italiandreambackend.Services;

import com.example.italiandreambackend.Entity.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPaiementService {

    List<Paiement> createPayment(Client client);
    ResponseEntity<?> updatePayment(String client_id ,List<Paiement> p);
    ResponseEntity<?> getPayments (String client_id) ;

}
