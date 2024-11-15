package com.example.diversamente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3;
    private Button btnNext, btnBackToHome;

    // Nuevas preguntas y opciones
    private String[] questions = {
            "¿Te sientes ansioso frecuentemente?", // Ansiedad
            "¿Tienes dificultad para concentrarte?", // TDAH
            "¿Te sientes triste la mayor parte del tiempo?", // Depresión
            "¿Tienes problemas para comer o mantener tu peso?", // Anorexia
            "¿Te cuesta adaptarte a cambios inesperados?", // Ansiedad
            "¿Tiendes a pensar en exceso sobre las cosas?", // Trastorno obsesivo-compulsivo
            "¿Evitas reuniones o multitudes?", // Fobia social
            "¿Te cuesta manejar tus emociones o tienes cambios de humor repentinos?" // Trastorno límite de la personalidad
    };
    private String[][] options = {
            {"Sí", "No", "A veces"},
            {"Sí", "No", "A veces"},
            {"Sí", "No", "A veces"},
            {"Sí", "No", "A veces"},
            {"Sí", "No", "A veces"},
            {"Sí", "No", "A veces"},
            {"Sí", "No", "A veces"},
            {"Sí", "No", "A veces"}
    };

    // Variables de puntaje por trastorno
    private int ansiedadScore = 0, tdahScore = 0, depresionScore = 0,
            anorexiaScore = 0, tocScore = 0, fobiaSocialScore = 0, tlpScore = 0;

    private int currentQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvQuestion = findViewById(R.id.tvQuestion);
        rgOptions = findViewById(R.id.rgOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        btnNext = findViewById(R.id.btnNext);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        loadQuestion();

        btnNext.setOnClickListener(v -> {
            if (rgOptions.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Selecciona una opción", Toast.LENGTH_SHORT).show();
                return;
            }

            calculateScore();
            if (currentQuestion < questions.length - 1) {
                currentQuestion++;
                loadQuestion();
            } else {
                showDiagnosis();
            }
        });

        btnBackToHome.setOnClickListener(v -> {
            // Acción para regresar al inicio
            Intent intent = new Intent(TestActivity.this, MainActivity.class); // Asegúrate de que MainActivity sea tu pantalla principal
            startActivity(intent);
            finish();
        });
    }

    private void loadQuestion() {
        tvQuestion.setText(questions[currentQuestion]);
        rbOption1.setText(options[currentQuestion][0]);
        rbOption2.setText(options[currentQuestion][1]);
        rbOption3.setText(options[currentQuestion][2]);
        rgOptions.clearCheck();
    }

    private void calculateScore() {
        int selectedOption = rgOptions.indexOfChild(findViewById(rgOptions.getCheckedRadioButtonId()));
        switch (currentQuestion) {
            case 0:
            case 4:
                ansiedadScore += (selectedOption == 0 ? 2 : selectedOption == 2 ? 1 : 0);
                break;
            case 1:
                tdahScore += (selectedOption == 0 ? 2 : selectedOption == 2 ? 1 : 0);
                break;
            case 2:
                depresionScore += (selectedOption == 0 ? 2 : selectedOption == 2 ? 1 : 0);
                break;
            case 3:
                anorexiaScore += (selectedOption == 0 ? 2 : selectedOption == 2 ? 1 : 0);
                break;
            case 5:
                tocScore += (selectedOption == 0 ? 2 : selectedOption == 2 ? 1 : 0);
                break;
            case 6:
                fobiaSocialScore += (selectedOption == 0 ? 2 : selectedOption == 2 ? 1 : 0);
                break;
            case 7:
                tlpScore += (selectedOption == 0 ? 2 : selectedOption == 2 ? 1 : 0);
                break;
        }
    }

    private void showDiagnosis() {
        String diagnosis;
        if (ansiedadScore >= tdahScore && ansiedadScore >= depresionScore &&
                ansiedadScore >= anorexiaScore && ansiedadScore >= tocScore &&
                ansiedadScore >= fobiaSocialScore && ansiedadScore >= tlpScore) {
            diagnosis = "Posible Ansiedad";
        } else if (tdahScore >= ansiedadScore && tdahScore >= depresionScore &&
                tdahScore >= anorexiaScore && tdahScore >= tocScore &&
                tdahScore >= fobiaSocialScore && tdahScore >= tlpScore) {
            diagnosis = "Posible TDAH";
        } else if (depresionScore >= ansiedadScore && depresionScore >= tdahScore &&
                depresionScore >= anorexiaScore && depresionScore >= tocScore &&
                depresionScore >= fobiaSocialScore && depresionScore >= tlpScore) {
            diagnosis = "Posible Depresión";
        } else if (anorexiaScore >= ansiedadScore && anorexiaScore >= tdahScore &&
                anorexiaScore >= depresionScore && anorexiaScore >= tocScore &&
                anorexiaScore >= fobiaSocialScore && anorexiaScore >= tlpScore) {
            diagnosis = "Posible Anorexia";
        } else if (tocScore >= ansiedadScore && tocScore >= tdahScore &&
                tocScore >= depresionScore && tocScore >= anorexiaScore &&
                tocScore >= fobiaSocialScore && tocScore >= tlpScore) {
            diagnosis = "Posible Trastorno Obsesivo-Compulsivo";
        } else if (fobiaSocialScore >= ansiedadScore && fobiaSocialScore >= tdahScore &&
                fobiaSocialScore >= depresionScore && fobiaSocialScore >= anorexiaScore &&
                fobiaSocialScore >= tocScore && fobiaSocialScore >= tlpScore) {
            diagnosis = "Posible Fobia Social";
        } else {
            diagnosis = "Posible Trastorno Límite de la Personalidad";
        }

        Toast.makeText(this, "Diagnóstico: " + diagnosis, Toast.LENGTH_LONG).show();
    }
}
