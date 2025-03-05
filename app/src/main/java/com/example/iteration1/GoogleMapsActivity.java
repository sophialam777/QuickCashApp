package com.example.iteration1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import com.example.iteration1.validator.Job;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Job> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        //Get the job list from the intent
        jobList = (ArrayList<Job>) getIntent().getSerializableExtra("jobList");

        //Initialize the map with SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Set a default location to Halifax, Nova Scotia
        LatLng halifax = new LatLng(44.6452, -63.5736);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(halifax, 12));

        if (jobList != null) {
            //Iterate over the job list and place markers on the map
            for (Job job : jobList) {
                LatLng jobLocation = new LatLng(job.getLatitude(), job.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions().position(jobLocation).title(job.getTitle()));

                //Set a tag for the marker with the job object
                marker.setTag(job);
            }

            mMap.setOnMarkerClickListener(marker -> {
                Job clickedJob = (Job) marker.getTag();
                if (clickedJob != null) {
                    //Show job details in a Dialog
                    showJobDetailsDialog(clickedJob);
                }
                return false;
            });
        }
    }

    private void showJobDetailsDialog(Job job) {
        String jobDetails = "Title: " + job.getTitle() + "\n\n" +
                "Description: " + job.getDescription() + "\n\n";

        //Create an AlertDialog to display job details
        new AlertDialog.Builder(GoogleMapsActivity.this)
                .setTitle("Job Details")
                .setMessage(jobDetails)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }
}
