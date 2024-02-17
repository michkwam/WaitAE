package com.example.waitae;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class RegisterPage extends AppCompatActivity {

    EditText name, username, email, password;
    TextView loginLink;

    Button signUp;

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

        name = findViewById(R.id.name);
        username = findViewById(R.id.regUsername);
        email = findViewById(R.id.email);
        password = findViewById(R.id.regPassword);
        signUp = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);
    }
}