package com.example.maibank.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maibank.R;
import com.example.maibank.Models.User;
import com.example.maibank.Util.DatabaseUtil;
import com.example.maibank.Util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Register a new user
 */
public class RegisterActivity extends Activity {
    /**
     * The current user
     */
    private User user;
    /**
     * The first name of the user field
     */
    private EditText firstName;
    /**
     * The last name of the user field
     */
    private EditText lastName;
    /**
     * The adress of the user field
     */
    private EditText address;
    /**
     * The phone of the user field
     */
    private EditText phone;
    /**
     * The CNP of the user field
     */
    private EditText CNP;
    /**
     * The email of the user field
     */
    private EditText email;
    /**
     * The desired password
     */
    private EditText password;
    /**
     * The confirmation of the password
     */
    private EditText confirmPassword;
    /**
     * The uid of the user
     */
    private String uid;
    /**
     * Fierbase authentication
     */
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = User.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.register_activity);
        initFields();

        findViewById(R.id.contactButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactIntent = new Intent(RegisterActivity.this, ContactActivity.class);
                startActivity(contactIntent);
            }
        });

        findViewById(R.id.regButtonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(getBaseContext().getResources().getString(R.string.progressDialog_register));
                progressDialog.show();

                String passwordText = password.getText().toString();
                if(Utils.isEmailValid(email) && isPasswordsFieldsValid(passwordText)){
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),passwordText).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        uid = firebaseAuth.getCurrentUser().getUid();
                                        setUserData();
                                        DatabaseUtil.addNodeToDatabase(DatabaseUtil.USERS, user);
                                        Intent intent
                                                = new Intent(RegisterActivity.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else {

                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Registration failed!",
                                                Toast.LENGTH_LONG)
                                                .show();

                                    }
                                }
                            });

                } else {
                    progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * Checks if the password is valid
     * (the password field and confirm password field are the same and has the length between 4 and 10 characters)
     * @param passwordText The password to check
     * @return True if the password is valid, otherwise false
     */
    private boolean isPasswordsFieldsValid(String passwordText) {
        return passwordText != null && passwordText.equals(confirmPassword.getText().toString()) && Utils.isPasswordFieldOk(password) ;
    }

    /**
     * Init the fields
     */
    private void initFields() {
        firstName = findViewById(R.id.regEditTextFName);
        lastName = findViewById(R.id.regEditTextLName);
        address = findViewById(R.id.regEditTextAddress);
        phone = findViewById(R.id.regEditTextPhone);
        CNP = findViewById(R.id.regEditTextCnp);
        email = findViewById(R.id.regEditTextEmail);
        password = findViewById(R.id.regEditTextPassword);
        confirmPassword = findViewById(R.id.regEditTextConfirmPassword);
    }

    /**
     * Sets the data from the fields into the user object
     */
    private void setUserData() {
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setAddress(address.getText().toString());
        user.setPhone(phone.getText().toString());
        user.setCNP(CNP.getText().toString());
        user.setEmail(email.getText().toString());
        user.setUID(uid);
    }
}