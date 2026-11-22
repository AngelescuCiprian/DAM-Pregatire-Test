# Seminar 7: Exercițiu: BibliotecaOnline

**Țintă: 45 de minute.** Proiect nou, Empty Views Activity, `findViewById`.

## Pasul 0: JSON-ul tău (10 min)
Pe site-ul dat de profesor (sau pe **npoint.io** / **jsonkeeper.com**) creează un JSON array cu **5 cărți**:
```json
[
  { "titlu": "Dune", "autor": "Frank Herbert", "an": 2021, "pret": 49.99, "disponibila": true },
  ...
]
```
Copiază **linkul raw / API**: deschis în browser, trebuie să arate **doar JSON-ul**, nu o pagină.

## Partea 1: descărcare + afișare (25 min)
1. Permisiunea `INTERNET` în Manifest.
2. Clasa `Carte` (`titlu`, `autor`, `an`, `pret`, `disponibila`) cu `toString()`.
3. `HttpsManager`: **scrie din memorie** partea de citire (URL → conexiune → BufferedReader → StringBuilder). Fără `trustEveryone`.
4. `CarteParser.parsareJson(String json)` → `List<Carte>`.
5. `MainActivity`: ListView cu adaptor generic, iar în `onCreate`:
   - `new Thread` → descarci;
   - te întorci pe firul principal → parsezi → `addAll` → `notifyDataSetChanged()`;
   - `Log.d` cu JSON-ul brut + Toast „S-au încărcat X cărți”.

## Partea 2: legare cu ce știi deja (10 min)
6. Click pe o carte → Toast cu autorul și prețul.
7. Meniu:
   - „Reîncarcă”: `clear()` + descarci din nou;
   - „Doar disponibile”: filtrare;
   - „Sortează după an”.

## Bonus
8. Fără internet (mod avion pe emulator), aplicația **nu crapă**: `try/catch` în thread + Toast „Eroare de rețea” (pe firul principal!).
9. Adaugă în JSON un obiect imbricat `"editura": { "nume": "Nemira", "oras": "București" }` și afișează-l în Toast.

## Verificare
- [ ] Lista apare cu cele 5 cărți din JSON-ul **tău** (schimbă o carte pe site → „Reîncarcă” → apare modificarea).
- [ ] În Logcat vezi JSON-ul brut.
- [ ] Mod avion → nu crapă.
