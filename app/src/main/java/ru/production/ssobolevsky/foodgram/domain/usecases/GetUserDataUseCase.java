package ru.production.ssobolevsky.foodgram.domain.usecases;


import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 14.07.2018.
 */

public interface GetUserDataUseCase {
    /**
     * Get full user data by user uid.
     * @param uid - uid of selected user.
     * @return list of two users.(current and selected)
     */
    Single<User> getUserData(String uid);
    /**
     * Set image for current user.
     * @param uri - uri of image.
     */
    void setUserImage(String uri);
    /**
     * Get image of selected user.
     * @param uid - uid of selected user.
     */
    Single<String> getUserImage(String uid);

}
