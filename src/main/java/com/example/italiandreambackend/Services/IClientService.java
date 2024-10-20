package com.example.italiandreambackend.Services;

import com.example.italiandreambackend.Entity.Client;
import com.example.italiandreambackend.Request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface IClientService {
    ResponseEntity<?> createClient(Client client);
    ResponseEntity<?> login(LoginRequest loginRequest);
    ResponseEntity<?> getAllClients();
    ResponseEntity<?> verifyClientByEmail(String email);
    ResponseEntity<?> resetPassword(String email ,String password);



}
