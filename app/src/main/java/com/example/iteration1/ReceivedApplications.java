package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.validator.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReceivedApplications extends AppCompatActivity {

    private ListView applicationListView;
    private ArrayList<JobApplication> applicationList;
    private ArrayAdapter<JobApplication> adapter;
    private Button goBackButton;
    private Job selectedJob;
    private String jobPosterEmail;

    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.received_applications);

        selectedJob = (Job) getIntent().getSerializableExtra("job");
        jobPosterEmail = selectedJob.getPosterEmail();

        applicationListView = findViewById(R.id.application_list);
        goBackButton = findViewById(R.id.received_applications_back_btn);

        applicationList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, applicationList);
        applicationListView.setAdapter(adapter);

        getApplications();

        // Handle item click to show application details
        applicationListView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            JobApplication selectedApplication = applicationList.get(position);
            Intent intent = new Intent(ReceivedApplications.this, ViewApplication.class);
            intent.putExtra("application", selectedApplication); // Pass the selected application
            intent.putExtra("job", selectedJob); // pass the job
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            // Navigate back to the employer job list
            Intent intent = new Intent(ReceivedApplications.this, MyPostings.class);
            startActivity(intent);
            finish();
        });
    }

    private void getApplications(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference applicationRef = database.getReference("applications");

        applicationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applicationList.clear();
                for (DataSnapshot applicationSnapshot : snapshot.getChildren()) {
                    String applicantName = applicationSnapshot.child("applicantName").getValue(String.class);
                    String jobName = applicationSnapshot.child("jobTitle").getValue(String.class);
                    String posterEmail = applicationSnapshot.child("posterEmail").getValue(String.class);
                    String applicantEmail = applicationSnapshot.child("applicantEmail").getValue(String.class);
                    String resumeURI = applicationSnapshot.child("resumeUri").getValue(String.class);
                    String question1Answer = applicationSnapshot.child("answer1").getValue(String.class);
                    String question2Answer = applicationSnapshot.child("answer2").getValue(String.class);
                    String status = applicationSnapshot.child("status").getValue(String.class);
                    String fcmToken = applicationSnapshot.child("fcmToken").getValue(String.class);
                    String applicationID = applicationSnapshot.child("applicationID").getValue(String.class);

                    if(posterEmail!=null) {
                        if (posterEmail.equals(jobPosterEmail) && jobName.equals(selectedJob.getTitle()) && !status.equals("Rejected")) {
                            JobApplication application = new JobApplication(applicationID, selectedJob.getTitle(), posterEmail, applicantEmail, resumeURI, question1Answer, question2Answer, applicantName, status, fcmToken);
                            applicationList.add(application);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Received Applications", "Failed to fetch applications: " + error.getMessage());
                Toast.makeText(ReceivedApplications.this, "Failed to fetch applications.", Toast.LENGTH_LONG).show();
            }
        });
    }
}