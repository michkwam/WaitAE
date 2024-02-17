package com.example.waitae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterPage extends AppCompatActivity {

    EditText signUpName, signUpUsername, signUpEmail, signUpPassword;
    TextView loginLink;
    Button signUpButton;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signUpName = findViewById(R.id.name);
        signUpUsername = findViewById(R.id.regUsername);
        signUpEmail = findViewById(R.id.email);
        signUpPassword = findViewById(R.id.regPassword);
        signUpButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPage = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(loginPage);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                db = FirebaseDatabase.getInstance();
                ref = db.getReference("patients");

                String fullName = signUpName.getText().toString();
                String username = signUpUsername.getText().toString();
                String email = signUpEmail.getText().toString();
                String password = signUpPassword.getText().toString();

                PatientClass newPatient = new PatientClass(fullName, username, email, password);
                ref.child(username).setValue(newPatient);

                Toast.makeText(RegisterPage.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                Intent mainPage = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(mainPage);
            }
        });
    }
}