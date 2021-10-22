package com.example.maibank.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import static com.example.maibank.util.DatabaseUtil.ACCOUNTS;

public class SendMoneyActivity extends Activity {

    private Spinner spinnerAccounts;
    private EditText soldField;
    private List<Pair<String, String>> accounts = new ArrayList<>();
    private SpinnerAdapter adapter;
    private Map<String, Account> account ;
    private Account currentAccount;
    private Accounts accountsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_money_activity);

        spinnerAccounts = (Spinner) findViewById(R.id.spinnerAccounts);

        populateAccounts();

        adapter = new SpinnerAdapter(SendMoneyActivity.this, accounts);
        spinnerAccounts.setAdapter(adapter);

        Button payButton = findViewById(R.id.btn_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soldField = findViewById(R.id.sold);
                double moneyToTransfer;
                String destinationIban = "";
                try{
                    moneyToTransfer = Double.parseDouble(soldField.getText().toString());
                    destinationIban = ((EditText)findViewById(R.id.fieldCustomerNumber)).getText().toString();
                    String currentIban = ((TextView)spinnerAccounts.getSelectedView().findViewById(R.id.providerIban)).getText().toString();
                    currentAccount = currentAccount(currentIban);
                } catch (NumberFormatException e){
                    moneyToTransfer = 0;
                    currentAccount = new Account(0,"dummy", "EUR");
                }
                new AlertDialog.Builder(SendMoneyActivity.this)
                        .setTitle("Transfer money")
                        .setMessage(new StringBuilder().append("Are you sure you want to make this transfer of ")
                                .append(moneyToTransfer).append(" ")
                                .append(currentAccount.getType().toUpperCase())
                                .append(" to ").append(destinationIban).toString())

                        .setPositiveButton(R.string.Yes, getAlertDialogListener(moneyToTransfer, destinationIban))

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

    }

    private DialogInterface.OnClickListener getAlertDialogListener(final double moneyToTransfer, final String destinationIban) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(currentAccount.getSold() > moneyToTransfer){
                    DatabaseUtil.getDatabase().getReference(ACCOUNTS).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String destinationAccountKey = null;
                                    Accounts userAccount = null;
                                    for (DataSnapshot children : dataSnapshot.getChildren()){
                                        userAccount = children.getValue(Accounts.class);
                                        Account destinationAccount = userAccount.getAccounts().get(destinationIban);
                                        if(destinationAccount != null) {
                                            currentAccount.setSold(currentAccount.getSold() - moneyToTransfer);
                                            double currentSoldDestination = destinationAccount.getSold();
                                            destinationAccount.setSold(currentSoldDestination + getTransferValue(currentAccount, destinationAccount, moneyToTransfer));
                                            destinationAccountKey = children.getKey();
                                            break;
                                        }
                                    }
                                    if(destinationAccountKey != null && userAccount != null){
                                        DatabaseUtil.updateCurrentModel(ACCOUNTS, accountsModel);
                                        DatabaseUtil.updateModel(ACCOUNTS, destinationAccountKey, userAccount);
                                        Transaction t = new Transaction(currentAccount.getIban(), destinationIban, soldField.getText().toString(), LocalDateTime.now().toString());
                                        DatabaseUtil.appendTransaction(t);
                                        Transaction t2 = new Transaction(currentAccount.getIban(), destinationIban, soldField.getText().toString(), LocalDateTime.now().toString());
                                        DatabaseUtil.appendTransaction(t2, destinationAccountKey);
                                        Toast.makeText(SendMoneyActivity.this, "Transfer success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SendMoneyActivity.this, "Invalid destination IBAN", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //handle databaseError
                                }
                            });

                } else{
                    Toast.makeText(SendMoneyActivity.this, "You do not have enough money", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private double getTransferValue(Account currentAccount, Account destinationAccount, double moneyToTransfer) {
        return moneyToTransfer;
    }

    private Account currentAccount(String iban){
        return account.get(iban);
    }

    private void populateAccounts(){
        Query q = DatabaseUtil.readModel(ACCOUNTS);

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
