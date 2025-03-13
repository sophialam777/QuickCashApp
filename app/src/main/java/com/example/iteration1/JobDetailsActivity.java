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

import java.util.HashMap;
import java.util.List;

public class JobDetailsActivity extends AppCompatActivity {

    public Uri selectedResumeUri;
    private TextView jobTitle, jobDescription, jobLocation, jobType, jobPay;
    private Button applyButton, goBackButton;
    private EditText question1Input, question2Input;
    private TextView question1Label, question2Label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        // Initialize views
        jobTitle = findViewById(R.id.job_title);
        jobLocation = findViewById(R.id.job_loc);
        jobDescription = findViewById(R.id.job_description);
        jobType = findViewById(R.id.job_type);
        jobPay = findViewById(R.id.job_pay);
        applyButton = findViewById(R.id.apply_button);
        goBackButton = findViewById(R.id.go_back_button);
        question1Label = findViewById(R.id.question1_label);
        question1Input = findViewById(R.id.question1_input);
        question2Label = findViewById(R.id.question2_label);
        question2Input = findViewById(R.id.question2_input);

        // Get job object and set details
        Job selectedJob = (Job) getIntent().getSerializableExtra("job");
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

        // Handle Attach Resume button click
        findViewById(R.id.attach_resume_button).setOnClickListener(v -> openFilePicker());

        // Handle Apply button click
        applyButton.setOnClickListener(v -> {
            if (selectedJob != null) {
                handleApplyButtonClick(selectedJob);
            } else {
                Toast.makeText(this, "Job details not found.", Toast.LENGTH_LONG).show();
            }
        });

        // Handle Go Back button click
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(JobDetailsActivity.this, JobListingsActivity.class);
            startActivity(intent);
            finish();
        });
    }

    //Open file picker to select resume (*/* means Any type of file)
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"application/pdf", "application/msword"});
        startActivityForResult(intent, 1);
    }

    private void handleApplyButtonClick(Job selectedJob) {
        String answer1 = question1Input.getText().toString().trim();
        String answer2 = question2Input.getText().toString().trim();

        if (selectedResumeUri == null) {
            // Show a message prompting the user to attach a resume if they try to apply without a resume
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference applicationsRef = database.getReference("applications");

        // Create a unique key for the application
        String applicationId = applicationsRef.push().getKey();

        // Get the current user's email (or other identifier)
        String applicantEmail = UserSession.email; // Assuming you have a UserSession class

        // Create a map to store application data
        HashMap<String, Object> applicationData = new HashMap<>();
        applicationData.put("jobTitle", job.getTitle());
        applicationData.put("applicantEmail", applicantEmail);
        applicationData.put("resumeUri", selectedResumeUri.toString());
        applicationData.put("answer1", answer1);
        applicationData.put("answer2", answer2);

        // Save the application data to Firebase
        if (applicationId != null) {
            applicationsRef.child(applicationId).setValue(applicationData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Application submitted successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to submit application. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    //Handle result after file selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri resumeUri = data.getData();
            selectedResumeUri = resumeUri;
            if (resumeUri != null) {
                Toast.makeText(this, "Resume selected: " + resumeUri.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}