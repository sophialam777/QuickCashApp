package com.example.iteration1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.dataBase_CRUD.fireBase_CRUD;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PreferredEmployeeActivity extends AppCompatActivity {

    private ListView preferredEmployeeListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> employeeList;
    private fireBase_CRUD fireBaseCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferredemployee);

        // Initialize Firebase CRUD class
        fireBaseCRUD = new fireBase_CRUD(FirebaseDatabase.getInstance());
        preferredEmployeeListView = findViewById(R.id.pEmployee_list_view);
        Button addEmployeeButton = findViewById(R.id.add_employee_button);
        Button backButton = findViewById(R.id.back_button);

        employeeList = new ArrayList<>();

        // Set up adapter for ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeList);
        preferredEmployeeListView.setAdapter(adapter);

        // Add Employee button
        addEmployeeButton.setOnClickListener(v -> showAddEmployeeDialog());

        // Back button to close activity
        backButton.setOnClickListener(v -> finish());

        // Load the current user's preferred employees
        loadPreferredEmployees();
    }

    // Load the preferred employees from Firebase for the logged-in user
    private void loadPreferredEmployees() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currentUserEmail = auth.getCurrentUser().getEmail(); // Get the current logged-in user's email

        if (currentUserEmail != null) {
            fireBaseCRUD.getPreferredEmployees(currentUserEmail, new fireBase_CRUD.PreferredEmployeesCallback() {
                @Override
                public void onCallback(ArrayList<String> employeeList) {
                    // Update the ListView with the employee list
                    PreferredEmployeeActivity.this.employeeList.clear();
                    PreferredEmployeeActivity.this.employeeList.addAll(employeeList);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    // Show dialog to add a new employee
    private void showAddEmployeeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Employee");

        // Layout for input fields
        View view = getLayoutInflater().inflate(R.layout.dialog_add_employee, null);
        builder.setView(view);

        final EditText inputName = view.findViewById(R.id.input_name);
        final EditText inputEmail = view.findViewById(R.id.input_email);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = inputName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                String employeeEntry = name + " - " + email;

                // Add employee to the preferred list for the logged-in user
                fireBaseCRUD.addPreferredEmployee(employeeEntry);  // Add the employee
                Toast.makeText(this, "Employee added!", Toast.LENGTH_SHORT).show();
                loadPreferredEmployees();  // Refresh the employee list
            } else {
                Toast.makeText(this, "Name and Email cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
