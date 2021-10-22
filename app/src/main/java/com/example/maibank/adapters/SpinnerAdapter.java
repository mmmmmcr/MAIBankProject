package com.example.maibank.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.maibank.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<Pair<String, String>> services;

    public SpinnerAdapter(Context applicationContext, List<Pair<String, String>> services) {
        this.context = applicationContext;
        this.services = services;
    }

    @Override
    public int getCount() {
        return services.size();
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
        view = inflater.inflate(R.layout.spinner_item_layout, null);

        TextView names = (TextView) view.findViewById(R.id.providerName);
        TextView ibans = (TextView) view.findViewById(R.id.providerIban);

        names.setText(services.get(i).first);
        ibans.setText(services.get(i).second);

        return view;
    }
}
