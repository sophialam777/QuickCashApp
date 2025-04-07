package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeApplicationStatusActivity extends AppCompatActivity {

    private ListView listView;
    private ApplicationStatusAdapter adapter;
    private ArrayList<JobApplication> applicationList;
    private Button back;
    private JobApplication selectedApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_application_status); // This layout must exist in res/layout

        listView = findViewById(R.id.applicationStatusListView);
        applicationList = new ArrayList<>();
        adapter = new ApplicationStatusAdapter(this, applicationList);
        listView.setAdapter(adapter);

        loadApplications();

        back = findViewById(R.id.my_applications_back_button);
        back.setOnClickListener(v ->{
            Intent intent = new Intent(this, MyAccount.class);
            startActivity(intent);
        });

        // Handle item click to view job offer
        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            selectedApplication = applicationList.get(position);
            if(selectedApplication.getStatus().equals("Offered Job")) {
                Intent intent = new Intent(EmployeeApplicationStatusActivity.this, ViewOffer.class);
                intent.putExtra("application", selectedApplication); // Pass the selected application
                startActivity(intent);
            }
        });
    }

    private void loadApplications() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference applicationsRef = database.getReference("applications");

        applicationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applicationList.clear();
                for (DataSnapshot appSnapshot : snapshot.getChildren()) {
                    String applicantEmail = appSnapshot.child("applicantEmail").getValue(String.class);
                    if (applicantEmail != null && applicantEmail.equalsIgnoreCase(UserSession.email)) {
                        String applicationId = appSnapshot.getKey();
                        String jobTitle = appSnapshot.child("jobTitle").getValue(String.class);
                        String resumeUri = appSnapshot.child("resumeUri").getValue(String.class);
                        String answer1 = appSnapshot.child("answer1").getValue(String.class);
                        String answer2 = appSnapshot.child("answer2").getValue(String.class);
                        String status = appSnapshot.child("status").getValue(String.class);
                        String posterEmail = appSnapshot.child("posterEmail").getValue(String.class);
                        String applicantName = appSnapshot.child("applicantName").getValue(String.class);

                        if (status == null) {
                            status = "Submitted";
                        }
                        JobApplication application = new JobApplication(applicationId, jobTitle, posterEmail, applicantEmail, resumeUri, answer1, answer2, applicantName, status);
                        applicationList.add(application);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeApplicationStatusActivity.this, "Failed to load applications.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
