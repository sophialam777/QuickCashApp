package com.example.iteration1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccount extends AppCompatActivity {

    private TextView ratingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.my_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeAccountInfo();
        initializeOnClickListener();
        loadUserRating();
    }

    public void initializeAccountInfo(){
        TextView userInfo = findViewById(R.id.user_info);
        String info = "Name: " + UserSession.name + "\nEmail: " + UserSession.email +
                "\nContact Number: " + UserSession.contact + "\nRole: " + UserSession.role;
        userInfo.setText(info);
    }

    public void initializeOnClickListener(){
        // Logout button
        Button logout = findViewById(R.id.logout_btn);
        logout.setOnClickListener(v -> {
            UserSession.logout();
            // Navigate to Login activity (implementation not shown)
        });

        // My Applications button (from User Story 14)
        Button viewApplications = findViewById(R.id.btn_view_applications);
        viewApplications.setOnClickListener(v -> {
            // Navigate to EmployeeApplicationStatusActivity (implementation not shown)
        });
    }

    private void loadUserRating() {
        ratingTextView = findViewById(R.id.profile_rating);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingsRef = database.getReference("ratings");

        ratingsRef.orderByChild("ratedUserEmail").equalTo(UserSession.email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        float totalRating = 0;
                        int count = 0;
                        for (DataSnapshot ratingSnapshot : snapshot.getChildren()) {
                            Float ratingValue = ratingSnapshot.child("ratingValue").getValue(Float.class);
                            if (ratingValue != null) {
                                totalRating += ratingValue;
                                count++;
                            }
                        }
                        float average = count > 0 ? totalRating / count : 0;
                        ratingTextView.setText("Rating: " + average);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(MyAccount.this, "Failed to load rating.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
