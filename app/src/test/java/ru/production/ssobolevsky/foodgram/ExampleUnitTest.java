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
import org.mockito.Mockito;

import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.mapper.UserEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.repositories.ProfileRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.repositories.ProfileRepository;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Context mContext;
    @Before
    public void createDb() {
        mContext = Mockito.mock(Context.class);
        FirebaseApp.initializeApp(mContext.getApplicationContext());
    }


    @Test
    public void getUserCorrect() {
        ProfileRepository.CallBack callback = new ProfileRepository.CallBack() {
            @Override
            public void onUserDataLoaded(User currentUser, User selectedUser) {

            }

            @Override
            public void onError() {

            }
        };
        ProfileRepository repository = new ProfileRepositoryImpl(new UserEntityDataMapper());
        repository.getUserProfileData(callback, "1Ym6LEocshO5FKVHll1amFuQgiS2");
    }
}