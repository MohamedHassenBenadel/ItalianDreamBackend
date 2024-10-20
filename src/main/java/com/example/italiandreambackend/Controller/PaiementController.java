    package com.example.italiandreambackend.Controller;


    import com.example.italiandreambackend.Entity.Client;
    import com.example.italiandreambackend.Entity.Paiement;
    import com.example.italiandreambackend.Services.IPaiementService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("apiPaiement")
    public class PaiementController {

        @Autowired
        IPaiementService iPaiementService;

        @GetMapping("get-payments")
        public ResponseEntity<?> getPayments(@RequestParam String client_id )
        {
            return iPaiementService.getPayments(client_id);
        }

        @PutMapping("update")
        public ResponseEntity<?> updatePayment(@RequestParam String client_id , @RequestBody List<Paiement> p ){
            return iPaiementService.updatePayment(client_id,p);
        }


    }
