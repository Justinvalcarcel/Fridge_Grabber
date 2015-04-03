package com.example.derekyu.fridgegrabber.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Created by derekyu on 4/3/15.
 */
public class Recipe implements Parcelable{
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> instructions;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
