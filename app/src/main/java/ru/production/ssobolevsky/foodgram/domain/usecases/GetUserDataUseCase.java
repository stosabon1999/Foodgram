package ru.production.ssobolevsky.foodgram.domain.usecases;


import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 14.07.2018.
 */

public interface GetUserDataUseCase {

    void getUserData(GetUserDataUseCase.Callback callback, String uid);
    void setUserImage(String uri, String uid);
    void getUserImage(GetUserDataUseCase.ImageCallback callback, String uid);

    interface Callback {
        void onUserDataLoaded(User user, int action);
        void onError();
    }

    interface ImageCallback {
        void onImageLoaded(String uri);
        void onError();
    }

}
