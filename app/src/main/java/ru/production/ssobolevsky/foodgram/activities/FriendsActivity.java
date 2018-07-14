package ru.production.ssobolevsky.foodgram.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.adapters.FriendsAdapter;
import ru.production.ssobolevsky.foodgram.models.User;

public class FriendsActivity extends AppCompatActivity {

    private static final String TAG = "FriendsActivity";
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewFriends;
    private LinearLayoutManager mManager;
    private FriendsAdapter mAdapter;
    private List<User> mUsers;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        init();
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar_friends);
        setSupportActionBar(mToolbar);
        mRecyclerViewFriends = findViewById(R.id.rv_friends);
        mManager = new LinearLayoutManager(FriendsActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewFriends.setLayoutManager(mManager);
        mAdapter = new FriendsAdapter(FriendsActivity.this);
        mUsers = new ArrayList<>();
        //TODO setData to adapter to show friends
        mRecyclerViewFriends.setAdapter(mAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_friends, menu);
        MenuItem mSearch = menu.findItem(R.id.action_searxh_friends);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUsersByName(newText);
                return false;
            }
        });
        return true;
    }

    /**
     * Method to search users in database by name, add them into list and show it on the screen.
     * If username contains name, then user will be added to list.
     * E.g. if name equals "ani" and username in database equals "Stanislav" then user will be added to list.
     * @param name - name what you want to search.
     */
    private void searchUsersByName(final String name) {
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mUsers.size() > 0) {
                    mUsers.clear();
                }
                Log.wtf(TAG, "загружаю список пользователей из бд по имени: " + name);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user.getName().contains(name)) {
                        mUsers.add(user);
                    }
                    mAdapter.setData(mUsers);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf(TAG, "Пользователи из бд не получены");
            }
        });
    }

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, FriendsActivity.class);
        return intent;
    }
}
