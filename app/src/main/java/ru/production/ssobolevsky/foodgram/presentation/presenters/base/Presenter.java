package ru.production.ssobolevsky.foodgram.presentation.presenters.base;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface Presenter<V> {

    void attachView(V mvpView);

    void detachView();
}
