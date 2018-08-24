package ru.production.ssobolevsky.foodgram.presentation.activities;

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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.data.repositories.GetFriendsRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.usecases.impl.GetFriendsUseCaseImpl;
import ru.production.ssobolevsky.foodgram.presentation.adapters.FriendsAdapter;
import ru.production.ssobolevsky.foodgram.presentation.presenters.FriendsPresenter;

public class FriendsActivity extends AppCompatActivity implements FriendsView {

    private static final String UID = "UID";

    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewFriends;
    private LinearLayoutManager mManager;
    private FriendsAdapter mAdapter;
    private FriendsPresenter mPresenter;
    private ProgressBar mProgressBar;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        init();
    }

    private void init() {
        mTextView = findViewById(R.id.tv_friends);
        mToolbar = findViewById(R.id.toolbar_friends);
        mProgressBar = findViewById(R.id.pb_friends);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        mRecyclerViewFriends = findViewById(R.id.rv_friends);
        mManager = new LinearLayoutManager(FriendsActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewFriends.setLayoutManager(mManager);
        mAdapter = new FriendsAdapter();
        mRecyclerViewFriends.setAdapter(mAdapter);
        mPresenter = new FriendsPresenter(new GetFriendsUseCaseImpl(new GetFriendsRepositoryImpl()));
        mPresenter.attachView(this);
        mPresenter.getFriends(getIntent().getStringExtra(UID));
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
                mPresenter.searchUserByName(newText);
                return false;
            }
        });
        return true;
    }



    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mPresenter = null;
        super.onDestroy();
    }

    public static final Intent newIntent(Context context, String uid) {
        Intent intent = new Intent(context, FriendsActivity.class);
        intent.putExtra(UID, uid);
        return intent;
    }

    @Override
    public void showFriends(List<User> users) {
        mTextView.setVisibility(View.GONE);
        mAdapter.setData(users);
    }

    @Override
    public void showProgress() {
        mRecyclerViewFriends.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mRecyclerViewFriends.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyFriendsText() {
        mRecyclerViewFriends.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
    }
}
