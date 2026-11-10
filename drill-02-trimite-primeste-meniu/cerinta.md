# Drill 2: Trimite & Primește + meniu mic

**Fără validări. Din memorie.** Nu te uita la Drill 1 sau la foaie decât dacă te blochezi peste 3 minute.

| Rundă | Țintă | Timpul tău |
|---|---|---|
| 1 | 20 min | |
| 2 | 12 min | |

## Cerință

1. Clasa **`Produs`**: `nume` (String), `pret` (double), `implements Serializable`, constructor + getteri + `toString()` („Lapte - 7.50 lei”).
2. **`MainActivity`**: un LinearLayout vertical cu butonul `btnAdauga` („Adaugă produs”) și, sub el, `listView`.
3. **`AddProdusActivity`**: EditText-urile `etNume` și `etPret` (inputType `numberDecimal`) și butonul `btnSalveaza`. La click creezi produsul și îl **trimiți înapoi** (`setResult` + `finish`).
4. În MainActivity, produsul primit **apare în listă** (launcher: OK → scoate → adaugă → notify).
5. **Click** pe un produs → **`DetaliiProdusActivity`**, deschisă cu `startActivity`, care afișează numele și prețul în 2 TextView-uri.
6. **Meniu** cu 2 opțiuni:
   - „Număr produse” → Toast „Ai X produse”;
   - „Total” → Toast „Total: Y lei” (suma prețurilor, calculată cu un `for` prin listă).

## Verificare
- [ ] Adaugi 3 produse și apar în listă.
- [ ] Back din formular fără salvare → nu crapă.
- [ ] Click pe un produs → detaliile sunt corecte.
- [ ] „Total” dă suma corectă, cu zecimale.

## Greșelile tipice (din prima mea rundă)
1. `findViewById` **înainte** de `setContentView` → `null` → crash. Codul tău vine **după** blocul generat.
2. `new ArrayAdapter<>(this, layout)` **fără listă** → nimic pe ecran.
3. Cheia `"carte"` copiată din alt proiect, deși trimiterea folosea `"produs"` → `null`.
4. Lipsește `notifyDataSetChanged()`.
5. `launcher.launch(...)` direct în `onCreate` → formularul se deschide singur la pornire. `launch` se pune **în click**.

**Ordinea din `onCreate`:** bloc generat → `findViewById` → adaptor (cu lista!) → `register` → click-uri.
