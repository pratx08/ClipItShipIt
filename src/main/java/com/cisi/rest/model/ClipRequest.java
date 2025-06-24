package com.cisi.rest.model;

import lombok.Data;

@Data
public class ClipRequest {
    private String channelName;
    private String videoId;
    private String title;
    private String thumbnailUrl;
    private String duration;
    private String moderatorName;
    private String clippedAt;
}
