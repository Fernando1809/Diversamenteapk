package com.example.diversamente;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Asegúrate de que este archivo exista

        Button btnRegister = findViewById(R.id.btnRegister); // Verifica que el ID sea correcto

        btnRegister.setOnClickListener(view -> {
            try {
                // Acción de registro aquí
                Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish(); // Cierra RegisterActivity después del registro
            } catch (Exception e) {
                e.printStackTrace(); // Imprime la traza de la excepción
                Toast.makeText(RegisterActivity.this, "Error en el registro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
