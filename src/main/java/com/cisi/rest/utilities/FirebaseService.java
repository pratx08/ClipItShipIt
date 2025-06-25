package com.cisi.rest.utilities;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;

public class FirebaseService {
    @Autowired
    private Firestore firestore;

    // Fetches the stream start time (in seconds) for a given channel
    public Long getStreamStartTime(String channel) {
        try {
            DocumentSnapshot snapshot = firestore.collection("stream_metadata").document(channel).get().get();
            if (snapshot.exists()) {
                return snapshot.getLong("start_time"); // Ensure this is saved in seconds
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Optional: API to manually set stream start time
    public boolean setStreamStartTime(String channel, long timestampSeconds) {
        try {
            firestore.collection("stream_metadata").document(channel)
                    .set(new StreamStartTime(timestampSeconds));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Inner class to hold stream metadata
    public static class StreamStartTime {
        private long start_time;

        public StreamStartTime() {}
        public StreamStartTime(long start_time) {
            this.start_time = start_time;
        }
        public long getStart_time() {
            return start_time;
        }
        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }
    }
}
