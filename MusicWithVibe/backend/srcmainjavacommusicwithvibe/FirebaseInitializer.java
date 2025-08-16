package com.musicwithvibe;

import java.io.FileInputStream;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

@Component
public class FirebaseInitializer {

    private static Firestore db;

    public FirebaseInitializer() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://music-with-vibe.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
            System.out.println("Firebase Initialized");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Firestore getDb() { return db; }
}
