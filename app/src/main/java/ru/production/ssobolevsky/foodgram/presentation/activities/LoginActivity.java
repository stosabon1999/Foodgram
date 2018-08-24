package ru.production.ssobolevsky.foodgram.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivityTag";
    public static final int REQUEST_CODE_REGISTER = 1001;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private TextView mCreateAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initListeners();
    }


    private void init() {
        mEmailEditText = findViewById(R.id.et_email);
        mPasswordEditText = findViewById(R.id.et_password);
        mLoginButton = findViewById(R.id.b_login);
        mCreateAccountTextView = findViewById(R.id.tv_create_account);
    }

    private void initListeners() {
        mLoginButton.setOnClickListener(new LoginButtonClickListener());
        mCreateAccountTextView.setOnClickListener(new RegisterButtonClickListener());
    }

    /**
     * Method to sign in with {@param email} and {@param password}.
     * If task is successful then {@link MainActivity} starts else toast shows.
     * @param email - email.
     * @param password - password.
     */
    private void signIn(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Заполните все поля",
                    Toast.LENGTH_SHORT).show();
        } else {
            MyFirebaseData.getAuth().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startActivity(MainActivity.newIntent(LoginActivity.this, MyFirebaseData.getFirebaseUserUid()));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Введите корректные данные",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    /**
     * Class to listen {@link #mLoginButton} clicks.
     * If clicked then user sign in. {@link #signIn}.
     */
    private class LoginButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            signIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        }
    }
    /**
     * Class to listen {@link #mCreateAccountTextView}  clicks.
     * If clicked then sign up activity starts. {@link SignUpActivity}.
     */
    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivityForResult(SignUpActivity.newIntent(LoginActivity.this), REQUEST_CODE_REGISTER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK) {
            startActivity(MainActivity.newIntent(LoginActivity.this, MyFirebaseData.getFirebaseUserUid()));
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
