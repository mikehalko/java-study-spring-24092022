package com.game.util;

public class EntityErrorResponse {
    private String error_description;
    private long timestamp;

    public String getErrorDescription() {
        return error_description;
    }
    public void setErrorDescription(String errorDescription) {
        this.error_description = errorDescription;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
