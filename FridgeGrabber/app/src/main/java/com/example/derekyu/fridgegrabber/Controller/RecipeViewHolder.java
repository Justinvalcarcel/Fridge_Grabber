package com.example.derekyu.fridgegrabber.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.derekyu.fridgegrabber.R;

/**
 * Created by derekyu on 4/25/15.
 */
public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected TextView recipe_name;
    protected TextView match_percentage;
    protected ImageView recipe_picture;


    public RecipeViewHolder(View itemView) {
        super(itemView);
        recipe_name = (TextView) itemView.findViewById(R.id.recipe_name);
        match_percentage = (TextView) itemView.findViewById(R.id.percent_match);
        recipe_picture = (ImageView) itemView.findViewById(R.id.recipe_picture);
        itemView.setOnClickListener(this);
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        RecipeRecycleAdapter.mItemClickListener.onItemClick(v, getPosition());
    }
}
