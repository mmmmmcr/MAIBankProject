package com.example.maibank.Util;

import android.widget.EditText;

/**
 * Util class
 */
public class Utils {

    /**
     * Private constructor
     */
    private Utils(){}

    /**
     * Checks to see if the email field is empty and has the email format
     * @param emailField The EditText representing email
     * @return True if the email string is not empty and it has an email format, false otherwise
     */
    public static boolean isEmailValid(EditText emailField) {
        boolean valid = true;
        String email = emailField.getText().toString();
        if (!(email.isEmpty() || android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            emailField.setError("enter a valid user");
            valid = false;
        } else {
            emailField.setError(null);
        }
        return valid;
    }

    /**
     * Check to see if password field is valid
     * @param passwordField The password EditText
     * @return True if the password field is valid, false otherwise
     */
    public static boolean isPasswordFieldOk(EditText passwordField){
        boolean valid = true;
        String password = passwordField.getText().toString();
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordField.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordField.setError(null);
        }
        return valid;
    }
}
