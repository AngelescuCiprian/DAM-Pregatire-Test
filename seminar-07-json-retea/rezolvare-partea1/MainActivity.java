package ro.ase.bibliotecaonline;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String URL_CARTI = "https://www.jsonkeeper.com/b/SQTX9";   // linkul tău
    private final ArrayList<Carte> listaCarti = new ArrayList<>();
    private ArrayAdapter<Carte> adapter;

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

        ListView listView = findViewById(R.id.lvCarti);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCarti);
        listView.setAdapter(adapter);

        // FIR SECUNDAR: rețea
        new Thread(() -> {
            String json = new HttpsManager(URL_CARTI).procesare();
            Log.d("MainActivity", "JSON primit " + json);
            // FIR PRINCIPAL: UI
            runOnUiThread(() -> {
                listaCarti.addAll(CarteParser.parsareJSON(json));
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "S-au încărcat " + listaCarti.size() + " cărți",
                        Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
