package ro.ase.simularetestdam_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// FIX: detaliile se AFIȘEAZĂ în TextView-uri (nu EditText/Spinner/CheckBox editabile care nu salvează nimic)
public class DetaliiLaptopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalii_laptop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Laptop l = (Laptop) getIntent().getSerializableExtra("laptopClickuit");
        if (l == null) {            // FIX: verificare null
            finish();
            return;
        }

        TextView tvModel = findViewById(R.id.tvModel);
        TextView tvPret = findViewById(R.id.tvPret);
        TextView tvTip = findViewById(R.id.tvTip);
        TextView tvGarantie = findViewById(R.id.tvGarantie);
        Button btnSearch = findViewById(R.id.btnSearch);

        tvModel.setText(l.getModel());
        tvPret.setText(getString(R.string.pret_format, l.getPret()));
        tvTip.setText(l.getTip());
        tvGarantie.setText(l.isGarantieExtinsa() ? R.string.garantie_da : R.string.garantie_nu);

        btnSearch.setOnClickListener(v -> {
            // FIX: caut după model, nu după tot toString()-ul
            String url = "https://www.google.com/search?q=" + Uri.encode(l.getModel());
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
    }
}
