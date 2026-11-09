# Capcane și erori: ce faci când crapă

## Aplicația a crăpat: procedura

1. Logcat → filtru `level:error` → cauți **`FATAL EXCEPTION`**.
2. Citești **prima linie** de sub el. E tipul erorii (tabelul de mai jos).
3. Dai click pe **primul link albastru cu un fișier de-al tău** (de exemplu `MainActivity.java:42`). Te duce direct pe linie.
4. Dacă tot nu e clar: pui un `Log.d(TAG, "x=" + x)` înainte de linia suspectă.

| Eroare | Cauza obișnuită | Fix |
|---|---|---|
| `NullPointerException` | `findViewById` a returnat `null`: id greșit sau view căutat în alt layout decât cel din `setContentView` | verifică id-ul în XML |
| `NullPointerException` pe `R.id.main` | ai schimbat rădăcina layout-ului și ai pierdut `android:id="@+id/main"` | pune id-ul înapoi sau șterge blocul `ViewCompat` |
| `NumberFormatException` | `Integer.parseInt("")` | validează golul **înainte** de parse |
| `ActivityNotFoundException` / „Unable to find explicit activity” | activitatea nu e în `AndroidManifest.xml` | creează activitatea cu New → Activity → Empty Views Activity sau adaug-o manual |
| `Resources$NotFoundException` | `tv.setText(numarInt)`: Android caută o resursă cu id-ul ăla | `setText(String.valueOf(x))` |
| `IllegalStateException: ... attempting to register while current state is RESUMED` | `registerForActivityResult` apelat în click | mută-l în `onCreate` |
| `IndexOutOfBoundsException` | poziție greșită după ștergere | în RecyclerView: `holder.getBindingAdapterPosition()` |
| `ClassCastException` | cheie `putExtra` folosită pentru două tipuri diferite | chei distincte |

## Nu crapă, dar nu merge

| Simptom | Cauza | Fix |
|---|---|---|
| Meniul nu apare | tema e `NoActionBar` | `themes.xml` → `parent="Theme.Material3.DayNight"` |
| Elementul adăugat nu apare în listă | lipsește `notifyDataSetChanged()` | adaugă-l după `add` |
| *(rotire)* Lista e goală după rotire, deși ai restaurat | ai făcut `carti = salvate` după crearea adaptorului | lista `final` + `addAll` |
| *(rotire)* Lista se dublează la rotire | date de test adăugate la fiecare `onCreate` | `if (savedInstanceState == null) { ... }` |
| RecyclerView gol | lipsește `setLayoutManager` | `new LinearLayoutManager(this)` |
| Callback-ul launcher-ului nu primește nimic | cheia din `putExtra` diferă de cea din `getSerializableExtra` | aceeași cheie, ideal o constantă |
| *(rotire)* `onSaveInstanceState` nu se apelează | ai ales varianta cu `PersistableBundle` | varianta cu **un** parametru `Bundle` |
| Culori greșite în RecyclerView după scroll | ai setat culoarea doar pe ramura `if` | setezi pe **ambele** ramuri |
| *(rotire)* Rezultatul vechi reapare după reset + rotire | resetul a golit doar UI-ul, nu și variabila | golește și variabila |
| `35 / 2 = 17` | împărțire întreagă | `double` + `/ 2.0` |
| Butoanele de pe un rând sunt inegale | `layout_width="wrap_content"` cu weight | `0dp` + `layout_weight="1"` |
| *(rotire)* Rotirea nu face nimic în emulator | Auto-rotate oprit | pornește-l din quick settings |
| Toast-ul din `onPause` nu apare la AlertDialog | dialogul e în aceeași activitate, deci nu se apelează `onPause` | exemplifică deschizând altă activitate |
| Back nu dă `onDestroy` pe MainActivity | comportament Android 12+ (task-ul merge în fundal) | normal, nu e bug |

## Ce se păstrează singur la rotire (nu intră la test)

| Se păstrează (view cu id) | Se pierde |
|---|---|
| textul din EditText | textul din TextView setat din cod |
| starea CheckBox-ului | culori și vizibilitate setate din cod (dacă nu decurg din CheckBox) |
| selecția din Spinner | **orice variabilă**: contoare, liste, rezultate |
