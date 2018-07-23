package ru.production.ssobolevsky.foodgram.domain.usecases.impl;

import android.util.Log;

import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.repositories.ProfileRepository;
import ru.production.ssobolevsky.foodgram.domain.usecases.GetUserDataUseCase;
import ru.production.ssobolevsky.foodgram.domain.util.ActionButtons;

/**
 * Created by pro on 14.07.2018.
 */

public class GetUserDataUseCaseImpl implements GetUserDataUseCase {

    private ProfileRepository mProfileRepository;
    private GetUserDataUseCase.Callback mCallback;
    private GetUserDataUseCase.ImageCallback mImageCallback;

    private final ProfileRepository.CallBack repositoryCallBack = new ProfileRepository.CallBack() {

        @Override
        public void onUserDataLoaded(User currentUser, User selectedUser) {
            int action = 1;
            if (currentUser.getUid().equals(selectedUser.getUid())) {
                action = ActionButtons.ACTION_EDIT;
            } else if (selectedUser.getContacts() != null
                    && selectedUser.getContacts().containsKey(currentUser.getUid())
                    && selectedUser.getContacts().get(currentUser.getUid()).equals(false)) {
                action = ActionButtons.ACTION_ACCEPT;
            } else if (currentUser.getContacts() != null
                    && currentUser.getContacts().containsKey(selectedUser.getUid())
                    && currentUser.getContacts().get(selectedUser.getUid()).equals(false)) {
                action = ActionButtons.ACTION_CANCEL;
            } else if (currentUser.getContacts() == null
                    || (!currentUser.getContacts().containsKey(selectedUser.getUid())
                    && !selectedUser.getContacts().containsKey(currentUser.getUid()))) {
                action = ActionButtons.ACTION_ADD;
            } else if (currentUser.getContacts() != null
                    && currentUser.getContacts().containsKey(selectedUser.getUid())
                    && currentUser.getContacts().get(selectedUser.getUid()).equals(true)) {
                action = ActionButtons.ACTION_SEND;
            }
            mCallback.onUserDataLoaded(selectedUser, action);
        }

        @Override
        public void onError() {

        }
    };

    private final ProfileRepository.ImageCallBack imageRepositoryCallBack = new ProfileRepository.ImageCallBack() {
        @Override
        public void onImageLoaded(String uri) {
            mImageCallback.onImageLoaded(uri);
        }

        @Override
        public void onError() {
            mImageCallback.onError();
        }
    };

    public GetUserDataUseCaseImpl(ProfileRepository profileRepository) {
        mProfileRepository = profileRepository;
    }

    @Override
    public void getUserData(GetUserDataUseCase.Callback callback, String uid) {
        mCallback = callback;
        mProfileRepository.getUserProfileData(repositoryCallBack, uid);
    }

    @Override
    public void setUserImage(String uri, String uid) {
        mProfileRepository.setUserImage(imageRepositoryCallBack, uri, uid);
    }

    @Override
    public void getUserImage(GetUserDataUseCase.ImageCallback callback, String uid) {
        mImageCallback = callback;
        mProfileRepository.getUserImage(imageRepositoryCallBack, uid);
    }

}
