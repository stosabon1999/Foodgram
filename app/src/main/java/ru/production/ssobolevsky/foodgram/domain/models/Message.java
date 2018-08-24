package ru.production.ssobolevsky.foodgram.domain.models;

public class Message {

    private String message;
    private String uid;
    private String time;
    private String senderUid;
    private String receiverUid;
    private long timestamp;

    public Message(String message, String uid, String time, String senderUid, String receiverUid, long timestamp) {
        this.message = message;
        this.uid = uid;
        this.time = time;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.timestamp = timestamp;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
