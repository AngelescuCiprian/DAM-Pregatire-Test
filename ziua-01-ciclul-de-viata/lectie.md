# Ziua 1: Ciclul de viață, Log, Toast

*Seminarul 1*

## Concepte

**Activitatea = un ecran.** Pe parcursul vieții trece prin stări, iar pentru fiecare Android îți apelează o metodă:

```
onCreate → onStart → onResume   →   [pe ecran, interactivă]
                                        │
                     onPause → onStop  ←┘  (Home, altă activitate peste)
                        │
           onRestart → onStart → onResume   (revii)
                        │
                    onDestroy   (Back, finish(), rotire)
```

| Situație | Ordine |
|---|---|
| Pornire | `onCreate → onStart → onResume` |
| Home | `onPause → onStop` |
| Revenire | `onRestart → onStart → onResume` |
| **Rotire** | `onPause → onStop → onDestroy → onCreate → onStart → onResume` |
| Back | `onPause → onStop → onDestroy`* |

\* Pe Android 12+, Back pe activitatea **principală** nu o mai distruge (task-ul merge în fundal, ca la Home). Pe o activitate secundară, `onDestroy` se apelează normal.

> ⚠️ Rotirea și `onSaveInstanceState` **nu intră la test** (confirmat de profesor). Partea despre rotire de mai jos e doar de referință.

**Rotirea = obiect nou.** Activitatea e distrusă și creată din nou, deci **toate câmpurile clasei revin la valorile inițiale**. Android salvează singur doar starea view-urilor care au id (textul din EditText, CheckBox-ul bifat). TextView-ul setat din cod și variabilele tale se pierd.

**Rezolvarea:** salvezi în `onSaveInstanceState` și restaurezi în `onCreate`, cu `if (savedInstanceState != null)`.


## Exemplificarea unei stări (tip de cerință la test)

*„Exemplificați momentul în care se apelează `onPause`.”* Suprascrii metoda cu `Ctrl+O`, pui Toast + Log și **declanșezi starea în fața profului**.

```java
@Override
protected void onPause() {
    super.onPause();
    Toast.makeText(this, "onPause - activitatea nu mai e în prim-plan", Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onPause");
}
```

| Metoda | Cum o declanșezi ca s-o arăți |
|---|---|
| `onCreate`, `onStart`, `onResume` | pornești aplicația sau deschizi activitatea |
| `onPause` → `onStop` | deschizi **altă activitate** (de exemplu formularul de adăugare) sau apeși Home |
| `onRestart` → `onStart` → `onResume` | revii (Back din activitatea a doua) |
| `onDestroy` | `finish()` sau Back pe o **activitate secundară** (de exemplu Salvează în AddCarteActivity) |

- Cel mai clar se vede **deschizând a doua activitate**: Toast-ul din `onPause` al primei activități rămâne pe ecran peste cea nouă.
- **AlertDialog nu declanșează `onPause`**, pentru că dialogul face parte din aceeași activitate.
- `onDestroy` arată-l pe activitatea **secundară**. Pe Android 12+, Back pe activitatea principală nu o distruge.

## Cod esențial

```java
private static final String TAG = "MainActivity";   // logt + Tab

Log.d(TAG, "onCreate");                             // logd + Tab
Toast.makeText(this, "Mesaj", Toast.LENGTH_SHORT).show();

@Override
protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt("counter", counter);
}

// în onCreate
if (savedInstanceState != null) {
    counter = savedInstanceState.getInt("counter");
}
```

## Greșeli de evitat (din rezolvarea mea)

- **Nu uita `Log` și în `onCreate`.** Altfel, în Logcat rotirea pare `onDestroy → onStart`.
- `Ctrl+O` oferă **două** variante de `onSaveInstanceState`. Ia varianta cu **un singur parametru** (`Bundle`). Cea cu `PersistableBundle` nu se apelează la rotire.
- Importuri nefolosite (de exemplu `PersistableBundle`): `Ctrl+Alt+O`.
- Declari view-ul o singură dată (nu de două ori `findViewById` pentru același TextView).
- Textul „Apăsat de X ori” se pune în `strings.xml` cu `%1$d`, nu concatenat în cod.
