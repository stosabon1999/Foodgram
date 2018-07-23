package ru.production.ssobolevsky.foodgram.presentation.presenters;

import java.util.List;

import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.usecases.GetFriendsUseCase;
import ru.production.ssobolevsky.foodgram.presentation.activities.FriendsView;
import ru.production.ssobolevsky.foodgram.presentation.presenters.base.BasePresenter;

/**
 * Created by pro on 14.07.2018.
 */

public class FriendsPresenter extends BasePresenter<FriendsView> {

    private FriendsView mView;
    private GetFriendsUseCase mUseCase;
    private GetFriendsUseCase.CallBack mCallBack = new GetFriendsUseCase.CallBack() {
        @Override
        public void onUsersFounded(List<User> users) {
            mView.showFoundedUsers(users);
        }

        @Override
        public void onError() {

        }
    };


    public void attachView(FriendsView view, GetFriendsUseCase useCase) {
        mView  = view;
        mUseCase = useCase;
    }
    
    public void detachView() {
        mView = null;
        mUseCase = null;
    }

    public void searchUserByName(String name) {
        mUseCase.searchUserByName(mCallBack, name);
    }

}
