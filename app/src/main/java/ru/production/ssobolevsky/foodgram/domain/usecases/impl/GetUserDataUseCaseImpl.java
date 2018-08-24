package ru.production.ssobolevsky.foodgram.domain.usecases.impl;

import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.repositories.ProfileRepository;
import ru.production.ssobolevsky.foodgram.domain.usecases.GetUserDataUseCase;
import ru.production.ssobolevsky.foodgram.domain.util.ActionButtons;

/**
 * Created by pro on 14.07.2018.
 */

public class GetUserDataUseCaseImpl implements GetUserDataUseCase {

    private ProfileRepository mProfileRepository;



    private User getUserState(User currentUser, User selectedUser) {
        int action = 1;
        if (currentUser.getUid().equals(selectedUser.getUid())) {
            selectedUser.setAction(ActionButtons.ACTION_EDIT);
        } else if (selectedUser.getContacts() != null
                && selectedUser.getContacts().containsKey(currentUser.getUid())
                && selectedUser.getContacts().get(currentUser.getUid()).equals(false)) {
            selectedUser.setAction(ActionButtons.ACTION_ACCEPT);
        } else if (currentUser.getContacts() != null
                && currentUser.getContacts().containsKey(selectedUser.getUid())
                && currentUser.getContacts().get(selectedUser.getUid()).equals(false)) {
            selectedUser.setAction(ActionButtons.ACTION_CANCEL);
        } else if (currentUser.getContacts() == null || selectedUser.getContacts() == null
                ||(!currentUser.getContacts().containsKey(selectedUser.getUid())
                && !selectedUser.getContacts().containsKey(currentUser.getUid()))) {
            selectedUser.setAction(ActionButtons.ACTION_ADD);
        } else if (currentUser.getContacts() != null
                && currentUser.getContacts().containsKey(selectedUser.getUid())
                && currentUser.getContacts().get(selectedUser.getUid()).equals(true)) {
            selectedUser.setAction(ActionButtons.ACTION_DELETE);
        }
        return selectedUser;
    }


    public GetUserDataUseCaseImpl(ProfileRepository profileRepository) {
        mProfileRepository = profileRepository;
    }

    @Override
    public Single<User> getUserData(String uid) {
        return mProfileRepository.getUserProfileData(uid)
                .map(users -> {
                    User currentUser = users.get(0);
                    User selectedUser = users.get(1);
                    User user = getUserState(currentUser, selectedUser);
                    getFriendsCount(user);
                    return user;
                });
    }

    /**
     * Get friends count from user contacts. If contact value is true then count increments.
     * @param user - selected user.
     */
    private void getFriendsCount(User user) {
        int size = 0;
        if (user.getContacts() != null) {
            for (Boolean bool : user.getContacts().values()) {
                if (bool) {
                    size++;
                }
            }
            user.setFriendSize(size);
        }
    }

    @Override
    public void setUserImage(String uri) {
        mProfileRepository.setUserImage(uri);
    }

    @Override
    public Single<String> getUserImage(String uid) {
        return mProfileRepository.getUserImage(uid);
    }

}
