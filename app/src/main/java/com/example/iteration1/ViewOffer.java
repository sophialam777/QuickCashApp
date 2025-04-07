package com.example.iteration1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewOffer extends AppCompatActivity {

    private JobApplication selectedApplication;
    private Button back, accept, reject;
    private TextView info;
    private JobOffer offer;

    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.view_offer);

        initializeViews();
        initializeOnClickListeners();
        findOffer();
    }

    private void initializeViews(){
        selectedApplication = (JobApplication)getIntent().getSerializableExtra("application");
        back = findViewById(R.id.view_offer_back);
        accept = findViewById(R.id.accept_offer_button);
        reject = findViewById(R.id.reject_offer_button);
        info = findViewById(R.id.offer_info);
    }

    private void initializeOnClickListeners(){
        back.setOnClickListener(v -> {
            Intent intent = new Intent(this, EmployeeApplicationStatusActivity.class);
            startActivity(intent);
        });

        accept.setOnClickListener(v -> confirmAcceptance());
        reject.setOnClickListener(v -> confirmRejection());
    }

    private void findOffer(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbref = database.getReference("jobOffers");
        Query query = dbref.orderByChild("applicationID").equalTo(selectedApplication.getApplicationID());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        offer = snapshot.getValue(JobOffer.class);
                        assert offer != null;
                        setOfferInfo(offer);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void setOfferInfo(JobOffer offer){
        String content = "Job Title: " + selectedApplication.getJobTitle() + "\n\n"
                + "Start Date: " + offer.getStartDate() + "\n"
                + "Location: " + offer.getLocation() + "\n"
                + "Salary: " + offer.getSalary() + "\n"
                + offer.getOther() + "\n";
        info.setText(content);
    }

    private void confirmRejection(){
        // create a popup for the user to confirm their decision
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reject Job Offer");
        builder.setMessage("Are you sure you want to reject this offer?\nThis cannot be undone.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            // reject the application if yes is pressed
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViewOffer.this, "Job Offer Rejected", Toast.LENGTH_LONG).show();
                selectedApplication.setStatus("Offer Rejected");
                Intent intent = new Intent(ViewOffer.this, EmployeeApplicationStatusActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            // do nothing if no is pressed
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void confirmAcceptance(){
        // create a popup for the user to confirm their decision
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Accept Job Offer");
        builder.setMessage("Confirm your acceptance of this job offer.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            // reject the application if yes is pressed
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViewOffer.this, "Job Offer Accepted!", Toast.LENGTH_LONG).show();
                selectedApplication.setStatus("Offer Accepted");
                Intent intent = new Intent(ViewOffer.this, EmployeeApplicationStatusActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            // do nothing if no is pressed
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}