package com.example.maibank.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.maibank.activities.EditInfoActivity;
import com.example.maibank.R;
import com.example.maibank.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class InfoPageFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_page_fragment, container, false);

        Button editInfoButton = view.findViewById(R.id.edit_button);
        editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditInfoActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewStateRestored(Bundle inState) {
        super.onViewStateRestored(inState);
        setInfo();
    }

    private void setInfo() {

        Query specific_user = database.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        specific_user.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TextView firstName = (TextView) getView().findViewById(R.id.infoTextViewFirstName);
                        firstName.setText(dataSnapshot.getValue(User.class).getFirstName());

                        TextView lastName = (TextView) getView().findViewById(R.id.infoTextViewSecondName);
                        lastName.setText(dataSnapshot.getValue(User.class).getLastName());

                        TextView email = (TextView) getView().findViewById(R.id.infoTextViewEmail);
                        email.setText(dataSnapshot.getValue(User.class).getEmail());

                        TextView CNP = (TextView) getView().findViewById(R.id.infoTextViewCNP);
                        CNP.setText(dataSnapshot.getValue(User.class).getCNP());

                        TextView phone = (TextView) getView().findViewById(R.id.infoTextViewPhone);
                        phone.setText(dataSnapshot.getValue(User.class).getAddress());

                        TextView address = (TextView) getView().findViewById(R.id.infoTextViewAddress);
                        address.setText(dataSnapshot.getValue(User.class).getPhone());

                        Log.d("Datasnapshot", email + " " + firstName);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
