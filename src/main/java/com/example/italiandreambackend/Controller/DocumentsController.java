package com.example.italiandreambackend.Controller;


import com.example.italiandreambackend.Entity.DocumentsBourse;
import com.example.italiandreambackend.Entity.DocumentsUni;
import com.example.italiandreambackend.Entity.DocumentsVisa;
import com.example.italiandreambackend.Services.IDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("apidocuments")
public class DocumentsController {

    @Autowired
    IDocumentService IDocumentService ;

    @PostMapping("create-doc-uni")
    public ResponseEntity<DocumentsUni> createDocumentUni(@RequestParam String cliend_id ,@RequestBody DocumentsUni du){
        return IDocumentService.createDocumentUni(cliend_id , du);
    }


    @PostMapping("create-doc-bourse")
    public ResponseEntity<DocumentsBourse> createDocumentBourse(@RequestParam String cliend_id ,@RequestBody DocumentsBourse db){
        return IDocumentService.createDocumentBourse(cliend_id , db);
    }


    @PostMapping("create-doc-visa")
    public ResponseEntity<DocumentsVisa> createDocumentVisa(@RequestParam String cliend_id ,@RequestBody DocumentsVisa dv){
        return IDocumentService.createDocumentVisa(cliend_id ,dv);
    }

    @GetMapping("documents-bourse")
    public ResponseEntity<?> getDocumentsBourseByClient(@RequestParam String client_id){
        return IDocumentService.getDocumentsBourseByClient(client_id);
    }

    @GetMapping("documents-uni")
    public ResponseEntity<?> getDocumentsUniByClient(@RequestParam String client_id){
        return IDocumentService.getDocumentsUniByClient(client_id);
    }

    @GetMapping("documents-visa")
    public ResponseEntity<?> getDocumentsVisaByClient(@RequestParam String client_id){
        return IDocumentService.getDocumentsVisaByClient(client_id);
    }

    @PutMapping("update-doc-uni")
    public ResponseEntity<DocumentsUni> updateDocumentUni(@RequestParam String client_id, @RequestBody DocumentsUni du) {
        return IDocumentService.updateDocumentUni(client_id, du);
    }

    @PutMapping("update-doc-bourse")
    public ResponseEntity<DocumentsBourse> updateDocumentBourse(@RequestParam String client_id, @RequestBody DocumentsBourse db) {
        return IDocumentService.updateDocumentBourse(client_id, db);
    }

    @PutMapping("update-doc-visa")
    public ResponseEntity<DocumentsVisa> updateDocumentVisa(@RequestParam String client_id, @RequestBody DocumentsVisa dv) {
        return IDocumentService.updateDocumentVisa(client_id, dv);
    }


}
