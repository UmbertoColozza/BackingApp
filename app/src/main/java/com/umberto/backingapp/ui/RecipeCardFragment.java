package com.umberto.backingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Ingredient;
import com.umberto.backingapp.data.Recipe;
import com.umberto.backingapp.utility.ApiClient;
import com.umberto.backingapp.utility.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeCardFragment extends Fragment {
    private static final String RECIPE_LIST="recipe_list";
    private RecyclerView mRecyclerView;
    private ArrayList<Recipe> mRecipesList;

    // OnItemClickListener interface, calls a method in the host activity named onItemSelected
    public interface OnItemClickListener {
        void onItemSelected(Recipe position);
    }

    // Empty constructor
    public RecipeCardFragment(){
    }

    // Inflates the GridView of all List Recipe
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_card, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rvRecipe);

        //if savedInstanceState is null load Load remote recipe list
        if(savedInstanceState==null) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ArrayList<Recipe>> call = apiService.getRecipes();
            call.enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    mRecipesList = response.body();
                    SetRecipeCardFragment();
                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    Log.d("MainActivity", "Error " + t.toString());
                }
            });
        } else {
            mRecipesList = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            SetRecipeCardFragment();
        }
        // Return the root view
        return rootView;
    }

    private void SetRecipeCardFragment(){
        //if attr isTable equal false set one column else set three column
        int numberOfColumns=1;
        if(getResources().getBoolean(R.bool.isTablet)){
            numberOfColumns=3;
        }
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this.getContext(),numberOfColumns);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        RecipeListAdapter recipeListAdapter=new RecipeListAdapter(this.getContext(),mRecipesList,(OnItemClickListener) getContext());
        mRecyclerView.setAdapter(recipeListAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(RECIPE_LIST,mRecipesList);
    }
}
