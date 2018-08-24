package ru.production.ssobolevsky.foodgram.domain.usecases;


import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 19.07.2018.
 */

public interface GetFriendsUseCase {
    /**
     * Method to search users by name and add them to list.
     * @param name - name what you want to search.
     */
    Single<List<User>> searchUserByName(String name);
    /**
     * Method to get friends of selected users.
     * @param uid- uid of selected user.
     */
    Single<List<User>> getFriends(String uid);


}
