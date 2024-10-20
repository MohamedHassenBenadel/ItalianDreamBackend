package com.example.italiandreambackend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Client implements Serializable {

    @Id
    String clientId ;
    String password;
    String prenom ;
    String nom ;
    @Column(unique = true)
    Integer cin ;
    Integer age ;
    @Column(unique = true)
    String email ;
    Education  education;
    String orientation ;
    Boolean banned ;
    Role type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documents_bourse_id")
    DocumentsBourse documentsBourse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documents_visa_id")
    private DocumentsVisa documentsVisa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documents_uni_id")
    private DocumentsUni documentsUni;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paiement> payments;


}
