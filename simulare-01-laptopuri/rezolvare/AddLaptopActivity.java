package ro.ase.simularetestdam_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddLaptopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_laptop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etModel=findViewById(R.id.etModel);
        EditText etPret=findViewById(R.id.etPret);
        Spinner spTip=findViewById(R.id.spTip);
        CheckBox cbGarantie=findViewById(R.id.cbGarantie);
        Button btnSalveaza=findViewById(R.id.btnSalveaza);
        btnSalveaza.setOnClickListener(v->
        {
            if(etModel.getText().toString().trim().isEmpty() || etPret.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this, "Completeaza toate campurile", Toast.LENGTH_SHORT).show();
                return;
            }
            String model=etModel.getText().toString().trim();
            double pret=Double.parseDouble(etPret.getText().toString().trim());
            if(pret<=0)
            {
                Toast.makeText(this, "Pret invalid", Toast.LENGTH_SHORT).show();
                return;
            }
            String tip=spTip.getSelectedItem().toString();
            boolean areGarantie=false;
            if(cbGarantie.isChecked())
                areGarantie=true;
            Laptop l=new Laptop(model,pret,tip,areGarantie);
            Intent data=new Intent();
            data.putExtra("laptop",l);
            setResult(RESULT_OK,data);
            finish();
        });

    }
}