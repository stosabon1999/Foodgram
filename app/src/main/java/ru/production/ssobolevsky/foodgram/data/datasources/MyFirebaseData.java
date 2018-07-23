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
    public static final String POSTS_TABLE = "posts";
    public static final String SOUPS_TABLE = "soups";

    public static DatabaseReference getFirebaseDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static String getFirebaseUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public static StorageReference getFirebaseStorage() {
        return FirebaseStorage.getInstance().getReference();
    }

}
