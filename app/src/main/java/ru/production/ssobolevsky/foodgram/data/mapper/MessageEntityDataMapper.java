package ru.production.ssobolevsky.foodgram.data.mapper;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.production.ssobolevsky.foodgram.data.models.MessageEntity;
import ru.production.ssobolevsky.foodgram.domain.models.Message;

public class MessageEntityDataMapper {

    /**
     * Map message entities from db to messages. Convert timestamp to correct date.
     * @param messageEntities - list of message entities.
     * @return list of messages.
     */
    public List<Message> transformMessages(List<MessageEntity> messageEntities) {
        List<Message> messages = new ArrayList<>();
        for (MessageEntity messageEntity : messageEntities) {
            Date d = new Date(messageEntity.getTimestamp());
            SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
            String time = formatDate.format(d);
            Message message = null;
            message = new Message(messageEntity.getMessage(),
                    messageEntity.getUid(),
                    time,
                    messageEntity.getSenderUid(),
                    messageEntity.getReceiverUid(),
                    messageEntity.getTimestamp());
            messages.add(message);
        }
        return messages;
    }

}
