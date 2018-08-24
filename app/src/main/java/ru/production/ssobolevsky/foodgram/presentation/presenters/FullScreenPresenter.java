package ru.production.ssobolevsky.foodgram.presentation.presenters;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.production.ssobolevsky.foodgram.domain.usecases.GetUserDataUseCase;
import ru.production.ssobolevsky.foodgram.presentation.activities.FullScreenView;
import ru.production.ssobolevsky.foodgram.presentation.presenters.base.BasePresenter;

public class FullScreenPresenter extends BasePresenter<FullScreenView> {

    private GetUserDataUseCase mGetUserDataUseCase;

    public FullScreenPresenter(GetUserDataUseCase getUserDataUseCase) {
        mGetUserDataUseCase = getUserDataUseCase;
    }

    public void getFullScreenImage(String uid) {
            mGetUserDataUseCase.getUserImage(uid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(uri -> getMvpView().loadImage(uri));
    }
}
