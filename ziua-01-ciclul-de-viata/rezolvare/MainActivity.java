package com.example.lifecycleapp;   // pune pachetul proiectului tău

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_COUNTER = "counter";

    private int counter = 0;
    private TextView tvCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "onCreate");
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        tvCounter = findViewById(R.id.tvCounter);
        Button btnAdd = findViewById(R.id.btnAdd);

        // restaurare după rotire
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(KEY_COUNTER);
        }
        afiseazaCounter();

        btnAdd.setOnClickListener(v -> {
            counter++;
            afiseazaCounter();
        });
    }

    private void afiseazaCounter() {
        tvCounter.setText(getString(R.string.counter_format, counter));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {   // varianta cu UN parametru
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNTER, counter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
