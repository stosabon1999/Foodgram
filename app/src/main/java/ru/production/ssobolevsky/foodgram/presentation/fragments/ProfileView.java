package ru.production.ssobolevsky.foodgram.presentation.fragments;

import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 15.07.2018.
 */

public interface ProfileView {

    void showButtonDelete();

    void showButtonEdit();

    void showButtonAccept();

    void showButtonCancel();

    void showButtonAdd();
    /**
     * Method to show user data by {@param user}.
     * @param user - user.
     */
    void showUserData(User user);
    /**
     * Method to show user image by {@param uri}.
     * @param uri - uri of image.
     */
    void loadImage(String uri);

    void showProgress();

    void hideProgress();
}
