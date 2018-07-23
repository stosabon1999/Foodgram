package ru.production.ssobolevsky.foodgram.presentation.fragments;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import ru.production.ssobolevsky.foodgram.data.repositories.AddUserRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.usecases.AddUserUseCase;
import ru.production.ssobolevsky.foodgram.domain.usecases.GetUserDataUseCase;
import ru.production.ssobolevsky.foodgram.domain.util.ActionButtons;

/**
 * Created by pro on 15.07.2018.
 */

public class ProfilePresenter {

    private ProfileView mView;
    private GetUserDataUseCase mGetUserDataUseCase;
    private AddUserUseCase mAddUserUseCase;

    private final GetUserDataUseCase.Callback mCallback = new GetUserDataUseCase.Callback() {
        @Override
        public void onUserDataLoaded(User user, int action) {
            mView.showUserData(user);
            showButton(action);
        }

        @Override
        public void onError() {

        }
    };

    private final AddUserUseCase.Callback mAddUserCallback = new AddUserUseCase.Callback() {
        @Override
        public void onRequestUpdated(int action) {
            fastShowButton(action);
        }

        @Override
        public void onError() {

        }
    };

    private final GetUserDataUseCase.ImageCallback mImageCallback = new GetUserDataUseCase.ImageCallback() {
        @Override
        public void onImageLoaded(String uri) {
            mView.loadImage(uri);
        }

        @Override
        public void onError() {

        }
    };


    void attachView(ProfileView view, GetUserDataUseCase useCase, AddUserUseCase addUserUseCase) {
        mView  = view;
        mGetUserDataUseCase = useCase;
        mAddUserUseCase = addUserUseCase;
    }
    //TODO СПРОСИТЬ ЧТО ДЕЛАТЬ, НЕ УСПЕВАТЬ ПРОГРУЖАТЬСЯ
    void detachView() {
        mView = null;
        mGetUserDataUseCase = null;
    }

    public void showUserProfile(String uid) {
        mGetUserDataUseCase.getUserData(mCallback, uid);
    }

    public void onButtonAddClick(String uid) {
        mAddUserUseCase.addUser(mAddUserCallback, uid);
    }

    public void onButtonCancelClick(String uid) {
        mAddUserUseCase.cancelRequest(mAddUserCallback, uid);
    }

    public void onButtonAcceptClick(String uid) {
        mAddUserUseCase.acceptRequest(mAddUserCallback, uid);
    }

    private void fastShowButton(int action) {
        switch (action) {
            case AddUserRepositoryImpl.ACTION_ADD :
                mView.showButtonCancel();
                break;
            case AddUserRepositoryImpl.ACTION_CANCEL :
                mView.showButtonAdd();
                break;
            case AddUserRepositoryImpl.ACTION_ACCEPT :
                mView.showButtonSend();
                break;
        }
    }

    private void showButton(int action) {
        switch (action) {
            case ActionButtons.ACTION_ADD :
                mView.showButtonAdd();
                break;
            case ActionButtons.ACTION_CANCEL :
                mView.showButtonCancel();
                break;
            case ActionButtons.ACTION_ACCEPT :
                mView.showButtonAccept();
                break;
            case ActionButtons.ACTION_EDIT :
                mView.showButtonEdit();
                break;
            case ActionButtons.ACTION_SEND :
                mView.showButtonSend();
                break;
        }
    }

    public void addUserImage(Uri selectedImage, String uid) {
        mGetUserDataUseCase.setUserImage(selectedImage.toString(), uid);
    }

    public void getUserImage(String uid) {
        mGetUserDataUseCase.getUserImage(mImageCallback, uid);
    }
}
