package com.example.diversamente;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class ChatbotActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // Configura la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configura el DrawerLayout y el ActionBarDrawerToggle para el menú de hamburguesa
        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configura el WebView
        WebView webView = findViewById(R.id.webview_chatbot);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilita JavaScript para el widget
        webView.loadUrl("https://app.gpt-trainer.com/widget/8feeaccbf3694ba1bab4751f423b189a");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Controla el botón de menú
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // Maneja las acciones según el ítem seleccionado
        switch (id) {
            case R.id.nav_home:
                // Acción para el ítem "Home"
                // Aquí puedes iniciar la actividad correspondiente o realizar otra acción
                break;
            case R.id.nav_about:
                // Acción para el ítem "About"
                break;
            case R.id.nav_test:
                // Acción para el ítem "Test"
                break;
            case R.id.nav_logout:
                // Acción para el ítem "Logout"
                break;
            // Agrega más casos según los elementos de tu menú
        }
        drawerLayout.closeDrawer(GravityCompat.START); // Cierra el drawer después de la selección
        return true;
    }
}
