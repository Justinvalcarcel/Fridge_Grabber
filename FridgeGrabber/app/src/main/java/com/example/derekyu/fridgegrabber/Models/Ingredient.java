package com.example.derekyu.fridgegrabber.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by derekyu on 4/3/15.
 */
public class Ingredient implements Parcelable {

    private String name;
    private String foodGroup;
    private String quantity;
    private String expiration;

    public Ingredient(String name1, String foodGroup1, String quantity1, String expiration1){
        this.name = name1;
        this.foodGroup = foodGroup1;
        this.quantity = quantity1;
        this.expiration = expiration1;

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
