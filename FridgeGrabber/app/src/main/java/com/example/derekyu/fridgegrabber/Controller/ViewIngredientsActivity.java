package com.example.derekyu.fridgegrabber.Controller;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.derekyu.fridgegrabber.Models.Ingredient;
import com.example.derekyu.fridgegrabber.R;

import java.util.ArrayList;

public class ViewIngredientsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ingredients);

        DatabaseHelper db = new DatabaseHelper(this);

        // get user's current ingredients from database
        ArrayList<Ingredient> ingredients = db.getPantry();

        //if none in the database, make a toast saying no ingredients were found
        if (ingredients.size() == 0)
        {
            Context context = getApplicationContext();
            CharSequence text = "No ingredients were found - Please add ingredients first!";
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(context, text, duration).show();
        }

        // use ArrayAdapter to populate listView with TextView objects from the ingredients ArrayList
        ArrayAdapter adapter = new ArrayAdapter<Ingredient>(this, android.R.layout.simple_list_item_1, ingredients);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);

    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_ingredients, menu);
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
