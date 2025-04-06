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

public class SearchResultActivity extends AppCompatActivity {
    private ListView jobListView;
    private ArrayList<Job> jobList;
    private ArrayAdapter<Job> adapter;
    private Button viewMapButton, goBackButton;
    private String[] options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult);

        initializeViews();
        initializeOnClickListeners();

        // Fetch jobs from Firebase
        fetchJobsFromFirebase();
    }

    private void initializeViews(){
        jobListView = findViewById(R.id.listView);
        viewMapButton = findViewById(R.id.view_map_button);
        goBackButton = findViewById(R.id.backButton);


        jobList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,  jobList );
        jobListView.setAdapter(adapter);

        //get selected search option
        options = getSelectedOptions();
    }

    private void initializeOnClickListeners(){
        // Handle item click to show job details
        jobListView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Job selectedJob = jobList.get(position);
            Intent intent = new Intent(SearchResultActivity.this, JobDetailsActivity.class);
            intent.putExtra("job", selectedJob); // Pass the selected job
            startActivity(intent);
        });

        viewMapButton.setOnClickListener(v -> {
            if (jobList.isEmpty()) {
                Log.e("JobListingsActivity", "jobList is empty");
            } else {
                Intent intent = new Intent(SearchResultActivity.this, GoogleMapsActivity.class);
                intent.putExtra("jobList", jobList); // Pass the job list to GoogleMapsActivity
                startActivity(intent);
            }
        });

        goBackButton.setOnClickListener(v -> {
            // Navigate back to the homepage
            Intent intent = new Intent(SearchResultActivity.this, JobSearchActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private String[] getSelectedOptions() {
        Intent intent = getIntent();
        String[] selectedOptions= intent.getStringArrayExtra("selectedOption");
        return selectedOptions;
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
                    String pay = jobSnapshot.child("pay").getValue(String.class).trim();
                    String description = jobSnapshot.child("description").getValue(String.class);
                    double latitude = jobSnapshot.child("latitude").getValue(Double.class);
                    double longitude = jobSnapshot.child("longitude").getValue(Double.class);
                    List<String> questions = (List<String>) jobSnapshot.child("questions").getValue();
                    String posterEmail = jobSnapshot.child("poster's email").getValue(String.class);
                    String posterName = jobSnapshot.child("posted by").getValue(String.class);

                    //set up boolen condition only add the correct job based on give criteria
                    boolean typeCriteria = (options[0].equals(type) || options[0].equals("Any") );
                    boolean payCriteria = true;
                    if(!options[1].equals("Any")) {
                        payCriteria = isLarger(options[1],pay);
                    }
                    boolean locationCriteria =  (options[2].equals(location) || options[2].equals("Any") );
                    //if the correct criteria are met
                    if( typeCriteria && payCriteria && locationCriteria){
                        Job job = new Job(title, location, description, type, pay, latitude, longitude, questions, posterEmail, posterName);
                        jobList.add(job);
                    }
                }
                adapter.notifyDataSetChanged(); // Refresh the list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("JobListingsActivity", "Failed to fetch jobs: " + error.getMessage());
                Toast.makeText(SearchResultActivity.this, "Failed to fetch jobs.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isLarger(String chosen, String jobPay){
        //get substring from 1 to remove $
        chosen = chosen.substring(1,chosen.length()-3); // -3 to remove the "/hr" part
        jobPay = jobPay.substring(1,jobPay.length()-5); // -5 to remove the "/hour" part
        //convert string to int
        int minSal = Integer.parseInt( chosen.trim());
        int currentSal = Integer.parseInt( jobPay.trim());
        return  minSal <= currentSal ;
    }
}
