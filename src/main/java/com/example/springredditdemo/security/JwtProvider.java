package com.example.springredditdemo.security;

import com.example.springredditdemo.exception.RedditException;
import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream inputStream = getClass().getResourceAsStream("/springreddit.jks");
            keyStore.load(inputStream, "secret".toCharArray());

        } catch (KeyStoreException | NoSuchAlgorithmException | IOException | CertificateException e) {
            throw new RedditException("Exception while loading keystore.");
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();

    }

    private Key getPrivateKey() {
        try {
            return keyStore.getKey("springreddit", "secret".toCharArray());

        } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new RedditException("Exception while loading keystore.");
        }
    }
}
