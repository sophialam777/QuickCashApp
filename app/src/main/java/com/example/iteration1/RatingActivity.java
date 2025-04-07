package com.example.iteration1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class RatingActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText feedbackInput, ratedUser;
    private Button submitButton;
    private String user;
    private boolean found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar = findViewById(R.id.rating_bar);
        feedbackInput = findViewById(R.id.feedback_input);
        submitButton = findViewById(R.id.submit_rating_button);
        ratedUser = findViewById(R.id.rated_user);

        checkForUser();

        submitButton.setOnClickListener(v -> {
            checkForUser();
        });
    }

    private void checkForUser(){
        user = ratedUser.getText().toString().trim();

        if (user == null){
            Toast.makeText(this, "User not found", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("users");
        Query query = dbref.orderByChild("email").equalTo(user);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Email exists in the database
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        found = true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error: " + databaseError.getMessage());
            }
        });

        if (!found){
            Toast.makeText(this, "User not found", Toast.LENGTH_LONG).show();
        } else {
            submitRating();
        }
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

        Rating rating = new Rating(ratingId, user, raterUserEmail, ratingValue, feedback);

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
