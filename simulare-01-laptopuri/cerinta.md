# Simulare Test 1: LaptopApp

**Timp: 90 de minute** · 9p + 1p din oficiu · Java, `findViewById`, ListView + **adaptor generic**

Strict pe lista profesorului. Totul se transmite cu `putExtra` / `getSerializableExtra`.

**View-uri obligatorii:** Button, TextView, EditText, Spinner, CheckBox, meniu.

---

1. **(0.5p) Ciclul de viață.** În `MainActivity`, suprascrie `onPause` cu un `Log.d` și un Toast.

2. **(1p) Clasa `Laptop`:**
   - `model` (String), `pret` (double), `tip` (Gaming / Office / Ultrabook) și `garantieExtinsa` (boolean);
   - `implements Serializable`, getteri, `setPret`, `toString()`.

3. **(1.5p) Activitate nouă, `AddLaptopActivity`:**
   - EditText pentru model și preț, Spinner pentru tip, CheckBox „Garanție extinsă”, buton de salvare;
   - validare: câmpuri goale sau preț ≤ 0 → Toast;
   - se deschide din `MainActivity` cu butonul „Adaugă laptop”, folosind **`launcher.launch()`**.

4. **(1.5p) Adăugare + afișare.**
   - Laptopul se întoarce cu `setResult` și se adaugă în `List<Laptop>`, afișată într-un **ListView** cu adaptor generic.
   - La pornire, lista e **populată** cu 4 laptopuri.
   - Pune un `Log.d` în callback-ul launcher-ului.

5. **(1.5p) Click → detalii.**
   - Click pe un laptop deschide `DetaliiLaptopActivity` cu **`startActivity()`** (Intent explicit + `putExtra`).
   - Activitatea afișează toate câmpurile în TextView-uri.
   - Are un buton „Caută pe Google”, care face **intent implicit** `ACTION_VIEW` cu modelul laptopului.

6. **(1.5p) Long click → AlertDialog** cu 3 butoane:
   - **„Reducere 10%”** (update): `setPret(pret * 0.9)` + notify;
   - **„Șterge”**;
   - **„Anulează”**.

7. **(1.5p) Meniu:**
   - „Sortează după preț”: crescător;
   - „Doar Gaming”: filtrare;
   - „Toate”: anulează filtrul;
   - „Număr laptopuri”: Toast cu numărul.

**Recomandat:** textele în `strings.xml` (extragi din mers cu `Alt+Enter` → *Extract string resource*).

**Bonus (+1p):** adaptor personalizat (`LaptopAdapter extends ArrayAdapter<Laptop>` + `item_laptop.xml`) în locul celui generic.

---

## Tips

- **Reducerea de 10%:** `setPret` modifică **același obiect**, care se află în ambele liste (`laptopuri` și `toateLaptopurile`), deci nu ai nimic de sincronizat. Doar `notifyDataSetChanged()`.
- **Ștergerea în timpul filtrării:** `Laptop sters = laptopuri.remove(position); toateLaptopurile.remove(sters);`
- **Adăugarea:** în ambele liste.
- **Precizie în Toast:** `String.format("%.2f", pret)`.

## Strategia pentru test

1. Ordinea: 2 → 3 → 4 → 5 → 6 → 7 → 1. Punctul 1 durează un minut.
2. **Rulează după fiecare punct.** O aplicație care pornește cu 6 puncte bate una completă care crapă.
3. Te-ai blocat mai mult de 5 minute? Treci mai departe și revii la final.
4. Un crash înseamnă Logcat → `FATAL EXCEPTION` → primul link albastru cu fișierul tău.
