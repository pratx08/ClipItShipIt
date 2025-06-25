package com.cisi.rest.utilities;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class YoutubeService {
    @Value("${youtube.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public JSONObject getLiveVideoInfo(String channelName) {
        // 1. Get channel ID from username
        String channelUrl = "https://www.googleapis.com/youtube/v3/channels?part=id&forUsername="
                + channelName + "&key=" + apiKey;
        String channelResponse = restTemplate.getForObject(channelUrl, String.class);
        JSONArray items = new JSONObject(channelResponse).getJSONArray("items");

        if (items.isEmpty()) return null;

        String channelId = items.getJSONObject(0).getString("id");

        // 2. Get live video from that channel
        String liveVideoUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId="
                + channelId + "&eventType=live&type=video&key=" + apiKey;
        String liveResponse = restTemplate.getForObject(liveVideoUrl, String.class);
        JSONArray liveItems = new JSONObject(liveResponse).getJSONArray("items");

        if (liveItems.isEmpty()) return null;

        JSONObject liveItem = liveItems.getJSONObject(0);
        JSONObject result = new JSONObject();
        result.put("videoId", liveItem.getJSONObject("id").getString("videoId"));
        result.put("title", liveItem.getJSONObject("snippet").getString("title"));
        return result;
    }
}
