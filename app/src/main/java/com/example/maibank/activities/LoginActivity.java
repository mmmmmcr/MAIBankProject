package com.example.maibank.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.maibank.R;
import com.example.maibank.models.User;
import com.example.maibank.util.SharedPreferencesUtil;
import com.example.maibank.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Login page logic
 */
public class LoginActivity extends Activity {
    /**
     * Tag identifier for this class
     */
    private static final String TAG = "LoginActivity";
    /**
     * Key used to store the user field
     */
    public static String USER_KEY_PREFERENCES = "userKey";
    /**
     * The field representing the user used to log into the app
     */
    EditText emailField;
    /**
     *
     * The password field
     */
    EditText passwordField;
    /**
     * The button used to login into the app
     */
    Button loginButton;
    /**
     * Fierbase authentification object
     */
    private FirebaseAuth auth;
    /**
     * Dialog to notify user about the progress while logging into the app
     */
    private ProgressDialog progressDialog;

    /**
     * Public constructor
     */
    public LoginActivity(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.login_activity);
        emailField = findViewById(R.id.input_email);
        passwordField = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        String lastUserInputAccount = SharedPreferencesUtil.get(USER_KEY_PREFERENCES, this);

        if(lastUserInputAccount != null){
            emailField.setText(lastUserInputAccount);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.saveToPreferences(emailField.getText().toString(), USER_KEY_PREFERENCES, LoginActivity.this);
                login();
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * The main login logic
     */
    public void login() {
        Log.d(TAG, "Login");
        if (!validateCredentials()) {
            return;
        }

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getBaseContext().getResources().getString(R.string.progressDialog_authentificate));
        progressDialog.show();

        loginUserAccount(emailField.getText().toString(), passwordField.getText().toString());
    }

    /**
     * @return True if the email and password provided are correct
     */
    private boolean validateCredentials() {
        return  Utils.isPasswordFieldOk(passwordField) && Utils.isEmailValid(emailField);
    }

    /**
     * Login the user with Fierbase authentication
     * @param email The email representing the username
     * @param password The password for the username
     */
    private void loginUserAccount(final String email, final String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = auth.getCurrentUser();
                                    User user = User.getInstance();
                                    user.setUID(currentUser.getUid());
                                    user.setEmail(currentUser.getEmail());

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }

                                else {
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                }
                            }
                        });

    }

    @Override
    public void onBackPressed() {

    }
}