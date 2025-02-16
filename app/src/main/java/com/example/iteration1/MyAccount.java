package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyAccount extends AppCompatActivity {

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
    }

    public void initializeAccountInfo(){
        TextView userInfo = findViewById(R.id.user_info);
        String info = "Name: " + UserSession.name + "\nEmail: " + UserSession.email + "\nContact Number: " + UserSession.contact + "\nRole" + UserSession.role + "\n";
        userInfo.setText(info);
    }

    public void initializeOnClickListener(){
        Button logout = findViewById(R.id.logout_btn);
        logout.setOnClickListener(v -> {
            UserSession.logout();
            Intent intent = new Intent(MyAccount.this, Login.class);
            startActivity(intent);
        });
    }
}
