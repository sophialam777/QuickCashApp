package com.example.iteration1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RatingActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText feedbackInput;
    private Button submitButton;

    // The email of the user being rated is passed via Intent extras
    private String ratedUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar = findViewById(R.id.rating_bar);
        feedbackInput = findViewById(R.id.feedback_input);
        submitButton = findViewById(R.id.submit_rating_button);

        // Retrieve the email of the user to be rated
        ratedUserEmail = getIntent().getStringExtra("ratedUserEmail");
        if (ratedUserEmail == null) {
            ratedUserEmail = "";
        }

        submitButton.setOnClickListener(v -> submitRating());
    }

    private void submitRating() {
        float ratingValue = ratingBar.getRating();
        String feedback = feedbackInput.getText().toString().trim();

        if (ratingValue == 0) {
            Toast.makeText(this, "Please select a rating value.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingsRef = database.getReference("ratings");

        String ratingId = ratingsRef.push().getKey();
        if (ratingId == null) {
            Toast.makeText(this, "Error generating rating ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Assume the current user (from UserSession) is the rater
        String raterUserEmail = UserSession.email;

        Rating rating = new Rating(ratingId, ratedUserEmail, raterUserEmail, ratingValue, feedback);

        ratingsRef.child(ratingId).setValue(rating)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RatingActivity.this, "Rating submitted successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RatingActivity.this, "Failed to submit rating. Please try again.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
