package com.musicwithvibe;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/playlist")
@CrossOrigin
public class PlaylistController {

    Firestore db = FirebaseInitializer.getDb();

    @PostMapping("/create")
    public String createPlaylist(@RequestParam String userId, @RequestParam String name) {
        try {
            CollectionReference playlists = db.collection("playlists");
            Map<String, Object> data = new HashMap<>();
            data.put("userId", userId);
            data.put("name", name);
            data.put("songs", new HashMap<String, Object>());
            playlists.add(data);
            return "Playlist created";
        } catch(Exception e) {
            e.printStackTrace();
            return "Error creating playlist";
        }
    }
}
