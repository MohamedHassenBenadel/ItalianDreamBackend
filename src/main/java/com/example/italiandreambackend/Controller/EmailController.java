    package com.example.italiandreambackend.Controller;


    import com.example.italiandreambackend.Entity.Paiement;
    import com.example.italiandreambackend.Services.EmailService;
    import com.example.italiandreambackend.Services.IPaiementService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("apiEmail")
    public class EmailController {

        @Autowired
        EmailService emailService;

        @GetMapping("sendemail")
        public ResponseEntity<Map<String, String>> getEmail(
                @RequestParam String sender,
                @RequestParam String tel,
                @RequestParam String subject,
                @RequestParam String body) {

            Map<String, String> response = new HashMap<>();

            try {
                emailService.sendEmail(sender, tel, subject, body);
                response.put("status", "success");
                response.put("message", "Email sent successfully");
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                response.put("status", "error");
                response.put("message", "Failed to send email: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
    }
