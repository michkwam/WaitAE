package com.example.waitae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private CardView profile;
    private CardView logOut;
    private CardView ai_advice;

    PatientClass user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logOut = findViewById(R.id.logout_btn);
        profile = findViewById(R.id.profile_view);
        ai_advice = findViewById(R.id.ai_advice);

        logOut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent loginPage = new Intent(MainActivity.this, LoginPage.class);
                startActivity(loginPage);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(MainActivity.this, Profile.class);

                Bundle user = getIntent().getExtras();
                if (user != null)
                    profilePage.putExtras(user);
                    startActivity(profilePage);
            }
        });

        ai_advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AIChat = new Intent(MainActivity.this, AIChatBox.class);
                startActivity(AIChat);
            }
        });
    }
}