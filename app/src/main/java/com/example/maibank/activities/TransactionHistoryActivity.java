package com.example.maibank.activities;

import android.Manifest;
import android.app.Activity;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.maibank.adapters.ListViewAdapter;
import com.example.maibank.models.Transactions;
import com.example.maibank.R;
import com.example.maibank.util.DatabaseUtil;
import com.example.maibank.util.PermissionUtility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;

public class TransactionHistoryActivity extends Activity {
    private Transactions transactions;
    private final ListViewAdapter[] adapter = new ListViewAdapter[1];
    private PermissionUtility permissionUtility;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactionhistory_activity);

        populateList();
        permissionUtility = new PermissionUtility(this, permissions);
        findViewById(R.id.downloadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMyPDF();
            }
        });
    }

    private void populateList() {
        DatabaseUtil.readModel(DatabaseUtil.TRANSACTIONS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactions = snapshot.getValue(Transactions.class);
                if (adapter[0] == null){

                    adapter[0] = new ListViewAdapter(TransactionHistoryActivity.this, transactions.getTransactionList());
                    ListView listView = (ListView) findViewById(R.id.listTransactions);

                    //set the adapter
                    listView.setAdapter(adapter[0]);
                }
                adapter[0].notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void createMyPDF(){
        if(permissionUtility.arePermissionsEnabled()) {
            PdfDocument myPdfDocument = new PdfDocument();
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
            PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

            Paint myPaint = new Paint();
            String myString = adapter[0].toString();
            int x = 10, y=25;

            for (String line:myString.split("\n")){
                myPage.getCanvas().drawText(line, x, y, myPaint);
                y+=myPaint.descent()-myPaint.ascent();
            }

            myPdfDocument.finishPage(myPage);

            String myFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/transactionHistory.pdf";
            File myFile = new File(myFilePath);
            try (FileOutputStream os = new FileOutputStream(myFile)) {
                myPdfDocument.writeTo(os);
                Toast.makeText(TransactionHistoryActivity.this, "Downloaded successfully to " + myFilePath, Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(TransactionHistoryActivity.this, "Could not download the report", Toast.LENGTH_LONG).show();
            }
            myPdfDocument.close();
        } else {
            permissionUtility.requestMultiplePermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}