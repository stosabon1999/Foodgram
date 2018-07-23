package ru.production.ssobolevsky.foodgram.presentation.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.data.mapper.UserEntityDataMapper;
import ru.production.ssobolevsky.foodgram.data.models.RecipeEntity;
import ru.production.ssobolevsky.foodgram.data.repositories.AddUserRepositoryImpl;
import ru.production.ssobolevsky.foodgram.data.repositories.ProfileRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.domain.usecases.impl.AddUserUseCaseImpl;
import ru.production.ssobolevsky.foodgram.domain.usecases.impl.GetUserDataUseCaseImpl;
import ru.production.ssobolevsky.foodgram.presentation.activities.MainActivity;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.presentation.activities.FriendsActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by pro on 19.06.2018.
 */

public class ProfileFragment extends Fragment implements ProfileView {

    private static final String TAG = "ProfileFragment";
    private static final int GALLERY_REQUEST = 1;

    private TextView mName;
    private ImageButton mImage;
    private ImageButton mImageButtonFriends;
    private Button mAddFriendButton;
    private Button mEditProfileButton;
    private Button mCancelRequestButton;
    private Button mSendMessageButton;
    private Button mAcceptRequestButton;

    private ProfilePresenter mPresenter;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(Bundle bundle) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
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
        mName = view.findViewById(R.id.tv_name);
        mImage = view.findViewById(R.id.cib_user_avatar);
        mImageButtonFriends = view.findViewById(R.id.cib_friends);
        mAddFriendButton = view.findViewById(R.id.btn_add_friend);
        mEditProfileButton = view.findViewById(R.id.btn_edit_profile);
        mCancelRequestButton = view.findViewById(R.id.btn_cancel_request);
        mSendMessageButton = view.findViewById(R.id.btn_send_message);
        mAcceptRequestButton = view.findViewById(R.id.btn_accept_request);

        mPresenter = new ProfilePresenter();
        mPresenter.attachView(this,
                new GetUserDataUseCaseImpl(new ProfileRepositoryImpl(new UserEntityDataMapper())),
                new AddUserUseCaseImpl(new AddUserRepositoryImpl()));
        mPresenter.showUserProfile(getArguments().getString(MainActivity.UID));
        mPresenter.getUserImage(getArguments().getString(MainActivity.UID));
    }

    private void initListeners() {
        mImageButtonFriends.setOnClickListener(new OnButtonFriendsClickListener());
        mAddFriendButton.setOnClickListener(new OnButtonAddFriendClickListener());
        mCancelRequestButton.setOnClickListener(new OnButtonCancelRequestClickListener());
        mSendMessageButton.setOnClickListener(new OnButtonSendMessageClickListener());
        mAcceptRequestButton.setOnClickListener(new OnButtonAcceptRequest());
        if (getArguments().getString(MainActivity.UID).equals(MyFirebaseData.getFirebaseUserUid())) {
            mImage.setOnClickListener(new OnButtonImageClickListener());
        }
    }

    @Override
    public void showButtonSend() {
        mAddFriendButton.setVisibility(View.GONE);
        mEditProfileButton.setVisibility(View.GONE);
        mSendMessageButton.setVisibility(View.VISIBLE);
        mCancelRequestButton.setVisibility(View.GONE);
        mAcceptRequestButton.setVisibility(View.GONE);
    }

    @Override
    public void showButtonEdit() {
        mAddFriendButton.setVisibility(View.GONE);
        mEditProfileButton.setVisibility(View.VISIBLE);
        mSendMessageButton.setVisibility(View.GONE);
        mCancelRequestButton.setVisibility(View.GONE);
        mAcceptRequestButton.setVisibility(View.GONE);
    }

    @Override
    public void showButtonAccept() {
        mAddFriendButton.setVisibility(View.GONE);
        mEditProfileButton.setVisibility(View.GONE);
        mSendMessageButton.setVisibility(View.GONE);
        mCancelRequestButton.setVisibility(View.GONE);
        mAcceptRequestButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showButtonCancel() {
        mAddFriendButton.setVisibility(View.GONE);
        mEditProfileButton.setVisibility(View.GONE);
        mSendMessageButton.setVisibility(View.GONE);
        mCancelRequestButton.setVisibility(View.VISIBLE);
        mAcceptRequestButton.setVisibility(View.GONE);
    }

    @Override
    public void showButtonAdd() {
        mAddFriendButton.setVisibility(View.VISIBLE);
        mEditProfileButton.setVisibility(View.GONE);
        mSendMessageButton.setVisibility(View.GONE);
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
                .resizeDimen(R.dimen.civ_size, R.dimen.civ_size)
                .into(mImage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GALLERY_REQUEST :
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    mPresenter.addUserImage(selectedImage, getArguments().getString(MainActivity.UID));
                }
        }
    }

    @Override
    public void showUserData(User user) {
        mName.setText(user.getName());
    }


    private class OnButtonFriendsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(FriendsActivity.newIntent(getActivity()));
        }
    }

    private class OnButtonAddFriendClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mPresenter.onButtonAddClick(getArguments().getString(MainActivity.UID));
        }
    }

    private class OnButtonCancelRequestClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mPresenter.onButtonCancelClick(getArguments().getString(MainActivity.UID));
        }
    }

    private class OnButtonSendMessageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class OnButtonAcceptRequest implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mPresenter.onButtonAcceptClick(getArguments().getString(MainActivity.UID));
        }
    }

    private class OnButtonImageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setUserImage();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }
}
