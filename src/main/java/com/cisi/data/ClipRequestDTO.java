package com.cisi.data;

public class ClipRequestDTO {
    private String channelName;
    private String clipTitle;
    private String clipUrl;
    private String moderatorName;
    private String duration;

    // Getters and setters
    public String getChannelName() { return channelName; }
    public void setChannelName(String channelName) { this.channelName = channelName; }

    public String getClipTitle() { return clipTitle; }
    public void setClipTitle(String clipTitle) { this.clipTitle = clipTitle; }

    public String getClipUrl() { return clipUrl; }
    public void setClipUrl(String clipUrl) { this.clipUrl = clipUrl; }

    public String getModeratorName() { return moderatorName; }
    public void setModeratorName(String moderatorName) { this.moderatorName = moderatorName; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
}
