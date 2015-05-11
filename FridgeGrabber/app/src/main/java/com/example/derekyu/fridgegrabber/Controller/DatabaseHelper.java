package com.example.derekyu.fridgegrabber.Controller;


//package com.nyu.cs9033.eta.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.derekyu.fridgegrabber.Models.Ingredient;
import com.example.derekyu.fridgegrabber.Models.Recipe;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    /*
    Database initialization. Because these are static final strings I commented out the redundant ones
    instead of deleting them just so it's easier to visualize the tables.
     */

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fridge";

    //Recipe table
    private static final String TABLE_RECIPE = "recipe";
    private static final String COLUMN_RID = "rid";
    private static final String COLUMN_RNAME = "rname";
    private static final String COLUMN_RTIME = "rtime";
    private static final String COLUMN_RINSTRUCTIONS = "rinstructions";
    private static final String COLUMN_RSTATUS = "rstatus"; // whether it's favorited, queued, etc

    //Recipe and ingredient relationship
    private static final String TABLE_RECIPE_INGREDIENT= "recipe_ingredient";
    private static final String COLUMN_RECIPE_INGREDIENT_RID =  "rid";
    private static final String COLUMN_RECIPE_INGREDIENT_IID = "iid";

    //Ingredients table
    private static final String TABLE_INGREDIENT= "ingredient";
    private static final String COLUMN_IID =  "iid";
    private static final String COLUMN_INAME = "iname";
    //private static final String COLUMN_ITYPE = "itype"; // this is ingredient foodgroup

    //Table of what ingredients the user has
    private static final String TABLE_PANTRY= "pantry";
    private static final String COLUMN_PANTRY_IID =  "iid";
    //private static final String COLUMN_PQUANTITY = "quantity";
    //private static final String COLUMN_PEXPIRATION = "expiration";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_RECIPE + " ( " +
                        COLUMN_RID + " TEXT PRIMARY KEY, " +
                        COLUMN_RNAME + " TEXT, " +
                        COLUMN_RTIME + " TEXT, " +
                        COLUMN_RINSTRUCTIONS + " TEXT, " +
                        COLUMN_RSTATUS + " TEXT)"
        );

        db.execSQL("CREATE TABLE " + TABLE_RECIPE_INGREDIENT + " ( " +
                        COLUMN_RECIPE_INGREDIENT_RID + " INTEGER REFERENCES RECIPE(rid), " +
                        COLUMN_RECIPE_INGREDIENT_IID + " INTEGER REFERENCES INGREDIENT(iid))"
        );

        db.execSQL("CREATE TABLE " + TABLE_INGREDIENT + " ( " +
                        COLUMN_IID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_INAME + " TEXT)"
        );

        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " ( " +
                        COLUMN_PANTRY_IID + " INTEGER REFERENCES INGREDIENT(iid))"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        onCreate(db);
    }


    // only inserts recipe into the table if its not already there
    public boolean insertRecipe(Recipe recipe) {

        Cursor recipeCursor = queryRecipeIDFromRecipeTable(recipe);

        //only insert recipe to db if not already in it
        boolean recipeNotInDb = (recipeCursor.getCount() == 0);

        // if recipe is NOT in the db then add it, along with its used ingredients into the ingredients table
        if (recipeNotInDb)
        {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_RID, recipe.getId());
            cv.put(COLUMN_RNAME, recipe.getName());
            cv.put(COLUMN_RTIME, recipe.getTime());
            cv.put(COLUMN_RINSTRUCTIONS, recipe.getInstructions());
            cv.put(COLUMN_RSTATUS, recipe.getStatus());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_RECIPE, null, cv);

            //first check if ingredients are in the ingredient table, if they are, get their ids, if not insert them and get the id from the insert
            //inserts the ingredients into the RECIPE_INGREDIENT table
            ArrayList<Ingredient> ingredients = recipe.getIngredients();
            for (Ingredient ingredient : ingredients) {

                //query db to see if ingredient already has a row in the ingredient table
                Cursor c = queryIngredientIDFromIngredientTable(ingredient);

                long ingredientID;
                // if query returns a result, get the ingredient id from the returned query
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    ingredientID = c.getLong(0);
                }
                // no results, insert into ingredient table and get the ingredient id after inserting
                else {
                    ingredientID = insertIngredient(ingredient);
                }

                ContentValues cv1 = new ContentValues();
                cv1.put(COLUMN_RECIPE_INGREDIENT_RID, recipe.getId());
                cv1.put(COLUMN_RECIPE_INGREDIENT_IID, ingredientID);
                //cv1.put(COLUMN_RECIPE_INGREDIENT_IID, ingredient.getName());


                db.insert(TABLE_RECIPE_INGREDIENT, null, cv1);
            }
        }

        //used to find out if recipe got inserted or not
        return recipeNotInDb;
    }

    // returns a cursor object representing query result (for id) from recipe table for a passed in recipe
    // (returns recipe id from table that matches the name of the passed in recipe)
    public Cursor queryRecipeIDFromRecipeTable(Recipe recipe)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_RID +
                        " FROM " + TABLE_RECIPE +
                        " WHERE " + COLUMN_RNAME + " = ?",
                new String [] {recipe.getName()}
        );

        return cursor;
    }




    // returns a cursor object representing query result (for id) from ingredient table for a passed in ingredient
    public Cursor queryIngredientIDFromIngredientTable(Ingredient ingredient)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_IID +
                        " FROM " + TABLE_INGREDIENT +
                        " WHERE " + COLUMN_INAME + " = ?",
                new String [] {ingredient.getName()}
        );

        return cursor;
    }

    public Cursor queryIngredientIDFromPantry(Ingredient ingredient){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PANTRY_IID + " FROM "
                            + TABLE_PANTRY + " NATURAL JOIN " + TABLE_INGREDIENT +  " WHERE " + COLUMN_INAME + " =?",
                new String [] {ingredient.getName()}) ;
        return cursor;
    }
    //adds new ingredients
    public long insertIngredient(Ingredient ingredient){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_INAME, ingredient.getName());
        //cv.put(COLUMN_ITYPE, ingredient.getFoodGroup());
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_INGREDIENT, null, cv);

    }

    //Make a Pantry object to differentiate from Ingredient
    public void insertPantry(Ingredient ingredient) {

        ContentValues cv = new ContentValues();
        // cv.put(COLUMN_INAME, ingredient.getName());
        //cv.put(COLUMN_PQUANTITY, ingredient.getQuantity());
        //cv.put(COLUMN_PEXPIRATION, ingredient.getExpiration());
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = queryIngredientIDFromIngredientTable(ingredient);
        Cursor pantryCursor = queryIngredientIDFromPantry(ingredient);
        if (pantryCursor.getCount() == 0) {
            if (c.getCount() > 0) {

                c.moveToFirst();
                cv.put(COLUMN_PANTRY_IID, c.getLong(0));
                db.insert(TABLE_PANTRY, null, cv);
            } else {
                long id = insertIngredient(ingredient);
                cv.put(COLUMN_PANTRY_IID, id);
                db.insert(TABLE_PANTRY, null, cv);
            }

        }

    }

    // returns the most recently inserted Recipe ID
    public String getRecipeID(){

        SQLiteDatabase db = this.getReadableDatabase();
        String recipeID;
        Cursor cursor = db.rawQuery("SELECT seq FROM sqlite_sequence WHERE name =? ",
                new String[] {String.valueOf(TABLE_RECIPE)});

        if(cursor.moveToFirst())
            recipeID = cursor.getString(0);
        else
            recipeID = "0";

        cursor.close();

        return recipeID;
    }

    /*
    //returns the Ingredient ID by the name input
    public String getIngredientID(Ingredient ingredient){
        SQLiteDatabase db = this.getReadableDatabase();
        String ingredientID;
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_INAME +
                " FROM " + TABLE_INGREDIENT +
                " WHERE " + COLUMN_INAME + " = ?",
                new String [] {String.valueOf(ingredient.getName())}
                );

        if(cursor.moveToFirst())
            ingredientID = cursor.getString(0);
        else
            ingredientID = "0";

        cursor.close();
        return ingredientID;
    }
    */

    //Use the Recipe as the parameter to get the Recipe object
    public Recipe getRecipe(String id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPE +
                        " WHERE " + COLUMN_RID +
                        " = ?",
                new String[]{id});

        Recipe recipe = null;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            /*
            recipe = new Recipe(null, null, null, null, null, null);
            recipe.setId(cursor.getString(0));
            recipe.setName(cursor.getString(1));
            recipe.setTime(cursor.getString(2));
            recipe.setInstructions(cursor.getString(3));
            recipe.setStatus(cursor.getString(4));
            */

            String recipeID = cursor.getString(0);
            String recipeName = cursor.getString(1);
            String recipeTime = cursor.getString(2);
            String recipeInstructions = cursor.getString(3);
            String recipeStatus = cursor.getString(4);

            //query for ingredients corresponding to this recipe id and populate arraylist with model objects
            ArrayList<Ingredient> ingredients = getRecipeIngredients(recipeID);

            recipe = new Recipe(recipeID, recipeName, recipeTime, recipeInstructions, recipeStatus, ingredients);
        }

        return recipe;
    }


    //returns names of all ingredients in recipe when given recipe id
    public ArrayList<Ingredient> getRecipeIngredients(String id){
        ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();

        SQLiteDatabase db = this.getReadableDatabase();

        // queries from the natural join of recipe_ingredient and ingredient tables for the name of ingredients that correspond to the recipe id
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_INAME +
                " FROM " + TABLE_RECIPE_INGREDIENT + " NATURAL JOIN " + TABLE_INGREDIENT +
                " WHERE " + COLUMN_RECIPE_INGREDIENT_RID +
                " = ?",
                new String[]{id});

        // loop through query results and construct ingredient model object from each row returned
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            /*
            Ingredient ingredient = new Ingredient(null,null,null,null);

            ingredient.setName(cursor.getString(1));
            //ingredient.setFoodGroup(cursor.getString(2));
            //ingredient.setQuantity(cursor.getString(3));
            //ingredient.setExpiration(cursor.getString(4));
            */

            String ingredientName = cursor.getString(0);

            ingredientList.add(new Ingredient(ingredientName));
        }

        return ingredientList;
    }

    //This will return a list of all recipes. I can modify it to return only certain types
    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPE, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            /*
            Recipe recipe = new Recipe(null,null,null,null,null,null);
            recipe.setId(cursor.getString(0));
            recipe.setName(cursor.getString(1));
            recipe.setTime(cursor.getString(2));
            recipe.setInstructions(cursor.getString(3));
            recipe.setStatus(cursor.getString(4));
            */

            String recipeID = cursor.getString(0);
            String recipeName = cursor.getString(1);
            String recipeTime = cursor.getString(2);
            String recipeInstructions = cursor.getString(3);
            String recipeStatus = cursor.getString(4);

            //query for ingredients corresponding to this recipe id and populate arraylist with model objects
            ArrayList<Ingredient> ingredients = getRecipeIngredients(recipeID);

            recipeList.add(new Recipe(recipeID, recipeName, recipeTime, recipeInstructions, recipeStatus, ingredients));
        }

        return recipeList;
    }

    //returns all favorited recipes
    public ArrayList<Recipe> getFavoriteRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPE +
                " WHERE " + COLUMN_RSTATUS +
                " = 'favorite'", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            /*
            Recipe recipe = new Recipe(null,null,null,null,null,null);
            recipe.setId(cursor.getString(0));
            recipe.setName(cursor.getString(1));
            recipe.setTime(cursor.getString(2));
            recipe.setInstructions(cursor.getString(3));
            recipe.setStatus(cursor.getString(4));

            recipeList.add(recipe);
            */

            String recipeID = cursor.getString(0);
            String recipeName = cursor.getString(1);
            String recipeTime = cursor.getString(2);
            String recipeInstructions = cursor.getString(3);
            String recipeStatus = cursor.getString(4);

            //query for ingredients corresponding to this recipe id and populate arraylist with model objects
            ArrayList<Ingredient> ingredients = getRecipeIngredients(recipeID);

            recipeList.add(new Recipe(recipeID, recipeName, recipeTime, recipeInstructions, recipeStatus, ingredients));

        }

        return recipeList;
    }

    //returns queued recipes for the swipe to choose feature
    public List<Recipe> getQueuedRecipes() {
        List<Recipe> recipeList = new ArrayList<Recipe>();

        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPE +
                " WHERE " + COLUMN_RSTATUS +
                " = 'queued'", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            Recipe recipe = new Recipe(null,null,null,null,null, null);
            recipe.setId(cursor.getString(0));
            recipe.setName(cursor.getString(1));
            recipe.setTime(cursor.getString(2));
            recipe.setInstructions(cursor.getString(3));
            recipe.setStatus(cursor.getString(4));

            recipeList.add(recipe);
        }

        return recipeList;
    }

    public ArrayList<Ingredient> getPantry(){
        ArrayList<Ingredient> pantry = new ArrayList<Ingredient>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_INAME +" FROM " + TABLE_PANTRY + " NATURAL JOIN " + TABLE_INGREDIENT, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Ingredient tempIngredient = new  Ingredient(cursor.getString(0));
            pantry.add(tempIngredient);
        }

        return pantry;


    }





}

