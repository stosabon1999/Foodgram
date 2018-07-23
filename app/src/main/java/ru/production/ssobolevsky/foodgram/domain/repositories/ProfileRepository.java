package ru.production.ssobolevsky.foodgram.domain.repositories;

import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 14.07.2018.
 */

public interface ProfileRepository {

    void getUserProfileData(final ProfileRepository.CallBack callback, final String uid);

    void setUserImage(final ProfileRepository.ImageCallBack callback, String uri, String uid);

    void getUserImage(final ProfileRepository.ImageCallBack callback, String uid);

    interface CallBack {
        void onUserDataLoaded(User currentUser, User selectedUser);
        void onError();
    }

    interface ImageCallBack {
        void onImageLoaded(String uri);
        void onError();
    }
}
