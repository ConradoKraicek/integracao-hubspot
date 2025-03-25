package com.example.hubspot.controller;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class WebhookValidator {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";


    public static boolean isValidSignature(String payload, String signature, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA256_ALGORITHM);
            sha256_HMAC.init(secretKey);
            byte[] hashBytes = sha256_HMAC.doFinal(payload.getBytes());
            String computedSignature = Base64.getEncoder().encodeToString(hashBytes);

            return computedSignature.equals(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Erro ao validar assinatura do webhook.", e);
        }
    }
}
