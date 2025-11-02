package org.lasalle.mega.loja.application.security.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class PasswordHasherServiceImpl implements PasswordEncoder {

    @Value("${app.instance-id:}")
    private String pepper;


    @Override
    public String encode(CharSequence rawPassword) {
        return convertStringToSha256(pepper + ":" + rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String computed = convertStringToSha256(pepper + ":" + rawPassword);

        return MessageDigest.isEqual(
                computed.getBytes(StandardCharsets.UTF_8),
                encodedPassword.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String convertStringToSha256(CharSequence sequence) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] dig = md.digest(sequence.toString().getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(dig.length * 2);

            for (byte b : dig) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao calcular SHA-256.", e);
        }
    }
}
