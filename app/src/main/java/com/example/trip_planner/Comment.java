package com.example.trip_planner;

public class Comment {
    private String content;
    private int tripId;

    public Comment(String content, int tripId) {
        this.content = content;
        this.tripId = tripId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

}
