# DAM – Pregătire Test 1

**Dispozitive și Aplicații Mobile** · ASE CSIE · Java + Android Studio

Lecții, cerințe și rezolvări pe zile, plus cheatsheet-uri pentru test. Totul e scris cu **`findViewById`** și proiecte de tip **Empty Views Activity**.

---

## Cum folosești repo-ul

1. Deschizi `cerinta.md` din ziua respectivă.
2. Rezolvi în Android Studio **fără să te uiți la rezolvare**. Pornește cronometrul.
3. Compari cu folderul `rezolvare/`. Greșelile de evitat sunt explicate în `lectie.md`.
4. Înainte de test: [`cheatsheet/`](cheatsheet/).

---

## Learning path

| # | Folder | Subiect | Status |
|---|---|---|---|
| 1 | [ziua-01-ciclul-de-viata](ziua-01-ciclul-de-viata/) | Ciclul de viață, Log, Toast, exemplificarea stărilor | ✅ |
| 2 | [ziua-02-componente-vizuale](ziua-02-componente-vizuale/) | EditText, CheckBox, Spinner, Button, validare, `strings.xml` / `colors.xml` | ✅ |
| 3 | [ziua-03-activitati-launcher-meniu](ziua-03-activitati-launcher-meniu/) | Activitate nouă, Intent explicit, `launch()` vs `startActivity()`, meniu, ListView + ArrayAdapter | ✅ |
| D1 | [drill-01-trimite-primeste](drill-01-trimite-primeste/) | Drill: trimitere și preluare de date între activități (3 runde cu cronometru) | ✅ |
| D2 | [drill-02-trimite-primeste-meniu](drill-02-trimite-primeste-meniu/) | Drill: trimitere date + meniu (număr, total) | ✅ |
| 4 | [ziua-04-operatii-lista-dialog-intent-implicit](ziua-04-operatii-lista-dialog-intent-implicit/) | Populare, click → detalii, long click → AlertDialog (ștergere / editare), sortare, filtrare, intent implicit | ✅ |
| 5 | — | Adapter personalizat (ListView `getView()` + RecyclerView), culori pe rând, ListView vs RecyclerView | ⏳ |
| 6 | — | Simulare de test, de la zero, cu timp | ⏳ |

---

## Ce cere profesorul → unde găsești

| Cerință din lista profului | Unde |
|---|---|
| Ciclul activităților, loguri și toast-uri (exemplificare: Toast în `onPause` etc.) | Ziua 1 · [cheatsheet §1–2](cheatsheet/02-cod-sabloane.md) |
| Componente vizuale: butoane, meniu, TextView, EditText, Spinner, CheckBox | Ziua 2 + Ziua 3 (meniu) · [§3–5](cheatsheet/02-cod-sabloane.md) |
| Preluare date de la tastatură și afișare | Ziua 2 · [§3](cheatsheet/02-cod-sabloane.md) |
| Liste de obiecte: afișare, populare, sortare, filtrare, adăugare, ștergere, update | Ziua 3 (afișare, adăugare) · Ziua 4 (restul) · [§8–11](cheatsheet/02-cod-sabloane.md) |
| Binding sau findViewById() | **findViewById** peste tot (decizie: fără Gradle sync la test) |
| Logcat și debugger | [cheatsheet/03-capcane-si-erori.md](cheatsheet/03-capcane-si-erori.md) · [shortcuts](cheatsheet/01-android-studio-shortcuts.md) |
| Intent implicit, explicit, `launcher.launch()` vs `startActivity()` | Ziua 3 + Drill 1 · [§6–7, §12](cheatsheet/02-cod-sabloane.md) · [foaia de transmitere date](cheatsheet/04-transmitere-date-intre-activitati.md) |
| AlertDialog, click / long click | Ziua 4 · [§9](cheatsheet/02-cod-sabloane.md) |
| ListView, RecyclerView | Ziua 3, Ziua 5 · [§8, §14](cheatsheet/02-cod-sabloane.md) |
| Adapter generic și personalizat (doar view) | Ziua 3 (generic), Ziua 5 (personalizat) · [§13–14](cheatsheet/02-cod-sabloane.md) |
| Crearea unei activități | Ziua 3 · [shortcuts](cheatsheet/01-android-studio-shortcuts.md) |


---

## Ce NU intră la test (confirmat de profesor)

- Rotirea și `onSaveInstanceState`. Rămân în repo doar ca referință, marcate *bonus*.
- FloatingActionButton. Folosim un `Button` obișnuit.
- Fragmentele (Seminarul 3).
- Componente vizuale în afara listei: doar butoane, meniu, TextView, EditText, Spinner, CheckBox.

## Reguli fixe pentru test

- **`findViewById`**, nu binding (profesorul lasă la alegere).
- Activitate nouă: **New → Activity → Empty Views Activity** (nu „Empty Activity”, aia e Compose). Se adaugă singură în Manifest.
- Formulare: **LinearLayout vertical**, nu ConstraintLayout.
- Ai meniu → tema **fără `NoActionBar`**.
- Rădăcina layout-ului păstrează **`android:id="@+id/main"`** (altfel crapă codul `ViewCompat` generat).
- Clasa model: **`implements Serializable`**.
- Textele în `strings.xml`, culorile în `colors.xml`.

---

## Structură

```
cheatsheet/            shortcuts, șabloane de cod, capcane și erori, transmitere date
ziua-XX-.../           lectie.md · cerinta.md · rezolvare/
drill-XX-.../          exerciții scurte de repetat pe timp
taskuri-seminar/       taskurile date de profesor la final de seminar
seminarii-profesor/    codul din seminariile 1, 2, 4, 5, 6 (doar src/main)
```
