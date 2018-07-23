package ru.production.ssobolevsky.foodgram.domain.usecases;

/**
 * Created by pro on 16.07.2018.
 */

public interface AddUserUseCase {

    void addUser(AddUserUseCase.Callback callback, String uid);
    void cancelRequest(AddUserUseCase.Callback callback, String uid);
    void acceptRequest(AddUserUseCase.Callback callback, String uid);

    interface Callback {
        void onRequestUpdated(int action);
        void onError();
    }
}
