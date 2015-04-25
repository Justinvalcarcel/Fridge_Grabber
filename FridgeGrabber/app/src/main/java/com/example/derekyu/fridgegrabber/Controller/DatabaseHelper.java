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
                        COLUMN_RID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

    public void insertRecipe(Recipe recipe) {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RNAME, recipe.getName());
        cv.put(COLUMN_RTIME, recipe.getTime());
        cv.put(COLUMN_RINSTRUCTIONS, recipe.getInstructions());
        cv.put(COLUMN_RSTATUS, recipe.getStatus());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RECIPE, null, cv);

        //inserts the ingredients into the RECIPE_INGREDIENT table
        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            ContentValues cv1 = new ContentValues();
            cv1.put(COLUMN_RID, recipe.getId());
            cv1.put(COLUMN_IID, ingredient.getName());
            db.insert(TABLE_RECIPE_INGREDIENT, null, cv1);
        }
    }

    //adds new ingredients
    public void insertIngredient(Ingredient ingredient){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_INAME, ingredient.getName());
        //cv.put(COLUMN_ITYPE, ingredient.getFoodGroup());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_INGREDIENT, null, cv);

    }

    //Make a Pantry object to differentiate from Ingredient
    public void insertPantry(Ingredient ingredient) {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_INAME, ingredient.getName());
        //cv.put(COLUMN_PQUANTITY, ingredient.getQuantity());
        //cv.put(COLUMN_PEXPIRATION, ingredient.getExpiration());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PANTRY, null, cv);
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



}

