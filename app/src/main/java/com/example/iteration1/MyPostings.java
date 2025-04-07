package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
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
import java.util.List;

public class MyPostings extends AppCompatActivity {

    private ListView jobListView;
    private ArrayList<Job> jobList;
    private ArrayAdapter<Job> adapter;
    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_postings);

        jobListView = findViewById(R.id.my_postings_list);
        goBackButton = findViewById(R.id.my_postings_back_button);

        jobList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobList);
        jobListView.setAdapter(adapter);

        // Fetch jobs from Firebase
        fetchJobsFromFirebase();

        // Handle item click to show job details
        jobListView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Job selectedJob = jobList.get(position);
            Intent intent = new Intent(MyPostings.this, ReceivedApplications.class);
            intent.putExtra("job", selectedJob); // Pass the selected job
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            // Navigate back to the homepage
            Intent intent = new Intent(MyPostings.this, EmployerDashboard.class);
            startActivity(intent);
            finish();
        });
    }

    private void fetchJobsFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference jobsRef = database.getReference("jobs");

        jobsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobList.clear(); // Clear the existing list
                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    // Parse job data from Firebase
                    String title = jobSnapshot.child("title").getValue(String.class);
                    String location = jobSnapshot.child("location").getValue(String.class);
                    String type = jobSnapshot.child("type").getValue(String.class);
                    String pay = jobSnapshot.child("pay").getValue(String.class);
                    String description = jobSnapshot.child("description").getValue(String.class);
                    double latitude = jobSnapshot.child("latitude").getValue(Double.class);
                    double longitude = jobSnapshot.child("longitude").getValue(Double.class);
                    List<String> questions = (List<String>) jobSnapshot.child("questions").getValue();
                    String posterEmail = jobSnapshot.child("poster's email").getValue(String.class);
                    String posterName = jobSnapshot.child("posted by").getValue(String.class);

                    if(posterName!=null && posterEmail!=null){
                        if(posterName.equals(UserSession.name.trim()) && posterEmail.equals(UserSession.email.trim())){
                            // Create a Job object and add it to the list
                            Job job = new Job(title, location, description, type, pay, latitude, longitude, questions, posterEmail, posterName);
                            jobList.add(job);
                        }
                    }
                }
                adapter.notifyDataSetChanged(); // Refresh the list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EmployerApplicationsActivity", "Failed to fetch jobs: " + error.getMessage());
                Toast.makeText(MyPostings.this, "Failed to fetch jobs.", Toast.LENGTH_LONG).show();
            }
        });
    }
}