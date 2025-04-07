package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private Button logout, viewApplications, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        initializeAccountInfo();
        initializeOnClickListener();
        loadUserRating();

        if(UserSession.role.equals("Employer")){
            viewApplications.setVisibility(View.INVISIBLE);
        } else {
            viewApplications.setVisibility(View.VISIBLE);
        }
    }

    public void initializeAccountInfo(){
        TextView userInfo = findViewById(R.id.user_info);
        String info = "Name: " + UserSession.name + "\nEmail: " + UserSession.email +
                "\nContact Number: " + UserSession.contact + "\nRole: " + UserSession.role;
        userInfo.setText(info);
    }

    public void initializeOnClickListener(){
        // Logout button
        logout = findViewById(R.id.logout_btn);
        logout.setOnClickListener(v -> {
            UserSession.logout();
            Intent intent = new Intent(MyAccount.this, Login.class);
            startActivity(intent);
        });

        // New Button: My Applications
        viewApplications = findViewById(R.id.btn_view_applications);
        viewApplications.setOnClickListener(v -> {
            Intent intent = new Intent(MyAccount.this, EmployeeApplicationStatusActivity.class);
            startActivity(intent);
        });

        back = findViewById(R.id.my_account_back_button);
        back.setOnClickListener(v -> {
            Intent intent;
            if(UserSession.role.equals("Employee")){
                intent = new Intent(MyAccount.this, EmployeeDashboard.class);
            } else if (UserSession.role.equals("Employer")){
                intent = new Intent(MyAccount.this, EmployerDashboard.class);
            } else {
                Toast.makeText(this, "Error, redirecting user to login page", Toast.LENGTH_LONG).show();
                intent = new Intent(MyAccount.this, Login.class);
                UserSession.logout();
            }
            startActivity(intent);
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
