# Taskuri date de profesor la final de seminar

Rezolvările se adaugă în zilele indicate.

## Task 1: Telefon + buton „Sună” *(Ziua 4)*
- Adaugă în `Student` câmpul `phone` (String), iar în `activity_add_student.xml` un EditText cu `inputType="phone"`.
- Validare: telefonul are **exact 10 cifre**.
- Pe ecranul de detalii afișează telefonul și adaugă butonul **„Sună”**, care deschide aplicația de apel cu numărul completat:
  ```java
  startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
  ```

## Task 2: Culori pe rând în RecyclerView *(Ziua 5)*
- Definește în `colors.xml` două culori: verde și albastru.
- În `onBindViewHolder` colorează numele studentului (`tvName`) după an: anii 1–2 cu verde, anul 3 cu albastru.
  ```java
  holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.verde));
  ```
- Verifică faptul că, după derulare și după ștergerea unui student, culorile rămân corecte pe fiecare rând. Fiecare rând își setează culoarea **de fiecare dată, inclusiv pe ramura `else`**.

## Task 3: Clasa Carte *(Ziua 3 ✅ + Ziua 4)*
- `Carte`: `titlu`, `gen` (Spinner: Roman, SF, Poezie, Istorie), `anul apariției`.
- Validare: anul între 1990 și 2026.
- Prima opțiune din meniu afișează un Toast cu numărul de cărți din listă.

→ Rezolvat în [ziua-03-activitati-launcher-meniu](../ziua-03-activitati-launcher-meniu/).
