package com.umberto.backingapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Recipe;

public class RecipeStepDetailActivity extends AppCompatActivity {
    private static final String TITLE="title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
        } else {
            this.setTitle(savedInstanceState.getCharSequence(TITLE));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(TITLE,this.getTitle());
    }
}
