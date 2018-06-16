package ru.production.ssobolevsky.foodgram;

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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginAndRegister";

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

        mAuth = FirebaseAuth.getInstance();
        init();
        initListeners();
    }


    private void init() {
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

    @Override
    protected void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    private void signIn(String email, String password) {
        // TODO (2) validate email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private class LoginButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            signIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
            startActivity(MainActivity.newIntent(LoginActivity.this));
        }
    }

    private class RegisterButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            startActivity(SignUpActivity.newIntent(LoginActivity.this));
        }
    }
}
