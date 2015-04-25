package com.example.derekyu.fridgegrabber.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by derekyu on 4/3/15.
 */
public class Ingredient implements Parcelable {

    private String name;

    /* COMMENTED OUT FOR NOW
    private String foodGroup;
    private String quantity;
    private String expiration;
    */

    // Parcelable creator - Do not modify this function
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel p) {
            return new Ingredient(p);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };


    /* COMMENTED OUT FOR NOW
    public Ingredient(String name1, String foodGroup1, String quantity1, String expiration1){
        this.name = name1;
        this.foodGroup = foodGroup1;
        this.quantity = quantity1;
        this.expiration = expiration1;

    }
    */

    // Constructor from passed in arguments
    public Ingredient(String name)
    {
        this.name = name;
    }

    // Constructor from parcel
    public Ingredient(Parcel p)
    {
        this.name = p.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    /*
    public void setFoodGroup(String foodGroup)
    {
        this.foodGroup = foodGroup;
    }

    public String getFoodGroup()
    {
        return foodGroup;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getExpiration()
    {
        return expiration;
    }

    public void setExpiration(String expiration)
    {
        this.expiration = expiration;
    }
    */

}
