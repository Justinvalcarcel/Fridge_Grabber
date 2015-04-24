package com.example.derekyu.fridgegrabber.Controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.derekyu.fridgegrabber.Models.Recipe;
import com.example.derekyu.fridgegrabber.R;

/**
 * Created by justinvalcarcel on 4/22/15.
 * This is a custom adapter so that the favorite recipes can be viewed.
 * listview_recipe_adapter is the XML that can be modified to add text/images/etc
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private int resource;
    private Recipe[] objects;

    public RecipeAdapter(Context context, int resource, Recipe[] objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(resource, parent, false);

        Recipe recipe = objects[position];

        String rname = recipe.getName();
        String rtime = recipe.getTime();
        Log.d("mytag",rname);

        TextView recipeAdaptName = (TextView) row.findViewById(R.id.recipeAdaptName);
        TextView recipeAdaptTime = (TextView) row.findViewById(R.id.recipeAdaptTime);
        Log.d("mytag","before setters");
        recipeAdaptName.setText(rname);
        recipeAdaptTime.setText(rtime);
        //recipeAdaptName.setText(recipe.getName());
        //recipeAdaptTime.setText(recipe.getTime());
        Log.d("mytag", "after setters");
        return row;
    }
}
