package com.example.diversamente;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class ChatbotActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


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
        } else if (id == R.id.nav_home) {
            // Acción para "Inicio"
        } else if (id == R.id.nav_test) {
            startActivity(new Intent(this, TestActivity.class));
        } else if (id == R.id.nav_logout) {
            // Mostrar diálogo de confirmación para cerrar sesión
            new AlertDialog.Builder(this)
                    .setTitle("Cerrar Sesión")
                    .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                    .setPositiveButton("Sí", (dialog, which) -> {
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
            Intent intent = new Intent(getApplicationContext(), ChatbotActivity.class);
            startActivity(intent);
        }catch (Exception error){
            Toast.makeText(this, error.getMessage() + "fallos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
