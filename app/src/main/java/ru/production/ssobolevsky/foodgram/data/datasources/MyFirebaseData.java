package ru.production.ssobolevsky.foodgram.data.datasources;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by pro on 14.07.2018.
 */

public class MyFirebaseData {

    public static final String USERS_TABLE = "users";
    public static final String CONTACTS_TABLE = "contacts";
    public static final String CHATS_TABLE = "chats";
    public static final String MESSAGES_TABLE = "messages";
    public static final int MAX_MESSAGES = 15;
    /**
     * Get reference of db to get access to db.
     * @return reference of db.
     */
    public static DatabaseReference getFirebaseDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Get uid of current user.
     * @return uid.
     */
    public static String getFirebaseUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    /**
     * Get user of current session.
     * @return current user.
     */
    public static FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * Sign out from current session.
     */
    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Get auth for sign in and sign up.
     * @return auth.
     */
    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    /**
     * Get reference of storage to get access to storage.
     * @return reference of storage.
     */
    public static StorageReference getFirebaseStorage() {
        return FirebaseStorage.getInstance().getReference();
    }

}
