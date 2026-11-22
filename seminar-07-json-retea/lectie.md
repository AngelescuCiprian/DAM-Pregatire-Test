# Seminar 7: Date din rețea (HTTPS) + parsare JSON

**Scop:** aplicația descarcă un JSON de pe internet, îl transformă în obiecte Java și le afișează într-un ListView.

## Fluxul complet

```
onCreate
  └─ new Thread ──────────────── (fir SECUNDAR: aici ai voie la rețea)
        HttpsManager.procesare()  → String json  (textul brut descărcat)
        └─ Handler(getMainLooper()).post ── (înapoi pe firul PRINCIPAL: aici ai voie la UI)
              MasinaParser.parsareJson(json) → List<Masina>
              masini.addAll(...)  +  adapter.notifyDataSetChanged()
```

## 1. Permisiunea de internet (Manifest)

```xml
<uses-permission android:name="android.permission.INTERNET" />
```
Se pune **în `<manifest>`, deasupra lui `<application>`**. Fără ea apare excepția `SecurityException: Permission denied (missing INTERNET permission?)`.

## 2. Regula celor 2 fire de execuție

| Firul principal (UI thread) | Fir secundar (`new Thread`) |
|---|---|
| desenează ecranul, primește click-uri | face operații lente: rețea, fișiere mari |
| ❌ **rețea interzisă** → `NetworkOnMainThreadException` | ❌ **UI interzis** → `CalledFromWrongThreadException` |

Rezultatul: descarci pe firul secundar, apoi **te întorci** pe cel principal ca să atingi lista sau ecranul.

```java
new Thread(() -> {
    String json = new HttpsManager(URL).procesare();          // fir secundar: rețea
    new Handler(getMainLooper()).post(() -> {                // înapoi pe firul principal
        afiseaza(json);                                      // UI: adapter, Toast, setText
    });
}).start();                                                  // nu uita start()!
```
Variantă echivalentă, mai scurtă: `runOnUiThread(() -> afiseaza(json));`

## 3. Descărcarea: `HttpsManager` (NU se învață, o primești gata făcută, inclusiv la test)

Doar ca să știi ce face, nu de memorat:
```java
HttpsURLConnection conn = (HttpsURLConnection) new URL(adresaUrl).openConnection();
BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
StringBuilder sb = new StringBuilder();
String line;
while ((line = reader.readLine()) != null) {
    sb.append(line);
}
reader.close();
conn.disconnect();
return sb.toString();
```
- `openConnection()` deschide conexiunea, iar `getInputStream()` e fluxul de octeți primit de la server.
- `InputStreamReader` transformă octeții în caractere, iar `BufferedReader` îți dă `readLine()`.
- `StringBuilder` lipește liniile într-un singur String.
- În `finally` închizi conexiunea (`close` / `disconnect`).

**Ce trebuie să știi:** `new HttpsManager(url).procesare()` îți dă **JSON-ul ca String** și trebuie apelat **pe un fir secundar**. Atât.

## 4. JSON: sintaxa

```json
[                                   ← [ ] = JSONArray (listă)
  {                                 ← { } = JSONObject (un obiect)
    "serie": "B123ABC",             ← "cheie": "text"   → getString("serie")
    "marca": "Renault",
    "cp": 100,                      ← număr întreg      → getInt("cp")
    "pret": 9500.5,                 ← număr real        → getDouble("pret")
    "electrica": false,             ← boolean           → getBoolean("electrica")
    "proprietar": { "nume": "Ion" } ← obiect în obiect → getJSONObject("proprietar").getString("nume")
  },
  { ... }
]
```
Reguli: cheile **mereu între ghilimele duble**, virgulă între elemente, **nicio virgulă după ultimul element**.

## 5. Parsarea: `MasinaParser`

```java
JSONArray array = new JSONArray(json);              // String → listă JSON
for (int i = 0; i < array.length(); i++) {
    JSONObject obj = array.getJSONObject(i);        // elementul i
    String serie = obj.getString("serie");          // câmp cu câmp
    int cp = obj.getInt("cp");
    lista.add(new Masina(serie, cp, obj.getString("marca")));
}
```
- Toate metodele pot arunca `JSONException`, așa că pui `try/catch`.
- Cheile (`"serie"`) trebuie să fie **identice** cu cele din JSON (case-sensitive). Seminarul le ține în constante, ca să nu greșești.
- `optString("cheie", "implicit")` / `optInt(...)`: nu aruncă excepție dacă lipsește cheia.

## 6. Afișarea

Identic cu ce știi: `masini.addAll(listaParsata)` + `adapter.notifyDataSetChanged()`, **pe firul principal**.

## Erori frecvente

| Eroare | Cauza |
|---|---|
| `SecurityException ... INTERNET permission` | lipsește `uses-permission` din Manifest |
| `NetworkOnMainThreadException` | rețeaua e accesată direct în `onCreate`, fără `Thread` |
| `CalledFromWrongThreadException` | `notifyDataSetChanged` / `setText` apelat din firul secundar |
| `JSONException: Value <html> ...` | URL-ul dă o **pagină HTML**, nu JSON-ul brut; folosește linkul de tip *raw / API* |
| `JSONException: No value for "x"` | cheia scrisă greșit sau lipsă din JSON |
| Nu se întâmplă nimic | ai uitat `.start()` la Thread, sau `notifyDataSetChanged()` |
| `UnknownHostException` | emulatorul n-are internet |
| NPE în `inchidereConexiuni` | conexiunea a eșuat, deci `bufferedReader` e `null`; verifică `!= null` înainte de `close()` |
