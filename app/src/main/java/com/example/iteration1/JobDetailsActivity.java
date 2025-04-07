package com.example.iteration1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.validator.Job;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class JobDetailsActivity extends AppCompatActivity {

    public Uri selectedResumeUri;
    private TextView jobTitle, jobDescription, jobType, jobPay, jobLocation,question1Label, question2Label;
    private Button applyButton, goBackButton, savePreferencesButton;
    private EditText question1Input, question2Input;
    private Job selectedJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        initializeViews();
        intitializeOnClickListeners();
    }

    private void initializeViews(){
        // Initialize views
        jobTitle = findViewById(R.id.job_title);
        jobDescription = findViewById(R.id.job_description);
        jobLocation = findViewById(R.id.job_loc);
        jobType = findViewById(R.id.job_type);
        jobPay = findViewById(R.id.job_pay);
        applyButton = findViewById(R.id.apply_button);
        goBackButton = findViewById(R.id.go_back_button);
        question1Label = findViewById(R.id.question1_label);
        question1Input = findViewById(R.id.question1_input);
        question2Label = findViewById(R.id.question2_label);
        question2Input = findViewById(R.id.question2_input);
        savePreferencesButton = findViewById(R.id.save_preferences_button);

        // Get job object from Intent
        selectedJob = (Job) getIntent().getSerializableExtra("job");
        if (selectedJob != null) {
            jobTitle.setText(selectedJob.getTitle());
            jobDescription.setText(selectedJob.getDescription());
            jobLocation.setText("Location: " + selectedJob.getLocation());
            jobType.setText("Type: " + selectedJob.getType());
            jobPay.setText("Pay: " + selectedJob.getPay());

            // Display job-specific questions
            List<String> questions = selectedJob.getQuestions();
            if (questions != null && !questions.isEmpty()) {
                question1Label.setVisibility(View.VISIBLE);
                question1Input.setVisibility(View.VISIBLE);
                question1Label.setText(questions.get(0));

                if (questions.size() > 1) {
                    question2Label.setVisibility(View.VISIBLE);
                    question2Input.setVisibility(View.VISIBLE);
                    question2Label.setText(questions.get(1));
                }
            }
        }
    }

    private void intitializeOnClickListeners(){
        // Attach Resume button
        findViewById(R.id.attach_resume_button).setOnClickListener(v -> openFilePicker());

        // Apply button
        applyButton.setOnClickListener(v -> {
            if (selectedJob != null) {
                handleApplyButtonClick(selectedJob);
            } else {
                Toast.makeText(this, "Job details not found.", Toast.LENGTH_LONG).show();
            }
        });

        // Go Back button
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(JobDetailsActivity.this, JobListingsActivity.class);
            startActivity(intent);
            finish();
        });

        savePreferencesButton.setOnClickListener(v -> {
            if (selectedJob != null) {
                savePreferences(selectedJob);
            } else {
                Toast.makeText(this, "Job details not available.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"application/pdf", "application/msword"});
        startActivityForResult(intent, 1);
    }

    private void handleApplyButtonClick(Job selectedJob) {
        String answer1 = question1Input.getText().toString().trim();
        String answer2 = question2Input.getText().toString().trim();

        if (selectedResumeUri == null) {
            Toast.makeText(this, "Please attach a resume before submitting your application.", Toast.LENGTH_LONG).show();
            return;
        }

        if (answer1.isEmpty()) {
            Toast.makeText(this, "Please answer all required questions.", Toast.LENGTH_LONG).show();
            return;
        }

        // Save the application
        saveApplication(selectedJob, answer1, answer2);
        Toast.makeText(this, "Application Submitted!", Toast.LENGTH_SHORT).show();
    }

    private void saveApplication(Job job, String answer1, String answer2) {
        DatabaseReference applicationsRef = FirebaseDatabase.getInstance().getReference("applications");

        // Unique key for the application
        String applicationId = applicationsRef.push().getKey();

        // Current user info
        String applicantEmail = UserSession.email;
        String applicantName = UserSession.name;
        String fcmToken = UserSession.fcmToken;

        // Store application data
        JobApplication newApplication = new JobApplication(applicationId, job.getTitle(), job.getPosterEmail(), applicantEmail, selectedResumeUri.toString(), answer1, answer2, applicantName, "Submitted", fcmToken);

        if (applicationId != null) {
            applicationsRef.child(applicationId).setValue(newApplication)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Application submitted successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to submit application. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedResumeUri = data.getData();
            if (selectedResumeUri != null) {
                Toast.makeText(this, "Resume selected: " + selectedResumeUri.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savePreferences(Job job) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get user's email as document ID
        String applicantEmail = UserSession.email; // e.g., "mohammedzaksaj@gmail.com"

        // Create preferences data
        HashMap<String, Object> preferences = new HashMap<>();
        preferences.put("pay", job.getPay());   // Get pay from selected job
        preferences.put("type", job.getType()); // Get type from selected job

        // Save preferences under "userpref" collection using email as document ID
        db.collection("userpref")
                .document(applicantEmail.replace(".", ",")) // Replace dots with commas
                .set(preferences)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Preferences saved successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to save preferences. Please try again.", Toast.LENGTH_LONG).show();
                });
    }
}
