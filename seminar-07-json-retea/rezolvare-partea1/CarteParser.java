package ro.ase.bibliotecaonline;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarteParser {

    // O singură metodă: String → JSONArray → for → JSONObject → Carte
    public static List<Carte> parsareJSON(String json) {
        ArrayList<Carte> carti = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                carti.add(new Carte(
                        obj.getString("titlu"),
                        obj.getString("autor"),
                        obj.getInt("an"),
                        obj.getDouble("pret"),
                        obj.getBoolean("disponibila")));
            }
        } catch (JSONException e) {
            // FIX: doar logăm (cu excepția!) și întoarcem ce s-a parsat — fără crash
            Log.e("CarteParser", "JSON invalid", e);
        }
        return carti;
    }
}
