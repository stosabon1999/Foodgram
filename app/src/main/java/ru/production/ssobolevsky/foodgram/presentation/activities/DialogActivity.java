package ru.production.ssobolevsky.foodgram.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.repositories.ChatRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.Message;
import ru.production.ssobolevsky.foodgram.domain.usecases.impl.ChatUseCaseImpl;
import ru.production.ssobolevsky.foodgram.presentation.adapters.MessagesAdapter;
import ru.production.ssobolevsky.foodgram.presentation.presenters.DialogPresenter;

import static ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData.MAX_MESSAGES;

public class DialogActivity extends AppCompatActivity implements DialogView {

    public static final String USER_UID = "UID";
    public static final String CHAT_UID = "CHAT_UID";
    public static final String USER_NAME = "USER_NAME";

    private ImageButton mButtonSend;
    private EditText mEditTextMessage;
    private RecyclerView mRecyclerViewMessages;
    private LinearLayoutManager mManager;
    private MessagesAdapter mAdapter;
    private Toolbar mToolbar;
    private DialogPresenter mDialogPresenter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        init();
        initListeners();
    }

    private void initListeners() {
        mButtonSend.setOnClickListener(new OnButtonSendClickListener());
        mToolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void init() {
        mProgressBar = findViewById(R.id.pb_dialog);
        mButtonSend = findViewById(R.id.ib_messages);
        mEditTextMessage = findViewById(R.id.et_messages);
        mRecyclerViewMessages = findViewById(R.id.rv_messages);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mManager.setStackFromEnd(true);
        mRecyclerViewMessages.setLayoutManager(mManager);
        mAdapter = new MessagesAdapter();
        mRecyclerViewMessages.setAdapter(mAdapter);
        mToolbar = findViewById(R.id.toolbar_messages);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(USER_NAME));
        mDialogPresenter = new DialogPresenter(new ChatUseCaseImpl(new ChatRepositoryImpl()));
        mDialogPresenter.attachView(this);
        if (getIntent().getExtras().getString(CHAT_UID) == null) {
            mDialogPresenter.getDialogByUserId(getIntent().getStringExtra(USER_UID), null);
        } else {
            mDialogPresenter.getDialogByChatId(getIntent().getStringExtra(CHAT_UID), null);
        }
    }

    public static Intent newInstance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, DialogActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void showDialog(List<Message> data) {
        mAdapter.setData(data);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerViewMessages.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerViewMessages.setVisibility(View.VISIBLE);
    }

    /**
     * Listener of {@link #mButtonSend}. When button clicked, then message sends.
     * If {@link #mEditTextMessage} is empty then toast shows else message sends.
     */
    private class OnButtonSendClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mEditTextMessage.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Введите текст сообщения", Toast.LENGTH_SHORT).show();
            } else if (getIntent().getExtras().getString(CHAT_UID) == null) {
                mDialogPresenter.sendMessageByUserUid(mEditTextMessage.getText().toString(), getIntent().getStringExtra(USER_UID));
                mEditTextMessage.setText("");
            } else {
                mDialogPresenter.sendMessageByChatUid(mEditTextMessage.getText().toString(), getIntent().getStringExtra(CHAT_UID));
                mEditTextMessage.setText("");
            }

        }
    }
}
