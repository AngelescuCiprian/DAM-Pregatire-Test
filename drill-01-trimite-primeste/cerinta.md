# Drill 1: Trimite & Primește

**Scop:** să scrii din reflex fluxul de date între activități. **Fără validări.**

**Metoda:** același proiect, **de 3 ori, din memorie**, cu cronometrul pornit. Ștergi proiectul (sau faci altul nou) între runde.

| Rundă | Țintă | Timpul tău |
|---|---|---|
| 1 | 15 min | |
| 2 | 10 min | |
| 3 | 6 min | |

## Partea 1: `launch()` (datele vin înapoi)

1. Clasa `Film`: `titlu` (String), `an` (int), `implements Serializable`, constructor și `toString()` generate cu `Alt+Insert`.
2. `AddFilmActivity` (Empty Views Activity): EditText-urile `etTitlu` și `etAn`, plus butonul `btnTrimite`. La click creezi Film-ul și îl trimiți înapoi.
3. `MainActivity`: un LinearLayout vertical cu `btnAdauga` și, sub el, `listView`. La click pe buton se deschide AddFilmActivity, iar filmul primit apare în listă.

## Partea 2: `startActivity()` (datele merg într-o singură direcție)

4. Click pe un film din listă → `DetaliiFilmActivity`, cu un TextView `tvDetalii` care afișează filmul.

## Scheletul mental

**Cel care trimite înapoi (AddFilmActivity):** 3 linii
```java
Intent data = new Intent();
data.putExtra("film", film);
setResult(RESULT_OK, data); finish();
```

**Cel care primește (MainActivity):** 4 locuri
1. câmpuri: `lista`, `adapter`, `launcher`
2. adaptorul + `setAdapter`
3. înregistrarea în `onCreate`. Callback-ul: **OK → scoate → adaugă → notify**
4. lansarea la click: `launcher.launch(...)`

**Diferența dintre cele două:**
- `startActivity`: datele merg **doar într-o direcție**, către ecranul nou (`getIntent()`).
- `launch`: datele vin **și înapoi** (`setResult` → callback).

**Truc:** după `new ActivityResultContracts.` apasă `Ctrl+Space` și alege `StartActivityForResult`.
