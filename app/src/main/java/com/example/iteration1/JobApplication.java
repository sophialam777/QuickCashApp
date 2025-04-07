package com.example.iteration1;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

public class JobApplication implements Serializable {

    private String jobTitle, posterEmail, applicantEmail, resumeUri, answer1, answer2, applicantName, status, fcmToken, applicationID;

    public JobApplication(String applicationID, String jobTitle, String posterEmail, String applicantEmail, String resumeUri, String answer1, String answer2, String applicantName, String status){
        this.jobTitle = jobTitle;
        this.posterEmail = posterEmail;
        this.applicantEmail = applicantEmail;
        this.resumeUri = resumeUri;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.applicantName = applicantName;
        this.status = status;
        this.applicationID = applicationID;
    }

    public JobApplication(String applicationID, String jobTitle, String posterEmail, String applicantEmail, String resumeUri, String answer1, String answer2, String applicantName, String status, String fcmToken){
        this.jobTitle = jobTitle;
        this.posterEmail = posterEmail;
        this.applicantEmail = applicantEmail;
        this.resumeUri = resumeUri;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.applicantName = applicantName;
        this.status = status;
        this.fcmToken = fcmToken;
        this.applicationID = applicationID;
    }

    public String getPosterEmail(){ return posterEmail; }
    public String getJobTitle(){ return jobTitle; }
    public String getApplicantEmail(){ return applicantEmail; }
    public String getResumeUri(){ return resumeUri; }
    public String getAnswer1(){ return answer1; }
    public String getAnswer2(){ return answer2; }
    public String getApplicantName(){ return applicantName; }
    public String getStatus(){ return status; }
    public String getFcmToken(){ return fcmToken; }
    public String getApplicationID(){ return applicationID; }
    public void setStatus(String status){
        this.status = status;

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("applications");
        Query query = dbref.orderByChild("applicationID").equalTo(applicationID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String applicationId = snapshot.getKey();
                        HashMap<String, Object> updates = new HashMap<>();
                        updates.put("status", status);
                        dbref.child(applicationId).updateChildren(updates);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public String getApplicantInfo(){ return applicantName + " (" + applicantEmail + ")"; }
    public String toString(){ return applicantName + " (" + applicantEmail + ") - " + status; }
}