package com.cisi.rest;

import com.cisi.data.Clip;
import com.cisi.data.ClipRequestDTO;
import com.cisi.biz.ClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clips")
public class ClipController {

    @Autowired
    private ClipService clipService;

    @PostMapping
    public ResponseEntity<Clip> createClip(@RequestBody ClipRequestDTO clipRequestDTO) {
        Clip savedClip = clipService.addClip(clipRequestDTO);
        return new ResponseEntity<>(savedClip, HttpStatus.CREATED);
    }
}
