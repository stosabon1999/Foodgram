package ru.production.ssobolevsky.foodgram.data.repositories;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.mapper.UserEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.repositories.ProfileRepository;

/**
 * Created by pro on 14.07.2018.
 */

public class ProfileRepositoryImpl implements ProfileRepository {

    private final UserEntityDataMapper mUserEntityDataMapper;

    public ProfileRepositoryImpl(UserEntityDataMapper userEntityDataMapper) {
        mUserEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public void getUserProfileData(final ProfileRepository.CallBack callback, final String uid) {
        MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.USERS_TABLE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserEntity currentUser = dataSnapshot.child(MyFirebaseData.getFirebaseUserUid()).getValue(UserEntity.class);
                        UserEntity selectedUser = dataSnapshot.child(uid).getValue(UserEntity.class);
                        callback.onUserDataLoaded(mUserEntityDataMapper.transform(currentUser), mUserEntityDataMapper.transform(selectedUser));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("ErrorDataLayer", "Данные пользователя не выгружены с базы данных");
                    }
                });
    }

    @Override
    public void setUserImage(final ProfileRepository.ImageCallBack callback, final String uri, final String uid) {
        MyFirebaseData.getFirebaseStorage()
                .child(MyFirebaseData.getFirebaseUserUid())
                .putFile(Uri.parse(uri));
    }

    @Override
    public void getUserImage(final ProfileRepository.ImageCallBack callback, String uid) {
        MyFirebaseData.getFirebaseStorage()
                .child(uid)
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        callback.onImageLoaded(uri.toString());
                    }
                });
    }
}
