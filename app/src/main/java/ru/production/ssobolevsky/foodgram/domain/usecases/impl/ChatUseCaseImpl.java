package ru.production.ssobolevsky.foodgram.domain.usecases.impl;

import java.util.List;

import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.data.mapper.ChatEntityDataMapper;
import ru.production.ssobolevsky.foodgram.domain.models.Chat;
import ru.production.ssobolevsky.foodgram.domain.models.Message;
import ru.production.ssobolevsky.foodgram.domain.repositories.ChatRepository;
import ru.production.ssobolevsky.foodgram.domain.usecases.ChatUseCase;

public class ChatUseCaseImpl implements ChatUseCase {

    private ChatRepository mChatRepository;

    public ChatUseCaseImpl(ChatRepository chatRepository) {
        mChatRepository = chatRepository;
    }

    @Override
    public void sendMessageByUserUid(String message, String selectedUid) {
        mChatRepository.sendMessageByUserUid(message, selectedUid);
    }

    @Override
    public void sendMessageByChatUid(String message, String chatUid) {
        mChatRepository.sendMessageByChatUid(message, chatUid);
    }

    @Override
    public Single<List<Chat>> getDialogs() {
        return mChatRepository.getChats()
                .map(chatEntities -> new ChatEntityDataMapper().transformChats(chatEntities));
    }

    @Override
    public Single<List<Message>> getDialogByUserId(String userUid, Long lastItem) {
        return mChatRepository.getDialogByUserId(userUid, lastItem);
    }

    @Override
    public Single<List<Message>> getDialogByChatId(String chatUid, Long lastItem) {
       return mChatRepository.getDialogByChatId(chatUid, lastItem);
    }
}
