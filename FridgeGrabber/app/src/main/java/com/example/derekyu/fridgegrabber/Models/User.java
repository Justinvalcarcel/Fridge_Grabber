package com.example.derekyu.fridgegrabber.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by derekyu on 4/3/15.
 */
public class User implements Parcelable {
    private String name;
    private ArrayList<Recipe> favorites;
    private ArrayList<Ingredient> fridge;


    public User(String name){
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel in){ return new User(in); }

        public User[] newArray(int size){ return new User[size]; }

    };

    public User(Parcel p){
        name = p.readString();
        favorites = p.readArrayList(Recipe.class.getClassLoader());
        fridge = p.readArrayList(Ingredient.class.getClassLoader());
    }





}
