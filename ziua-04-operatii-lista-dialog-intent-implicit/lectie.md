# Ziua 4: Operații pe listă, AlertDialog, intent implicit

*Seminarul 5 + ce lipsește din seminarii (sortare, filtrare, update)*

## 1. Evenimentele din ListView

```java
listView.setOnItemClickListener((parent, view, position, id) -> { ... });
listView.setOnItemLongClickListener((parent, view, position, id) -> { ...; return true; });
```
- **`position`** e indexul: `lista.get(position)` e obiectul apăsat.
- Long click **returnează `true`** („am tratat evenimentul”). Cu `false` se declanșează și click-ul simplu.
- Împărțirea standard: **click = vezi** (detalii), **long click = modifici** (șterge / editează).

## 2. Intent implicit vs explicit

| Explicit | Implicit |
|---|---|
| `new Intent(this, DetaliiActivity.class)` | `new Intent(Intent.ACTION_VIEW, uri)` |
| tu alegi **activitatea** | tu spui **acțiunea**, sistemul alege aplicația |

```java
// link
startActivity(new Intent(Intent.ACTION_VIEW,
        Uri.parse("https://www.google.com/search?q=" + Uri.encode(titlu))));
// apel (fără permisiune)
startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telefon)));
// share
Intent send = new Intent(Intent.ACTION_SEND);
send.setType("text/plain");
send.putExtra(Intent.EXTRA_TEXT, carte.toString());
startActivity(Intent.createChooser(send, "Distribuie cartea prin"));
```

## 3. AlertDialog

```java
new AlertDialog.Builder(this)                     // import androidx.appcompat.app.AlertDialog
        .setTitle(c.getTitlu())
        .setMessage("Ce vrei să faci cu această carte?")
        .setPositiveButton("Șterge", (dialog, which) -> { ... })
        .setNeutralButton("Editează", (dialog, which) -> { ... })
        .setNegativeButton("Anulează", null)      // null = doar închide
        .show();                                  // fără show() nu apare
```
Un dialog are maxim 3 butoane: pozitiv, neutru și negativ.

## 4. Operațiile pe listă

Regula: **modifici lista, apoi `notifyDataSetChanged()`**.

| Operație | Cod |
|---|---|
| Populare | `add(...)` × N în `onCreate` (anii **în intervalul de validare!**) |
| Adăugare | `add(c)` (din launcher, `pos == -1`) |
| Ștergere | `remove(position)` |
| Update | `set(pos, c)` (din launcher, `pos != -1`) |
| Sortare | `sort(Comparator.comparing(Carte::getTitlu))` / `comparingInt(Carte::getAn)` |
| Filtrare | `clear()` + adaugi din lista completă doar ce corespunde |

## 5. Un singur launcher pentru adăugare și editare

Formularul e același, iar diferența e **poziția**: la adăugare nu o trimiți și primești `-1` înapoi; la editare o trimiți și o primești înapoi.

```java
launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
        Carte c = (Carte) result.getData().getSerializableExtra("carte");
        int pos = result.getData().getIntExtra("pos", -1);
        if (c != null) {
            if (pos == -1) carti.add(c);      // adăugare
            else carti.set(pos, c);           // editare
            adapter.notifyDataSetChanged();
        }
    }
});
```
În formular: `data.putExtra("pos", getIntent().getIntExtra("pos", -1));`

## 6. Filtrarea = 2 liste sincronizate

```java
private final ArrayList<Carte> toateCartile = new ArrayList<>();   // sursa completă
private final ArrayList<Carte> carti = new ArrayList<>();          // ce se afișează (în adapter)
```
**Orice adăugare, ștergere sau editare se face în AMBELE.** Sortarea și filtrarea schimbă doar `carti`.
```java
// adăugare
carti.add(c); toateCartile.add(c);
// ștergere
Carte stearsa = carti.remove(position); toateCartile.remove(stearsa);
// editare — ÎNAINTE de carti.set
toateCartile.set(toateCartile.indexOf(carti.get(pos)), c); carti.set(pos, c);
```

## Greșeli de evitat (din rezolvarea mea)

1. **Precompletarea fără verificare pe `null`:** `getSerializableExtra("carte")` e `null` la adăugare, deci crash la „Adaugă carte”. Pui `if (existenta != null)`.
2. **Lista completă nesincronizată:** cartea ștearsă reapare la „Toate”, cea adăugată dispare, cea editată revine la versiunea veche.
3. **Date de test în afara validării** (de exemplu 1920 la validarea 1990–2026): editarea nu se mai poate salva.
4. **Două variabile `c` în aceeași metodă** (una în lambda, una afară): redenumește în `existenta`.
5. `android.app.AlertDialog` merge, dar alege importul **`androidx.appcompat.app.AlertDialog`**.
6. `tvAn.setText(an)` cu `int` crapă, pentru că Android caută o resursă. Folosește `String.valueOf(an)`.
7. Codul tău se scrie **după** blocul generat (`EdgeToEdge` / `setContentView` / `ViewCompat`), nu între bucățile lui.
