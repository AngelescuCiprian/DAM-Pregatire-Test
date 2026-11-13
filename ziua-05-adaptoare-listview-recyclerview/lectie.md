# Ziua 5: Adaptor generic vs personalizat, ListView vs RecyclerView

*Seminarele 5 și 6*

## 1. Ce e un adaptor

Lista ta (`ArrayList<Film>`) e doar date, iar ListView-ul și RecyclerView-ul sunt doar „ecranul”. **Adaptorul e puntea dintre ele.** Lista vizuală îl întreabă două lucruri:
1. **„Câte rânduri am?”** → `lista.size()`
2. **„Dă-mi rândul de pe poziția X”** → adaptorul ia `lista.get(X)` și construiește un View cu datele lui

## 2. Generic vs personalizat

| | Generic | Personalizat |
|---|---|---|
| Clasa | `ArrayAdapter<Film>`, gata făcută | **clasa ta**: `FilmAdapter extends ...` |
| Layout-ul unui rând | `android.R.layout.simple_list_item_1` (un singur TextView) | **al tău**: `item_film.xml` (oricâte view-uri) |
| Ce afișează | `toString()` | ce scrii tu: `setText` pe fiecare câmp |
| Cod | o linie | o clasă |
| Funcționează cu | doar ListView | ListView (`getView`) **sau** RecyclerView |

„Doar view” = adaptorul doar **construiește și completează rândul**. Click-urile, ștergerea și dialogurile rămân în activitate.

## 3. ListView vs RecyclerView

Ambele afișează o listă printr-un adaptor. **RecyclerView e versiunea nouă și mai strictă.**

| | ListView | RecyclerView |
|---|---|---|
| Adaptor generic | ✅ `ArrayAdapter` | ❌ nu există, **mereu personalizat** |
| Adaptor personalizat | `extends ArrayAdapter<Film>` + `getView()` | `extends RecyclerView.Adapter<VH>` + 3 metode |
| Refolosirea rândurilor | opțională (`convertView`) | **obligatorie** (ViewHolder) |
| LayoutManager | nu | **obligatoriu**, altfel ecran gol |
| Click pe rând | `setOnItemClickListener` gata făcut | nu există: îl pui tu în adaptor |
| Notificare | `notifyDataSetChanged()` | `notifyItemInserted / Removed / Changed(pos)`, cu animații |
| Când îl folosești | liste mici, simple | liste mari, rânduri complexe |

## 4. Reciclarea (de unde vine numele)

Ai 1000 de filme, dar pe ecran încap 10. RecyclerView **creează doar ~12 rânduri**. Când un rând iese din ecran prin scroll, nu e distrus: e **refolosit** pentru filmul care intră, iar adaptorul îi rescrie textele.

```
onCreateViewHolder  → CREEAZĂ un rând gol din XML       (de puține ori)
onBindViewHolder    → UMPLE rândul cu filmul de pe pos  (la fiecare scroll)
getItemCount        → CÂTE rânduri sunt
```
Consecința: în `onBind` setezi **toate** câmpurile **de fiecare dată**. Ce nu rescrii rămâne de la filmul anterior.

**ViewHolder** = o „cutie” care ține referințele la TextView-urile unui rând. `findViewById` se face **o singură dată**, la creare, nu la fiecare scroll.

## 5. Corespondența dintre cele două adaptoare

| ListView: `getView()` | RecyclerView |
|---|---|
| `if (convertView == null) inflate(...)` | `onCreateViewHolder` |
| `findViewById` + `setText` | constructorul ViewHolder-ului + `onBindViewHolder` |
| `getCount()` (moștenit din ArrayAdapter) | `getItemCount()` |

Ideea e aceeași, doar împărțită diferit.

## 6. Click-uri în RecyclerView (bonus)

RecyclerView n-are `setOnItemClickListener`. Varianta din Seminarul 6:
1. În adaptor declari o **interfață** `OnFilmListener { void onFilmClick(int position); }`.
2. Adaptorul o primește în constructor, iar în `onBindViewHolder` pune `holder.itemView.setOnClickListener(v -> listener.onFilmClick(holder.getBindingAdapterPosition()))`.
3. Activitatea face `implements FilmRecyclerAdapter.OnFilmListener` (cu `Alt+Enter` → Implement methods) și trimite `this` în constructor.

Folosești `holder.getBindingAdapterPosition()`, nu `position`: după o ștergere, `position` din `onBind` poate fi învechit.

## 7. Tips & tricks

- **Ordinea de lucru:** layout-ul rândului (`item_film.xml`) → adaptorul → layout-ul activității → activitatea.
- **⚠️ `item_film.xml` are rădăcina cu `layout_height="wrap_content"`.** Cu `match_parent`, fiecare rând ocupă tot ecranul și vezi un singur film.
- `inflate(R.layout.item_film, parent, false)`: **`false`** e obligatoriu, altfel crash.
- Ca să generezi metodele adaptorului:
  - scrii `extends RecyclerView.Adapter<FilmRecyclerAdapter.FilmViewHolder>`;
  - pe ViewHolder-ul roșu: `Alt+Enter` → *Create inner class*;
  - pe numele clasei: `Alt+Enter` → *Implement methods* (sau `Ctrl+I`), care le generează pe toate 3.
- În ListView: `Ctrl+O` → `getView`.
- În adaptorul RecyclerView nu ai `this` ca context: folosești `parent.getContext()`, respectiv `holder.itemView.getContext()`.
- RecyclerView în Palette: **Containers → RecyclerView**. Dacă e roșu, adaugi dependența `androidx.recyclerview:recyclerview:1.3.2`.
- **RecyclerView gol?** Verifică întâi `setLayoutManager`, apoi `getItemCount` (să nu returneze 0), apoi `wrap_content` în `item_film.xml`.
- Treci adaptorului **aceeași listă** cu care lucrezi în activitate (`final`). Altfel modifici o listă, iar adaptorul o afișează pe alta.
