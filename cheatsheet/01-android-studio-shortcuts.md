# Android Studio: shortcuts și lucru rapid

Scurtăturile sunt pentru keymap-ul implicit de Windows. Pe Mac: `Ctrl` → `Cmd`, `Alt` → `Option`.

## Generează cod în loc să-l scrii

| Shortcut | Ce face | Unde îl folosești |
|---|---|---|
| `Ctrl+O` | Override metode | `onPause`, `onSaveInstanceState`, `onCreateOptionsMenu`, `onOptionsItemSelected` |
| `Ctrl+I` | Implement metode din interfață | adaptor RecyclerView, listener-e |
| `Alt+Insert` | Generate: constructor, getter/setter, `toString()` | clasa model (Carte, Film, Student) |
| `Alt+Enter` | Quick fix: import, creare resursă string, implement methods | orice e roșu sau galben |
| `Ctrl+Space` | Autocomplete | `ActivityResultContracts.` → `StartActivityForResult` |
| `Ctrl+Shift+Enter` | Termină instrucțiunea (pune `;`, acolade) | peste tot |
| `Ctrl+Alt+V` | Extrage o variabilă din expresie | `etAn.getText().toString().trim()` → `String anStr` |
| `Ctrl+Alt+T` | Surround with (if, try/catch) | `try/catch` la `startActivity` cu intent implicit |
| `Ctrl+P` | Arată parametrii metodei | când nu știi ordinea în `new ArrayAdapter<>(...)` |

## Live templates (scrii prescurtarea + `Tab`)

| Template | Rezultat |
|---|---|
| `logt` | `private static final String TAG = "MainActivity";` |
| `logd` / `logi` / `loge` | `Log.d(TAG, "metoda: ");` |
| `logm` | log cu numele metodei și parametrii |
| `Toast` | `Toast.makeText(this, "", Toast.LENGTH_SHORT).show();` |
| `fori` | `for (int i = 0; i < ; i++)` |
| `iter` | `for (Carte c : carti)` |

## Editare și navigare

| Shortcut | Ce face |
|---|---|
| `Ctrl+Alt+L` | Reformatează codul |
| `Ctrl+Alt+O` | Șterge importurile nefolosite |
| `Ctrl+D` / `Ctrl+Y` | Duplică / șterge linia |
| `Ctrl+/` | Comentează linia |
| `Alt+Shift+↑/↓` | Mută linia |
| `Shift+F6` | Redenumește peste tot (și id-uri: XML + Java) |
| `Ctrl+B` sau `Ctrl+Click` | Mergi la definiție (pe `R.layout.x` îți deschide XML-ul) |
| `Shift Shift` | Caută orice |
| `Ctrl+E` | Fișiere recente |
| `Ctrl+Shift+A` | Caută o acțiune după nume |

## Rulare

| Shortcut | Ce face |
|---|---|
| `Shift+F10` | Run |
| `Ctrl+F10` | Apply Code Changes (fără restart complet) |
| `Ctrl+F2` | Stop |
| `Shift+F9` | Debug |

## Debugger (doar pentru urgențe, nu e cerință)

| Acțiune | Shortcut |
|---|---|
| Breakpoint | click pe marginea din stânga sau `Ctrl+F8` |
| Step Over / Step Into | `F8` / `F7` |
| Resume | `F9` |
| Evaluate expression | `Alt+F8` |
| Atașare la o aplicație deja pornită | iconița „Attach Debugger to Android Process” |

## Crearea fișierelor

- **Activitate:** click dreapta pe pachet → New → Activity → **Empty Views Activity**. Se creează Java + XML + intrare în Manifest.
- **Clasă:** click dreapta pe pachet → New → Java Class.
- **Meniu:** click dreapta pe `res` → New → Android Resource File → Resource type: **Menu** (nume: `menu`).
- **Layout pentru un rând** (adaptor personalizat): click dreapta pe `res/layout` → New → Layout Resource File.

## Designer

- Comută pe **Split** (dreapta sus), ca să vezi XML-ul și designul simultan.
- Trage elementele din **Palette** în **Component Tree** (stânga jos), nu pe ecranul desenat. Ordinea e exactă.
- În **Attributes**, lupa caută orice proprietate: `id`, `hint`, `inputType`, `entries`, `visibility`, `layout_weight`.
- Pentru texte: butonul mic din dreapta câmpului deschide „Pick a Resource”. Sau scrii textul direct în XML și pe el `Alt+Enter` → *Extract string resource*.
- Spinner fără cod: un `string-array` în `strings.xml`, apoi `entries` = `@array/nume`.
- Butoane egale pe un rând: LinearLayout orizontal (`layout_height="wrap_content"`), iar fiecare buton are `layout_width="0dp"` și `layout_weight="1"`.
- FAB: Palette → Buttons → FloatingActionButton. `src` = `@android:drawable/ic_input_add`, `layout_gravity` = `bottom|end`, `layout_margin` = `16dp` (în FrameLayout).

## Emulator și Logcat

- Rotire: `Ctrl+←` / `Ctrl+→`. **Auto-rotate** trebuie să fie pornit în quick settings-ul emulatorului.
- Filtre în Logcat: `tag:MainActivity`, `level:error`, `package:mine`.
