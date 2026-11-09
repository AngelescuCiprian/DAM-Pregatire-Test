# Ziua 1: Cerință

**Țintă: 20 de minute.** Proiect nou: **Empty Views Activity**, Java.

## Partea 1: aplicația care loghează stările

1. În `MainActivity` adaugă un TAG (`logt` + Tab).
2. Cu `Ctrl+O` suprascrie `onStart`, `onResume`, `onPause`, `onStop`, `onRestart` și `onDestroy`.
3. În fiecare metodă, inclusiv `onCreate`, pune un `Log.d` cu numele metodei. În `onCreate` pune și un Toast.
4. Rulează și notează ordinea logurilor când:
   - a) pornești aplicația
   - b) apeși Home
   - c) revii în aplicație
   - d) rotești ecranul
   - e) apeși Back

## Partea 2 (bonus, NU intră la test): pierderea datelor la rotire

1. Layout: un LinearLayout vertical (păstrează `android:id="@+id/main"`) cu:
   - `EditText` cu id `etText`
   - `Button` cu id `btnAdd` și textul „Adaugă”
   - `TextView` cu id `tvCounter`
2. Câmpul `private int counter = 0;`. La click pe buton, `counter++` și TextView-ul afișează „Apăsat de X ori” (text din `strings.xml`, cu `%1$d`).
3. Apasă de 5 ori, scrie ceva în EditText și rotește. **Ce s-a păstrat? Ce nu? De ce?**
4. Repară cu `onSaveInstanceState` + restaurarea în `onCreate`.

## Partea 3: exemplificare (tip de test)

Suprascrie `onPause` și `onDestroy` cu câte un **Toast**. Adaugă o a doua activitate, deschisă cu un buton, și arată:
- Toast-ul din `onPause` al MainActivity, care apare la deschiderea celei de-a doua activități;
- Toast-ul din `onDestroy` al celei de-a doua activități, care apare când apeși Back din ea.

## Verificare

- Logcat-ul pentru rotire arată `onPause → onStop → onDestroy → onCreate → onStart → onResume`.
- După rotire, contorul rămâne la aceeași valoare.
