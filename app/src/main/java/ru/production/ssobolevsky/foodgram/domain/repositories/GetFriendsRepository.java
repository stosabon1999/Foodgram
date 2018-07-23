package ru.production.ssobolevsky.foodgram.domain.repositories;

import java.util.List;

import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 19.07.2018.
 */

public interface GetFriendsRepository {
    /**
     * Method to search users in database by name, add them into list and show it on the screen.
     * If username contains name, then user will be added to list.
     * E.g. if name equals "ani" and username in database equals "Stanislav" then user will be added to list.
     * @param name - name what you want to search.
     */
    void searchUserByName(GetFriendsRepository.CallBack callBack, String name);
    void getFriends();

    interface CallBack {
        void onUsersFounded(List<User> users);
        void onError();
    }

}
