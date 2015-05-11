package com.example.derekyu.fridgegrabber.Controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.derekyu.fridgegrabber.Models.Ingredient;
import com.example.derekyu.fridgegrabber.R;

/**
 * Created by justinvalcarcel on 4/22/15.
 * This is a custom adapter so that the favorite recipes can be viewed.
 * listview_recipe_adapter is the XML that can be modified to add text/images/etc
 */
public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private int resource;
    private Ingredient[] objects;


    public IngredientAdapter(Context context, int resource, Ingredient[] objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(resource, parent, false);

        Ingredient ingredient = objects[position];

        String iname = ingredient.getName().toLowerCase();

        Log.d("mytag",iname);
        DatabaseHelper db = new DatabaseHelper(context);

        TextView ingredientAdaptName = (TextView) row.findViewById(R.id.ingredient_name_adapt);


        if(db.ingredientInPantry(ingredient)){
            ingredientAdaptName.setTextColor(Color.rgb(0, 200, 0));
        }
        else{
            ingredientAdaptName.setTextColor(Color.rgb(200, 0, 0));
        }

        Log.d("mytag","before setters");
        ingredientAdaptName.setText(iname);

        Log.d("mytag", "after setters");
        return row;
    }
}
