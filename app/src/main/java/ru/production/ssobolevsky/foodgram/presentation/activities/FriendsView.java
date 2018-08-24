package ru.production.ssobolevsky.foodgram.presentation.activities;

import java.util.List;

import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 19.07.2018.
 */

public interface FriendsView {
    /**
     * Method to show friends or founded users in recycler view of friends.
     * @param users - list of users.
     */
    void showFriends(List<User> users);

    void showProgress();

    void hideProgress();

    void showEmptyFriendsText();

}
