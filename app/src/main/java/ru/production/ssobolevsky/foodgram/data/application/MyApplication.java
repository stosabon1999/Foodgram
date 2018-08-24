package ru.production.ssobolevsky.foodgram.data.application;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }
}
