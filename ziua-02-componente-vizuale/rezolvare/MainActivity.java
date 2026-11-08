package com.example.biletfilm;   // pune pachetul proiectului tău

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_RESULT = "result";
    private static final String KEY_DISCOUNT = "discount";

    private EditText etName, etAge;
    private Spinner spType;
    private CheckBox cbStudent, cbDetails;
    private TextView tvResult, tvDetails;

    private String textRezultat = "";
    private boolean reducereAplicata = false;

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

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        spType = findViewById(R.id.spType);
        cbStudent = findViewById(R.id.cbStudent);
        cbDetails = findViewById(R.id.cbDetails);
        tvResult = findViewById(R.id.tvResult);
        tvDetails = findViewById(R.id.tvDetails);
        Button btnCalc = findViewById(R.id.btnCalc);
        Button btnReset = findViewById(R.id.btnReset);

        if (savedInstanceState != null) {
            textRezultat = savedInstanceState.getString(KEY_RESULT, "");
            reducereAplicata = savedInstanceState.getBoolean(KEY_DISCOUNT);
            afiseazaRezultat();
        }

        cbDetails.setOnCheckedChangeListener((buttonView, isChecked) ->
                tvDetails.setVisibility(isChecked ? View.VISIBLE : View.GONE));

        btnCalc.setOnClickListener(v -> calculeaza());
        btnReset.setOnClickListener(v -> reseteaza());
    }

    private void calculeaza() {
        String nume = etName.getText().toString().trim();
        String varstaStr = etAge.getText().toString().trim();

        // 1. gol
        if (nume.isEmpty() || varstaStr.isEmpty()) {
            Toast.makeText(this, R.string.err_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        // 2. parse  3. interval
        int varsta = Integer.parseInt(varstaStr);
        if (varsta < 12 || varsta > 100) {
            Toast.makeText(this, R.string.err_age, Toast.LENGTH_SHORT).show();
            return;
        }

        double pret;
        switch (spType.getSelectedItemPosition()) {
            case 0:  pret = 25; break;
            case 1:  pret = 40; break;
            case 2:  pret = 35; break;
            default: pret = 0;
        }

        reducereAplicata = cbStudent.isChecked();
        if (reducereAplicata) {
            pret = pret / 2.0;          // double → 17.50, nu 17
        }

        textRezultat = getString(R.string.result_format,
                nume, spType.getSelectedItem().toString(), pret);
        afiseazaRezultat();
        Log.d(TAG, "Pret calculat: " + pret);
    }

    private void afiseazaRezultat() {
        tvResult.setText(textRezultat);
        int culoare = reducereAplicata ? R.color.verde : R.color.albastru;
        tvResult.setTextColor(ContextCompat.getColor(this, culoare));
    }

    private void reseteaza() {
        etName.setText("");
        etAge.setText("");
        spType.setSelection(0);
        cbStudent.setChecked(false);
        cbDetails.setChecked(false);
        textRezultat = "";              // golești și variabila, nu doar ecranul
        reducereAplicata = false;
        tvResult.setText("");
        etName.requestFocus();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_RESULT, textRezultat);
        outState.putBoolean(KEY_DISCOUNT, reducereAplicata);
    }
}
