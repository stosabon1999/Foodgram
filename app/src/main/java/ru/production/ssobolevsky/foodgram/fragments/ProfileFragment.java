package ru.production.ssobolevsky.foodgram.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.activities.MainActivity;
import ru.production.ssobolevsky.foodgram.models.User;
import ru.production.ssobolevsky.foodgram.activities.FriendsActivity;

/**
 * Created by pro on 19.06.2018.
 */

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private TextView mName;
    private ImageButton mImage;
    private ImageButton mImageButtonFriends;
    private Button mAddFriendButton;
    private Button mEditProfileButton;
    private Button mCancelRequestButton;
    private Button mSendMessageButton;
    private Button mAcceptRequestButton;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(Bundle bundle) {
        Log.d(TAG, "Profile Fragment запущен");
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
        mName = view.findViewById(R.id.tv_name);
        mImage = view.findViewById(R.id.cib_user_avatar);
        mImageButtonFriends = view.findViewById(R.id.cib_friends);
        mAddFriendButton = view.findViewById(R.id.btn_add_friend);
        mEditProfileButton = view.findViewById(R.id.btn_edit_profile);
        mCancelRequestButton = view.findViewById(R.id.btn_cancel_request);
        mSendMessageButton = view.findViewById(R.id.btn_send_message);
        mAcceptRequestButton = view.findViewById(R.id.btn_accept_request);
        init();
        initListeners();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        final String uid = getArguments().getString(MainActivity.UID);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.child(mAuth.getCurrentUser().getUid()).getValue(User.class);
                User selectedUser = dataSnapshot.child(uid).getValue(User.class);
                updateButtons(currentUser, selectedUser);
                mName.setText(selectedUser.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Имя пользователя не выгружено с базы данных");
            }
        });
    }

    private void initListeners() {
        mImageButtonFriends.setOnClickListener(new OnButtonFriendsClickListener());
        mAddFriendButton.setOnClickListener(new OnButtonAddFriendClickListener());
        mCancelRequestButton.setOnClickListener(new OnButtonCancelRequestClickListener());
        mSendMessageButton.setOnClickListener(new OnButtonSendMessageClickListener());
        mAcceptRequestButton.setOnClickListener(new OnButtonAcceptRequest());
    }

    private void updateButtons(User currentUser, User selectedUser) {
        if (currentUser.getUid().equals(selectedUser.getUid())) {
            mAddFriendButton.setVisibility(View.GONE);
            mEditProfileButton.setVisibility(View.VISIBLE);
            mSendMessageButton.setVisibility(View.GONE);
            mCancelRequestButton.setVisibility(View.GONE);
            mAcceptRequestButton.setVisibility(View.GONE);
            Log.wtf(TAG, "Мой профиль");
        } else if (selectedUser.getContacts() != null
                && selectedUser.getContacts().containsKey(currentUser.getUid())
                && selectedUser.getContacts().get(currentUser.getUid()).equals(false)) {
            mAddFriendButton.setVisibility(View.GONE);
            mEditProfileButton.setVisibility(View.GONE);
            mSendMessageButton.setVisibility(View.GONE);
            mCancelRequestButton.setVisibility(View.GONE);
            mAcceptRequestButton.setVisibility(View.VISIBLE);
            Log.wtf(TAG, "Принять заявку");
        } else if (currentUser.getContacts() != null
                && currentUser.getContacts().containsKey(selectedUser.getUid())
                && currentUser.getContacts().get(selectedUser.getUid()).equals(false)) {
            mAddFriendButton.setVisibility(View.GONE);
            mEditProfileButton.setVisibility(View.GONE);
            mSendMessageButton.setVisibility(View.GONE);
            mCancelRequestButton.setVisibility(View.VISIBLE);
            mAcceptRequestButton.setVisibility(View.GONE);
            Log.wtf(TAG, "Отменить заявку");
        } else if (currentUser.getContacts() == null
                || (!currentUser.getContacts().containsKey(selectedUser.getUid())
                && !selectedUser.getContacts().containsKey(currentUser.getUid()))) {
            mAddFriendButton.setVisibility(View.VISIBLE);
            mEditProfileButton.setVisibility(View.GONE);
            mSendMessageButton.setVisibility(View.GONE);
            mCancelRequestButton.setVisibility(View.GONE);
            mAcceptRequestButton.setVisibility(View.GONE);
            Log.wtf(TAG, "Добавить в друзья");
        } else if (currentUser.getContacts() != null
                && currentUser.getContacts().containsKey(selectedUser.getUid())
                && currentUser.getContacts().get(selectedUser.getUid()).equals(true)) {
            mAddFriendButton.setVisibility(View.GONE);
            mEditProfileButton.setVisibility(View.GONE);
            mSendMessageButton.setVisibility(View.VISIBLE);
            mCancelRequestButton.setVisibility(View.GONE);
            mAcceptRequestButton.setVisibility(View.GONE);
            Log.wtf(TAG, "Отправить сообщение");
        }
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
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
            mDatabaseReference.child("contacts").child(getArguments().getString(MainActivity.UID)).setValue(false);
        }
    }

    private class OnButtonCancelRequestClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").
                    child(mAuth.getCurrentUser().getUid()).
                    child("contacts").
                    child(getArguments().getString(MainActivity.UID));
            mDatabaseReference.removeValue();
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
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").
                    child(mAuth.getCurrentUser().getUid()).
                    child("contacts").
                    child(getArguments().getString(MainActivity.UID));
            mDatabaseReference.setValue(true);
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").
                    child(getArguments().getString(MainActivity.UID)).
                    child("contacts").
                    child(mAuth.getCurrentUser().getUid());
            mDatabaseReference.setValue(true);
        }
    }
}
