package com.example.derekyu.fridgegrabber.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.derekyu.fridgegrabber.Models.Ingredient;
import com.example.derekyu.fridgegrabber.Models.Recipe;
import com.example.derekyu.fridgegrabber.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipeListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe_list);

        //creates Database object
        DatabaseHelper db = new DatabaseHelper(this);


        //Uncomment this section if you want to have a test run
        /*
        Ingredient ingredient = new Ingredient("Cheese","dairy","5","April 5");
        ArrayList<Ingredient> inglist = new ArrayList<Ingredient>();
        inglist.add(ingredient);
        Recipe test = new Recipe("1","Tacos", "45 minutes", "Cook them!", "favorite", inglist);
        db.insertRecipe(test);
        */
        //Test End

        //recipeList is a list of all favorited recipes
        List<Recipe> recipeList = db.getFavoriteRecipes();

        //I had to convert it to an array because I wrote the Adapter as an Array Adapter
        final Recipe[] recipeArray = recipeList.toArray(new Recipe[recipeList.size()]);

        String testString = recipeArray[0].getName();
        Integer testsize = recipeList.size();
        String testtest = Integer.toString(testsize);
        Log.d("mytag", testtest);

        //List Adapters

        final RecipeAdapter recipeAdapt = new RecipeAdapter(this, R.layout.listview_recipe_adapter, recipeArray);
        ListView recipe_list = (ListView) findViewById(R.id.listView);
        recipe_list.setAdapter(recipeAdapt);

        //When you click on a recipe then
        recipe_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Intent intent = new Intent(getApplicationContext(), RecipeDetails.class);
                String recipeID = recipeArray[position].getId();
                intent.putExtra("recipeID", recipeID);
                startActivity(intent);
            }
        });

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
