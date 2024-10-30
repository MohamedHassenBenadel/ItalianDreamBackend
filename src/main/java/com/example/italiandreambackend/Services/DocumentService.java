package com.example.italiandreambackend.Services;

import com.example.italiandreambackend.Entity.Client;
import com.example.italiandreambackend.Entity.DocumentsBourse;
import com.example.italiandreambackend.Entity.DocumentsUni;
import com.example.italiandreambackend.Entity.DocumentsVisa;
import com.example.italiandreambackend.Repository.ClientRepository;
import com.example.italiandreambackend.Repository.DocumentsBourseRepository;
import com.example.italiandreambackend.Repository.DocumentsUniRepository;
import com.example.italiandreambackend.Repository.DocumentsVisaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


@Service
public class DocumentService implements IDocumentService{

    @Autowired
    DocumentsBourseRepository documentsBourseRepository;
    @Autowired
    DocumentsVisaRepository documentsVisaRepository;
    @Autowired
    DocumentsUniRepository documentsUniRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public ResponseEntity<DocumentsUni> createDocumentUni(String client_id, DocumentsUni du) {
        Optional<Client> clientOpt = clientRepository.findById(client_id);

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            du.setClient(client);
            DocumentsUni created_doc = documentsUniRepository.save(du);
            client.setDocumentsUni(created_doc);
            clientRepository.save(client);
            return new ResponseEntity<>(created_doc, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Client not found
        }
    }

    @Override
    public ResponseEntity<DocumentsBourse> createDocumentBourse(String client_id, DocumentsBourse db) {
        Optional<Client> clientOpt = clientRepository.findById(client_id);

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            db.setClient(client);
            DocumentsBourse created_doc = documentsBourseRepository.save(db);
            client.setDocumentsBourse(created_doc);
            clientRepository.save(client);
            return new ResponseEntity<>(created_doc, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Client not found
        }
    }

    @Override
    public ResponseEntity<DocumentsVisa> createDocumentVisa(String client_id, DocumentsVisa dv) {
        Optional<Client> clientOpt = clientRepository.findById(client_id);

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            dv.setClient(client);
            DocumentsVisa created_doc = documentsVisaRepository.save(dv);
            client.setDocumentsVisa(created_doc);
            clientRepository.save(client);
            return new ResponseEntity<>(created_doc, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Client not found
        }
    }

    @Override
    public ResponseEntity<?> getDocumentsUniByClient(String client_id) {
        Optional<Client> optionalClient = clientRepository.findById(client_id);
        if(optionalClient.isPresent()){
            Client client = optionalClient.get();
            return new ResponseEntity<>(client.getDocumentsUni(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Client doesn't exist", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getDocumentsBourseByClient(String client_id) {
        Optional<Client> optionalClient = clientRepository.findById(client_id);
        if(optionalClient.isPresent()){
            Client client = optionalClient.get();
            return new ResponseEntity<>(client.getDocumentsBourse(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Client doesn't exist", HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<?> getDocumentsVisaByClient(String client_id) {
        Optional<Client> optionalClient = clientRepository.findById(client_id);
        if(optionalClient.isPresent()){
            Client client = optionalClient.get();
            return new ResponseEntity<>(client.getDocumentsVisa(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Client doesn't exist", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<DocumentsUni> updateDocumentUni(String client_id, DocumentsUni du) {
        Optional<Client> optionalClient = clientRepository.findById(client_id);
        if (!optionalClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Client client = optionalClient.get();
        DocumentsUni existingDocument = client.getDocumentsUni();

        if (existingDocument == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Apply the updates from 'du' to 'existingDocument'
        existingDocument.setDoc1(du.getDoc1());
        existingDocument.setDoc1DueDate(du.getDoc1DueDate());

        existingDocument.setDoc2(du.getDoc2());
        existingDocument.setDoc2DueDate(du.getDoc2DueDate());


        existingDocument.setDoc3(du.getDoc3());
        existingDocument.setDoc3DueDate(du.getDoc3DueDate());


        // Add other fields to update as needed

        DocumentsUni updatedDocument = documentsUniRepository.save(existingDocument);
        return ResponseEntity.ok(updatedDocument);
    }

    @Override
    public ResponseEntity<DocumentsBourse> updateDocumentBourse(String client_id, DocumentsBourse db) {
        Optional<Client> clientOpt = clientRepository.findById(client_id);
        if (!clientOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Client client = clientOpt.get();
        DocumentsBourse existingDocument = client.getDocumentsBourse();

        if (existingDocument == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        existingDocument.setDoc1(db.getDoc1());
        existingDocument.setDoc1DueDate(db.getDoc1DueDate());

        existingDocument.setDoc2(db.getDoc2());
        existingDocument.setDoc2DueDate(db.getDoc2DueDate());


        existingDocument.setDoc3(db.getDoc3());
        existingDocument.setDoc3DueDate(db.getDoc3DueDate());

        DocumentsBourse updatedDocument = documentsBourseRepository.save(existingDocument);
        return ResponseEntity.ok(updatedDocument);
    }

    @Override
    public ResponseEntity<DocumentsVisa> updateDocumentVisa(String client_id, DocumentsVisa dv) {
        Optional<Client> clientOpt = clientRepository.findById(client_id);
        if (!clientOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Client client = clientOpt.get();
        DocumentsVisa existingDocument = client.getDocumentsVisa();

        if (existingDocument == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        existingDocument.setDoc1(dv.getDoc1());
        existingDocument.setDoc1DueDate(dv.getDoc1DueDate());

        existingDocument.setDoc2(dv.getDoc2());
        existingDocument.setDoc2DueDate(dv.getDoc2DueDate());


        existingDocument.setDoc3(dv.getDoc3());
        existingDocument.setDoc3DueDate(dv.getDoc3DueDate());

        DocumentsVisa updatedDocument = documentsVisaRepository.save(existingDocument);
        return ResponseEntity.ok(updatedDocument);
    }

    public void initializeDocuments(Client client) {
        LocalDate currentDate = LocalDate.now();
        Date doc1DueDate = java.sql.Date.valueOf(currentDate.plusMonths(2).withDayOfMonth(1));
        Date doc2DueDate = java.sql.Date.valueOf(currentDate.plusMonths(2).withDayOfMonth(7));
        Date doc3DueDate = java.sql.Date.valueOf(currentDate.plusMonths(2).withDayOfMonth(15));

        // Create and set default values for DocumentsUni
        DocumentsUni documentsUni = new DocumentsUni();
        documentsUni.setDoc1(false);
        documentsUni.setDoc1DueDate(doc1DueDate);
        documentsUni.setDoc2(false);
        documentsUni.setDoc2DueDate(doc2DueDate);
        documentsUni.setDoc3(false);
        documentsUni.setDoc3DueDate(doc3DueDate);
        documentsUni.setClient(client);
        documentsUniRepository.save(documentsUni);

        // Create and set default values for DocumentsBourse
        DocumentsBourse documentsBourse = new DocumentsBourse();
        documentsBourse.setDoc1(false);
        documentsBourse.setDoc1DueDate(doc1DueDate);
        documentsBourse.setDoc2(false);
        documentsBourse.setDoc2DueDate(doc2DueDate);
        documentsBourse.setDoc3(false);
        documentsBourse.setDoc3DueDate(doc3DueDate);
        documentsBourse.setClient(client);
        documentsBourseRepository.save(documentsBourse);

        // Create and set default values for DocumentsVisa
        DocumentsVisa documentsVisa = new DocumentsVisa();
        documentsVisa.setDoc1(false);
        documentsVisa.setDoc1DueDate(doc1DueDate);
        documentsVisa.setDoc2(false);
        documentsVisa.setDoc2DueDate(doc2DueDate);
        documentsVisa.setDoc3(false);
        documentsVisa.setDoc3DueDate(doc3DueDate);
        documentsVisa.setClient(client);
        documentsVisaRepository.save(documentsVisa);

        // Associate documents with the client
        client.setDocumentsUni(documentsUni);
        client.setDocumentsBourse(documentsBourse);
        client.setDocumentsVisa(documentsVisa);
    }


}
