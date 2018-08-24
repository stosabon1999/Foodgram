package ru.production.ssobolevsky.foodgram.domain.usecases;

import io.reactivex.Single;

/**
 * Created by pro on 16.07.2018.
 */

public interface AddUserUseCase {
    /**
     * Add selected user to friends and return identifier of action by selected user uid.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    Single<Integer> addUser(String uid);
    /**
     * Cancel request of friendship with selected user and return identifier of action by selected user uid.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    Single<Integer> cancelRequest(String uid);
    /**
     * Accept request of friendship with selected user and return identifier of action by selected user uid.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    Single<Integer> acceptRequest(String uid);
    /**
     * Delete selected user from friends and return identifier of action by selected user uid.
     * @param uid - uid of selected user.
     * @return identifier of action.
     */
    Single<Integer> deleteFriend(String uid);

}
