package ru.production.ssobolevsky.foodgram.data.repositories;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.mapper.UserEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.domain.repositories.GetFriendsRepository;

/**
 * Created by pro on 19.07.2018.
 */

public class GetFriendsRepositoryImpl implements GetFriendsRepository {

    @Override
    public void searchUserByName(final GetFriendsRepository.CallBack callBack, final String name) {
        MyFirebaseData.getFirebaseDatabaseReference().child(MyFirebaseData.USERS_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserEntity> userEntities = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserEntity userEntity = snapshot.getValue(UserEntity.class);
                    if (userEntity.getName().contains(name)) {
                        userEntities.add(userEntity);
                    }
                    //mAdapter.setData(mUserEntities);
                }
                callBack.onUsersFounded(new UserEntityDataMapper().transformUsers(userEntities));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getFriends() {

    }
}
