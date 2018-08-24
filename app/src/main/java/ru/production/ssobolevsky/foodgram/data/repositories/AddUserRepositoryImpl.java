package ru.production.ssobolevsky.foodgram.data.repositories;

import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.domain.repositories.AddUserRepository;
import ru.production.ssobolevsky.foodgram.domain.usecases.AddUserUseCase;

/**
 * Created by pro on 16.07.2018.
 */

public class AddUserRepositoryImpl implements AddUserRepository {
    /**
     * Identifier of action add.
     */
    public static final int ACTION_ADD = 1;
    /**
     * Identifier of action cancel.
     */
    public static final int ACTION_CANCEL = 2;
    /**
     * Identifier of action accept.
     */
    public static final int ACTION_ACCEPT = 3;
    /**
     * Identifier of action delete.
     */
    public static final int ACTION_DELETE = 4;

    /**
     * Send request of friendship to selected user.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    @Override
    public Single<Integer> addUser(String uid) {
        return Single.create(emitter -> {
            MyFirebaseData
                    .getFirebaseDatabaseReference()
                    .child(MyFirebaseData.USERS_TABLE)
                    .child(MyFirebaseData.getFirebaseUserUid())
                    .child(MyFirebaseData.CONTACTS_TABLE)
                    .child(uid)
                    .setValue(false);
        emitter.onSuccess(ACTION_ADD);});
    }
    /**
     * Cancel request of friendship to selected user.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    @Override
    public Single<Integer> cancelRequest(String uid) {
        return Single.create(emitter -> {
            MyFirebaseData
                    .getFirebaseDatabaseReference()
                    .child(MyFirebaseData.USERS_TABLE)
                    .child(MyFirebaseData.getFirebaseUserUid())
                    .child(MyFirebaseData.CONTACTS_TABLE)
                    .child(uid)
                    .removeValue();
            emitter.onSuccess(ACTION_CANCEL);});
    }
    /**
     * Accept request of friendship with selected user.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    @Override
    public Single<Integer> acceptRequest(String uid) {
        return Single.create(emitter -> {
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
            emitter.onSuccess(ACTION_ACCEPT);});
    }
    /**
     * Delete selected user from friends.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    @Override
    public Single<Integer> deleteFriend(String uid) {
        return Single.create(emitter -> {
            MyFirebaseData
                    .getFirebaseDatabaseReference()
                    .child(MyFirebaseData.USERS_TABLE)
                    .child(MyFirebaseData.getFirebaseUserUid())
                    .child(MyFirebaseData.CONTACTS_TABLE)
                    .child(uid)
                    .setValue(false);
            MyFirebaseData
                    .getFirebaseDatabaseReference()
                    .child(MyFirebaseData.USERS_TABLE)
                    .child(uid)
                    .child(MyFirebaseData.CONTACTS_TABLE)
                    .child(MyFirebaseData.getFirebaseUserUid())
                    .removeValue();
            emitter.onSuccess(ACTION_DELETE);});
    }
}
