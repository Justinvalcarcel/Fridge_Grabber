package com.example.derekyu.fridgegrabber.Controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.derekyu.fridgegrabber.Models.Ingredient;
import com.example.derekyu.fridgegrabber.Models.Recipe;
import com.example.derekyu.fridgegrabber.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        DatabaseHelper db = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        String intentRecipeID = extras.getString("recipeID");

        Recipe recipe = db.getRecipe(intentRecipeID);

        TextView recipeID = (TextView) this.findViewById(R.id.recipeID);
        TextView recipeName = (TextView) this.findViewById(R.id.recipeName);
        TextView recipeTime = (TextView) this.findViewById(R.id.recipeTime);
        TextView recipeInstructions = (TextView) this.findViewById(R.id.recipeInstructions);
        recipeID.setText(intentRecipeID);
        recipeName.setText(recipe.getName());
        recipeTime.setText(recipe.getTime());
        recipeInstructions.setText(recipe.getInstructions());

        //Gets the names of the ingredients so that Listview can show them
        List<Ingredient> ingredientList = db.getRecipeIngredients(intentRecipeID);
        List<String> ingredientNames = new ArrayList<String>();
        for (Ingredient ingredient : ingredientList){
            ingredientNames.add(ingredient.getName());
        }

        //I had to convert it to an array because I wrote the Adapter as an Array Adapter
        //final Recipe[] ingredientArray = ingredientList.toArray(new Recipe[ingredientList.size()]);

        ListAdapter ingredientAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientNames);
        //ListAdapter phone_adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contact_phoneNo);
        ListView trip_list_view = (ListView) findViewById(R.id.ingredientList);
        trip_list_view.setAdapter(ingredientAdapter);


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
