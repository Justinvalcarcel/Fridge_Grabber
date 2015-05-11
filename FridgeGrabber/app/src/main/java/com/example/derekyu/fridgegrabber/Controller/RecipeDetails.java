package com.example.derekyu.fridgegrabber.Controller;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.derekyu.fridgegrabber.Models.Ingredient;
import com.example.derekyu.fridgegrabber.Models.Recipe;
import com.example.derekyu.fridgegrabber.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("FridgeGrabber");

        final DatabaseHelper db = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        String intentRecipeID = extras.getString("recipeID");

        Recipe recipe = db.getRecipe(intentRecipeID);

        TextView recipeName = (TextView) this.findViewById(R.id.recipeName);
        TextView recipeTime = (TextView) this.findViewById(R.id.recipeTime);
        TextView recipeInstructions = (TextView) this.findViewById(R.id.recipeInstructions);

        recipeName.setText(recipe.getName());
        recipeTime.setText(recipe.getTime());
        recipeInstructions.setText(recipe.getInstructions());

        //Gets the names of the ingredients so that Listview can show them
        final List<Ingredient> ingredientList = db.getRecipeIngredients(intentRecipeID);
        final List<String> ingredientNames = new ArrayList<String>();
        for (Ingredient ingredient : ingredientList){
            ingredientNames.add(ingredient.getName());
        }

        //I had to convert it to an array because I wrote the Adapter as an Array Adapter
        //final Recipe[] ingredientArray = ingredientList.toArray(new Recipe[ingredientList.size()]);
/*
        ListAdapter ingredientAdapter = new ArrayAdapter<String>(this, android.R.layout.test_list_item, ingredientNames);
        ListView trip_list_view = (ListView) findViewById(R.id.ingredientList);
        trip_list_view.setDividerHeight(0);
        trip_list_view.setClickable(true);


        trip_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                db.removeFromPantry(ingredientList.get(position));
                Toast.makeText(getBaseContext(), ingredientNames.get(position) + " removed from pantry", Toast.LENGTH_SHORT).show();
            }

        });
        trip_list_view.setAdapter(ingredientAdapter);
        */
        //
        final Ingredient[] ingredientArray = ingredientList.toArray(new Ingredient[ingredientList.size()]);
        final IngredientAdapter ingredientAdapt = new IngredientAdapter(this, R.layout.listview_ingredient_adapter, ingredientArray);
        ListView recipe_list = (ListView) findViewById(R.id.ingredientList);
        recipe_list.setAdapter(ingredientAdapt);

        //When you click on a recipe then
        recipe_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Ingredient ingredient = ingredientList.get(position);

                        db.removeFromPantry(ingredient);
                        Toast.makeText(getBaseContext(), ingredientNames.get(position) + " removed from pantry", Toast.LENGTH_SHORT).show();
                        ingredientAdapt.notifyDataSetChanged();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Intent intent;
        switch(id) {
            case R.id.recipes:
                intent = new Intent(RecipeDetails.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.modify_ingredients:
                intent = new Intent(RecipeDetails.this, ModifyIngredientsActivity.class);
                startActivity(intent);
            case R.id.view_ingredients:
                intent = new Intent(RecipeDetails.this, ViewIngredientsActivity.class);
                startActivity(intent);
                break;
            case R.id.favorited_recipes:
                intent = new Intent(RecipeDetails.this, FavoriteRecipeListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
