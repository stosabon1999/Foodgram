package ru.production.ssobolevsky.foodgram.data.repositories;

import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.domain.repositories.AddUserRepository;
import ru.production.ssobolevsky.foodgram.domain.usecases.AddUserUseCase;

/**
 * Created by pro on 16.07.2018.
 */

public class AddUserRepositoryImpl implements AddUserRepository {

    public static final int ACTION_ADD = 1;
    public static final int ACTION_CANCEL = 2;
    public static final int ACTION_ACCEPT = 3;
    @Override
    public void addUser(AddUserRepository.Callback callback, String uid) {
        MyFirebaseData
                .getFirebaseDatabaseReference()
                .child(MyFirebaseData.USERS_TABLE)
                .child(MyFirebaseData.getFirebaseUserUid())
                .child(MyFirebaseData.CONTACTS_TABLE)
                .child(uid)
                .setValue(false);
        callback.onRequestUpdated(ACTION_ADD);
    }

    @Override
    public void cancelRequest(AddUserRepository.Callback callback, String uid) {
        MyFirebaseData
                .getFirebaseDatabaseReference()
                .child(MyFirebaseData.USERS_TABLE)
                .child(MyFirebaseData.getFirebaseUserUid())
                .child(MyFirebaseData.CONTACTS_TABLE)
                .child(uid)
                .removeValue();
        callback.onRequestUpdated(ACTION_CANCEL);
    }

    @Override
    public void acceptRequest(AddUserRepository.Callback callback, String uid) {
        MyFirebaseData
                .getFirebaseDatabaseReference()
                .child(MyFirebaseData.USERS_TABLE)
                .child(MyFirebaseData.getFirebaseUserUid())
                .child(MyFirebaseData.CONTACTS_TABLE)
                .child(uid)
                .setValue(true);
        MyFirebaseData
                .getFirebaseDatabaseReference()
                .child(MyFirebaseData.USERS_TABLE)
                .child(uid)
                .child(MyFirebaseData.CONTACTS_TABLE)
                .child(MyFirebaseData.getFirebaseUserUid())
                .setValue(true);
        callback.onRequestUpdated(ACTION_ACCEPT);
    }
}
