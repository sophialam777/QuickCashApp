package com.example.iteration1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.notfication.AccessTokenListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.Logger;
import com.google.firebase.messaging.FirebaseMessaging;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.InputStream;
import java.util.Collections;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PostJob extends AppCompatActivity {

    private static final Object CREDENTIALS_FILE_PATH = "quickcash3130-4607d-cf3b2b5f73d8.json";
    private EditText jobTitleInput, jobLocationInput, jobTypeInput, jobPayInput, jobDescriptionInput;
    private EditText jobLatitudeInput, jobLongitudeInput, jobQuestionsInput;
    private Button postJobButton;
    public static String toast_msg;
    private RequestQueue requestQueue;
    private static final String PUSH_NOTIFICATION_ENDPOINT = "https://fcm.googleapis.com/v1/projects/quickcash3130-4607d/messages:send";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_posting);
        requestQueue = Volley.newRequestQueue(this);

        FirebaseMessaging.getInstance().subscribeToTopic("jobs");
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
        if (jobTitle.isEmpty() || jobLocation.isEmpty() || jobType.isEmpty() || jobPay.isEmpty()
                || jobDescription.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty() || questionsStr.isEmpty()) {
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

        // Save job details to Firebase and check preferences after posting
        saveJobToFirebase(jobTitle, jobLocation, jobType, jobPay, jobDescription, latitude, longitude, questions);
    }


    private void saveJobToFirebase(String jobTitle, String jobLocation, String jobType, String jobPay,
                                   String jobDescription, double latitude, double longitude, List<String> questions) {
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


                            getFirebaseToken(new AccessTokenListener() {
                                @Override
                                public void onAccessTokenReceived(String token) {
                                    sendNotification(token, jobTitle, jobLocation);
                                }

                                @Override
                                public void onAccessTokenError(Exception exception) {
                                    Log.e("PostJob", "Error retrieving token: " + exception.getMessage());
                                    Toast.makeText(PostJob.this, "Failed to get FCM token", Toast.LENGTH_SHORT).show();
                                }
                            });

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




    private void sendNotification(String authToken,String jobTitle, String jobLocation) {
        try {
            // Build the notification payload
            JSONObject notificationJSONBody = new JSONObject();
            notificationJSONBody.put("title", "New Job For You");
            notificationJSONBody.put("body", "A new job  '" + jobTitle + "' is available in " + jobLocation);

            JSONObject dataJSONBody = new JSONObject();
            dataJSONBody.put("jobLocation", jobLocation);
            dataJSONBody.put("jobTitle", jobTitle);

            JSONObject messageJSONBody = new JSONObject();
            messageJSONBody.put("topic", "jobs");  // Target all users subscribed to the "jobs" topic
            messageJSONBody.put("notification", notificationJSONBody);
            messageJSONBody.put("data", dataJSONBody);

            JSONObject pushNotificationJSONBody = new JSONObject();
            pushNotificationJSONBody.put("message", messageJSONBody);

            // Log the complete JSON payload for debugging
            Log.d("NotificationBody", "JSON Body: " + pushNotificationJSONBody.toString());

            // Create the request
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    PUSH_NOTIFICATION_ENDPOINT,
                    pushNotificationJSONBody,
                    response -> {
                        Log.d("NotificationResponse", "Response: " + response.toString());
                        Toast.makeText(this, "Notification Sent Successfully", Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        Log.e("NotificationError", "Error Response: " + error.toString());
                        if (error.networkResponse != null) {
                            Log.e("NotificationError", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("NotificationError", "Error Data: " + new String(error.networkResponse.data));
                        }
                        Toast.makeText(this, "Failed to Send Notification", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");

                    // Use OAuth token here instead of the Firebase token
                    String oauthToken = getOAuthToken();
                    if (oauthToken != null) {
                        headers.put("Authorization", "Bearer " + oauthToken);
                    } else {
                        Log.e("NotificationError", "Failed to get OAuth token.");
                    }

                    return headers;
                }
            };

            // Add the request to the queue
            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("NotificationJSONException", "Error creating notification JSON: " + e.getMessage());
            Toast.makeText(this, "Error creating notification payload", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void getFirebaseToken(AccessTokenListener listener) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("PostJob", "Fetching FCM token failed", task.getException());
                        listener.onAccessTokenError(task.getException());
                        return;
                    }

                    // Get the FCM token
                    String authToken = task.getResult();
                    listener.onAccessTokenReceived(authToken);
                });
    }
    private String getOAuthToken() {
        try {
            // Open the service account credentials file from the assets folder
            InputStream credentialsStream = getAssets().open("quickcash3130-4607d-cf3b2b5f73d8.json");

            // Load the credentials using the GoogleCredentials class
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/firebase.messaging"));

            // Refresh the credentials to ensure they are up-to-date
            credentials.refreshIfExpired();

            // Return the OAuth token
            return credentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle error properly, return null if unable to get the token
        }
    }





}
