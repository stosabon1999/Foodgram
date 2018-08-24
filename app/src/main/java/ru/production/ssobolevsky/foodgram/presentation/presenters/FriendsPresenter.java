package ru.production.ssobolevsky.foodgram.presentation.presenters;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.production.ssobolevsky.foodgram.data.repositories.GetFriendsRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.usecases.GetFriendsUseCase;
import ru.production.ssobolevsky.foodgram.domain.usecases.impl.GetFriendsUseCaseImpl;
import ru.production.ssobolevsky.foodgram.presentation.activities.FriendsView;
import ru.production.ssobolevsky.foodgram.presentation.presenters.base.BasePresenter;

/**
 * Created by pro on 14.07.2018.
 */

public class FriendsPresenter extends BasePresenter<FriendsView> {

    private GetFriendsUseCase mUseCase;

    public FriendsPresenter(GetFriendsUseCase useCase) {
        mUseCase = useCase;
    }

    /**
     * Search users by input text and show it.{@link FriendsView#showFriends(List)}
     * @param name - input text.
     */
    public void searchUserByName(String name) {
        getMvpView().showProgress();
        mUseCase.searchUserByName(name)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    if (users.size() == 0) {
                        getMvpView().showEmptyFriendsText();
                    } else {
                        getMvpView().showFriends(users);
                    }
                    getMvpView().hideProgress();
                });
    }

    /**
     * Get friends of selected user by uid and show it. {@link FriendsView#showFriends(List)}
     * @param uid - uid of user.
     */
    public void getFriends(String uid) {
        getMvpView().showProgress();
        mUseCase.getFriends(uid)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    if (users.size() == 0) {
                        getMvpView().showEmptyFriendsText();
                    } else {
                        getMvpView().showFriends(users);
                    }
                    getMvpView().hideProgress();
                });
    }

}
