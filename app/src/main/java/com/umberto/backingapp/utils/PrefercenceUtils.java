package com.umberto.backingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Recipe;
import com.google.gson.Gson;

public class PrefercenceUtils {
    //Retrieve saved recipe from json string. if is empty return empty recipe
    public static Recipe getRecipe(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyRecipe = context.getString(R.string.pref_recipe_key);
        String jsonRecipe = sp.getString(keyRecipe, "");
        if (TextUtils.isEmpty(jsonRecipe)){
            return new Recipe();
        }
        Gson gson = new Gson();
        Recipe recipe=gson.fromJson(jsonRecipe,Recipe.class);
        return recipe;
    }

    //Saved recipe to json string.
    public static void setRecipe(Context context,Recipe recipe){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        Gson gson = new Gson();
        String value=gson.toJson(recipe);
        editor.putString(context.getString(R.string.pref_recipe_key), value);
        editor.apply();
    }
}
