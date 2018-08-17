package ru.production.ssobolevsky.foodgram.presentation.fragments;

import android.net.Uri;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.production.ssobolevsky.foodgram.data.repositories.AddUserRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.usecases.AddUserUseCase;
import ru.production.ssobolevsky.foodgram.domain.usecases.GetUserDataUseCase;
import ru.production.ssobolevsky.foodgram.domain.util.ActionButtons;
import ru.production.ssobolevsky.foodgram.presentation.presenters.base.BasePresenter;

/**
 * Created by pro on 15.07.2018.
 */

public class ProfilePresenter extends BasePresenter<ProfileView> {

    private GetUserDataUseCase mGetUserDataUseCase;
    private AddUserUseCase mAddUserUseCase;

    public ProfilePresenter(GetUserDataUseCase getUserDataUseCase, AddUserUseCase addUserUseCase) {
        mGetUserDataUseCase = getUserDataUseCase;
        mAddUserUseCase = addUserUseCase;
    }

    public void showUserProfile(String uid) {
            getMvpView().showProgress();
            mGetUserDataUseCase.getUserData(uid)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userState -> {
                        if (getMvpView() != null) {
                            getMvpView().showUserData(userState);
                            showButton(userState.getAction());
                            getMvpView().hideProgress();
                        }
                    });
    }

    public void onButtonAddClick(String uid) {
        mAddUserUseCase.addUser(uid)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action -> {
                    fastShowButton(action);
                });
    }

    public void onButtonCancelClick(String uid) {
        mAddUserUseCase.cancelRequest(uid)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action -> {
                    fastShowButton(action);
                });
    }

    public void onButtonAcceptClick(String uid) {
        mAddUserUseCase.acceptRequest(uid)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action -> {
                    fastShowButton(action);
                });
    }

    public void onButtonDeleteClick(String uid) {
        mAddUserUseCase.deleteFriend(uid)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action -> {
                    fastShowButton(action);
                });
    }

    private void fastShowButton(int action) {
        switch (action) {
            case AddUserRepositoryImpl.ACTION_ADD :
                getMvpView().showButtonCancel();
                break;
            case AddUserRepositoryImpl.ACTION_CANCEL :
                getMvpView().showButtonAdd();
                break;
            case AddUserRepositoryImpl.ACTION_ACCEPT :
                getMvpView().showButtonDelete();
                break;
            case AddUserRepositoryImpl.ACTION_DELETE :
                getMvpView().showButtonAccept();
                break;
        }
    }

    private void showButton(int action) {
        switch (action) {
            case ActionButtons.ACTION_ADD :
                getMvpView().showButtonAdd();
                break;
            case ActionButtons.ACTION_CANCEL :
                getMvpView().showButtonCancel();
                break;
            case ActionButtons.ACTION_ACCEPT :
                getMvpView().showButtonAccept();
                break;
            case ActionButtons.ACTION_EDIT :
                getMvpView().showButtonEdit();
                break;
            case ActionButtons.ACTION_DELETE :
                getMvpView().showButtonDelete();
                break;
        }
    }

    public void addUserImage(Uri selectedImage, String uid) {
        mGetUserDataUseCase.setUserImage(selectedImage.toString(), uid);
    }

    public void getUserImage(String uid) {
        mGetUserDataUseCase.getUserImage(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    if (getMvpView() != null) {
                        getMvpView().loadImage(uri);
                    }
                });
    }

}
