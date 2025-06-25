package com.cisi.rest.api;

import com.cisi.rest.handlers.ClipHandler;
import com.cisi.rest.model.ClipRequest;
import com.cisi.rest.model.entity.ClipEntity;
import com.cisi.rest.utilities.FirebaseService;
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

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping
    public ResponseEntity<ClipEntity> createClip(@RequestBody ClipRequest request) {
        ClipEntity savedClip = clipHandler.createClip(request);
        return ResponseEntity.ok(savedClip);
    }

    @GetMapping("/nightbot")
    public ResponseEntity<?> createClipFromNightbot(
            @RequestParam String channel,
            @RequestParam String user) {

        // Step 1: Get YouTube video info
        JSONObject videoInfo = youTubeService.getLiveVideoInfo(channel);
        if (videoInfo == null) {
            return ResponseEntity.badRequest().body("No live stream found for channel: " + channel);
        }

        String videoId = videoInfo.getString("videoId");
        String title = videoInfo.getString("title");
        String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";

        // Step 2: Get current time
        long clipCenter = Instant.now().getEpochSecond();
        String clippedAt = Instant.now().toString();

        // Step 3: Get stream start time
        Long streamStartTime = firebaseService.getStreamStartTime(channel);
        if (streamStartTime == null) {
            return ResponseEntity.badRequest().body("Stream start time not set for channel: " + channel);
        }

        // Step 4: Calculate timestamps
        long relativeCenter = clipCenter - streamStartTime;
        long clipStart = Math.max(relativeCenter - 30, 0); // avoid negative
        long clipEnd = relativeCenter + 30;

        String clipLink = "https://www.youtube.com/watch?v=" + videoId + "&t=" + clipStart + "s";
        String duration = "00:01:00";

        // Step 5: Build ClipRequest
        ClipRequest request = new ClipRequest();
        request.setChannelName(channel);
        request.setVideoId(videoId);
        request.setTitle(title);
        request.setThumbnailUrl(thumbnailUrl);
        request.setDuration(duration);
        request.setModeratorName(user);
        request.setClippedAt(clippedAt);
        request.setClipTimestamp(relativeCenter);
        request.setClipLink(clipLink);

        // Step 6: Save clip
        ClipEntity savedClip = clipHandler.createClip(request);
        return ResponseEntity.ok(savedClip);
    }

    @PostMapping("/setStreamStart")
    public ResponseEntity<String> setStreamStartTime(@RequestParam String channel) {
        long now = Instant.now().getEpochSecond();
        boolean success = firebaseService.setStreamStartTime(channel, now);
        if (success) {
            return ResponseEntity.ok("Stream start time set.");
        } else {
            return ResponseEntity.status(500).body("Failed to set stream start time.");
        }
    }
}
