package com.cisi.rest.utilities.transformer;

import com.cisi.rest.model.ClipRequest;
import com.cisi.rest.model.entity.ClipEntity;

import java.time.Instant;

public class ClipTransformer {

    public static ClipEntity toEntity(ClipRequest request, String clipId) {
        return ClipEntity.builder()
                .clipId(clipId)
                .channelName(request.getChannelName())
                .videoId(request.getVideoId())
                .title(request.getTitle())
                .thumbnailUrl(request.getThumbnailUrl())
                .duration(request.getDuration())
                .moderatorName(request.getModeratorName())
                .clippedAt(
                        request.getClippedAt() != null
                                ? request.getClippedAt()
                                : Instant.now().toString()
                )
                .viewCount(0)
                .likeCount(0)
                .build();
    }
}
