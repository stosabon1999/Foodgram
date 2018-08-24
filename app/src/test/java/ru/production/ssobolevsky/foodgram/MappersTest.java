package ru.production.ssobolevsky.foodgram;

import android.app.Instrumentation;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.mapper.ChatEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.mapper.MessageEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.mapper.UserEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.models.ChatEntity;
import ru.production.ssobolevsky.foodgram.data.models.MessageEntity;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.data.repositories.ProfileRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.Chat;
import ru.production.ssobolevsky.foodgram.domain.models.Message;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.repositories.ProfileRepository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MappersTest {

    @Test
    public void mapChatEntitiesToChats() {
        List<ChatEntity> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ChatEntity chatEntity = new ChatEntity("message" + i, UUID.randomUUID().toString(), System.currentTimeMillis(), "user uid", "selected uid");
            chatEntity.setUserName("Ivab" + i);
            data.add(chatEntity);
        }
        List<Chat> newData = new ChatEntityDataMapper().transformChats(data);

        assertThat(data.size(), is(newData.size()));
        assertTrue(data.get(9).getLastMessage().equals(newData.get(9).getLastMessage()));
        assertTrue(data.get(0).getUid().equals(newData.get(0).getUid()));
        assertTrue(data.get(0).getUserUid().equals(newData.get(0).getUserUid()));
        assertTrue(data.get(0).getSelectedUserUid().equals(newData.get(0).getSelectedUserUid()));
        assertTrue(data.get(0).getUserName().equals(newData.get(0).getName()));

        Date d = new Date(data.get(0).getTimeStamp());
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
        String time = formatDate.format(d);

        assertTrue(newData.get(0).getDate().equals(time));
    }

    @Test
    public void mapMessageEntitiesToMessages() {
        List<MessageEntity> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MessageEntity messageEntity = new MessageEntity("message" + i, UUID.randomUUID().toString(), System.currentTimeMillis(), "user uid", "selected uid", "message uid");
            data.add(messageEntity);
        }
        List<Message> newData = new MessageEntityDataMapper().transformMessages(data);

        assertThat(data.size(), is(newData.size()));
        assertTrue(data.get(9).getMessage().equals(newData.get(9).getMessage()));
        assertTrue(data.get(0).getUid().equals(newData.get(0).getUid()));
        assertTrue(data.get(0).getSenderUid().equals(newData.get(0).getSenderUid()));
        assertTrue(data.get(0).getReceiverUid().equals(newData.get(0).getReceiverUid()));
        assertTrue(data.get(0).getTimestamp() == newData.get(0).getTimestamp());

        Date d = new Date(data.get(0).getTimestamp());
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
        String time = formatDate.format(d);

        assertTrue(newData.get(0).getTime().equals(time));
    }

    @Test
    public void mapUserEntitiesToUsers() {
        List<UserEntity> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Boolean> contacts = new HashMap<>();
            contacts.put(UUID.randomUUID().toString(), true);
            contacts.put(UUID.randomUUID().toString(), false);
            UserEntity userEntity = new UserEntity("email" + i, "ivan" + i, UUID.randomUUID().toString(), contacts);
            data.add(userEntity);
        }
        List<User> newData = new UserEntityDataMapper().transformUsers(data);

        assertThat(data.size(), is(newData.size()));
        assertTrue(data.get(9).getEmail().equals(newData.get(9).getEmail()));
        assertTrue(data.get(0).getUid().equals(newData.get(0).getUid()));
        assertTrue(data.get(0).getName().equals(newData.get(0).getName()));
        assertTrue(data.get(0).getContacts().equals(newData.get(0).getContacts()));
    }
}