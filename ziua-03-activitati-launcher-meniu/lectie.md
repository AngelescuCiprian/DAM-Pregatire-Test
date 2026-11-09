# Ziua 3: Activitate nouă, Intent, launcher, meniu, ListView

*Seminarul 4*

## Concepte

**1. Activitate = un ecran** = clasa Java + layout XML + intrare în `AndroidManifest.xml`. Le creezi pe toate trei deodată cu New → Activity → **Empty Views Activity**.

**2. Intent = „biletul” cu care pornești un ecran.**
- **Explicit:** `new Intent(this, AddCarteActivity.class)`, adică știi exact ce ecran deschizi.
- **Implicit:** `new Intent(Intent.ACTION_DIAL, uri)`: îi spui sistemului ce vrei să faci, iar el alege aplicația (Ziua 4).
- Datele le pui cu `putExtra("cheie", valoare)` și le scoți cu `get...Extra("cheie")`. **Cheia trebuie să fie identică la ambele capete.**

**3. Serializable.** Ca să trimiți un obiect al tău printr-un Intent, clasa trebuie să aibă `implements Serializable`. Nu trebuie să implementezi nicio metodă.

**4. `startActivity()` vs `launcher.launch()`**

| | `startActivity(intent)` | `launcher.launch(intent)` |
|---|---|---|
| Ce face | deschide ecranul și atât | deschide ecranul și **așteaptă rezultat** |
| Direcția datelor | doar spre ecranul nou (`getIntent()`) | spre ecranul nou **și înapoi** (`setResult` → callback) |
| Exemple | detalii, Despre | adăugare, editare |

**5. Fluxul launcher-ului: 3 momente, în 3 locuri diferite din cod**

```
MainActivity                              AddCarteActivity
─────────────                             ─────────────────
1. onCreate: registerForActivityResult
   (spui DINAINTE ce faci cu răspunsul)
2. click buton → launcher.launch(intent) ─►  completare formular + validare
                                            Intent data = new Intent();
                                            data.putExtra("carte", carte);
                                            setResult(RESULT_OK, data);
                                            finish();
3. callback-ul rulează ACUM  ◄────────────
   OK → scoate → adaugă → notify
```

- `registerForActivityResult` se apelează **în `onCreate`**. Apelat în click, aplicația crapă.
- Callback-ul se scrie la pasul 1, dar **rulează abia la pasul 3**, ca un `setOnClickListener`.
- Back fără salvare = `RESULT_CANCELED`. De asta verifici `RESULT_OK`.

**6. ListView + ArrayAdapter**
- Adaptorul e puntea dintre `ArrayList<Carte>` și ce se vede pe ecran.
- `android.R.layout.simple_list_item_1` afișează `toString()` pentru fiecare obiect.
- După orice modificare a listei: `adapter.notifyDataSetChanged()`.

**7. Meniul.** E format din `res/menu/menu.xml` + `onCreateOptionsMenu` (inflate) + `onOptionsItemSelected` (`item.getItemId()`). **Apare doar dacă tema nu e `NoActionBar`.**

## Greșeli de evitat

1. Tema rămasă `NoActionBar`: meniul nu apare.
2. Ai schimbat rădăcina layout-ului și ai pierdut `android:id="@+id/main"`: crash la pornire.
3. Lista se declară `private final ArrayList<Carte> carti = new ArrayList<>();`: adaptorul ține referința la acest obiect, deci nu-i atribui niciodată altă listă cu `=`.
4. Lipsește `notifyDataSetChanged()`: cartea e în listă, dar nu apare pe ecran.
5. `getSerializableExtra("x", Carte.class)` merge doar cu minSdk ≥ 33. Varianta cu cast `(Carte)` merge oricum.
6. Textul din Toast se pune în `strings.xml` cu `%1$d`.
7. **Viteza.** Fă întâi toate layout-urile, apoi tot codul. Ordinea fișierelor: model → `strings.xml` → layout Add → AddActivity → layout Main → MainActivity → meniu. Rulează după fiecare pas mare.
