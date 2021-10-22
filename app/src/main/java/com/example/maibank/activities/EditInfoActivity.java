package com.example.maibank.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.maibank.fragments.InfoPageFragment;
import com.example.maibank.models.User;
import com.example.maibank.R;
import com.example.maibank.util.DatabaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditInfoActivity extends Activity {
    private EditText firstName;
    private EditText lastName;
    private EditText CNP;
    private EditText address;
    private EditText phone;
    private EditText email;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info_page_activity);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Query specific_user = database.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        specific_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                setInitialData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findViewById(R.id.update_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditInfoActivity.this, InfoPageFragment.class);
                setUserData();
                DatabaseUtil.addNodeToDatabase(DatabaseUtil.USERS, user);
                startActivity(intent);
            }
        });
    }

    private void setInitialData() {
        firstName = findViewById(R.id.infoEditTextFirstName);
        firstName.setText(user.getFirstName());

        lastName = findViewById(R.id.infoEditTextLastName);
        lastName.setText(user.getLastName());

        CNP = findViewById(R.id.infoEditTextCNP);
        CNP.setText(user.getCNP());

        address = findViewById(R.id.infoEditTextAddress);
        address.setText(user.getAddress());

        phone = findViewById(R.id.infoEditTextPhone);
        phone.setText(user.getPhone());

        email = findViewById(R.id.infoEditTextEmail);
        email.setText(user.getEmail());
    }

    private void setUserData() {
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setAddress(address.getText().toString());
        user.setPhone(phone.getText().toString());
        user.setCNP(CNP.getText().toString());
        user.setEmail(email.getText().toString());
    }

}
