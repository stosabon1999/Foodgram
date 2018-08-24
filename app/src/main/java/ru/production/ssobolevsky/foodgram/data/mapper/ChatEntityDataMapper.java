package ru.production.ssobolevsky.foodgram.data.mapper;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ru.production.ssobolevsky.foodgram.data.models.ChatEntity;
import ru.production.ssobolevsky.foodgram.domain.models.Chat;

public class ChatEntityDataMapper {

    /**
     * Map chat entities from db to chats. Convert timestamp to correct date.
     * @param chatEntities - list of chat entities.
     * @return list of chats.
     */
    public List<Chat> transformChats(List<ChatEntity> chatEntities) {
        List<Chat> chats = new ArrayList<>();
        for (ChatEntity chatEntity : chatEntities) {
            Date d = new Date(chatEntity.getTimeStamp());
            SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
            String time = formatDate.format(d);
            Chat chat = null;
            if (chatEntity != null) {
                chat = new Chat(chatEntity.getLastMessage(),
                        chatEntity.getUid(),
                        time,
                        chatEntity.getUserUid(),
                        chatEntity.getSelectedUserUid(),
                        chatEntity.getUserName());
            }
            chats.add(chat);
        }

        return chats;
    }
}
