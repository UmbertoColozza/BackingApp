package com.umberto.backingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Ingredient;
import com.umberto.backingapp.data.Recipe;
import com.umberto.backingapp.data.Step;

import java.util.ArrayList;

public class RecipeListStepDescriptionActivity extends AppCompatActivity implements RecipeListStepDescriptionFragment.OnStepDescriptionItemClickListener {
    private static final String TITLE="title";
    public static final String RECIPE = "recipe";
    private Recipe recipe;
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list_step);
        if(findViewById(R.id.flRecipeStepDetail)!=null){
            mTwoPane=true;
        } else {
            mTwoPane=false;
        }

        if(savedInstanceState == null) {
            recipe = (Recipe)getIntent().getParcelableExtra("recipe");

            if(recipe!=null) {
                this.setTitle(recipe.getName());
                // Create a new head RecipeListStepDescriptionFragment
                RecipeListStepDescriptionFragment recipeListStepDescriptionFragment = new RecipeListStepDescriptionFragment();
                // Set recipe for the fragment
                recipeListStepDescriptionFragment.setRecipe(recipe);

                // Add the fragment to its container using a FragmentManager and a Transaction
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.flRecipeStepDescription, recipeListStepDescriptionFragment)
                        .addToBackStack(null)
                        .commit();


                if(mTwoPane){
                    RecipeMediaFragment recipeMediaFragment=new RecipeMediaFragment();
                    recipeMediaFragment.setRecipe(recipe,0, true);

                    fragmentManager.beginTransaction()
                            .add(R.id.flRecipeStepDetail, recipeMediaFragment)
                            .addToBackStack(null)
                            .commit();

                }
            }
        } else {
            this.setTitle(savedInstanceState.getCharSequence(TITLE));
            recipe=savedInstanceState.getParcelable(RECIPE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Recipe" ,"Back pressed");
    }

    @Override
    public void onItemSelected(int stepPosition) {
        if(mTwoPane){
            RecipeMediaFragment recipeMediaFragment=new RecipeMediaFragment();
            recipeMediaFragment.setRecipe(recipe,stepPosition, true);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flRecipeStepDetail, recipeMediaFragment)
                    .commit();
        } else {
            Bundle b = new Bundle();
            b.putParcelable("recipe", recipe);
            b.putInt("step_index", stepPosition);

            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(TITLE,this.getTitle());
        outState.putParcelable(RECIPE,recipe);
    }
}
