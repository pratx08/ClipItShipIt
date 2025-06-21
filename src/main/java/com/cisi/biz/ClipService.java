package com.cisi.biz;

import com.cisi.data.Clip;
import com.cisi.data.ClipRequestDTO;
import com.cisi.data.ClipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClipService {

    @Autowired
    private ClipRepository clipRepository;

    public Clip addClip(ClipRequestDTO dto) {
        Clip clip = new Clip();
        clip.setChannelName(dto.getChannelName());
        clip.setClipTitle(dto.getClipTitle());
        clip.setClipUrl(dto.getClipUrl());
        clip.setModeratorName(dto.getModeratorName());
        clip.setDuration(dto.getDuration());
        clip.setTimestamp(LocalDateTime.now());
        return clipRepository.save(clip);
    }
}
