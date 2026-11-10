# Ziua 4: BibliotecaApp (de la zero)

**Țintă: 70 de minute în total.** Proiect nou: **Empty Views Activity**, Java, `findViewById`, butoane simple.

## 4A: Populare, detalii, intent implicit (~25 min)

1. Tema fără `NoActionBar` (o să ai meniu la 4C).
2. `Carte`: `titlu`, `gen`, `an`, `implements Serializable`, constructor + getteri + `toString()`.
3. `MainActivity`: un LinearLayout vertical cu `btnAdauga` și `listView`. Metoda `populeaza()` adaugă 5 cărți (măcar 2 SF, ani între 1990 și 2026).
4. `DetaliiCarteActivity`, cu `tvTitlu`, `tvGen`, `tvAn`, `btnCauta` și `btnDistribuie`:
   - citește cartea din `getIntent()`;
   - „Caută pe Google” → `ACTION_VIEW`;
   - „Distribuie” → `ACTION_SEND` + chooser.
5. **Click** pe o carte → detaliile ei, cu `startActivity`.
6. `onPause` în MainActivity cu un Toast.

## 4B: Adăugare, ștergere, editare (~30 min)

1. `AddCarteActivity`, cu `etTitlu`, `spGen` (Roman, SF, Poezie, Istorie), `etAn` și `btnSalveaza`:
   - validare: gol → parse → interval 1990–2026;
   - la salvare trimite înapoi `"carte"` și `"pos"`.
2. **Un singur launcher:** `pos == -1` înseamnă `add`, altfel `set(pos, ...)`.
3. `btnAdauga` → deschide formularul gol.
4. **Long click** → AlertDialog cu 3 butoane:
   - **Șterge**: `remove` + Toast;
   - **Editează**: deschide formularul cu `"carte"` și `"pos"`;
   - **Anulează**.
5. La editare, formularul vine **precompletat**, inclusiv Spinner-ul.

## 4C: Sortare și filtrare din meniu (~15 min)

1. Meniul are 4 opțiuni:
   - „Sortează după titlu”
   - „Sortează după an”
   - „Doar SF”
   - „Toate cărțile”
2. Lista completă `toateCartile`, sincronizată la adăugare, ștergere și editare.

## Verificare

- [ ] La pornire apar 5 cărți. Click pe una → detaliile sunt corecte.
- [ ] „Caută pe Google” și „Distribuie” funcționează.
- [ ] Adaugi o carte → apare la final. Anul 1985 → Toast de eroare.
- [ ] Long click → „Anulează” doar închide dialogul.
- [ ] Long click → „Editează” → formularul e precompletat. Salvezi → cartea se modifică pe loc.
- [ ] Long click → „Șterge” → cartea dispare.
- [ ] Filtrezi pe SF, ștergi una, apeși „Toate cărțile” → cartea ștearsă **nu** reapare.
- [ ] Adaugi o carte cât timp e activ filtrul SF, apoi „Toate cărțile” → cartea e acolo.
