package ru.production.ssobolevsky.foodgram.presentation.activities;

import java.util.List;

import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 19.07.2018.
 */

public interface FriendsView {

    void showFoundedUsers(List<User> users);

}
