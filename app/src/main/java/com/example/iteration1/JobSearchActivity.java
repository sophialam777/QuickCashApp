package com.example.iteration1;
import android.content.Intent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;

public class JobSearchActivity extends AppCompatActivity {

        private String[] jobTypeOptions = { "Food Delivery"};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.jobsearch);
        }
}


