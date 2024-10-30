package com.example.italiandreambackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    String clientId ;
    String prenom ;
    String nom ;
    Integer age ;
    String email ;
    Education  education;
    String orientation ;
    Boolean banned ;
    Integer code;
    Role type;
    DocumentsBourse documentsBourse;
    DocumentsVisa documentsVisa;
    DocumentsUni documentsUni;
    List<Paiement> payments;

}
