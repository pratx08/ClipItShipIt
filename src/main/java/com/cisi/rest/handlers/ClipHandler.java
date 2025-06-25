package com.cisi.rest.handlers;

import com.cisi.rest.model.ClipRequest;
import com.cisi.rest.model.entity.ClipEntity;

public interface ClipHandler {
    ClipEntity createClip(ClipRequest request);
}
