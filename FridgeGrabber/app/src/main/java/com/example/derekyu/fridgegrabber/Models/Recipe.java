package com.example.derekyu.fridgegrabber.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Created by derekyu on 4/3/15.
 */
public class Recipe implements Parcelable{

    private String id;
    private String name;
    private String time;
    private String instructions;
    private String status;
    private ArrayList<Ingredient> ingredients;

    // Parcelable creator - Do not modify this function
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel p) {
            return new Recipe(p);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    public Recipe(String id1, String name1, String time1, String instructions1,
                  String status1, ArrayList<Ingredient> ingredients1){
        this.id = id1;
        this.name = name1;
        this.time = time1;
        this.instructions = instructions1;
        this.status = status1;
        this.ingredients = ingredients1;
    }

    // Constructor from parcel
    public Recipe (Parcel p)
    {
        this.id = p.readString();
        this.name = p.readString();
        this.time = p.readString();
        this.instructions = p.readString();
        this.status = p.readString();
        this.ingredients = new ArrayList<Ingredient>();
        p.readTypedList(this.ingredients, Ingredient.CREATOR);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.time);
        dest.writeString(this.instructions);
        dest.writeString(this.status);
        dest.writeTypedList(this.ingredients);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
