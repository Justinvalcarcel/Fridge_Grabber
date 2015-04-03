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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }





}
