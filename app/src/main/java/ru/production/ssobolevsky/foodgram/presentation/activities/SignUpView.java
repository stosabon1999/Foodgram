package ru.production.ssobolevsky.foodgram.presentation.activities;

public interface SignUpView {

    /**
     * Method to return ro main screen after successful registration. {@link MainActivity}
     */
    void goToProfileScreen();

    /**
     * Method invokes when registration failed.
     */
    void registrationError();

    /**
     * Method invokes when registration data is invalid.
     */
    void showInvalidData();
}
