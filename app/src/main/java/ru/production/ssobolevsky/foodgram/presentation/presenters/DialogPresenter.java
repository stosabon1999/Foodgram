package ru.production.ssobolevsky.foodgram.presentation.presenters;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.production.ssobolevsky.foodgram.domain.usecases.ChatUseCase;
import ru.production.ssobolevsky.foodgram.presentation.activities.DialogView;
import ru.production.ssobolevsky.foodgram.presentation.presenters.base.BasePresenter;

public class DialogPresenter extends BasePresenter<DialogView> {

    private ChatUseCase mChatUseCase;

    public DialogPresenter(ChatUseCase chatUseCase) {
        mChatUseCase = chatUseCase;
    }

    /**
     * Send message to user by user id.
     * @param message - text of message.
     * @param selectedUid - selected user.
     */
    public void sendMessageByUserUid(String message, String selectedUid) {
        mChatUseCase.sendMessageByUserUid(message, selectedUid);
    }
    /**
     * Send message to user by chat id.
     * @param message - text of message.
     * @param chatUid - chat uid.
     */
    public void sendMessageByChatUid(String message, String chatUid) {
        mChatUseCase.sendMessageByChatUid(message, chatUid);
    }

    /**
     * Get chat with user by user uid and show it.{@link DialogView#showDialog(List)}
     * @param userUid - user uid.
     */
    public void getDialogByUserId(String userUid, Long lastItem) {
        getMvpView().showProgress();
        mChatUseCase.getDialogByUserId(userUid, lastItem)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> {
                getMvpView().showDialog(list);
                getMvpView().hideProgress();
                });
    }
    /**
     * Get chat with user by chat uid and show it.{@link DialogView#showDialog(List)}
     * @param chatUid - chat uid.
     */
    public void getDialogByChatId(String chatUid, Long lastItem) {
        getMvpView().showProgress();
        mChatUseCase.getDialogByChatId(chatUid, lastItem)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    getMvpView().showDialog(list);
                    getMvpView().hideProgress();
                });
    }
}
