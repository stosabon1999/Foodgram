package ru.production.ssobolevsky.foodgram.data.models;

import java.util.Objects;

public class MessageEntity {

    private String message;
    private String uid;
    private long timestamp;
    private String senderUid;
    private String receiverUid;
    private String messageUid;

    public MessageEntity() {
    }

    public MessageEntity(String message, String uid, long timestamp, String senderUid, String receiverUid, String messageUid) {
        this.message = message;
        this.uid = uid;
        this.timestamp = timestamp;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.messageUid = messageUid;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public String getMessageUid() {
        return messageUid;
    }

    public void setMessageUid(String messageUid) {
        this.messageUid = messageUid;
    }

}
