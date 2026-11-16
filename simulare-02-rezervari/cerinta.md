# Simulare 2 (recapitulare): RezervariApp

**Timp: 75 de minute** · 9p + 1p din oficiu · Java, `findViewById`, ListView + adaptor generic

Strict pe lista profesorului. Totul se transmite cu `putExtra` / `getSerializableExtra`.

## Înainte să începi: checklist de capcane
- [ ] Tema **fără `NoActionBar`** în `values/themes.xml` **și** în `values-night/themes.xml`
- [ ] Activitățile noi le creezi cu New → Activity → **Empty Views Activity**
- [ ] Codul tău se scrie **după** blocul generat (`EdgeToEdge` / `setContentView` / `ViewCompat`)
- [ ] Adaptorul se creează **cu lista**, iar după orice modificare urmează `notifyDataSetChanged()`
- [ ] **Aceeași cheie** la `putExtra` și la `getSerializableExtra`
- [ ] Rulezi după fiecare punct

---

1. **(0.5p) Ciclul de viață.** În `MainActivity`, `onPause` cu `Log.d` + Toast.

2. **(1p) Clasa `Rezervare`:**
   - `numeClient` (String), `telefon` (String), `nrPersoane` (int), `sala` (String: Interior / Terasă / VIP), `fumatori` (boolean);
   - `Serializable`, constructor, getteri, `toString()` („Popescu · 4 pers. · Terasă”).

3. **(1.5p) `AddRezervareActivity`:**
   - EditText pentru nume, telefon (`inputType="phone"`) și număr de persoane (`number`), Spinner pentru sală, CheckBox „Zonă fumători”, buton „Salvează”;
   - **validare:** câmpuri goale, telefonul are **exact 10 cifre**, numărul de persoane e **între 1 și 20** (altfel Toast);
   - se deschide cu butonul „Rezervare nouă”, folosind **`launcher.launch()`**.

4. **(1.5p) Afișare + adăugare:**
   - **ListView** cu adaptor generic, **populat** la pornire cu 4 rezervări (măcar 2 pe Terasă, date valide);
   - rezervarea nouă apare în listă;
   - `Log.d` în callback.

5. **(1.5p) Click → detalii:**
   - `DetaliiRezervareActivity`, deschisă cu **`startActivity()`**, afișează toate câmpurile în **TextView-uri**;
   - butonul „Sună clientul” deschide aplicația de apel cu numărul completat (**intent implicit** `ACTION_DIAL`).

6. **(1.5p) Long click → AlertDialog:**
   - **„Editează”** (update): deschide `AddRezervareActivity` **precompletată**, inclusiv Spinner-ul și CheckBox-ul, iar la salvare rezervarea se modifică pe loc;
   - **„Șterge”**;
   - **„Anulează”**.

7. **(1.5p) Meniu:**
   - „Sortează după persoane”: **descrescător**;
   - „Doar Terasă”: filtrare;
   - „Toate”: anulează filtrul;
   - „Total persoane”: Toast cu suma persoanelor din listă.

**Recomandat:** textele în `strings.xml`.

---

## Hint-uri (citește-le doar dacă te blochezi)
- **Telefon exact 10 cifre:** `telefon.length() != 10` → Toast.
- **ACTION_DIAL:** `startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + r.getTelefon())));`
- **Sortare descrescătoare:** `Comparator.comparingInt(Rezervare::getNrPersoane).reversed()`
- **CheckBox precompletat:** `cbFumatori.setChecked(existenta.isFumatori());`
- **Editarea cu filtru activ:** `toate.set(toate.indexOf(lista.get(pos)), noua);` **înainte** de `lista.set(pos, noua)`.
- **Un singur launcher:** `pos == -1` → `add`, altfel → `set`.
- **Suma:** `int total = 0; for (Rezervare r : lista) total += r.getNrPersoane();`
