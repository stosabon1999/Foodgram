package ru.production.ssobolevsky.foodgram.presentation.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.presentation.activities.LoginActivity;
import ru.production.ssobolevsky.foodgram.presentation.activities.SignUpView;
import ru.production.ssobolevsky.foodgram.presentation.presenters.base.BasePresenter;

/**
 * Created by pro on 14.07.2018.
 */

public class SignUpPresenter extends BasePresenter<SignUpView> {

    public SignUpPresenter() {}

    /**
     * Create account with input data.
     * If data are correct then go to profile screen. {@link SignUpView#goToProfileScreen()}
     * If data are invalid then show toast. {@link SignUpView#showInvalidData()}
     * If response is not successful then show registration error toast. {@link SignUpView#registrationError()}
     * @param email - email.
     * @param password - password.
     * @param name - name.
     */
    public void createAccount(String email, String password, final String name) {
        if (email.isEmpty() || password.isEmpty()) {
            getMvpView().showInvalidData();
        } else {
            MyFirebaseData.getAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            UserEntity userEntity = new UserEntity(email, name, MyFirebaseData.getFirebaseUserUid(), new HashMap<>());
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                            database.child("users").child(MyFirebaseData.getFirebaseUserUid()).setValue(userEntity);
                            getMvpView().goToProfileScreen();
                        } else {
                            getMvpView().registrationError();
                        }
                    });
        }

    }
}
