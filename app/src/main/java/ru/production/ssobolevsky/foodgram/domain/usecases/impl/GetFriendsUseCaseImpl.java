package ru.production.ssobolevsky.foodgram.domain.usecases.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.mapper.UserEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.data.repositories.GetFriendsRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.repositories.GetFriendsRepository;
import ru.production.ssobolevsky.foodgram.domain.usecases.GetFriendsUseCase;

/**
 * Created by pro on 19.07.2018.
 */

public class GetFriendsUseCaseImpl implements GetFriendsUseCase {

    private GetFriendsRepository mGetFriendsRepository;

    public GetFriendsUseCaseImpl(GetFriendsRepository getFriendsRepository) {
        mGetFriendsRepository = getFriendsRepository;
    }
    @Override
    public Single<List<User>> searchUserByName(String name) {
        return mGetFriendsRepository.searchUserByName(name);
    }
    @Override
    public Single<List<User>> getFriends(String uid) {
        return mGetFriendsRepository.getFriends(uid);
    }

}
