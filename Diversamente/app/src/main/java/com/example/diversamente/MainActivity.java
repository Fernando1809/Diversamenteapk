package com.example.diversamente;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Asegúrate de que este archivo exista

        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(view -> {
            // Iniciar la actividad de registro
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
