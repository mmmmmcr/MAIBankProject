package com.example.maibank.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.maibank.Activities.LoginActivity;
import com.example.maibank.Models.Account;
import com.example.maibank.Models.Accounts;
import com.example.maibank.R;
import com.example.maibank.Util.DatabaseUtil;
import com.example.maibank.Util.SharedPreferencesUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomePageFragment extends Fragment {

    private static final String MIN_AMOUNT = "minAmount";
    private static final String MAX_AMOUNT = "maxAmount";
    private FirebaseAuth auth;
    private Query accounts;
    private double currentSold = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        accounts = DatabaseUtil.readModel(DatabaseUtil.ACCOUNTS);
        View view = inflater.inflate(R.layout.home_page_fragment, container, false);
        String minFromPreferences = SharedPreferencesUtil.get(MIN_AMOUNT, getActivity());
        String maxFromPreferences = SharedPreferencesUtil.get(MAX_AMOUNT, getActivity());
        final TextView percentage = view.findViewById(R.id.account_percentage);
        final EditText min = view.findViewById(R.id.input_min);

        if(minFromPreferences != null && !minFromPreferences.isEmpty()){
            min.setText(minFromPreferences);
        }
        final EditText max = view.findViewById(R.id.input_max);
        if(maxFromPreferences != null && !maxFromPreferences.isEmpty()){
            max.setText(maxFromPreferences);
        }
        final TextView textCurrentSold = view.findViewById(R.id.sold);

        if(accounts != null){
            accounts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Accounts accountsModel = dataSnapshot.getValue(Accounts.class);
                    if(accountsModel != null){
                        Map<String, Account> accounts = accountsModel.getAccounts();
                        if(!accounts.isEmpty()){
                            currentSold = ((Account)accounts.values().toArray()[0]).getSold();
                            String typeAccount = " " + ((Account)accounts.values().toArray()[0]).getType().toUpperCase();
                            textCurrentSold.setText("Sold: " + String.valueOf(currentSold) + typeAccount);
                        }
                    } else {
                        currentSold = 0;
                    }

                    updateAccountLimits(min, max, currentSold,percentage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        updateAccountLimits(min,max,currentSold,percentage);
        Button setButton = view.findViewById(R.id.btn_set_min_max);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.saveToPreferences(min.getText().toString(), MIN_AMOUNT, getActivity());
                SharedPreferencesUtil.saveToPreferences(max.getText().toString(),MAX_AMOUNT, getActivity());
                updateAccountLimits(min, max, currentSold, percentage);
            }
        });

        Button logout = view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    public void updateAccountLimits(EditText min, EditText max, double currentSold, TextView percentage) {
        try{
            int minParsed = Integer.parseInt(min.getText().toString());
            int maxParsed = Integer.parseInt(max.getText().toString());
            double sold = currentSold - minParsed;
            double result = (100 * sold) / (Math.abs(maxParsed - minParsed));
            if(result < 0){
                percentage.setText(" < " + 0 + " %");
            } else if (result > 100){
                percentage.setText(" > " + 100 + " %");
            } else {
                percentage.setText(String.format("%.2f", result) + " %");
            }
        } catch (NumberFormatException e ){
            e.printStackTrace();
        }
    }
}
