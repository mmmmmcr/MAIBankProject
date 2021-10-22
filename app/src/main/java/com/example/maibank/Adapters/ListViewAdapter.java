package com.example.maibank.Adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.maibank.Models.Transaction;
import com.example.maibank.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Transaction> transactions;

    public ListViewAdapter(Context applicationContext, List<Transaction> transactionList) {
        this.context = applicationContext;
        this.transactions = transactionList;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.historyadapter_view, null);

        TextView myAccounts = (TextView) view.findViewById(R.id.textViewMyAccount);
        TextView destinationAccount = (TextView) view.findViewById(R.id.textViewDestinationAccount);
        TextView amount = (TextView) view.findViewById(R.id.textViewAmount);
        TextView date = (TextView) view.findViewById(R.id.textViewTransactionDate);

        myAccounts.setText("Sender Account: " + transactions.get(i).getMyAccount());
        destinationAccount.setText("Receiver Account: " + transactions.get(i).getDestinationAccount());
        amount.setText("Amount: " + transactions.get(i).getAmount());
        date.setText("Transaction Date: " + transactions.get(i).getTransactionDate());

        return view;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Transaction History \n" + "\n");

        for (Transaction t : transactions) {
            stringBuilder.append("Sender Account: " + t.getMyAccount() + "\n");
            stringBuilder.append("Receiver Account: " + t.getDestinationAccount() + "\n");
            stringBuilder.append("Amount: " + t.getAmount() + "\n");
            stringBuilder.append("Transaction Date: " + t.getTransactionDate() + "\n");
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
