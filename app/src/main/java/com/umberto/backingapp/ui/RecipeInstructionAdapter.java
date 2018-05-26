package com.umberto.backingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Ingredient;

import java.util.ArrayList;

public class RecipeInstructionAdapter extends RecyclerView.Adapter<RecipeInstructionAdapter.RecipeInstructionAdapterViewHolder>{
    private ArrayList<Ingredient> ingredients;

    // Keeps track of the context and list of recipes to display
    private Context mContext;

    public RecipeInstructionAdapter(Context context,ArrayList<Ingredient> ingredientsList){
        mContext=context;
        ingredients=ingredientsList;
    }

    @NonNull
    @Override
    public RecipeInstructionAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecipeInstructionAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInstructionAdapterViewHolder holder, int position) {
        holder.tvQuantityValue.setText(TextUtils.concat(Float.toString(ingredients.get(position).getQuantity())," ",ingredients.get(position).getMeasure()));
        holder.tvIngredientValue.setText(ingredients.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        if(ingredients==null) {
            return 0;
        }
        return ingredients.size();
    }


    class RecipeInstructionAdapterViewHolder extends RecyclerView.ViewHolder {
        final TextView tvQuantityValue;
        final TextView tvIngredientValue;

        RecipeInstructionAdapterViewHolder(View view) {
            super(view);
            tvQuantityValue = (TextView)view.findViewById(R.id.tvQuantityValue);
            tvIngredientValue = (TextView)view.findViewById(R.id.tvIngredientValue);
        }
    }
}
