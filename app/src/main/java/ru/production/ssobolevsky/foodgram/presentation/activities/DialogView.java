package ru.production.ssobolevsky.foodgram.presentation.activities;

import java.util.List;

import ru.production.ssobolevsky.foodgram.domain.models.Message;

public interface DialogView {
    /**
     * Method to show {@param data} into recycler view of messages.
     * @param data - list of messages.
     */
    void showDialog(List<Message> data);

    void showProgress();

    void hideProgress();

}
