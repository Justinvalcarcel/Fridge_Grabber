package com.example.derekyu.fridgegrabber.Controller;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.derekyu.fridgegrabber.Models.Ingredient;
import com.example.derekyu.fridgegrabber.Models.Recipe;
import com.example.derekyu.fridgegrabber.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<String> imgUrls = new ArrayList<String>();
    private ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
    private String apikey2 = "?api_key=dvxTtAMf3IHeKp2MWGcw564P1drhT4ep";
    private String apikey = "&api_key=dvxTtAMf3IHeKp2MWGcw564P1drhT4ep";
    private String url = "http://api.bigoven.com/recipes?pg=1&rpp=5&any_kw=";
    private String urlIngredient = "http://api.bigoven.com/recipe/";
    ConnectivityManager cnMgr;
    private static MainActivity mContext;
    LinearLayoutManager layoutManager;
    private ArrayList<Ingredient> userIngredients;
    private ArrayList<Double> matchPercentage = new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        String s = "FridgeGrabber";
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(s);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        userIngredients = (ArrayList<Ingredient>) db.getPantry();
        String tempUrl;
        if(userIngredients.size() != 0) {
            //execute AsyncTask to get Data and display a list of recipes
            tempUrl = url;
            tempUrl = tempUrl.concat(userIngredients.get(0).getName());
            for (int i = 1; i < userIngredients.size(); i++) {
                tempUrl = tempUrl.concat("%20" + userIngredients.get(i).getName());

            }
            tempUrl = tempUrl.concat(apikey);
            tempUrl = tempUrl.replaceAll(" ", "%20");
            Log.d("newURL", tempUrl);

        }
        else {
            tempUrl = "http://api.bigoven.com/recipes?pg=1&rpp=5&api_key=dvxTtAMf3IHeKp2MWGcw564P1drhT4ep";

        }
        new RecipeSyncTask().execute(tempUrl);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContext = this;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);

        } else {
            setContentView(R.layout.activity_main);
        }
    }

    public static String GET(String url) {
        //processing HTTP GET requests
        Log.d("url", url);
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            //create HttpObject and Request Object
            HttpGet get = new HttpGet(url);
            get.setHeader("Accept", "application/json");
            //add header for BigOven Api to get JSON type data
            HttpResponse httpResponse = httpClient.execute(get);
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {
                //convert input to string
                result = convertInputStreamToString(inputStream);

            } else {
                result = "Did not work!";
            }
        } catch (Exception e) {
            Log.d("Error", e.toString());
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        //create a buffer reader that gets the data
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //read from input stream and add it to the line
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        inputStream.close();
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
        else if ( id == R.id.modify_ingredients){
            Intent intent = new Intent(MainActivity.this, ModifyIngredientsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    class RecipeSyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            try {
                //Log.d("Activity", recipes);
                ArrayList<String> recipeIDs;
                String result = GET(urls[0]);
                JSONObject jsonResult = new JSONObject(result);
                JSONArray recipes = jsonResult.getJSONArray("Results");
                recipeIDs = new ArrayList<String>();
                for (int i = 0; i < recipes.length(); i++) {
                    //Log.d("Activity", recipes.getJSONObject(i).getString("RecipeID"));

                    recipeIDs.add(recipes.getJSONObject(i).getString("RecipeID"));
                    imgUrls.add(recipes.getJSONObject(i).getString("ImageURL"));


                }
                for (int i = 0; i < recipeIDs.size(); i++) {
                    urlIngredient = GET("http://api.bigoven.com/recipe/" + recipeIDs.get(i) + apikey2);
                    Log.d("url", urlIngredient);
                    JSONObject jsonObject = new JSONObject(urlIngredient);
                    JSONArray ingredient = jsonObject.getJSONArray("Ingredients");
                    String instruction = jsonObject.getString("Instructions");
                    String title = jsonObject.getString("Title");
                    String id = recipeIDs.get(i);
                    String webURL = jsonObject.getString("WebURL");
                    Recipe tempRecipe = new Recipe();
                    tempRecipe.setId(id);
                    tempRecipe.setInstructions(instruction);
                    tempRecipe.setName(title);
                    tempRecipe.setImgUrl(imgUrls.get(i));
                    tempRecipe.setUrl(webURL);
                    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
                    for (int x = 0; x < ingredient.length(); x++) {
                        String name = ingredient.getJSONObject(x).getString("Name");
                        Ingredient tempIngredient = new Ingredient(name);
//                        tempIngredient.setName(name);
                        ingredients.add(tempIngredient);
                        Log.d("ingredient",name);

                    }

                    tempRecipe.setIngredients(ingredients);
                    checkForMatch(tempRecipe);
                    recipeList.add(tempRecipe);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Loading...", Toast.LENGTH_LONG).show();
            if (userIngredients.size() ==0){
                Toast.makeText(getBaseContext(), "No Ingredients Were Entered", Toast.LENGTH_LONG).show();
            }
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleList);
            RecipeRecycleAdapter rp = new RecipeRecycleAdapter(recipeList, matchPercentage);
            recyclerView.setAdapter(rp);
            rp.SetOnItemClickListener(new RecipeRecycleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Recipe temprec = recipeList.get(position);
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("recipe",temprec );
                    startActivity(intent);
                }
            });

        }
    }

    static public MainActivity instance() {
        return mContext;
    }

    public void checkForMatch(Recipe recipe) {
        Double counter = 0.0d;
        for ( Ingredient i : recipe.getIngredients()){
            for (Ingredient x: userIngredients){
                Log.d("x", x.getName().toLowerCase());
                Log.d("i", i.getName().toLowerCase());
                if (x.getName().toLowerCase().contains(i.getName().toLowerCase()) || i.getName().toLowerCase().contains(x.getName().toLowerCase())){

                    counter +=1;
                }
            }
        }
        Double matchPercent = counter/ (recipe.getIngredients().size());
        matchPercentage.add(Math.round(100.0*matchPercent)/100.0);

    }





}
