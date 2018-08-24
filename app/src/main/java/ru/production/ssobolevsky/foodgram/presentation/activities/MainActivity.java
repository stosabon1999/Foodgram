package ru.production.ssobolevsky.foodgram.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.presentation.fragments.CategoriesFragment;
import ru.production.ssobolevsky.foodgram.presentation.fragments.ChatsFragment;
import ru.production.ssobolevsky.foodgram.presentation.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    public static final String UID = "UID";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Bundle bundle = new Bundle();
                        bundle.putString(UID, MyFirebaseData.getFirebaseUserUid());
                        loadFragment(ProfileFragment.newInstance(bundle));
                        return true;
                    case R.id.navigation_dashboard:
                        loadFragment(CategoriesFragment.newInstance());
                        return true;
                    case R.id.navigation_notifications:
                        loadFragment(ChatsFragment.newInstance());
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkUser();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Bundle bundle = new Bundle();
        if (getIntent().getStringExtra(UID) == null) {
            bundle.putString(UID, MyFirebaseData.getFirebaseUserUid());
        } else {
            bundle.putString(UID, getIntent().getStringExtra(UID));
        }
        loadFragment(ProfileFragment.newInstance(bundle));
    }

    /**
     * Method to load selected fragment into activity/
     * @param fragment - selected fragment.
     */
    private void loadFragment(Fragment fragment) {
        FragmentManager ft = getSupportFragmentManager();
        ft.beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();
    }

    /**
     * Method to check is user logged in.
     * If user is logged then load profile fragment {@link ProfileFragment}.
     * If user isn't logged in then return to login activity {@link LoginActivity}.
     */
    private void checkUser() {
        if (MyFirebaseData.getFirebaseUser() == null) {
            startActivity(LoginActivity.newInstance(MainActivity.this));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit :
                startActivity(LoginActivity.newInstance(MainActivity.this));
                MyFirebaseData.signOut();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public static Intent newIntent(Context context, String uid) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(UID, uid);
        return intent;
    }

}
