package com.umberto.backingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Recipe;

import java.util.ArrayList;

public class RecipeListStepDescriptionFragment extends Fragment {
    // Final Strings to store state information about the recipe
    public static final String RECIPE = "recipe";

    private Recipe recipe;
    // Define a new interface OnItemClickListener that triggers a callback in the host activity
    OnStepDescriptionItemClickListener mCallback;

    // OnItemClickListener interface, calls a method in the host activity named onItemSelected
    public interface OnStepDescriptionItemClickListener {
        void onItemSelected(int stepPosition);
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public RecipeListStepDescriptionFragment(){}

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Load the saved state (the list of images and list index) if there is one
        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE);
        }

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_recipe_list_step_description, container, false);

        // Get a reference to the RecyclerView ingredients in the fragment layout
        final RecyclerView rvIngredients = (RecyclerView) rootView.findViewById(R.id.rvRecipeStepIngredients);
        // Get a reference to the RecyclerView Step description in the fragment layout
        final RecyclerView rvStep = (RecyclerView) rootView.findViewById(R.id.rvRecipeStepDescription);
        if(rvIngredients != null){
            // Set the adapter for rvIngredients
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
            rvIngredients.setLayoutManager(linearLayoutManager);
            RecipeInstructionAdapter recipeInstructionAdapter=new RecipeInstructionAdapter(this.getContext(),recipe.getIngredients());
            rvIngredients.setAdapter(recipeInstructionAdapter);
        }
        if(rvStep != null){
            // Set the adapter for rvIngredients
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
            rvStep.setLayoutManager(linearLayoutManager);
            RecipeStepDescriptionAdapter recipeStepDescriptionAdapter =new RecipeStepDescriptionAdapter(this.getContext(),recipe.getSteps(),(OnStepDescriptionItemClickListener)this.getContext());
            rvStep.setAdapter(recipeStepDescriptionAdapter);
        }

        // Return the rootView
        return rootView;
    }

    public void setRecipe(Recipe recipe){
        this.recipe=recipe;
    }
    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable(RECIPE, recipe);
    }
}
