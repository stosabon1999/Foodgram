package ru.production.ssobolevsky.foodgram.presentation.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.mapper.UserEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.repositories.AddUserRepositoryImpl;
import ru.production.ssobolevsky.foodgram.data.repositories.ProfileRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.usecases.impl.AddUserUseCaseImpl;
import ru.production.ssobolevsky.foodgram.domain.usecases.impl.GetUserDataUseCaseImpl;
import ru.production.ssobolevsky.foodgram.presentation.activities.DialogActivity;
import ru.production.ssobolevsky.foodgram.presentation.activities.FullScreenActivity;
import ru.production.ssobolevsky.foodgram.presentation.activities.MainActivity;
import ru.production.ssobolevsky.foodgram.presentation.activities.FriendsActivity;
import ru.production.ssobolevsky.foodgram.presentation.presenters.ProfilePresenter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by pro on 19.06.2018.
 */

public class ProfileFragment extends Fragment implements ProfileView {

    private static final int GALLERY_REQUEST = 10001;

    private Toolbar mToolbar;
    private ImageView mToolbarImage;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LinearLayout mLinearLayoutFriends;
    private Button mAddFriendButton;
    private Button mEditProfileButton;
    private Button mCancelRequestButton;
    private Button mAcceptRequestButton;
    private Button mDeleteFriendButton;
    private FloatingActionButton mFloatingActionButton;
    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout;
    private TextView mFriendsCountTextView;

    private ProfilePresenter mPresenter;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(Bundle bundle) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initListeners();
    }

    private void init(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mToolbarImage = view.findViewById(R.id.profile_back_image);
        mCollapsingToolbarLayout = view.findViewById(R.id.profile_collapsing);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        mFloatingActionButton = view.findViewById(R.id.profile_fab);
        mLinearLayoutFriends = view.findViewById(R.id.profile_ll_friends);
        mAddFriendButton = view.findViewById(R.id.btn_add_friend);
        mEditProfileButton = view.findViewById(R.id.btn_edit_profile);
        mCancelRequestButton = view.findViewById(R.id.btn_cancel_request);
        mAcceptRequestButton = view.findViewById(R.id.btn_accept_request);
        mDeleteFriendButton = view.findViewById(R.id.btn_delete_friend);
        mProgressBar = view.findViewById(R.id.pb_profile);
        mLinearLayout = view.findViewById(R.id.linear_profile);
        mFriendsCountTextView = view.findViewById(R.id.profile_tv_friends);
        mPresenter = new ProfilePresenter(new GetUserDataUseCaseImpl(new ProfileRepositoryImpl(new UserEntityDataMapper())),
                new AddUserUseCaseImpl(new AddUserRepositoryImpl()));
        mPresenter.attachView(this);
        mPresenter.showUserProfile(getArguments().getString(MainActivity.UID));
        mPresenter.getUserImage(getArguments().getString(MainActivity.UID));
    }

    private void initListeners() {
        mToolbarImage.setOnClickListener(new OnButtonImageClickListener());
        mLinearLayoutFriends.setOnClickListener(new OnLinearLayoutFriendsClickListener());
        mAddFriendButton.setOnClickListener(new OnButtonAddFriendClickListener());
        mCancelRequestButton.setOnClickListener(new OnButtonCancelRequestClickListener());
        mFloatingActionButton.setOnClickListener(new OnButtonSendMessageClickListener());
        mAcceptRequestButton.setOnClickListener(new OnButtonAcceptRequest());
        mDeleteFriendButton.setOnClickListener(new OnButtonDeleteFriendClickListener());
    }

    @Override
    public void showButtonDelete() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mDeleteFriendButton.setVisibility(View.VISIBLE);
        mAddFriendButton.setVisibility(View.GONE);
        mEditProfileButton.setVisibility(View.GONE);
        mCancelRequestButton.setVisibility(View.GONE);
        mAcceptRequestButton.setVisibility(View.GONE);
    }

    @Override
    public void showButtonEdit() {
        mFloatingActionButton.setVisibility(View.GONE);
        mDeleteFriendButton.setVisibility(View.GONE);
        mAddFriendButton.setVisibility(View.GONE);
        mEditProfileButton.setVisibility(View.VISIBLE);
        mCancelRequestButton.setVisibility(View.GONE);
        mAcceptRequestButton.setVisibility(View.GONE);
    }

    @Override
    public void showButtonAccept() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mDeleteFriendButton.setVisibility(View.GONE);
        mAddFriendButton.setVisibility(View.GONE);
        mEditProfileButton.setVisibility(View.GONE);
        mCancelRequestButton.setVisibility(View.GONE);
        mAcceptRequestButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showButtonCancel() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mDeleteFriendButton.setVisibility(View.GONE);
        mAddFriendButton.setVisibility(View.GONE);
        mEditProfileButton.setVisibility(View.GONE);
        mCancelRequestButton.setVisibility(View.VISIBLE);
        mAcceptRequestButton.setVisibility(View.GONE);
    }

    @Override
    public void showButtonAdd() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mDeleteFriendButton.setVisibility(View.GONE);
        mAddFriendButton.setVisibility(View.VISIBLE);
        mEditProfileButton.setVisibility(View.GONE);
        mCancelRequestButton.setVisibility(View.GONE);
        mAcceptRequestButton.setVisibility(View.GONE);
    }

    private void setUserImage() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK);
        pickPhotoIntent.setType("image/*");
        startActivityForResult(pickPhotoIntent, GALLERY_REQUEST);
    }

    @Override
    public void loadImage(String uri) {
        Picasso.get()
                .load(Uri.parse(uri))
                .into(mToolbarImage);
    }

    @Override
    public void showProgress() {
        mLinearLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLinearLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_REQUEST :
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    mPresenter.addUserImage(selectedImage);
                }
        }
    }

    @Override
    public void showUserData(User user) {
        mCollapsingToolbarLayout.setTitle(user.getName());
        if (user.getContacts() == null) {
            mFriendsCountTextView.setText(R.string.zero_friends);
        } else {
            mFriendsCountTextView.setText(user.getFriendSize() + " " + getString(R.string.friends_size));
        }

    }

    /**
     * Show dialog when clicked to image of user.
     */
    private void showAlertDialog() {
        final CharSequence[] items = {getString(R.string.open_photo),getString(R.string.edit_photo)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(R.string.photo)
                .setItems(items, (dialog, item) -> {
            if (item == 0) {
                startActivity(FullScreenActivity.newIntent(getActivity(), getArguments().getString(MainActivity.UID)));
            }
            if (item == 1) {
                setUserImage();
            }
        }).create().show();
    }
    /**
     * When {@link #mLinearLayoutFriends} clicks then {@link FriendsActivity} starts.
     */
    private class OnLinearLayoutFriendsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(FriendsActivity.newIntent(getActivity(), getArguments().getString(MainActivity.UID)));
        }
    }

    /**
     * When {@link #mAddFriendButton} is clicked then friend is added. {@link ProfilePresenter#onButtonAddClick(String)}
     */
    private class OnButtonAddFriendClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mPresenter.onButtonAddClick(getArguments().getString(MainActivity.UID));
        }
    }
    /**
     * When {@link #mCancelRequestButton} is clicked then request of friendship cancels. {@link ProfilePresenter#onButtonCancelClick(String)}
     */
    private class OnButtonCancelRequestClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mPresenter.onButtonCancelClick(getArguments().getString(MainActivity.UID));
        }
    }
    /**
     * When {@link #mFloatingActionButton} is clicked then {@link DialogActivity} starts.
     */
    private class OnButtonSendMessageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString(DialogActivity.USER_UID, getArguments().getString(MainActivity.UID));
            bundle.putString(DialogActivity.USER_NAME, mCollapsingToolbarLayout.getTitle().toString());
            startActivity(DialogActivity.newInstance(getContext(), bundle));
        }
    }
    /**
     * When {@link #mAcceptRequestButton} is clicked then request of friendship accepts. {@link ProfilePresenter#onButtonAddClick(String)}
     */
    private class OnButtonAcceptRequest implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mPresenter.onButtonAcceptClick(getArguments().getString(MainActivity.UID));
        }
    }
    /**
     * When {@link #mDeleteFriendButton} is clicked then friend deletes. {@link ProfilePresenter#onButtonDeleteClick(String)}
     */
    private class OnButtonDeleteFriendClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mPresenter.onButtonDeleteClick(getArguments().getString(MainActivity.UID));
        }
    }
    /**
     * When {@link #mToolbarImage} is clicked then alert dialog shows. {@link #showAlertDialog()}
     */
    private class OnButtonImageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (getArguments().getString(MainActivity.UID).equals(MyFirebaseData.getFirebaseUserUid())){
                showAlertDialog();
            } else {
                startActivity(FullScreenActivity.newIntent(getActivity(), getArguments().getString(MainActivity.UID)));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
