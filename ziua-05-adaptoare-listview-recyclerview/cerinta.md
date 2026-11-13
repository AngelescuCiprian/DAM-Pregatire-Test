# Ziua 5: FilmeApp (de la zero)

**Țintă: 60 de minute.** Proiect nou: **Empty Views Activity**, Java, `findViewById`.

## 5A: ListView, întâi generic, apoi personalizat (~25 min)

1. Clasa `Film`: `titlu`, `regizor`, `an`, `implements Serializable`, constructor + getteri + `toString()` („Inception (2010)”).
2. `MainActivity`: un LinearLayout vertical cu `btnRecycler` („Vezi în RecyclerView”) și `listView`. Populează 8 filme.
3. **Adaptor generic:** `ArrayAdapter<Film>` cu `simple_list_item_1`. **Rulează** și observă că se afișează `toString()`.
4. **Layout pentru un rând,** `item_film.xml`: `tvTitlu` (18sp, bold) și `tvDetalii` („regizor · an”). Rădăcina are **`wrap_content`**.
5. **Adaptor personalizat,** `FilmAdapter extends ArrayAdapter<Film>`, cu `getView()`. Îl pui în locul celui generic. **Rulează** și compară.
6. În activitate (adaptorul e „doar view”):
   - click → Toast cu titlul;
   - long click → AlertDialog „Ștergi filmul?”, cu Da/Nu.

## 5B: RecyclerView, aceleași date (~25 min)

1. `RecyclerActivity`, deschisă din `btnRecycler` cu `startActivity` + `putExtra("filme", filme)`.
2. `FilmRecyclerAdapter extends RecyclerView.Adapter<...>`, cu:
   - ViewHolder (`tvTitlu`, `tvDetalii`);
   - `onCreateViewHolder`, `onBindViewHolder`, `getItemCount`;
   - **refolosește `item_film.xml`**.
3. În activitate: primești lista din `getIntent()`, apoi `setLayoutManager(new LinearLayoutManager(this))` + `setAdapter`.

## 5C (bonus): click-uri în RecyclerView (~10 min)

1. Interfața `OnFilmListener` în adaptor, cu `onFilmClick(int)` și `onFilmLongClick(int)`.
2. `RecyclerActivity implements OnFilmListener`:
   - click → Toast;
   - long click → AlertDialog → `remove` + **`notifyItemRemoved(position)`**.

## Verificare

- [ ] Cu adaptorul generic vezi „Inception (2010)”, cu cel personalizat vezi 2 rânduri de text cu stiluri diferite.
- [ ] Toate cele 8 filme sunt vizibile, iar un rând **nu** ocupă tot ecranul.
- [ ] Ștergerea din ListView funcționează.
- [ ] RecyclerView-ul arată aceleași filme ca ListView-ul.
- [ ] *(bonus)* Ștergerea din RecyclerView are animație.

**Întrebări de verificare:**
1. Ce se întâmplă dacă uiți `setLayoutManager`?
2. De ce RecyclerView nu are adaptor generic?
3. Ce face `onCreateViewHolder` și ce face `onBindViewHolder`? Care se apelează mai des?
