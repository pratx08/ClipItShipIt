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

    public JSONObject getLiveVideoInfo(String channelHandle) {
        // 1. Resolve channel handle to channel ID
        String cleanHandle = channelHandle.startsWith("@") ? channelHandle.substring(1) : channelHandle;
        String searchUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=channel&q="
                + cleanHandle + "&key=" + apiKey;

        String searchResponse = restTemplate.getForObject(searchUrl, String.class);
        JSONArray searchItems = new JSONObject(searchResponse).getJSONArray("items");
        if (searchItems.isEmpty()) return null;

        String channelId = searchItems.getJSONObject(0).getJSONObject("snippet").getString("channelId");

        // 2. Get live video from resolved channel ID
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
