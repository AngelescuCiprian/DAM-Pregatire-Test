# Foaie: transmiterea datelor între 2 activități

## Ideea în 4 propoziții

- **Cine trimite** pune date în Intent cu `putExtra("cheie", valoare)`.
- **Cine primește** le scoate cu `get...Extra("cheie")`, folosind **aceeași cheie**.
- **Cine răspunde** înapoi apelează `setResult(RESULT_OK, data)` + `finish()`.
- **Cine așteaptă răspunsul** are un `launcher`, înregistrat în `onCreate`.

## Există doar 2 drumuri

| | Drumul 1: `startActivity` | Drumul 2: `launcher.launch` |
|---|---|---|
| Direcția | A → B | A → B → **înapoi la A** |
| B citește datele cu | `getIntent().get...Extra()` | `getIntent()` (dacă primește ceva) |
| B se închide cu | `finish()` sau Back | `setResult(RESULT_OK, data)` + `finish()` |
| A primește în | — | callback-ul launcher-ului |
| Exemple | detalii, Despre, intent implicit | **adăugare**, **editare** |

**Întrebarea care decide drumul:** *„Aștept ceva înapoi?”* Dacă da, `launch`. Dacă nu, `startActivity`.

---

## Înainte de cod: 3 condiții

1. Clasa are **`implements Serializable`**. Fără asta, `putExtra(..., obiect)` nu compilează.
2. Activitatea a doua e creată cu **New → Activity → Empty Views Activity**, ca să fie în Manifest.
3. **Cheia e identică** la ambele capete. `"carte"` ≠ `"Carte"` ≠ `"carti"`, iar o cheie greșită înseamnă `null`.

---

## Drumul 1: `startActivity` (A trimite, B afișează)

**A (MainActivity), la click pe un element din listă:**
```java
listView.setOnItemClickListener((parent, view, position, id) -> {
    Intent i = new Intent(this, DetaliiActivity.class);   // explicit: de aici → acolo
    i.putExtra("carte", carti.get(position));             // pun datele
    startActivity(i);                                     // pornesc, NU aștept nimic
});
```

**B (DetaliiActivity), în `onCreate`, după `setContentView`:**
```java
Carte c = (Carte) getIntent().getSerializableExtra("carte");   // Intent-ul cu care am fost pornită
if (c != null) {
    tvDetalii.setText(c.toString());
}
```

---

## Drumul 2: `launch` (B creează obiectul, A îl primește)

### Partea B (AddActivity): 3 pași, la click pe Salvează
```java
// 1. creez obiectul (după validare)
Carte carte = new Carte(titlu, gen, an);

// 2. îl pun într-un Intent GOL (nu are destinație: se întoarce de unde a venit)
Intent data = new Intent();
data.putExtra("carte", carte);

// 3. setez rezultatul și închid
setResult(RESULT_OK, data);
finish();
```

### Partea A (MainActivity): 4 locuri

**① Câmpuri** (deasupra lui `onCreate`)
```java
private final ArrayList<Carte> carti = new ArrayList<>();
private ArrayAdapter<Carte> adapter;
private ActivityResultLauncher<Intent> addLauncher;
```

**② Adaptorul** (în `onCreate`)
```java
adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carti);
listView.setAdapter(adapter);
```

**③ Înregistrarea** (în `onCreate`, **niciodată în click**). Callback-ul îl ții minte după 4 cuvinte: **OK → scoate → adaugă → notify**
```java
addLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {   // OK
                Carte c = (Carte) result.getData().getSerializableExtra("carte");  // scoate
                carti.add(c);                                                      // adaugă
                adapter.notifyDataSetChanged();                                    // notify
            }
        });
```

**④ Lansarea** (la click pe buton)
```java
addLauncher.launch(new Intent(this, AddCarteActivity.class));
```

### Ce se întâmplă, în ordine
```
① ② ③ rulează la pornire (callback-ul doar se ÎNREGISTREAZĂ, nu rulează)
④ click → se deschide AddActivity
   userul completează → setResult + finish → AddActivity se închide
③ ACUM rulează callback-ul → cartea apare în listă
```

---

## Tipuri simple, nu doar obiecte

```java
i.putExtra("an", 2020);                 // int
i.putExtra("titlu", "Dune");            // String
i.putExtra("pos", position);            // poziția din listă (pentru editare)

int an = getIntent().getIntExtra("an", -1);        // -1 = valoarea implicită dacă lipsește
String t = getIntent().getStringExtra("titlu");
int pos = result.getData().getIntExtra("pos", -1);
```

## Editarea = cele două drumuri combinate

1. A trimite obiectul **și poziția**: `putExtra("carte", ...)` + `putExtra("pos", position)`, apoi `editLauncher.launch(i)`.
2. B precompletează câmpurile din `getIntent()`, apoi la salvare pune înapoi `"carte"` și `"pos"`.
3. A, în callback: `carti.set(pos, c)` + `notifyDataSetChanged()`.

---

## Nu merge? Verifică în ordinea asta

| Simptom | Verifică |
|---|---|
| Crash la deschiderea ecranului | activitatea e în Manifest? |
| Crash la pornirea aplicației | `registerForActivityResult` e în `onCreate`, nu în click? |
| `putExtra` subliniat cu roșu | clasa are `implements Serializable`? |
| Obiectul primit e `null` | cheia e identică la ambele capete? |
| Nu se întâmplă nimic la întoarcere | B a apelat `setResult(RESULT_OK, data)` **înainte** de `finish()`? |
| E în listă, dar nu apare pe ecran | ai apelat `notifyDataSetChanged()`? |

## Reține în 10 secunde

```
Trimit:     putExtra("cheie", x)
Primesc:    getIntent().get...Extra("cheie")
Răspund:    setResult(RESULT_OK, data); finish();
Aștept:     launcher în onCreate  →  OK → scoate → adaugă → notify
Decizie:    aștept ceva înapoi? DA = launch · NU = startActivity
```
