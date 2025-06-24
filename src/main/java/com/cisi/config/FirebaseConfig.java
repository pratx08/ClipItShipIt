package com.cisi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                String serviceAccountJson = System.getenv("FIREBASE_SERVICE_ACCOUNT_JSON");

                if (serviceAccountJson == null || serviceAccountJson.isBlank()) {
                    throw new IllegalStateException("Environment variable 'FIREBASE_SERVICE_ACCOUNT_JSON' is not set or empty.");
                }

                InputStream serviceAccountStream = new ByteArrayInputStream(
                        serviceAccountJson.getBytes(StandardCharsets.UTF_8)
                );

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                        .build();

                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }

    @Bean
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }
}
