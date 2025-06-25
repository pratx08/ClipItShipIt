package com.cisi.rest.utilities;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FirebaseService {

    @Autowired
    private Firestore firestore;

    public Long getStreamStartTime(String channel) {
        try {
            channel = channel.trim().toLowerCase();
            DocumentSnapshot snapshot = firestore.collection("stream_metadata").document(channel).get().get();
            if (snapshot.exists()) {
                return snapshot.getLong("start_time");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setStreamStartTime(String channel, long timestampSeconds) {
        try {
            firestore.collection("stream_metadata").document(channel)
                    .set(Map.of("start_time", timestampSeconds));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
