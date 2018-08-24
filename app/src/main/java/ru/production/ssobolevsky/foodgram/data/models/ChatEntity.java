package ru.production.ssobolevsky.foodgram.data.models;

public class ChatEntity {

    private String lastMessage;
    private String uid;
    private long timeStamp;
    private String userUid;
    private String selectedUserUid;
    private String userName;

    public ChatEntity() {
    }

    public ChatEntity(String lastMessage, String uid, long timeStamp, String userUid, String selectedUserUid) {
        this.lastMessage = lastMessage;
        this.uid = uid;
        this.timeStamp = timeStamp;
        this.userUid = userUid;
        this.selectedUserUid = selectedUserUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getSelectedUserUid() {
        return selectedUserUid;
    }

    public void setSelectedUserUid(String selectedUserUid) {
        this.selectedUserUid = selectedUserUid;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
