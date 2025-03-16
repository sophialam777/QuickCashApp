package com.example.iteration1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PostJob extends AppCompatActivity {

    private EditText jobTitleInput, jobLocationInput, jobTypeInput, jobPayInput, jobDescriptionInput;
    private EditText jobLatitudeInput, jobLongitudeInput, jobQuestionsInput;
    private Button postJobButton;
    public static String toast_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_posting);

        // Initialize views
        jobTitleInput = findViewById(R.id.job_title_input);
        jobLocationInput = findViewById(R.id.job_location_input);
        jobTypeInput = findViewById(R.id.job_type_input);
        jobPayInput = findViewById(R.id.job_pay_input);
        jobDescriptionInput = findViewById(R.id.job_description_input);
        jobLatitudeInput = findViewById(R.id.job_latitude_input);
        jobLongitudeInput = findViewById(R.id.job_longitude_input);
        jobQuestionsInput = findViewById(R.id.job_questions_input);
        postJobButton = findViewById(R.id.post_job_button);

        // Handle Post Job button click
        postJobButton.setOnClickListener(v -> postJob());
    }

    private void postJob() {
        // Get input values
        String jobTitle = jobTitleInput.getText().toString().trim();
        String jobLocation = jobLocationInput.getText().toString().trim();
        String jobType = jobTypeInput.getText().toString().trim();
        String jobPay = jobPayInput.getText().toString().trim();
        String jobDescription = jobDescriptionInput.getText().toString().trim();
        String latitudeStr = jobLatitudeInput.getText().toString().trim();
        String longitudeStr = jobLongitudeInput.getText().toString().trim();
        String questionsStr = jobQuestionsInput.getText().toString().trim();

        // Validate mandatory fields
        if (jobTitle.isEmpty() || jobLocation.isEmpty() || jobType.isEmpty() || jobPay.isEmpty() || jobDescription.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty() || questionsStr.isEmpty()) {
            Toast.makeText(this, "Please fill all mandatory fields.", Toast.LENGTH_LONG).show();
            toast_msg = "Please fill all mandatory fields.";
            return;
        }

        // Parse latitude and longitude
        double latitude, longitude;
        try {
            latitude = Double.parseDouble(latitudeStr);
            longitude = Double.parseDouble(longitudeStr);
            toast_msg = "";
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid latitude or longitude.", Toast.LENGTH_LONG).show();
            toast_msg = "Invalid latitude or longitude.";
            return;
        }

        // Split questions into a List
        List<String> questions = Arrays.asList(questionsStr.split(","));

        // Save job details to Firebase
        saveJobToFirebase(jobTitle, jobLocation, jobType, jobPay, jobDescription, latitude, longitude, questions);
    }

    private void saveJobToFirebase(String jobTitle, String jobLocation, String jobType, String jobPay, String jobDescription, double latitude, double longitude, List<String> questions) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference jobsRef = database.getReference("jobs");

        // Create a unique key for the job
        String jobId = jobsRef.push().getKey();

        // Create a map to store job data
        HashMap<String, Object> jobData = new HashMap<>();
        jobData.put("title", jobTitle);
        jobData.put("location", jobLocation);
        jobData.put("type", jobType);
        jobData.put("pay", jobPay);
        jobData.put("description", jobDescription);
        jobData.put("latitude", latitude);
        jobData.put("longitude", longitude);
        jobData.put("questions", questions);

        // Log the job data
        Log.d("PostJob", "Job Data: " + jobData.toString());

        // Save the job data to Firebase
        if (jobId != null) {
            jobsRef.child(jobId).setValue(jobData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("PostJob", "Job posted successfully!");
                            Toast.makeText(this, "Job posted successfully!", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity after posting
                        } else {
                            Log.e("PostJob", "Failed to post job: " + task.getException().getMessage());
                            Toast.makeText(this, "Failed to post job. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Log.e("PostJob", "Failed to generate job ID.");
            Toast.makeText(this, "Failed to generate job ID.", Toast.LENGTH_LONG).show();
        }
    }
}