package com.example.maibank.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maibank.adapters.SpinnerAdapter;
import com.example.maibank.models.Account;
import com.example.maibank.models.Accounts;
import com.example.maibank.models.Transaction;
import com.example.maibank.R;
import com.example.maibank.util.DatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PayABillActivity extends Activity {
    private Spinner spinnerAccounts;
    private Spinner spinnerServices;
    private EditText soldField;
    private EditText customerNumberField;
    private List<Pair<String, String>> accounts = new ArrayList<>();
    private List<Pair<String, String>> services = new ArrayList<Pair<String, String>>(){{add(new Pair<>("E-on","RO99NVSKX2344SC")); add(new Pair<>("Rcs-Rds","RO59ASSKX2344S2")); add(new Pair<>("Orange","RO66NDDKX2T940W"));}};
    private SpinnerAdapter adapter;
    private Map<String, Account> account ;
    private Account currentAccount;
    private Accounts accountsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payabill_activity);

        spinnerAccounts = (Spinner) findViewById(R.id.spinnerAccounts);
        spinnerServices = (Spinner) findViewById(R.id.planets_spinnerServices);

        populateAccounts();

        adapter = new SpinnerAdapter(PayABillActivity.this, accounts);
        spinnerAccounts.setAdapter(adapter);
        spinnerServices.setAdapter(new SpinnerAdapter(PayABillActivity.this, services));

        Button payButton = findViewById(R.id.btn_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soldField = findViewById(R.id.fieldViewSold);
                customerNumberField = findViewById(R.id.fieldCustomerNumber);

                String currentIban = ((TextView)spinnerAccounts.getSelectedView().findViewById(R.id.providerIban)).getText().toString();
                String destinationIban = ((TextView)spinnerServices.getSelectedView().findViewById(R.id.providerIban)).getText().toString();
                currentAccount = currentAccount(currentIban);

                double soldParsed = Double.parseDouble(soldField.getText().toString());
                if(currentAccount.getSold() > soldParsed){
                    currentAccount.setSold(currentAccount.getSold() - soldParsed);
                    DatabaseUtil.updateCurrentModel(DatabaseUtil.ACCOUNTS, accountsModel);
                    Transaction t = new Transaction(currentIban, destinationIban, soldField.getText().toString(), LocalDateTime.now().toString());
                    DatabaseUtil.appendTransaction(t);
                    Intent intent = new Intent(PayABillActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(PayABillActivity.this, "You do not have enough money", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Account currentAccount(String iban){

        return account.get(iban);
    }

    private void populateAccounts(){
        Query q = DatabaseUtil.readModel(DatabaseUtil.ACCOUNTS);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accountsModel = dataSnapshot.getValue(Accounts.class);
                if(accountsModel != null){
                    account = accountsModel.getAccounts();
                    if(!account.isEmpty()){
                        for(Account a : account.values()){
                            accounts.add(new Pair<>(a.getType(), a.getIban()));

                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
