package ru.production.ssobolevsky.foodgram.data.mapper;

import java.util.ArrayList;
import java.util.List;

import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.domain.models.User;

/**
 * Created by pro on 15.07.2018.
 */

public class UserEntityDataMapper {

    public User transform(UserEntity userEntity) {
        User user = null;
        if (userEntity != null) {
            user = new User(userEntity.getEmail(),
                    userEntity.getName(),
                    userEntity.getUid(),
                    userEntity.getContacts());
        }
        return user;
    }

    public List<User> transformUsers(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            User user = null;
            if (userEntity != null) {
                user = new User(userEntity.getEmail(),
                        userEntity.getName(),
                        userEntity.getUid(),
                        userEntity.getContacts());
            }
            users.add(user);
        }

        return users;
    }

}
