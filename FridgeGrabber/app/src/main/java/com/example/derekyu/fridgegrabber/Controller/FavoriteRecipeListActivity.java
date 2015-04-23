package com.example.derekyu.fridgegrabber.Controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.derekyu.fridgegrabber.Models.Ingredient;
import com.example.derekyu.fridgegrabber.Models.Recipe;
import com.example.derekyu.fridgegrabber.R;

import java.util.ArrayList;

public class FavoriteRecipeListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe_list);

        //creates Database object
        DatabaseHelper db = new DatabaseHelper(this);


        //TEST ONLY
        Ingredient ingredient = new Ingredient("Cheese","dairy","5","April 5");
        ArrayList<Ingredient> inglist = new ArrayList<Ingredient>();
        inglist.add(ingredient);
        Recipe test = new Recipe("1","Tacos", "45 minutes", "Cook them!", "favorite", inglist);
        db.insertRecipe(test);
        //Test END

        //recipeList is a list of all favorited recipes
        ArrayList<Recipe> recipeList = db.getFavoriteRecipes();
        //I had to convert it to an array because I wrote the Adapter as an Array Adapter
        Recipe[] recipeArray = recipeList.toArray(new Recipe[recipeList.size()]);

        //List Adapters

        RecipeAdapter recipeAdapt = new RecipeAdapter(this, R.layout.activity_favorite_recipe_list, recipeArray);
        ListView recipe_list = (ListView) findViewById(R.id.listView);
        recipe_list.setAdapter(recipeAdapt);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite_recipe_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
