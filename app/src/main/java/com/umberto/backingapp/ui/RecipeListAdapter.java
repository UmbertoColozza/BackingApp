package com.umberto.backingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Recipe;
import java.util.List;

// Custom adapter class that displays a list of recipe in a GridView
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeAdapterViewHolder> {
    // Keeps track of the context and list of recipes to display
    private Context mContext;
    private List<Recipe> mRecipesList;
    private RecipeCardFragment.OnItemClickListener itemClickListener;

    /**
     * Constructor method
     * @param mRecipesList The list of recipes to display
     * @param itemClickListener set event listener
     */
    public RecipeListAdapter(Context context, List<Recipe> mRecipesList, RecipeCardFragment.OnItemClickListener itemClickListener){
        mContext=context;
        this.mRecipesList=mRecipesList;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int position) {
        String name=mRecipesList.get(position).getName();
        holder.tvRecipeName.setText(name);
    }

    @Override
    public int getItemCount() {
        if(mRecipesList==null){
            return 0;
        }
        return mRecipesList.size();
    }

    class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvRecipeName;

        RecipeAdapterViewHolder(View view) {
            super(view);

            tvRecipeName = (TextView)view.findViewById(R.id.txRecipeName);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            itemClickListener.onItemSelected(mRecipesList.get(position));
        }
    }
}
