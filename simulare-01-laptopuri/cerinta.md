# Simulare Test 1: LaptopApp

**Timp: 90 de minute** · Punctaj: 9p + 1p din oficiu · Java, `findViewById`, ListView + **adaptor generic**

**View-uri obligatorii:** Spinner, CheckBox (−1p pentru fiecare view nefolosit)

**Id-urile view-urilor din formular conțin numele tău**, de exemplu `angelescu_ciprian_salvare` sau `angelescu_ciprian_model`.

---

1. **(0.5p) Proiect + ciclul de viață.** Creează proiectul. În `MainActivity`, suprascrie `onPause`, cu un `Log.d` și un Toast care arată când se apelează.

2. **(1p) Clasa `Laptop`:**
   - atribute `model` (String), `pret` (double), `tip` (String: Gaming / Office / Ultrabook) și `garantieExtinsa` (boolean);
   - poate fi transmisă între activități;
   - are `toString()`.

3. **(1.5p) `AddLaptopActivity`.** Activitate nouă pentru introducerea datelor:
   - EditText pentru model și preț, Spinner pentru tip, CheckBox „Garanție extinsă”, buton de salvare;
   - **validare:** câmpurile nu sunt goale, iar prețul e mai mare decât 0 (altfel Toast);
   - se deschide din `MainActivity` cu butonul „Adaugă laptop”.

4. **(1.5p) Returnarea obiectului.**
   - Obiectul creat se returnează către `MainActivity` **prin Bundle**.
   - Acolo se adaugă într-o `List<Laptop>`, afișată într-un **ListView** cu adaptor generic.
   - La pornire lista e **populată** cu 4 laptopuri.

5. **(1p) Click pe item** → Toast cu obiectul selectat.

6. **(1p) Long click pe item** → AlertDialog cu 3 opțiuni:
   - **„Reducere 10%”**: prețul laptopului scade cu 10% (update);
   - **„Șterge”**: elimină laptopul;
   - **„Anulează”**.

7. **(1.5p) Meniu** cu opțiunile:
   - „Sortează după preț”: crescător;
   - „Doar Gaming”: filtrare;
   - „Toate”: anulează filtrul;
   - „Statistici”: deschide **a treia activitate**, `StatisticiActivity`, cu `startActivity`, căreia **îi trimite lista**. Aceasta afișează în TextView-uri:
     - numărul de laptopuri;
     - prețul mediu;
     - câte au garanție extinsă.

8. **(1p) Intent implicit.** În `StatisticiActivity`, butonul „Trimite raport” trimite textul statisticilor prin `ACTION_SEND`.

**Toate textele din interfață se iau din fișierele de resurse**, atât cele din XML, cât și cele din Java (−1p).

**Bonus (+1p):** adaptor personalizat (`LaptopAdapter extends ArrayAdapter<Laptop>` + `item_laptop.xml`), în locul celui generic.

---

## Tips

**Bundle** (cum apare formularea în alte teste):
```java
// AddLaptopActivity
Bundle b = new Bundle();
b.putSerializable("laptop", laptop);
Intent data = new Intent();
data.putExtras(b);
setResult(RESULT_OK, data);
finish();

// MainActivity, în callback
Laptop l = (Laptop) result.getData().getExtras().getSerializable("laptop");
```

**Lista trimisă către altă activitate:**
```java
// MainActivity
intent.putExtra("lista", new ArrayList<>(toateLaptopurile));   // ArrayList e Serializable

// StatisticiActivity
ArrayList<Laptop> lista = (ArrayList<Laptop>) getIntent().getSerializableExtra("lista");
```

**Reducerea de 10% fără să mai sincronizezi listele:** pune în `Laptop` un setter `setPret`, apoi apelezi `l.setPret(l.getPret() * 0.9)` + `notifyDataSetChanged()`. Obiectul e același în ambele liste, deci se actualizează peste tot.

**CheckBox:** `cb.isChecked()` se salvează în `garantieExtinsa`.

## Strategia pentru test

1. **Ordinea** urmează punctajul: 2 → 3 → 4 (launcher) → 5 → 6 → 7 → 8 → 1. Punctul 1 îl faci oricând, durează 1 minut.
2. **Textele le pui în `strings.xml` din mers.** Pui cursorul pe text și `Alt+Enter` → *Extract string resource*. Nu le lăsa pe final.
3. **Rulează după fiecare punct.** O aplicație care pornește și face 6 din 8 puncte valorează mai mult decât una completă care crapă.
4. Te-ai blocat mai mult de 5 minute? **Treci mai departe** și revii la final.
5. Un crash înseamnă Logcat → `FATAL EXCEPTION` → primul link albastru cu fișierul tău.
