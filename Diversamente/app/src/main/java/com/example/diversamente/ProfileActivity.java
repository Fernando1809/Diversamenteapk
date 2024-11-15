package com.example.diversamente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileActivity extends AppCompatActivity {

    private EditText edtUserName, edtUserEmail;
    private Button btnBackToHome, btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edtUserName = findViewById(R.id.edtUserName);
        edtUserEmail = findViewById(R.id.edtUserEmail);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // Obtener el usuario actual de Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Mostrar nombre y correo del usuario en los EditText
            edtUserName.setText(user.getDisplayName());
            edtUserEmail.setText(user.getEmail());
        } else {
            edtUserName.setText("Usuario no encontrado");
            edtUserEmail.setText("Correo no disponible");
        }

        // Configurar el botón para guardar los cambios
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = edtUserName.getText().toString().trim();
                String newEmail = edtUserEmail.getText().toString().trim();

                // Verificar que los campos no estén vacíos
                if (!newName.isEmpty() && !newEmail.isEmpty()) {
                    updateUserInfo(newName, newEmail);
                } else {
                    Toast.makeText(ProfileActivity.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el botón para volver al inicio
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a HomeActivity
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Finaliza la actividad de perfil
            }
        });
    }

    private void updateUserInfo(String name, String email) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Actualizar nombre
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Nombre actualizado", Toast.LENGTH_SHORT).show();
                        }
                    });

            // Si el correo cambia, se necesita reautenticar al usuario
            if (!user.getEmail().equals(email)) {
                user.updateEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "Correo actualizado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProfileActivity.this, "Error al actualizar correo", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }
}
