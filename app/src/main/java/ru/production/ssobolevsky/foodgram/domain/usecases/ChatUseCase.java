package ru.production.ssobolevsky.foodgram.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.data.models.ChatEntity;
import ru.production.ssobolevsky.foodgram.data.models.MessageEntity;
import ru.production.ssobolevsky.foodgram.domain.models.Chat;
import ru.production.ssobolevsky.foodgram.domain.models.Message;

public interface DialogUseCase {

    void sendMessageByUserUid(String message, String selectedUid);
    void sendMessageByChatUid(String message, String chatUid);
    Single<List<Chat>> getDialogs();

    Single<List<Message>> getDialogByUserId(String userUid);
    Single<List<Message>> getDialogByChatId(String chatUid);
}
