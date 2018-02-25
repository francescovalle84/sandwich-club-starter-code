package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // Sandwich components
        String mainName = "";
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        List<String> ingredients = new ArrayList<>();

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichInfo = new JSONObject(json);

            JSONObject sandwichName = sandwichInfo.getJSONObject("name");
            sandwich.setMainName(sandwichName.getString("mainName"));

            JSONArray alsoKnownAsArray = sandwichName.getJSONArray("alsoKnownAs");
            for(int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add((String) alsoKnownAsArray.get(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAs);

            sandwich.setPlaceOfOrigin(sandwichInfo.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichInfo.getString("description"));
            sandwich.setImage(sandwichInfo.getString("image"));

            JSONArray ingredientsArray = sandwichInfo.getJSONArray("ingredients");
            for(int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add((String) ingredientsArray.get(i));
            }
            sandwich.setIngredients(ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
