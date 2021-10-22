package com.example.maibank.util;

import androidx.annotation.NonNull;

import com.example.maibank.models.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DatabaseUtil {
    public static String USERS = "users";
    public static String ACCOUNTS = "accounts";
    public static String TRANSACTIONS = "transactions";
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private  DatabaseUtil(){
        //avoid instantiation
    }

    public static void addNodeToDatabase(String parentTable, Object object){
        database.getReference().child(parentTable).child(firebaseAuth.getCurrentUser().getUid()).setValue(object);
    }
    public static void updateCurrentModel(String parentTable, Object object){
        FirebaseDatabase.getInstance().getReference(parentTable).child(firebaseAuth.getCurrentUser().getUid()).setValue(object);
    }

    public static void updateModel(String parentTable, String child, Object object){
        FirebaseDatabase.getInstance().getReference(parentTable).child(child).setValue(object);
    }

    public static Query readModel(String parentTable){
        return database.getReference(parentTable).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public static void deleteModel(String parentTable){
        database.getReference(parentTable).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
    }

    public static void appendTransaction(final Transaction t){

        appendTransaction(t, firebaseAuth.getCurrentUser().getUid());
    }

    public static void appendTransaction (final Transaction t, String child){
        final DatabaseReference transactionList = database.getReference().child(TRANSACTIONS).child(child).child("transactionList");

        transactionList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                transactionList.child(String.valueOf(snapshot.getChildrenCount())).setValue(t);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static FirebaseDatabase getDatabase(){
        return database;
    }

    public static FirebaseAuth getAuthentication(){
        return firebaseAuth;
    }
}
