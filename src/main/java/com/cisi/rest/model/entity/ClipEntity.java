package com.cisi.rest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClipEntity {

    private String clipId;
    private String channelName;
    private String videoId;
    private String title;
    private String thumbnailUrl;
    private String duration;
    private String moderatorName;
    private String clippedAt;

    private int viewCount;
    private int likeCount;

    private Long clipTimestamp;
    private String clipLink;
}
