package ru.production.ssobolevsky.foodgram.presentation.fragments;

import java.util.List;

import ru.production.ssobolevsky.foodgram.domain.models.Chat;

public interface ChatsView {
    /**
     * Method to show chats of current user.
     * @param chats - list of chats.
     */
    void showDialogs(List<Chat> chats);

    void showProgress();

    void hideProgress();

    void showEmptyDialogs();
}
