package ru.production.ssobolevsky.foodgram.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.mapper.UserEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.repositories.ProfileRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.usecases.impl.GetUserDataUseCaseImpl;
import ru.production.ssobolevsky.foodgram.presentation.presenters.FullScreenPresenter;

public class FullScreenActivity extends AppCompatActivity implements FullScreenView {

    private static final String USER_UID = "UID";

    private PhotoView mImageView;
    private FullScreenPresenter mFullScreenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        init();
    }

    private void init() {
        mImageView = findViewById(R.id.iv_full_screen);
        mFullScreenPresenter = new FullScreenPresenter(new GetUserDataUseCaseImpl(new ProfileRepositoryImpl(new UserEntityDataMapper())));
        mFullScreenPresenter.attachView(this);
        mFullScreenPresenter.getFullScreenImage(getIntent().getStringExtra(USER_UID));
        getWindow().setStatusBarColor(Color.BLACK);
    }

    public static Intent newIntent(Context context, String uid) {
        Intent intent = new Intent(context, FullScreenActivity.class);
        intent.putExtra(USER_UID, uid);
        return intent;
    }

    @Override
    public void loadImage(String uri) {
        Picasso.get()
                .load(Uri.parse(uri))
                .into(mImageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFullScreenPresenter.detachView();
    }
}
