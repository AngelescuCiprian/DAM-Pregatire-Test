# Șabloane de cod pentru test

Toate exemplele folosesc o clasă `Carte` (`titlu`, `gen`, `an`) și `findViewById`. Pentru altă clasă, schimbi doar numele.

**Cuprins**
1. [Ciclul de viață](#1-ciclul-de-viață)
2. [Log și Toast](#2-log-și-toast)
3. [Citire de la tastatură + validare](#3-citire-de-la-tastatură--validare)
4. [Spinner, CheckBox, vizibilitate](#4-spinner-checkbox-vizibilitate)
5. [Meniu](#5-meniu)
6. [Intent explicit + startActivity (date într-o direcție)](#6-intent-explicit--startactivity-date-într-o-direcție)
7. [Launcher (primești date înapoi)](#7-launcher-primești-date-înapoi)
8. [ListView + ArrayAdapter generic](#8-listview--arrayadapter-generic)
9. [Click, long click, AlertDialog, ștergere](#9-click-long-click-alertdialog-ștergere)
10. [Update (editare)](#10-update-editare)
11. [Sortare și filtrare](#11-sortare-și-filtrare)
12. [Intent implicit](#12-intent-implicit)
13. [Adapter personalizat pentru ListView (getView)](#13-adapter-personalizat-pentru-listview-getview)
14. [RecyclerView + adapter personalizat](#14-recyclerview--adapter-personalizat)
15. [Rotire: salvarea datelor](#15-rotire-salvarea-datelor)
16. [Resurse: strings, colors](#16-resurse-strings-colors)
17. [Comparații de teorie](#17-comparații-de-teorie)

---

## 1. Ciclul de viață

| Situație | Ordine |
|---|---|
| Pornire | `onCreate → onStart → onResume` |
| Home / altă activitate peste | `onPause → onStop` |
| Revenire | `onRestart → onStart → onResume` |
| **Rotire** | `onPause → onStop → onDestroy → onCreate → onStart → onResume` |
| Back | `onPause → onStop → onDestroy` (pe Android 12+, pe activitatea principală, fără `onDestroy`) |

```java
private static final String TAG = "MainActivity";   // logt + Tab

@Override
protected void onPause() {          // Ctrl+O → onPause
    super.onPause();
    Log.d(TAG, "onPause");
}
```

## 2. Log și Toast

```java
Log.d(TAG, "mesaj");                // d=debug, i=info, e=error
Toast.makeText(this, "mesaj", Toast.LENGTH_SHORT).show();       // nu uita .show()!
Toast.makeText(this, R.string.err_empty, Toast.LENGTH_SHORT).show();
```

## 3. Citire de la tastatură + validare

Ordinea e mereu aceeași: **gol → parse → interval**.

```java
String titlu = etTitlu.getText().toString().trim();
String anStr = etAn.getText().toString().trim();

if (titlu.isEmpty() || anStr.isEmpty()) {
    Toast.makeText(this, R.string.err_empty, Toast.LENGTH_SHORT).show();
    return;
}
int an = Integer.parseInt(anStr);        // double → Double.parseDouble
if (an < 1990 || an > 2026) {
    Toast.makeText(this, R.string.err_an, Toast.LENGTH_SHORT).show();
    return;
}
```

Validări frecvente:
```java
phone.length() == 10                     // telefon din exact 10 cifre (inputType="phone")
phone.matches("\\d{10}")                 // exact 10 cifre, fără alte caractere
email.contains("@")
```

## 4. Spinner, CheckBox, vizibilitate

**Spinner din XML** (varianta cea mai rapidă):
```xml
<!-- strings.xml -->
<string-array name="genuri">
    <item>Roman</item>
    <item>SF</item>
    <item>Poezie</item>
    <item>Istorie</item>
</string-array>

<!-- layout -->
<Spinner android:id="@+id/spGen" android:entries="@array/genuri" ... />
```

**Spinner din cod** (dacă se cere explicit adaptor):
```java
ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(
        this, R.array.genuri, android.R.layout.simple_spinner_item);
spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spGen.setAdapter(spAdapter);
```

```java
String gen = spGen.getSelectedItem().toString();   // textul ales
int pos = spGen.getSelectedItemPosition();         // indexul ales
spGen.setSelection(0);                             // reset / preselectare

boolean bifat = cbStudent.isChecked();
cbStudent.setChecked(false);
cbDetails.setOnCheckedChangeListener((buttonView, isChecked) ->
        tvDetails.setVisibility(isChecked ? View.VISIBLE : View.GONE));
```

## 5. Meniu

⚠️ Tema trebuie să fie **fără `NoActionBar`** (`res/values/themes.xml` → `parent="Theme.Material3.DayNight"`).

`res/menu/menu.xml`:
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/nav_count" android:title="@string/nav_count" />
    <item android:id="@+id/nav_about" android:title="@string/nav_about" />
</menu>
```

```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.nav_count) {
        Toast.makeText(this, getString(R.string.count_format, carti.size()), Toast.LENGTH_SHORT).show();
        return true;
    }
    if (id == R.id.nav_about) {
        startActivity(new Intent(this, AboutActivity.class));
        return true;
    }
    return super.onOptionsItemSelected(item);
}
```

## 6. Intent explicit + startActivity (date într-o direcție)

Folosești varianta asta când **deschizi un ecran și nu aștepți nimic înapoi**: detalii, Despre.

```java
// MainActivity: trimite
Intent intent = new Intent(this, DetaliiActivity.class);
intent.putExtra("carte", carte);           // Carte implements Serializable
startActivity(intent);

// DetaliiActivity: primește (în onCreate)
Carte carte = (Carte) getIntent().getSerializableExtra("carte");
if (carte == null) { finish(); return; }
tvTitlu.setText(carte.getTitlu());
```

## 7. Launcher (primești date înapoi)

Folosești varianta asta când **deschizi un ecran și aștepți un rezultat**: adăugare, editare.

Callback-ul îl ții minte după 4 cuvinte: **OK → scoate → adaugă → notify**.

```java
// MainActivity: câmp
private ActivityResultLauncher<Intent> addLauncher;

// MainActivity: în onCreate (NU în click!)
addLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {   // OK
                Carte c = (Carte) result.getData().getSerializableExtra("carte");  // scoate
                if (c != null) {
                    carti.add(c);                                                  // adaugă
                    adapter.notifyDataSetChanged();                                // notify
                }
            }
        });

// MainActivity: la click
addLauncher.launch(new Intent(this, AddCarteActivity.class));
```

```java
// AddCarteActivity: după validare
Intent data = new Intent();
data.putExtra("carte", carte);
setResult(RESULT_OK, data);
finish();
```

Varianta profului, `getSerializableExtra("carte", Carte.class)`, merge doar cu **minSdk ≥ 33**. Cu cast `(Carte)` merge oricum.

## 8. ListView + ArrayAdapter generic

```java
private final ArrayList<Carte> carti = new ArrayList<>();   // final! restaurezi cu addAll
private ArrayAdapter<Carte> adapter;

adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carti);
listView.setAdapter(adapter);
// afișează toString() pentru fiecare obiect

carti.add(c);
adapter.notifyDataSetChanged();     // după ORICE modificare a listei
```

Populare cu date de test:
```java
if (savedInstanceState == null) {          // altfel se dublează la rotire!
    carti.add(new Carte("Dune", "SF", 2021));
    carti.add(new Carte("Ion", "Roman", 1995));
}
```

## 9. Click, long click, AlertDialog, ștergere

```java
listView.setOnItemClickListener((parent, view, position, id) -> {
    Intent i = new Intent(this, DetaliiActivity.class);
    i.putExtra("carte", carti.get(position));
    startActivity(i);
});

listView.setOnItemLongClickListener((parent, view, position, id) -> {
    new AlertDialog.Builder(this)                       // androidx.appcompat.app.AlertDialog
            .setTitle(R.string.dlg_delete_title)
            .setMessage(getString(R.string.dlg_delete_msg, carti.get(position).getTitlu()))
            .setPositiveButton(R.string.btn_yes, (dialog, which) -> {
                carti.remove(position);
                adapter.notifyDataSetChanged();
            })
            .setNegativeButton(R.string.btn_no, null)
            .show();
    return true;          // true = evenimentul a fost consumat (nu se mai declanșează și click-ul)
});
```

## 10. Update (editare)

Ideea: trimiți obiectul **și poziția** către formular. Formularul le întoarce înapoi, iar tu faci `set(pos, ...)`.

```java
// MainActivity: launcher separat pentru editare
editLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Carte c = (Carte) result.getData().getSerializableExtra("carte");
                int pos = result.getData().getIntExtra("pos", -1);
                if (c != null && pos != -1) {
                    carti.set(pos, c);
                    adapter.notifyDataSetChanged();
                }
            }
        });

// pornire editare (de exemplu la click pe item)
Intent i = new Intent(this, AddCarteActivity.class);
i.putExtra("carte", carti.get(position));
i.putExtra("pos", position);
editLauncher.launch(i);
```

```java
// AddCarteActivity, în onCreate: precompletare dacă e editare
Carte existenta = (Carte) getIntent().getSerializableExtra("carte");
if (existenta != null) {
    etTitlu.setText(existenta.getTitlu());
    etAn.setText(String.valueOf(existenta.getAn()));      // setText(int) caută o resursă → crash!
    String[] genuri = getResources().getStringArray(R.array.genuri);
    for (int k = 0; k < genuri.length; k++) {
        if (genuri[k].equals(existenta.getGen())) spGen.setSelection(k);
    }
}

// la salvare, trimiți și poziția înapoi (la adăugare va fi -1)
data.putExtra("pos", getIntent().getIntExtra("pos", -1));
```

## 11. Sortare și filtrare

**Sortare** (de exemplu dintr-o opțiune de meniu):
```java
carti.sort(Comparator.comparing(Carte::getTitlu));             // alfabetic
carti.sort(Comparator.comparingInt(Carte::getAn));             // după an, crescător
carti.sort(Comparator.comparingInt(Carte::getAn).reversed());  // după an, descrescător
adapter.notifyDataSetChanged();

// varianta clasică, fără method references:
Collections.sort(carti, (a, b) -> a.getTitlu().compareToIgnoreCase(b.getTitlu()));
```

**Filtrare** (păstrezi lista completă separat):
```java
private final ArrayList<Carte> toateCartile = new ArrayList<>();  // sursa
private final ArrayList<Carte> carti = new ArrayList<>();         // ce se afișează (în adapter)

// la adăugare: în AMBELE
toateCartile.add(c); carti.add(c);

// filtrare
carti.clear();
for (Carte c : toateCartile) {
    if (c.getGen().equals("SF")) carti.add(c);       // sau c.getAn() > 2010 etc.
}
adapter.notifyDataSetChanged();

// anulare filtru
carti.clear();
carti.addAll(toateCartile);
adapter.notifyDataSetChanged();
```

Varianta rapidă, dacă nu mai ai operații pe listă după filtrare:
```java
ArrayList<Carte> filtrate = new ArrayList<>();
for (Carte c : carti) if (c.getAn() > 2010) filtrate.add(c);
listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filtrate));
```

## 12. Intent implicit

Spui **ce** vrei să faci, iar sistemul alege aplicația.

```java
// Apel (nu cere permisiune; ACTION_CALL ar cere)
startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));

// Site
Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ase.ro"));
try {
    startActivity(web);
} catch (ActivityNotFoundException e) {
    Toast.makeText(this, R.string.err_no_app, Toast.LENGTH_SHORT).show();
}

// Share
Intent send = new Intent(Intent.ACTION_SEND);
send.setType("text/plain");
send.putExtra(Intent.EXTRA_TEXT, carte.toString());
startActivity(Intent.createChooser(send, getString(R.string.share_title)));
```

## 13. Adapter personalizat pentru ListView (getView)

Ai nevoie de un layout pentru un rând, `res/layout/item_carte.xml`, cu `tvTitlu` și `tvInfo`.

```java
public class CarteAdapter extends ArrayAdapter<Carte> {

    public CarteAdapter(@NonNull Context context, @NonNull List<Carte> carti) {
        super(context, 0, carti);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_carte, parent, false);
        }
        Carte c = getItem(position);

        TextView tvTitlu = convertView.findViewById(R.id.tvTitlu);
        TextView tvInfo = convertView.findViewById(R.id.tvInfo);
        tvTitlu.setText(c.getTitlu());
        tvInfo.setText(c.getGen() + " · " + c.getAn());

        return convertView;
    }
}

// în MainActivity, în loc de ArrayAdapter generic:
private CarteAdapter adapter;
adapter = new CarteAdapter(this, carti);
listView.setAdapter(adapter);
// click / long click / notifyDataSetChanged: identic cu ListView-ul generic
```

## 14. RecyclerView + adapter personalizat

Dacă `RecyclerView` e roșu în XML, adaugi în `build.gradle.kts (:app)`: `implementation("androidx.recyclerview:recyclerview:1.3.2")` (de obicei vine deja prin `material`).

```java
public class CarteAdapter extends RecyclerView.Adapter<CarteAdapter.CarteViewHolder> {

    public interface OnCarteListener {          // evenimentele le tratează activitatea
        void onCarteClick(Carte carte);
        void onCarteDelete(int position);
    }

    private final List<Carte> carti;
    private final OnCarteListener listener;

    public CarteAdapter(List<Carte> carti, OnCarteListener listener) {
        this.carti = carti;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carte, parent, false);
        return new CarteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarteViewHolder holder, int position) {
        Carte c = carti.get(position);
        holder.tvTitlu.setText(c.getTitlu());
        holder.tvInfo.setText(c.getGen() + " · " + c.getAn());

        // culoare condiționată: setezi culoarea pe AMBELE ramuri (rândurile se refolosesc!)
        int culoare = c.getAn() > 2010 ? R.color.verde : R.color.albastru;
        holder.tvTitlu.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), culoare));

        holder.itemView.setOnClickListener(v -> listener.onCarteClick(c));
    }

    @Override
    public int getItemCount() {
        return carti.size();
    }

    static class CarteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitlu, tvInfo;
        CarteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitlu = itemView.findViewById(R.id.tvTitlu);
            tvInfo = itemView.findViewById(R.id.tvInfo);
        }
    }
}
```

```java
// MainActivity implements CarteAdapter.OnCarteListener   (Alt+Enter → Implement methods)
recyclerView.setLayoutManager(new LinearLayoutManager(this));   // fără el NU se afișează nimic
adapter = new CarteAdapter(carti, this);
recyclerView.setAdapter(adapter);

carti.add(c);         adapter.notifyItemInserted(carti.size() - 1);
carti.remove(pos);    adapter.notifyItemRemoved(pos);
carti.set(pos, c);    adapter.notifyItemChanged(pos);
// sortare / filtrare  → adapter.notifyDataSetChanged();
```

## 15. Rotire: salvarea datelor

```java
@Override
protected void onSaveInstanceState(@NonNull Bundle outState) {   // varianta cu UN parametru
    super.onSaveInstanceState(outState);
    outState.putSerializable("carti", carti);    // ArrayList e Serializable
    outState.putString("rezultat", textRezultat);
}

// în onCreate, ÎNAINTE de a crea adaptorul
if (savedInstanceState != null) {
    ArrayList<Carte> salvate = (ArrayList<Carte>) savedInstanceState.getSerializable("carti");
    if (salvate != null) carti.addAll(salvate);     // addAll, NU carti = salvate
}
```

## 16. Resurse: strings, colors

```xml
<!-- strings.xml -->
<string name="count_format">Sunt %1$d cărți în listă</string>
<string name="result_format">%1$s, biletul tău %2$s costă %3$.2f lei</string>
<!-- %s text · %d întreg · %.2f real cu 2 zecimale · %1$, %2$ = ordinea argumentelor -->

<!-- colors.xml -->
<color name="verde">#2E7D32</color>
<color name="albastru">#1565C0</color>
```

```java
getString(R.string.count_format, carti.size());
tv.setTextColor(ContextCompat.getColor(this, R.color.verde));      // în activitate
tv.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.verde)); // în adapter
```

## 17. Comparații de teorie

| | `startActivity()` | `launcher.launch()` |
|---|---|---|
| Rezultat înapoi | nu | da (callback) |
| Activitatea a doua | `getIntent()` + `finish()` | `setResult(RESULT_OK, data)` + `finish()` |
| Exemple | detalii, Despre, intent implicit | adăugare, editare |

| | Intent explicit | Intent implicit |
|---|---|---|
| Ce specifici | clasa: `new Intent(this, X.class)` | acțiunea: `ACTION_DIAL`, `ACTION_VIEW`, `ACTION_SEND` |
| Cine răspunde | activitatea ta | orice aplicație care știe acțiunea |

| | ListView | RecyclerView |
|---|---|---|
| Adaptor | `ArrayAdapter` (generic) sau `getView()` | `RecyclerView.Adapter` + **ViewHolder obligatoriu** |
| Refolosire rânduri | opțional (`convertView`) | obligatoriu, automat |
| LayoutManager | nu | **obligatoriu** (`LinearLayoutManager`) |
| Click pe rând | `setOnItemClickListener` gata făcut | îl pui tu în `onBindViewHolder` |
| Notificare | `notifyDataSetChanged()` | `notifyItemInserted / Removed / Changed` (animații) |
| Când îl folosești | liste mici, simple | liste mari, rânduri complexe, performanță |

| | `View.GONE` | `View.INVISIBLE` |
|---|---|---|
| Se vede | nu | nu |
| Ocupă loc | nu | da |
