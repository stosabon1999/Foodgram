package ru.production.ssobolevsky.foodgram.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ru.production.ssobolevsky.foodgram.R;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivityTag";
    public static final int REQUEST_CODE_REGISTER = 1001;

    private FirebaseAuth mAuth;
    private ImageView mLogo;
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
        mAuth = FirebaseAuth.getInstance();
        mLogo = findViewById(R.id.iv_logo);
        mEmailEditText = findViewById(R.id.et_email);
        mPasswordEditText = findViewById(R.id.et_password);
        mLoginButton = findViewById(R.id.b_login);
        mCreateAccountTextView = findViewById(R.id.tv_create_account);
    }

    private void initListeners() {
        mLoginButton.setOnClickListener(new LoginButtonClickListener());
        mCreateAccountTextView.setOnClickListener(new RegisterButtonClickListener());
    }


    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            startActivity(MainActivity.newIntent(LoginActivity.this, mAuth.getCurrentUser().getUid()));
                            finish();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private class LoginButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            signIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        }
    }

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivityForResult(SignUpActivity.newIntent(LoginActivity.this), REQUEST_CODE_REGISTER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK) {
            startActivity(MainActivity.newIntent(LoginActivity.this, mAuth.getCurrentUser().getUid()));
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}
