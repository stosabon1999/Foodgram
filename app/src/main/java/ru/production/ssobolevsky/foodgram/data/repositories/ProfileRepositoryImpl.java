package ru.production.ssobolevsky.foodgram.data.repositories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ThumbnailUtils;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
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
    /**
     * Get full user data by user uid from database.
     * @param uid - uid of selected user.
     * @return list of two users.(current and selected)
     */
    @Override
    public Single<List<User>> getUserProfileData(final String uid) {
        return Single.create(emitter -> MyFirebaseData.getFirebaseDatabaseReference()
                .child(MyFirebaseData.USERS_TABLE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserEntity currentUser = dataSnapshot.child(MyFirebaseData.getFirebaseUserUid()).getValue(UserEntity.class);
                        UserEntity selectedUser = dataSnapshot.child(uid).getValue(UserEntity.class);
                        List<UserEntity> list = Arrays.asList(currentUser, selectedUser);
                        emitter.onSuccess(mUserEntityDataMapper.transformUsers(list));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }));
    }
    /**
     * Set image for current user. Add it to storage.
     * @param uri - uri of image.
     */
    @Override
    public void setUserImage(final String uri) {
        MyFirebaseData.getFirebaseStorage()
                .child(MyFirebaseData.getFirebaseUserUid())
                .putFile(Uri.parse(uri));
    }
    /**
     * Get image of selected user from storage
     * @param uid - uid of selected user.
     */
    @Override
    public Single<String> getUserImage(String uid) {
        return Single.create(emitter -> {
            MyFirebaseData.getFirebaseStorage()
                    .child(uid)
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> emitter.onSuccess(uri.toString()));
        });
    }
}
