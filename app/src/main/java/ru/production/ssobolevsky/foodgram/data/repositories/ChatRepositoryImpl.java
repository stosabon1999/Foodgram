package ru.production.ssobolevsky.foodgram.data.repositories;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.mapper.MessageEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.models.ChatEntity;
import ru.production.ssobolevsky.foodgram.data.models.MessageEntity;
import ru.production.ssobolevsky.foodgram.domain.models.Message;
import ru.production.ssobolevsky.foodgram.domain.repositories.ChatRepository;

import static ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData.MAX_MESSAGES;

public class ChatRepositoryImpl implements ChatRepository {
    /**
     * Send message to user by user id. Add message to database and update data of current chat.
     * @param message - new message.
     * @param selectedUid - uid of selected user.
     */
    @Override
    public void sendMessageByUserUid(String message, String selectedUid) {
        MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.CHATS_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean chatExists = false;
                ChatEntity currentChat = null;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ChatEntity chatEntity = child.getValue(ChatEntity.class);
                    if ((chatEntity.getUserUid().equals(MyFirebaseData.getFirebaseUserUid())
                            && chatEntity.getSelectedUserUid().equals(selectedUid))
                            || (chatEntity.getUserUid().equals(selectedUid)
                            && chatEntity.getSelectedUserUid().equals(MyFirebaseData.getFirebaseUserUid()))) {
                        chatExists = true;
                        currentChat = chatEntity;
                        break;
                    }
                }
                if (chatExists) {
                    Log.wtf("LOL", selectedUid);
                    updateChat(currentChat, message, selectedUid);
                    pushMessage(currentChat, message);
                } else {
                    ChatEntity chat = createChat(message, selectedUid);
                    pushMessage(chat, message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    /**
     * Send message to user by chat uid. Add message to database and update data of current chat.
     * @param message - new message.
     * @param chatUid - uid of selected chat.
     */
    @Override
    public void sendMessageByChatUid(String message, String chatUid) {
        MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.CHATS_TABLE)
                .child(chatUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ChatEntity chat = dataSnapshot.getValue(ChatEntity.class);
                        if (chat.getUserUid().equals(MyFirebaseData.getFirebaseUserUid())) {
                            updateChat(chat, message, chat.getSelectedUserUid());
                        } else {
                            updateChat(chat, message, chat.getUserUid());
                        }
                        pushMessage(chat, message);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    /**
     * Get chats of current user from database.
     * @return list of chats
     */
    @Override
    public Single<List<ChatEntity>> getChats() {
        return Single.create(emitter -> MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.CHATS_TABLE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<ChatEntity> chatEntities = new ArrayList<>();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            if (childSnapshot.child("selectedUserUid").getValue().equals(MyFirebaseData.getFirebaseUserUid())
                                    || childSnapshot.child("userUid").getValue().equals(MyFirebaseData.getFirebaseUserUid())) {
                                chatEntities.add(childSnapshot.getValue(ChatEntity.class));
                            }
                        }
                        getNamesForChats(chatEntities)
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(chats -> emitter.onSuccess(chats));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }));
    }
    /**
     * Get chat with user by user uid from database.
     * @param userUid - uid of selected user.
     * @return list of messages between current user and selected user.
     */
    @Override
    public Single<List<Message>> getDialogByUserId(String userUid, Long lastItem) {
        return Single.create(emitter -> MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.MESSAGES_TABLE)
                .orderByChild("timestamp")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<MessageEntity> list = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if ((snapshot.child("senderUid").getValue().equals(MyFirebaseData.getFirebaseUserUid())
                                    && snapshot.child("receiverUid").getValue().equals(userUid))
                                    ||(snapshot.child("receiverUid").getValue().equals(MyFirebaseData.getFirebaseUserUid())
                                    && snapshot.child("senderUid").getValue().equals(userUid))) {
                                MessageEntity messageEntity = snapshot.getValue(MessageEntity.class);
                                list.add(messageEntity);
                            }
                        }
                        emitter.onSuccess(new MessageEntityDataMapper().transformMessages(list));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }));
    }
    /**
     * Get chat with user by chat uid from database.
     * @param chatUid - uid of chat.
     * @return list of messages between current user and selected user.
     */
    @Override
    public Single<List<Message>> getDialogByChatId(String chatUid, Long lastItem) {
     return Single.create(emitter -> MyFirebaseData.getFirebaseDatabaseReference()
             .child(MyFirebaseData.MESSAGES_TABLE)
             .orderByChild("timestamp")
             .addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     List<MessageEntity> list = new ArrayList<>();
                     for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                         if (snapshot.child("uid").getValue().equals(chatUid)) {
                             MessageEntity messageEntity = snapshot.getValue(MessageEntity.class);
                             list.add(messageEntity);
                         }
                     }
                     emitter.onSuccess(new MessageEntityDataMapper().transformMessages(list));
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             }));
    }

    /**
     * Get names of users who has dialogs with current user by chat entities.
     * @param chatEntities - chat entities.
     * @return list of chat entities with chat names.
     */
    private Single<List<ChatEntity>> getNamesForChats(List<ChatEntity> chatEntities) {
        return Single.create(emitter -> MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.USERS_TABLE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (ChatEntity chatEntity : chatEntities) {
                            if (chatEntity.getUserUid().equals(MyFirebaseData.getFirebaseUserUid())) {
                                chatEntity.setUserName(String.valueOf(dataSnapshot.child(chatEntity.getSelectedUserUid()).child("name").getValue()));
                            } else {
                                chatEntity.setUserName(String.valueOf(dataSnapshot.child(chatEntity.getUserUid()).child("name").getValue()));
                            }
                        }
                        emitter.onSuccess(chatEntities);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }));
    }

    /**
     * Update chat. Invokes when message sended.
     * @param currentChat - current chat.
     * @param message - new message.
     * @param selectedUid - user who receives message.
     */
    private void updateChat(ChatEntity currentChat, String message, String selectedUid) {
        DatabaseReference chatRef = MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.CHATS_TABLE)
                .child(currentChat.getUid());
        chatRef.child("lastMessage").setValue(message);
        chatRef.child("timeStamp").setValue(new Timestamp(System.currentTimeMillis()).getTime());
        chatRef.child("userUid").setValue(MyFirebaseData.getFirebaseUserUid());
        chatRef.child("selectedUserUid").setValue(selectedUid);

    }

    /**
     * Create chat. Invokes when the first message between two users sends.
     * @param message - first message.
     * @param selectedUid - user who receives message.
     * @return chat entity.
     */
    private ChatEntity createChat(String message, String selectedUid) {
        DatabaseReference chatRef = MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.CHATS_TABLE).push();
        ChatEntity chat = new ChatEntity(message,
                chatRef.getKey(),
                new Timestamp(System.currentTimeMillis()).getTime(),
                MyFirebaseData.getFirebaseUserUid(),
                selectedUid
        );
                MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.CHATS_TABLE)
                .child(chatRef.getKey())
                .setValue(chat);
        return chat;
    }

    /**
     * Push message to db when it send.
     * @param chat - current chat.
     * @param message - new message.
     */
    private void pushMessage(ChatEntity chat, String message) {
        DatabaseReference reference = MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.MESSAGES_TABLE)
                .push();
        reference.setValue(new MessageEntity(message,
                chat.getUid(),
                new Timestamp(System.currentTimeMillis()).getTime(),
                MyFirebaseData.getFirebaseUserUid(),
                chat.getSelectedUserUid(), reference.getKey()));
    }
}

