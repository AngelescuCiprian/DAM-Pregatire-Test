# Ziua 3: CartiApp

**Țintă: 45 de minute.** Proiect nou: **Empty Views Activity**, Java, cu `findViewById`.

## Cerințe

1. **Tema:** în `res/values/themes.xml` scoți `NoActionBar` (`parent="Theme.Material3.DayNight"`).

2. **Clasa `Carte`:**
   - câmpurile `titlu` (String), `gen` (String), `an` (int);
   - `implements Serializable`;
   - constructor, getteri și `toString()`, toate generate cu `Alt+Insert`. Format: „Dune · SF, 2021”.

3. **`AddCarteActivity`:**
   - EditText `etTitlu`
   - Spinner `spGen` (Roman, SF, Poezie, Istorie)
   - EditText `etAn` (inputType `number`)
   - Button `btnSalveaza`

   Validare:
   - câmpuri goale → Toast;
   - an în afara intervalului 1990–2026 → Toast.

   La salvare: trimiți cartea înapoi cu `setResult(RESULT_OK, data)` + `finish()`.

4. **`MainActivity`:**
   - layout: un LinearLayout vertical cu un `Button` (`btnAdauga`, „Adaugă carte”) și, sub el, un `ListView` (`listView`);
   - lista de cărți afișată prin `ArrayAdapter` cu `simple_list_item_1`;
   - `btnAdauga` → deschide `AddCarteActivity` cu **`launcher.launch()`**;
   - cartea primită se adaugă în listă și apare pe ecran, iar în Logcat apare un `Log.d` cu ea.

5. **Meniu** (`res/menu/menu.xml`):
   - „Număr cărți” → Toast „Sunt X cărți în listă” (text din `strings.xml` cu `%1$d`);
   - „Despre” → deschide `AboutActivity` cu **`startActivity()`**. Aceasta are un TextView „CartiApp v1.0” și un buton „Înapoi” care apelează `finish()`.


## Verificare

- [ ] Adaugi 3 cărți și apar în ordine.
- [ ] Anul 1985 → Toast, iar ecranul nu se închide.
- [ ] Back din formular fără salvare → nu crapă și nu adaugă nimic.
- [ ] Meniu → „Număr cărți” = 3.
- [ ] Meniu → „Despre” → „Înapoi” → revii la lista intactă.
