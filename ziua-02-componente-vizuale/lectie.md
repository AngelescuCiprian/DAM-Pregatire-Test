# Ziua 2: Componente vizuale, validare, resurse

*Seminarul 2 (calculatorul de IMC)*

## Concepte

| Componentă | Citire | Scriere / control |
|---|---|---|
| EditText | `et.getText().toString().trim()` | `et.setText("")`, `et.requestFocus()` |
| TextView | — | `tv.setText(...)`, `tv.setTextColor(...)`, `tv.setVisibility(...)` |
| CheckBox | `cb.isChecked()` | `cb.setChecked(false)`, `setOnCheckedChangeListener` |
| Spinner | `sp.getSelectedItem().toString()` / `sp.getSelectedItemPosition()` | `sp.setSelection(0)` |
| Button | — | `btn.setOnClickListener(v -> ...)` |

**Validarea are mereu ordinea gol → parse → interval**, iar fiecare pas ratat se termină cu Toast + `return;`. Dacă faci parse înainte să verifici golul, aplicația crapă cu `NumberFormatException`.

**`inputType`** controlează tastatura: `number` (doar cifre), `numberDecimal` (cu zecimale), `phone`, `textPersonName`, `textEmailAddress`.

**Resurse:**
- `strings.xml`: textele, inclusiv cele cu format (`%1$s` text, `%2$d` întreg, `%3$.2f` real cu 2 zecimale), plus `string-array` pentru Spinner;
- `colors.xml`: culorile, folosite cu `ContextCompat.getColor(this, R.color.verde)`.

**Vizibilitate:** `View.VISIBLE`, `View.GONE` (nu ocupă loc), `View.INVISIBLE` (ocupă loc).

**Dimensiuni:** `dp` pentru distanțe și mărimi, `sp` pentru text (respectă setarea de mărime a fontului din telefon). `match_parent` = cât părintele, `wrap_content` = cât conținutul.

## Greșeli de evitat (din rezolvarea mea)

1. **Împărțire întreagă:** `int pret = 35; pret /= 2;` dă **17**. Folosește `double` și `/ 2.0`.
2. **Resetul golește și variabilele**, nu doar ecranul. Altfel, după reset + rotire, rezultatul vechi reapare.
3. **Culoarea din `colors.xml` + `ContextCompat`**, nu `Color.GREEN`. Taskul de la seminar cere explicit forma asta.
4. **Rândul cu butoane:** LinearLayout orizontal cu `layout_height="wrap_content"`, iar butoanele cu `layout_width="0dp"` + `layout_weight="1"`.
5. La rotire trebuie refăcută **și culoarea**, nu doar textul. Salvezi și un `boolean`.
6. Textele, inclusiv cel compus pentru rezultat, se pun în `strings.xml`.
