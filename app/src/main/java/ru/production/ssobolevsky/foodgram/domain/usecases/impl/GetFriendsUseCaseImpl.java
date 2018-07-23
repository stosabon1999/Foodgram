package ru.production.ssobolevsky.foodgram.domain.usecases.impl;

import java.util.List;

import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.repositories.GetFriendsRepository;
import ru.production.ssobolevsky.foodgram.domain.usecases.GetFriendsUseCase;

/**
 * Created by pro on 19.07.2018.
 */

public class GetFriendsUseCaseImpl implements GetFriendsUseCase {

    private GetFriendsRepository mGetFriendsRepository;

    private GetFriendsUseCase.CallBack mCallBack;
    private GetFriendsRepository.CallBack mRepositoryCallback = new GetFriendsRepository.CallBack() {
        @Override
        public void onUsersFounded(List<User> users) {
            mCallBack.onUsersFounded(users);
        }

        @Override
        public void onError() {

        }
    };

    public GetFriendsUseCaseImpl(GetFriendsRepository getFriendsRepository) {
        mGetFriendsRepository = getFriendsRepository;
    }

    @Override
    public void searchUserByName(GetFriendsUseCase.CallBack callBack, String name) {
        mCallBack = callBack;
        mGetFriendsRepository.searchUserByName(mRepositoryCallback, name);
    }

    @Override
    public void getFriends() {

    }
}
