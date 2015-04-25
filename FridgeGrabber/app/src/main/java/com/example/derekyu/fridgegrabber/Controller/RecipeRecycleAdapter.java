package com.example.derekyu.fridgegrabber.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.derekyu.fridgegrabber.Models.Recipe;
import com.example.derekyu.fridgegrabber.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by derekyu on 4/25/15.
 */
public class RecipeRecycleAdapter extends RecyclerView.Adapter<RecipeViewHolder>{

    private List<Recipe> recipeList;
    private Context mContext = MainActivity.instance();
    private List<Double> matchPercentage;
    static OnItemClickListener mItemClickListener;

    public RecipeRecycleAdapter(List<Recipe> recipeList, List<Double> matchPercentage){
        this.recipeList = recipeList;
        this.matchPercentage = matchPercentage;
    }
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view, viewGroup, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder viewHolder, int i) {
        Recipe recipe = recipeList.get(i);
        viewHolder.recipe_name.setText(recipe.getName());
        Log.d("recipeUrl", recipe.getImgUrl());
        Picasso.with(mContext).load(recipe.getImgUrl()).fit().centerCrop().into(viewHolder.recipe_picture);
        Double percent = matchPercentage.get(i)* 100;
        int wholePercent = percent.intValue();
        viewHolder.match_percentage.setText(""+ wholePercent + "%");
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


}
