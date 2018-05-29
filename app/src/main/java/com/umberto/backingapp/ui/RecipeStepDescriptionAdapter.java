package com.umberto.backingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Step;
import java.util.ArrayList;

public class RecipeStepDescriptionAdapter extends RecyclerView.Adapter<RecipeStepDescriptionAdapter.RecipeStepAdapterViewHolder>{
    private RecipeListStepDescriptionFragment.OnStepDescriptionItemClickListener clickListener;

    private ArrayList<Step> steps;

    // Keeps track of the context and list of recipes to display
    private Context mContext;

    public RecipeStepDescriptionAdapter(Context context, ArrayList<Step> stepList, RecipeListStepDescriptionFragment.OnStepDescriptionItemClickListener clickListener){
        mContext=context;
        steps=stepList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipeStepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.step_description_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeStepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterViewHolder holder, int position) {
        holder.tvStepDescription.setText(steps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(steps==null) {
            return 0;
        }
        return steps.size();
    }


    class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvStepDescription;

        RecipeStepAdapterViewHolder(View view) {
            super(view);

            tvStepDescription = (TextView)view.findViewById(R.id.tvStepDescription);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemSelected(getAdapterPosition());
        }
    }
}
