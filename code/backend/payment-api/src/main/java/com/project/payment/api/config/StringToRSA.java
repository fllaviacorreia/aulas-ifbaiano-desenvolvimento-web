package com.project.payment.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class StringToRSA {

    @Bean
    public RSAPublicKey rsaPublicKey() {
        try {
            Resource resource = new ClassPathResource("public.pem");
            InputStream inputStream = resource.getInputStream();
            byte[] publicKeyBytes = inputStream.readAllBytes();
            inputStream.close();

            String publicKeyPEM = new String(publicKeyBytes, StandardCharsets.UTF_8)
                    .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decodedBytes = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA public key", e);
        }
    }

    @Bean
    public RSAPrivateKey rsaPrivateKey() {
        try {
            Resource resource = new ClassPathResource("private_pkcs8.der");
            InputStream inputStream = resource.getInputStream();
            byte[] privateKeyBytes = inputStream.readAllBytes();
            inputStream.close();

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA private key", e);
        }
    }
}
