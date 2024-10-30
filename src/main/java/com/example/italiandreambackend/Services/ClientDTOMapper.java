package com.example.italiandreambackend.Services;

import com.example.italiandreambackend.Entity.Client;
import com.example.italiandreambackend.Entity.ClientDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClientDTOMapper implements Function<Client, ClientDTO> {
    @Override
    public ClientDTO apply(Client client) {
        if (client == null) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientId(client.getClientId());
        clientDTO.setPrenom(client.getPrenom());
        clientDTO.setNom(client.getNom());
        clientDTO.setAge(client.getAge());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setEducation(client.getEducation());
        clientDTO.setOrientation(client.getOrientation());

        // Set the payments directly
        clientDTO.setPayments(client.getPayments());  // Directly set the payments list

        // Set documents directly
        clientDTO.setDocumentsBourse(client.getDocumentsBourse()); // Directly set the documentsBourse
        clientDTO.setDocumentsVisa(client.getDocumentsVisa());     // Directly set the documentsVisa
        clientDTO.setDocumentsUni(client.getDocumentsUni());       // Directly set the documentsUni



        return clientDTO;
    }

}
