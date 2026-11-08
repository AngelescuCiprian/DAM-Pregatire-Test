# Ziua 2: Calculator bilet de film

**Țintă: 30 de minute.** Proiect nou: **Empty Views Activity**, Java. Rădăcina din `activity_main.xml` e un LinearLayout vertical cu `padding="16dp"` și `android:id="@+id/main"`.

## Layout (din designer)

| Id | Tip | Detalii |
|---|---|---|
| `etName` | EditText | hint „Nume”, inputType `textPersonName` |
| `etAge` | EditText | hint „Vârstă”, inputType `number` |
| `spType` | Spinner | `entries` = `@array/ticket_types` (Standard, VIP, 3D) |
| `cbStudent` | CheckBox | „Student (-50%)” |
| `cbDetails` | CheckBox | „Arată prețurile” |
| `btnCalc`, `btnReset` | Button | pe același rând, lățimi egale (`0dp` + weight 1) |
| `tvResult` | TextView | bold, 18sp |
| `tvDetails` | TextView | „Standard 25 lei · VIP 40 lei · 3D 35 lei”, `visibility="gone"` |

În `colors.xml`: `verde` (#2E7D32) și `albastru` (#1565C0).

## Logică

1. Bifezi `cbDetails` → `tvDetails` apare. Debifezi → dispare.
2. **Calculează:**
   - nume sau vârstă goală → Toast „Completează numele și vârsta!”;
   - vârsta în afara intervalului 12–100 → Toast;
   - prețul după poziția din Spinner: 25 / 40 / 35. Student → jumătate;
   - `tvResult`: „Ion, biletul tău VIP costă 20.00 lei” (`strings.xml` cu `%1$s %2$s %3$.2f`);
   - culoare **verde** dacă s-a aplicat reducerea, **albastru** altfel;
   - `Log.d` cu prețul.
3. **Resetează:** golește câmpurile, Spinner-ul revine pe 0, debifezi ambele CheckBox-uri, golești rezultatul (și variabila!), focus pe `etName`.
4. **Rotire:** rezultatul și culoarea lui se păstrează.

## Verificare

- 3D + student = **17.50**, nu 17.
- Calculează → Resetează → rotește: rezultatul **nu** reapare.
- Calculează cu student → rotește: rezultatul rămâne **verde**.
