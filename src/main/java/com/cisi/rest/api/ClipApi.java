package com.cisi.rest.api;

import com.cisi.rest.handlers.ClipHandler;
import com.cisi.rest.model.ClipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clips")
public class ClipApi {

    @Autowired
    private ClipHandler clipHandler;

    @PostMapping
    public ResponseEntity<String> createClip(@RequestBody ClipRequest request) {
        String clipId = clipHandler.createClip(request);
        return ResponseEntity.ok(clipId);
    }
}
