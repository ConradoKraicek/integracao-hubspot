package com.example.hubspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Value("${hubspot.client.secret}")
    private String clientSecret;

    @PostMapping("/contact-creation")
    public ResponseEntity<String> handleContactCreation(@RequestBody String payload,
                                                        @RequestHeader("X-HubSpot-Signature") String signature) {
        if (!WebhookValidator.isValidSignature(payload, signature, clientSecret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Assinatura inválida.");
        }

        processContactCreation(payload);

        return ResponseEntity.ok("Webhook recebido com sucesso.");
    }

    private void processContactCreation(String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ContactCreationPayload contactPayload = objectMapper.readValue(payload, ContactCreationPayload.class);

            String email = contactPayload.getPropertyValue();
            long contactId = contactPayload.getObjectId();
            System.out.println("Novo contato criado - ID: " + contactId + ", Email: " + email);

            // Exemplo: Salvar em um banco de dados ou disparar ações
            // contactService.saveContact(contactPayload);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar payload do webhook.", e);
        }
    }
}
