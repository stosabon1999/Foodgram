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

}
