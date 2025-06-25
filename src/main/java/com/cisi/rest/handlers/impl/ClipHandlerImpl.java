package com.cisi.rest.handlers.impl;

import com.cisi.rest.handlers.ClipHandler;
import com.cisi.rest.model.ClipRequest;
import com.cisi.rest.model.entity.ClipEntity;
import com.cisi.rest.utilities.transformer.ClipTransformer;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClipHandlerImpl implements ClipHandler {

    @Autowired
    private Firestore firestore;

    @Override
    public ClipEntity  createClip(ClipRequest request) {
        String clipId = UUID.randomUUID().toString();
        ClipEntity entity = ClipTransformer.toEntity(request, clipId);

        DocumentReference ref = firestore.collection("clips").document(clipId);
        ref.set(entity);  // Fire and forget, non-blocking

        return entity;
    }
}
