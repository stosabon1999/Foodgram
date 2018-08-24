package ru.production.ssobolevsky.foodgram.domain.models;

import java.util.HashMap;

/**
 * Created by pro on 15.07.2018.
 */

public class User {


    private String mEmail;

    private String mName;

    private String mUid;

    private HashMap<String, Boolean> contacts;

    private int action;

    private int friendSize;

    public HashMap<String, Boolean> getContacts() {
        return contacts;
    }

    public void setContacts(HashMap<String, Boolean> contacts) {
        this.contacts = contacts;
    }

    public User() {
    }

    public User(String email, String name, String uid, HashMap<String, Boolean> contacts) {
        mEmail = email;
        mName = name;
        mUid = uid;
        this.contacts = contacts;
        action = 1;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getFriendSize() {
        return friendSize;
    }

    public void setFriendSize(int friendSize) {
        this.friendSize = friendSize;
    }

}
