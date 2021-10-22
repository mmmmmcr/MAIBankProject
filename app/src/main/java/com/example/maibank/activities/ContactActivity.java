package com.example.maibank.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maibank.R;

/**
 * Class for Contact Page
 */
public class ContactActivity extends Activity {
    /**
     * Array of email addresses
     */
    String[] emailsArray = {"mai.bank@gamil.com","mai_bank@gmail.com"};
    /**
     * Array of phone numbers
     */
    String[] phonesArray = {"0778995436","0002220383"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        populateList(emailsArray, R.id.listEmailAddresses, "email");
        populateList(phonesArray, R.id.listPhoneNumbers, "phone");
    }

    /**
     * Function which populates the listview
     * @param array The array which will pe upload in listview
     * @param id The id of the listview
     * @param action The name of the object in the listview
     */
    private void populateList(String [] array, int id, final String action) {
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, array);

        ListView listView = (ListView) findViewById(id);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(action.equalsIgnoreCase(getString(R.string.email))){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(intent.EXTRA_EMAIL, new String[]{((TextView)view).getText().toString()});
                    intent.putExtra(intent.EXTRA_SUBJECT, getString(R.string.maiBank_support));
                    startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
                }

                if(action.equalsIgnoreCase("phone")){
                    String phone = ((TextView)view).getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }

            }
        });

        //set the adapter
        listView.setAdapter(adapter);
    }
}
