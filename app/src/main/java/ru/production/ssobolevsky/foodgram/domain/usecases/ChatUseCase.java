package ru.production.ssobolevsky.foodgram.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.data.models.ChatEntity;
import ru.production.ssobolevsky.foodgram.data.models.MessageEntity;
import ru.production.ssobolevsky.foodgram.domain.models.Chat;
import ru.production.ssobolevsky.foodgram.domain.models.Message;

public interface ChatUseCase {
    /**
     * Send message to user by user uid.
     * @param message - new message.
     * @param selectedUid - user uid.
     */
    void sendMessageByUserUid(String message, String selectedUid);
    /**
     * Send message to user by chat uid.
     * @param message - new message.
     * @param chatUid - chat uid.
     */
    void sendMessageByChatUid(String message, String chatUid);
    /**
     * Get chats of current user.
     * @return list of chats
     */
    Single<List<Chat>> getDialogs();
    /**
     * Get chat with user by user uid. If user scrolled to the top then add next messages after timestamp.
     * @param userUid - uid of selected user.
     * @param lastItem - timestamp of top message.
     * @return list of messages between current user and selected user.
     */
    Single<List<Message>> getDialogByUserId(String userUid, Long lastItem);
    /**
     * Get chat with user by chat uid. If user scrolled to the top then add next messages after timestamp.
     * @param chatUid - uid of chat.
     * @param lastItem - timestamp of top message.
     * @return list of messages between current user and selected user.
     */
    Single<List<Message>> getDialogByChatId(String chatUid, Long lastItem);
}
