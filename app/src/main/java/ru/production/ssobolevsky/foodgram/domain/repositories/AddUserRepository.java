package ru.production.ssobolevsky.foodgram.domain.repositories;

import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.domain.usecases.AddUserUseCase;

/**
 * Created by pro on 16.07.2018.
 */

public interface AddUserRepository {
    /**
     * Send request of friendship to selected user.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    Single<Integer> addUser(String uid);
    /**
     * Cancel request of friendship to selected user.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    Single<Integer> cancelRequest(String uid);
    /**
     * Accept request of friendship with selected user.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    Single<Integer> acceptRequest(String uid);
    /**
     * Delete selected user from friends.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    Single<Integer> deleteFriend(String uid);

}
