package ru.production.ssobolevsky.foodgram.domain.repositories;

import ru.production.ssobolevsky.foodgram.domain.usecases.AddUserUseCase;

/**
 * Created by pro on 16.07.2018.
 */

public interface AddUserRepository {

    void addUser(AddUserRepository.Callback callback, String uid);
    void cancelRequest(AddUserRepository.Callback callback, String uid);
    void acceptRequest(AddUserRepository.Callback callback, String uid);

    interface Callback {
        void onRequestUpdated(int id);
        void onError();
    }

}
