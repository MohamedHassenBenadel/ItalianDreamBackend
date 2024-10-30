package com.example.italiandreambackend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
public class DocumentsVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Integer id;

    Boolean doc1;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date doc1DueDate;

    Boolean doc2;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date doc2DueDate;

    Boolean doc3;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date doc3DueDate;

    @OneToOne(mappedBy = "documentsVisa", cascade = CascadeType.ALL)
    @JsonIgnore
     Client client;

}
