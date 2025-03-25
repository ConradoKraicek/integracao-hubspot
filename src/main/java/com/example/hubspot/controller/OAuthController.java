package com.example.hubspot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/auth")
public class OAuthController {

    @Value("${hubspot.client.id}")
    private String clientId;

    @Value("${hubspot.client.secret}")
    private String clientSecret;

    @Value("${hubspot.redirect.uri}")
    private String redirectUri;

    @Value("${hubspot.authorization.url}")
    private String authorizationUrl;

    @Value("${hubspot.authorization.scope}")
    private String scope;


    @GetMapping("/authorize")
    public RedirectView hubspotAuth() {
        String authUrl = "https://app.hubspot.com/oauth/authorize" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scope;
        return new RedirectView(authUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam String code) {
        String tokenUrl = "https://api.hubapi.com/oauth/v1/token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, params, String.class);
        return response;
    }
}