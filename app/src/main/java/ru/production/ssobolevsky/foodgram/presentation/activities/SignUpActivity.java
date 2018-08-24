package ru.production.ssobolevsky.foodgram.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.presentation.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpView {


    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mLoginEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mSignUpButton;

    private SignUpPresenter mSignUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        initListeners();
    }

    private void init() {
        mFirstNameEditText = findViewById(R.id.et_register_first_name);
        mLastNameEditText = findViewById(R.id.et_register_last_name);
        mLoginEditText = findViewById(R.id.et_register_login);
        mPasswordEditText = findViewById(R.id.et_register_password);
        mConfirmPasswordEditText = findViewById(R.id.et_register_confirm_password);
        mSignUpButton = findViewById(R.id.b_register_sign_up);
        mSignUpPresenter = new SignUpPresenter();
        mSignUpPresenter.attachView(this);
    }

    private void initListeners() {
        mSignUpButton.setOnClickListener(new SignUpButtonClickListener());
    }

    @Override
    public void goToProfileScreen() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void registrationError() {
        Toast.makeText(SignUpActivity.this, "Некорректный email или пароль", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidData() {
        Toast.makeText(SignUpActivity.this, "Заполните все поля",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Class to listen {@link #mSignUpButton} clicks.
     * If clicked then check is data valid or not. If data is valid then create account{@link SignUpPresenter}
     * Else show toast.
     */
    private class SignUpButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (mPasswordEditText.getText().toString().equals(mConfirmPasswordEditText.getText().toString())
                    && mFirstNameEditText.getText().toString().length() > 1
                    && mLastNameEditText.getText().toString().length() > 1) {
                mSignUpPresenter.createAccount(mLoginEditText.getText().toString(),
                        mPasswordEditText.getText().toString(),
                        mFirstNameEditText.getText().toString() + " " + mLastNameEditText.getText().toString());
            } else {
                Toast.makeText(SignUpActivity.this, "Проверьте корректность данных", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

}
