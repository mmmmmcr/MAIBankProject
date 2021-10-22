package com.example.maibank.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.maibank.activities.PayABillActivity;
import com.example.maibank.activities.SendMoneyActivity;
import com.example.maibank.activities.TransactionHistoryActivity;
import com.example.maibank.R;

public class PaymentsPageFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payments_page_fragment, container, false);

        view.findViewById(R.id.btn_payBill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PayABillActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_sendMoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SendMoneyActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_transactionHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TransactionHistoryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
