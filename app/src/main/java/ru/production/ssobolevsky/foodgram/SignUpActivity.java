package ru.production.ssobolevsky.foodgram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText mNameEditText;

    private EditText mLoginEditText;

    private EditText mPasswordEditText;

    private EditText mConfirmPasswordEditText;

    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
        initListeners();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();

        mNameEditText = findViewById(R.id.et_register_name);
        mLoginEditText = findViewById(R.id.et_register_login);
        mPasswordEditText = findViewById(R.id.et_register_password);
        mConfirmPasswordEditText = findViewById(R.id.et_register_confirm_password);
        mSignUpButton = findViewById(R.id.b_register_sign_up);
    }

    private void initListeners() {
        mSignUpButton.setOnClickListener(new SignUpButtonClickListener());
    }

    private void createAccount(final String email, String password, final String name) {
        Log.d(LoginActivity.TAG, "create account:" + email);
        // TODO (1) validate email and password

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(LoginActivity.TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            createUser(user.getUid(), email, name);

                        } else {
                            Log.w(LoginActivity.TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUser(String userId, String email, String name) {
        User user = new User(email, name);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child(userId).setValue(user);
    }

    private class SignUpButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            createAccount(mLoginEditText.getText().toString(), mPasswordEditText.getText().toString(), mNameEditText.getText().toString());
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

}
