package com.umberto.backingapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Recipe;

public class RecipeStepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        if(savedInstanceState == null) {
            Recipe recipe = (Recipe)getIntent().getParcelableExtra("recipe");
            int stepIndex = getIntent().getIntExtra("step_index",0);
            this.setTitle(recipe.getName());
            RecipeMediaFragment recipeMediaFragment=new RecipeMediaFragment();
            recipeMediaFragment.setRecipe(recipe,stepIndex,false);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.flRecipeStepDetail, recipeMediaFragment)
                    .commit();
        }
    }
}
