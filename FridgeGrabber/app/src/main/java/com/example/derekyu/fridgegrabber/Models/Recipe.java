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

    public Recipe(String id1, String name1, String time1, String instructions1,
                  String status1, ArrayList<com.example.derekyu.fridgegrabber.Models.Ingredient> ingredients1){
        this.id = id1;
        this.name = name1;
        this.time = time1;
        this.instructions = instructions1;
        this.status = status1;
        this.ingredients = ingredients1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

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

    public ArrayList<com.example.derekyu.fridgegrabber.Models.Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<com.example.derekyu.fridgegrabber.Models.Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
