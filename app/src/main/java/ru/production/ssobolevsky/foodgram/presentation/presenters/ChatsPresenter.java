package ru.production.ssobolevsky.foodgram.presentation.presenters;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.production.ssobolevsky.foodgram.domain.usecases.ChatUseCase;
import ru.production.ssobolevsky.foodgram.presentation.fragments.ChatsView;
import ru.production.ssobolevsky.foodgram.presentation.presenters.base.BasePresenter;

public class ChatsPresenter extends BasePresenter<ChatsView> {

    private ChatUseCase mUseCase;

    public ChatsPresenter(ChatUseCase useCase) {
        mUseCase = useCase;
    }

    /**
     * Get all chats of current user and show it. {@link ChatsView#showDialogs(List)}
     */
    public void getDialogs() {
        getMvpView().showProgress();
        mUseCase.getDialogs()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chats -> {
                    if (getMvpView() != null) {
                        if (chats.size() == 0) {
                            getMvpView().showEmptyDialogs();
                        } else {
                            getMvpView().showDialogs(chats);
                        }
                        getMvpView().hideProgress();
                    }
                });
    }
}
