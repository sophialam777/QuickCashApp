package com.example.iteration1.dataBase_CRUD;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fireBase_CRUD {
    private ArrayList<String> emailList;
    private ArrayList<String> employeeList;
    private final FirebaseDatabase database;
    private DatabaseReference dbRef;
    private DatabaseReference userRef;

    public fireBase_CRUD(FirebaseDatabase database) {
        this.database = database;
        this.emailList = new ArrayList<>();
        this.employeeList = new ArrayList<>();
        dbRef = database.getReference();
        getUserRef();
        initializeDatabaseRefListeners();
    }

    private void initializeDatabaseRefListeners() {
        setUserEmailListener();
    }

    private void getUserRef() {
        this.userRef = dbRef.child("users");
    }

    // Helper method to sanitize email and avoid invalid Firebase path characters
    private String sanitizeEmail(String email) {
        return email.replace(".", "_").replace("@", "_at_").replace("#", "_hash_")
                .replace("$", "_dollar_").replace("[", "_open_bracket_").replace("]", "_close_bracket_");
    }

    // Listener to get users' email
    private void setUserEmailListener() {
        this.userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emailList.clear();
                // Loop through every user and add their email to the emailList
                for (DataSnapshot userID : snapshot.getChildren()) {
                    String userEmail = userID.child("email").getValue(String.class);
                    emailList.add(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // Get the email list of users
    public ArrayList<String> getEmailList() {
        return this.emailList;
    }

    // Add preferred employee for the current logged-in user (email)
    public void addPreferredEmployee(String employeeEntry) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currentUserEmail = auth.getCurrentUser().getEmail(); // Get the current logged-in user's email

        if (currentUserEmail != null) {
            String sanitizedEmail = sanitizeEmail(currentUserEmail);
            DatabaseReference userPreferredRef = dbRef.child("users").child(sanitizedEmail).child("preferred_employees");

            // Add the employee to the logged-in user's preferred employee list
            userPreferredRef.push().setValue(employeeEntry);
        }
    }

    // Get preferred employees for a specific user (email)
    public void getPreferredEmployees(String email, final PreferredEmployeesCallback callback) {
        String sanitizedEmail = sanitizeEmail(email);
        DatabaseReference userPreferredRef = dbRef.child("users").child(sanitizedEmail).child("preferred_employees");

        userPreferredRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employeeList.clear();
                // Loop through preferred employees and add to list
                for (DataSnapshot employeeSnapshot : snapshot.getChildren()) {
                    String employee = employeeSnapshot.getValue(String.class);
                    employeeList.add(employee);
                }
                callback.onCallback(employeeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // Callback interface to pass the data back to the calling class
    public interface PreferredEmployeesCallback {
        void onCallback(ArrayList<String> employeeList);
    }
}
