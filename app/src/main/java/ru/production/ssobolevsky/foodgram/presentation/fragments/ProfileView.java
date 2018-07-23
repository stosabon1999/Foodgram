package ru.production.ssobolevsky.foodgram.presentation.fragments;

import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 15.07.2018.
 */

public interface ProfileView {

    void showButtonSend();

    void showButtonEdit();

    void showButtonAccept();

    void showButtonCancel();

    void showButtonAdd();

    void showUserData(User user);

    void loadImage(String uri);
}
