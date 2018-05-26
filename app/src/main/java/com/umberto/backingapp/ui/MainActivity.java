package com.umberto.backingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Recipe;
import com.umberto.backingapp.utils.PrefercenceUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements RecipeCardFragment.OnItemClickListener {
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Implement OnItemClickListener from RecipeCardFragment retrieve selected recipe
    @Override
    public void onItemSelected(Recipe recipe) {
        Bundle b = new Bundle();
        b.putParcelable("recipe", recipe);

        //Save current recipe for widget
        PrefercenceUtils.setRecipe(this, recipe);

        // Attach the Bundle to an intent
        final Intent intent = new Intent(this, RecipeListStepDescriptionActivity.class);
        intent.putExtras(b);
        startActivity(intent);

    }
}
