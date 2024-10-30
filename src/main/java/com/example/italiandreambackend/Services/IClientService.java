package com.example.italiandreambackend.Services;

import com.example.italiandreambackend.Entity.Client;
import com.example.italiandreambackend.Request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface IClientService {
    ResponseEntity<?> createClient(Client client);
    ResponseEntity<?> login(LoginRequest loginRequest);
    ResponseEntity<?> getAllClients();
    ResponseEntity<?> verifyClientByEmail(String email);
    ResponseEntity<?> verifyClientByCode(String email ,  Integer code);
    ResponseEntity<?> resetPassword(String email ,String password);

    ResponseEntity<?> ChangePassword(String clientId ,String password);

    ResponseEntity<?> getClientProfile(String clientId);







}
