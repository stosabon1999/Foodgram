package ru.production.ssobolevsky.foodgram.domain.usecases.impl;

import ru.production.ssobolevsky.foodgram.domain.repositories.AddUserRepository;
import ru.production.ssobolevsky.foodgram.domain.usecases.AddUserUseCase;

/**
 * Created by pro on 16.07.2018.
 */

public class AddUserUseCaseImpl implements AddUserUseCase {

    private AddUserRepository mAddUserRepository;
    private AddUserUseCase.Callback mCallback;

    public AddUserUseCaseImpl(AddUserRepository addUserRepository) {
        mAddUserRepository = addUserRepository;
    }

    private AddUserRepository.Callback mRepositoryCallback = new AddUserRepository.Callback() {
        @Override
        public void onRequestUpdated(int action) {
            mCallback.onRequestUpdated(action);
        }

        @Override
        public void onError() {

        }
    };

    @Override
    public void addUser(AddUserUseCase.Callback callback, String uid) {
        mCallback = callback;
        mAddUserRepository.addUser(mRepositoryCallback, uid);
    }

    @Override
    public void cancelRequest(AddUserUseCase.Callback callback, String uid) {
        mCallback = callback;
        mAddUserRepository.cancelRequest(mRepositoryCallback, uid);
    }

    @Override
    public void acceptRequest(AddUserUseCase.Callback callback, String uid) {
        mCallback = callback;
        mAddUserRepository.acceptRequest(mRepositoryCallback, uid);
    }
}
