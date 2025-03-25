package com.example.hubspot.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Value("${hubspot.api.url}")
    private String hubspotApiUrl;

    private final AtomicInteger requestCount = new AtomicInteger(0);
    private static final int RATE_LIMIT = 10;
    private static final long RATE_LIMIT_INTERVAL = TimeUnit.SECONDS.toMillis(1);

    @PostMapping
    public ResponseEntity<String> createContact(@RequestBody Map<String, Object> contactData,
                                                @RequestHeader("Authorization") String accessToken) {
        if (!checkRateLimit()) {
            logger.warn("Rate limit excedido. Requisição bloqueada.");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit excedido. Tente novamente mais tarde.");
        }

        String url = hubspotApiUrl + "/crm/v3/objects/contacts";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(contactData, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            logger.info("Enviando requisição para criar contato: {}", contactData);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            logger.info("Resposta recebida da API do HubSpot: {}", response.getBody());
            return response;

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Erro na requisição para a API do HubSpot: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode())
                    .body("Erro na requisição: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            logger.error("Erro inesperado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno no servidor: " + e.getMessage());
        }
    }


    private boolean checkRateLimit() {
        long currentTime = System.currentTimeMillis();
        long lastResetTime = currentTime - RATE_LIMIT_INTERVAL;

        if (requestCount.get() > 0 && currentTime - lastResetTime >= RATE_LIMIT_INTERVAL) {
            requestCount.set(0);
        }

        if (requestCount.incrementAndGet() > RATE_LIMIT) {
            return false;
        }

        return true;
    }

}

