package com.musicwithvibe;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@RestController
@RequestMapping("/api/music")
@CrossOrigin
public class MusicController {

    private final String AUDIO_API_BASE = "https://api.audiomack.com/v1";
    private final String YOUTUBE_API_KEY = "YOUR_YOUTUBE_API_KEY";

    @GetMapping("/search")
    public JSONArray searchSongs(@RequestParam String query) throws Exception {
        URL url = new URL(AUDIO_API_BASE + "/search/songs?query=" + query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder inline = new StringBuilder();
        while(sc.hasNext()) inline.append(sc.nextLine());
        sc.close();

        JSONObject data = new JSONObject(inline.toString());
        JSONArray results = data.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject song = results.getJSONObject(i);
            String title = song.getString("title") + " " + song.getString("artist");
            String ytUrl = searchYouTube(title);
            song.put("youtube_preview", ytUrl);
        }
        return results;
    }

    private String searchYouTube(String keyword) {
        try {
            String query = keyword.replace(" ", "+");
            URL url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + query +
                    "&key=" + YOUTUBE_API_KEY + "&type=video&maxResults=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner sc = new Scanner(conn.getInputStream());
            StringBuilder inline = new StringBuilder();
            while(sc.hasNext()) inline.append(sc.nextLine());
            sc.close();

            JSONObject json = new JSONObject(inline.toString());
            JSONArray items = json.getJSONArray("items");
            if (items.length() > 0) {
                String videoId = items.getJSONObject(0).getJSONObject("id").getString("videoId");
                return "https://www.youtube.com/embed/" + videoId;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
