package com.example.iteration1.dataBase_CRUD;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fireBase_CRUD {
    ArrayList<String> emailList;
    private final FirebaseDatabase database;
    private DatabaseReference dbRef = null;
    private DatabaseReference userRef = null;

    public fireBase_CRUD (FirebaseDatabase database) {
        this.database = database;
        this.emailList = new ArrayList<>();
        dbRef = database.getReference();
        getUserRef();
        initializeDatabaseRefListeners();
    }

    private void initializeDatabaseRefListeners(){
        setEtEmailListener();
    }

    private void getUserRef(){
        this.userRef = dbRef.child("users");
    }

    protected void setEtEmailListener() {
        this.userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //loop through every user and add their email to the emailList
                for (DataSnapshot userID : snapshot.getChildren()) {
                    String userEmail = userID.child("email").getValue(String.class);
                    fireBase_CRUD.this.emailList.add(userEmail);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<String> getEmailList(){
        return this.emailList;
    }


}
