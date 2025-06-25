package com.cisi.rest.api;

import com.cisi.rest.handlers.ClipHandler;
import com.cisi.rest.model.ClipRequest;
import com.cisi.rest.utilities.YoutubeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/clips")
public class ClipApi {

    @Autowired
    private ClipHandler clipHandler;

    @Autowired
    private YoutubeService youTubeService;

    @PostMapping
    public ResponseEntity<String> createClip(@RequestBody ClipRequest request) {
        String clipId = clipHandler.createClip(request);
        return ResponseEntity.ok(clipId);
    }

    @GetMapping("/nightbot")
    public ResponseEntity<String> createClipFromNightbot(
            @RequestParam String channel,
            @RequestParam String user) {

        JSONObject videoInfo = youTubeService.getLiveVideoInfo(channel);
        if (videoInfo == null) {
            return ResponseEntity.badRequest().body("No live stream found for channel: " + channel);
        }

        String videoId = videoInfo.getString("videoId");
        String title = videoInfo.getString("title");
        String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
        String clippedAt = Instant.now().toString();
        String duration = "00:01:00";

        ClipRequest request = new ClipRequest();
        request.setChannelName(channel);
        request.setVideoId(videoId);
        request.setTitle(title);
        request.setThumbnailUrl(thumbnailUrl);
        request.setDuration(duration);
        request.setModeratorName(user);
        request.setClippedAt(clippedAt);

        String clipId = clipHandler.createClip(request);
        return ResponseEntity.ok("Clip saved with ID: " + clipId);
    }
}
