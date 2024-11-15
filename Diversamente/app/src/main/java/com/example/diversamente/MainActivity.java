package com.example.diversamente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText txtEmail, txtPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Verifica que este archivo exista y sea correcto

        // Inicializa Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Verifica si el usuario ya está autenticado
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            navigateToHome();
        }

        // Inicializa los elementos de la interfaz
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Configura los listeners
        btnSignUp.setOnClickListener(view -> startActivity(new Intent(this, SignUpActivity.class)));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (!isValidEmail(email)) {
            MsgToast("Por favor ingresa un email válido");
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            MsgToast("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            navigateToHome();
                        } else {
                            MsgToast("Error al iniciar sesión: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private boolean isValidEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void MsgToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
