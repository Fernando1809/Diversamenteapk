package com.example.diversamente;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChatbotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // Configura la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configura el WebView para el chatbot
        WebView webView = findViewById(R.id.webview_chatbot);
        webView.setWebViewClient(new WebViewClient()); // Asegura que las URLs se abran dentro del WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilita JavaScript para el widget
        webView.loadUrl("https://app.gpt-trainer.com/widget/8feeaccbf3694ba1bab4751f423b189a");

        // Configura el botón para regresar al HomeActivity
        Button btnExitChatbot = findViewById(R.id.btn_exit_chatbot);
        btnExitChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa a la HomeActivity
                Intent intent = new Intent(ChatbotActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Finaliza ChatbotActivity para que el usuario no pueda regresar
            }
        });
    }
}
