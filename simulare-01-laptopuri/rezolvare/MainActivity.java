package ro.ase.simularetestdam_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Laptop> listaLaptopuri=new ArrayList<>();
    private ArrayList<Laptop> listaToata=new ArrayList<>();
    private ArrayAdapter<Laptop> adapter;
    private ActivityResultLauncher<Intent> launcher;
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Suntem in onPause", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "Suntem  in onPause: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        {
            if(id==R.id.itmSortPret)
            {
                listaLaptopuri.sort(Comparator.comparingDouble(Laptop::getPret));
                adapter.notifyDataSetChanged();
                return true;
            }
            if(id==R.id.itmGaming)
            {
                ArrayList<Laptop> listaGaming=new ArrayList<>();
                for(Laptop l:listaToata)
                {
                    if(l.getTip().equals("Gaming"))
                        listaGaming.add(l);
                }
                listaLaptopuri.clear();
                listaLaptopuri.addAll(listaGaming);
                adapter.notifyDataSetChanged();
                return  true;
            }
            if(id==R.id.itmToate)
            {
                listaLaptopuri.clear();
                listaLaptopuri.addAll(listaToata);
                adapter.notifyDataSetChanged();
                return true;
            }
            if(id==R.id.itmNrLaptopuri)
            {
                Toast.makeText(this, getString(R.string.nrLaptopuri,listaLaptopuri.size()), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ListView listView=findViewById(R.id.listView);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listaLaptopuri);
        listView.setAdapter(adapter);
        populeaza();
//        adapter.notifyDataSetChanged();

        launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->
        {
           if(result.getResultCode()==RESULT_OK && result.getData()!=null)
           {
               Laptop l=(Laptop)result.getData().getSerializableExtra("laptop");
               if(l!=null)
               {
                   listaLaptopuri.add(l);
                   listaToata.add(l);
                   adapter.notifyDataSetChanged();
                   Log.d("MainActivity", "Laptop primit: " + l);   // FIX: Log.d cerut în callback
               }
           }
        });

        Button btnAdauga=findViewById(R.id.btnAdauga);
        btnAdauga.setOnClickListener(v->
        {
            launcher.launch(new Intent(this, AddLaptopActivity.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView.setOnItemClickListener((p,v,position,i)->
        {
            Laptop l=listaLaptopuri.get(position);
            Intent intent=new Intent(this, DetaliiLaptopActivity.class);
            intent.putExtra("laptopClickuit",l);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((p,v,position,i)->
        {
            Laptop l=listaLaptopuri.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Alege optiunile")
                    .setMessage("Ce doresti?")
                    .setPositiveButton("Reducere 10%",(m,pr)->
                    {
                        double pretRedus=l.getPret()*0.9;
                        l.setPret(pretRedus);          // același obiect e în ambele liste → nu mai trebuie set()
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Sterge",(dw,dwdq)->
                    {
                        listaLaptopuri.remove(position);
                        listaToata.remove(l);
                        adapter.notifyDataSetChanged();
                    })
                    .setNeutralButton("Anuleaza",null)
                    .show();
            return true;
        });
    }

    void populeaza()
    {
        Laptop l1=new Laptop("Lenovo",4000,"Gaming",true);
        Laptop l2=new Laptop("Lenovo",3000,"Office",false);
        Laptop l3=new Laptop("HP",27000,"Ultrabook",false);
        Laptop l4=new Laptop("Acer",7000,"Gaming",true);
        listaLaptopuri.add(l1);
        listaLaptopuri.add(l2);
        listaLaptopuri.add(l3);
        listaLaptopuri.add(l4);
        listaToata.addAll(listaLaptopuri);   // FIX: sincronizarea direct aici, nu la finalul lui onCreate
        adapter.notifyDataSetChanged();
    }
}