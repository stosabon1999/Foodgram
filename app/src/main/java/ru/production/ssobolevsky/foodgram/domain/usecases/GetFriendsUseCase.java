package ru.production.ssobolevsky.foodgram.domain.usecases;


import java.util.List;

import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 19.07.2018.
 */

public interface GetFriendsUseCase {

    void searchUserByName(GetFriendsUseCase.CallBack callBack, String name);
    void getFriends();

    interface CallBack {
        void onUsersFounded(List<User> users);
        void onError();
    }

}
