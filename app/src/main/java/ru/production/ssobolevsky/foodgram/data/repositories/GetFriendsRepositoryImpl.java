package ru.production.ssobolevsky.foodgram.data.repositories;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.mapper.UserEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.repositories.GetFriendsRepository;

/**
 * Created by pro on 19.07.2018.
 */

public class GetFriendsRepositoryImpl implements GetFriendsRepository {
    /**
     * Method to search users by name in database and add them to list.
     * If username contains name, then user will be added to list.
     * E.g. if name equals "ani" and username in database equals "Stanislav" then user will be added to list.
     * Searching is case-sensitive.
     * @param name - name what you want to search.
     */
    @Override
    public Single<List<User>> searchUserByName(final String name) {

        return Single.create(emitter -> MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.USERS_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<UserEntity> userEntities = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            UserEntity userEntity = snapshot.getValue(UserEntity.class);
                            if (userEntity.getName().contains(name)) {
                                userEntities.add(userEntity);
                            }
                        }
                        emitter.onSuccess(new UserEntityDataMapper().transformUsers(userEntities));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }));
    }
    /**
     * Method to get friends of selected users from database.
     * @param uid- uid of selected user.
     */
    @Override
    public Single<List<User>> getFriends(String uid) {
       return Single.create(emitter -> MyFirebaseData.getFirebaseDatabaseReference()
               .child(MyFirebaseData.USERS_TABLE)
               .addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               DataSnapshot newSnapshot = dataSnapshot.child(uid)
                       .child(MyFirebaseData.CONTACTS_TABLE);
               final List<UserEntity> userEntities = new ArrayList<>();
               for (DataSnapshot childSnapshot : newSnapshot.getChildren()) {
                   if (childSnapshot.getValue().equals(true)) {
                       String uid = childSnapshot.getKey();
                       userEntities.add(dataSnapshot.child(uid).getValue(UserEntity.class));
                   }
               }
               emitter.onSuccess(new UserEntityDataMapper().transformUsers(userEntities));
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       }));
    }

}
