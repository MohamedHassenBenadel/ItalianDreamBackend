package com.example.italiandreambackend.Controller;

import com.example.italiandreambackend.Entity.Client;
import com.example.italiandreambackend.Request.LoginRequest;
import com.example.italiandreambackend.Request.PasswordResetRequest;
import com.example.italiandreambackend.Services.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("apiclient")
public class ClientController {

    @Autowired
    IClientService iClientService;

    @PostMapping("add-client")
    public ResponseEntity<?> addclient(@RequestBody Client c ){
        return iClientService.createClient(c);
    }

    @GetMapping("get-all-clients")
    public ResponseEntity<?> getAllClients(){
        return iClientService.getAllClients();
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return iClientService.login(loginRequest);
    }

    @GetMapping("verifyclient")
    public ResponseEntity<?> verifyClientByEmail(@RequestParam String email){
        return iClientService.verifyClientByEmail(email);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        return iClientService.resetPassword(request.getEmail(), request.getPassword());
    }




}
