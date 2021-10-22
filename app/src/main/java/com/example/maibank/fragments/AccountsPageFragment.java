package com.example.maibank.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.maibank.activities.ContactActivity;
import com.example.maibank.models.Account;
import com.example.maibank.models.Accounts;
import com.example.maibank.R;
import com.example.maibank.util.DatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AccountsPageFragment extends Fragment {
    private static final int FIRST_ACCOUNT_INDEX = 0;
    private static final int SECOND_ACCOUNT_INDEX = 1;
    private TextView ibanFirstAccount, soldFirstAccount, typeFirstAccount;
    private TextView ibanSecondAccount, soldSecondAccount, typeSecondAccount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accounts_page_fragment, container, false);
        initData(view);

        Query accounts = DatabaseUtil.readModel(DatabaseUtil.ACCOUNTS);
        accounts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Accounts accountsModel = dataSnapshot.getValue(Accounts.class);

                Map<String, Account> accounts = accountsModel.getAccounts();
                setAttributes(accounts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button createNewAccountButton = view.findViewById(R.id.create_new_account_btn);
        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setAttributes(Map<String, Account> accounts) {
        if (!accounts.isEmpty()) {
            ibanFirstAccount.setText("IBAN: " + ((Account) accounts.values().toArray()[FIRST_ACCOUNT_INDEX]).getIban());
            typeFirstAccount.setText("TYPE: " + ((Account) accounts.values().toArray()[FIRST_ACCOUNT_INDEX]).getType().toUpperCase());
            soldFirstAccount.setText("Sold: " + ((Account) accounts.values().toArray()[FIRST_ACCOUNT_INDEX]).getSold());

            ibanSecondAccount.setText("IBAN: " + ((Account) accounts.values().toArray()[SECOND_ACCOUNT_INDEX]).getIban());
            typeSecondAccount.setText("TYPE: " + ((Account) accounts.values().toArray()[SECOND_ACCOUNT_INDEX]).getType().toUpperCase());
            soldSecondAccount.setText("Sold: " + ((Account) accounts.values().toArray()[SECOND_ACCOUNT_INDEX]).getSold());
        }
    }

    private void initData(View view) {
        ibanFirstAccount = view.findViewById(R.id.ibanFirstAccount);
        soldFirstAccount = view.findViewById(R.id.soldFirstAccount);
        typeFirstAccount = view.findViewById(R.id.typeFirstAccount);
        ibanSecondAccount = view.findViewById(R.id.ibanSecondAccount);
        soldSecondAccount = view.findViewById(R.id.soldSecondAccount);
        typeSecondAccount = view.findViewById(R.id.typeSecondAccount);

    }

}

