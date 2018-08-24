package ru.production.ssobolevsky.foodgram.domain.models;

public class Chat {

    private String lastMessage;
    private String uid;
    private String date;
    private String userUid;
    private String selectedUserUid;
    private String mName;

    public Chat() {
    }

    public Chat(String lastMessage, String uid, String date, String userUid, String selectedUserUid, String name) {
        this.lastMessage = lastMessage;
        this.uid = uid;
        this.date = date;
        this.userUid = userUid;
        this.selectedUserUid = selectedUserUid;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

    public String getDate() {
        return date;
    }

    public void setDate(String timeStamp) {
        this.date = timeStamp;
    }
}
