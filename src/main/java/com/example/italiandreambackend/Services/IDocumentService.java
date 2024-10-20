package com.example.italiandreambackend.Services;

import com.example.italiandreambackend.Entity.DocumentsBourse;
import com.example.italiandreambackend.Entity.DocumentsUni;
import com.example.italiandreambackend.Entity.DocumentsVisa;
import org.springframework.http.ResponseEntity;

public interface IDocumentService {


    ResponseEntity<DocumentsUni> createDocumentUni(String client_id , DocumentsUni du);
    ResponseEntity<DocumentsBourse> createDocumentBourse(String client_id , DocumentsBourse db);
    ResponseEntity<DocumentsVisa> createDocumentVisa(String client_id , DocumentsVisa dv);
    ResponseEntity<?> getDocumentsUniByClient(String client_id);
    ResponseEntity<?> getDocumentsBourseByClient(String client_id);
    ResponseEntity<?> getDocumentsVisaByClient(String client_id);

    ResponseEntity<DocumentsUni> updateDocumentUni(String client_id, DocumentsUni du);
    ResponseEntity<DocumentsBourse> updateDocumentBourse(String client_id, DocumentsBourse db);
    ResponseEntity<DocumentsVisa> updateDocumentVisa(String client_id, DocumentsVisa dv);


}
