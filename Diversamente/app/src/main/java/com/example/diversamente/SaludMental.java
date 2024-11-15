package com.example.diversamente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SaludMental extends AppCompatActivity {
    Integer counter = 0;
    Button btnGood, btnBad;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diversamente-1d32d-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salud_mental);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        connectButtons();
        btnGood.setOnClickListener(v -> {
            updateCounter();
        });
        btnBad.setOnClickListener(v -> {
            openChatbot();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCounter();

    }
    private void connectButtons(){
        btnGood = findViewById(R.id.btnGood);
        btnBad = findViewById(R.id.btnBad);
    }

    private void MsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    private void openChatbot() {
        try {
            Intent intent = new Intent(getApplicationContext(), ChatbotActivity.class);
            databaseReference.child("users").child(user.getUid()).child("counter").setValue(0)
                    .addOnSuccessListener(aVoid -> startActivity(intent))
                    .addOnFailureListener(error -> MsgToast("Error al actualizar: " + error.getMessage()));

        }catch (Exception error){
            Toast.makeText(this, error.getMessage() + "fallos", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCounter(){
        try {
            counter++;
            databaseReference.child("users").child(user.getUid()).child("counter").setValue(counter)
                    .addOnSuccessListener(aVoid -> goHome())
                    .addOnFailureListener(error -> MsgToast("Error al actualizar: " + error.getMessage()));
        } catch (Exception error) {
            Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getCounter(){
        databaseReference.child("users").child(user.getUid()).child("counter").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Long counterValue = task.getResult().getValue(Long.class);
                    if (counterValue != null) {
                        counter = counterValue.intValue();
                        Log.d("firebase", "Counter value: " + counter);
                    }

                }
            }
        });
    }

    private void goHome() {
        MsgToast("Me alegra que estes bien");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}