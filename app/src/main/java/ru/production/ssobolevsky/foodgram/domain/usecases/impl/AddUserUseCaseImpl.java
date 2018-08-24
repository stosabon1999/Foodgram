package ru.production.ssobolevsky.foodgram.domain.usecases.impl;

import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.domain.repositories.AddUserRepository;
import ru.production.ssobolevsky.foodgram.domain.usecases.AddUserUseCase;

/**
 * Created by pro on 16.07.2018.
 */

public class AddUserUseCaseImpl implements AddUserUseCase {

    private AddUserRepository mAddUserRepository;

    public AddUserUseCaseImpl(AddUserRepository addUserRepository) {
        mAddUserRepository = addUserRepository;
    }

    @Override
    public Single<Integer> addUser(String uid) {
        return mAddUserRepository.addUser(uid);
    }

    @Override
    public Single<Integer> cancelRequest(String uid) {
        return mAddUserRepository.cancelRequest(uid);
    }

    @Override
    public Single<Integer> acceptRequest(String uid) {
        return mAddUserRepository.acceptRequest(uid);
    }

    @Override
    public Single<Integer> deleteFriend(String uid) {
        return mAddUserRepository.deleteFriend(uid);
    }
}
