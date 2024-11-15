package com.example.diversamente;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Integer counter = 0;
    private TextView lblUUID, lblWelcomeMessage;
    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lblUUID = findViewById(R.id.lblCounter);
        lblWelcomeMessage = findViewById(R.id.lblWelcomeMessage); // Referencia al TextView de bienvenida
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Inicializar Firebase Auth y usuario
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diversamente-1d32d-default-rtdb.firebaseio.com/");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        findViewById(R.id.btnChatbot).setOnClickListener(v -> openChatbot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            getCounter();
            showWelcomeMessage();
        } else {
            Log.e("HomeActivity", "No user is logged in");
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            // Mostrar diálogo con información sobre "¿Quiénes somos?"
            new AlertDialog.Builder(this)
                    .setTitle("¿Quiénes somos?")
                    .setMessage("En Diversa-mente, nos dedicamos a brindar información y apoyo en temas relacionados con la salud mental.\n" +
                            "Nuestro objetivo es ofrecer recursos, guías y orientación para mejorar el bienestar mental de las personas.")
                    .setPositiveButton("Aceptar", null) // Botón para cerrar el diálogo
                    .show();
        } else if (id == R.id.nav_perfil) {
            // Redirigir a perfil
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_test) {
            // Redirigir a test
            startActivity(new Intent(this, TestActivity.class));
        } else if (id == R.id.nav_logout) {
            // Mostrar diálogo de confirmación para cerrar sesión
            new AlertDialog.Builder(this)
                    .setTitle("Cerrar Sesión")
                    .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        mAuth.signOut();
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Cierra la actividad actual
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openChatbot() {
        try {
            Intent intent = new Intent(getApplicationContext(), SaludMental.class);
            startActivity(intent);
        } catch (Exception error) {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getCounter() {
        if (user != null) {
            databaseReference.child("users").child(user.getUid()).child("counter").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        Long counterValue = task.getResult().getValue(Long.class);
                        if (counterValue != null) {
                            counter = counterValue.intValue();
                            lblUUID.setText(String.valueOf(counter));
                        } else {
                            lblUUID.setText("0");
                        }
                    } else {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                }
            });
        }
    }

    // Método para mostrar el nombre del usuario en la bienvenida
    private void showWelcomeMessage() {
        if (user != null) {
            String welcomeText = "¡Bienvenido/a, " + user.getDisplayName() + "!";
            lblWelcomeMessage.setText(welcomeText);
        }
    }
}
