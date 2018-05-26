package com.umberto.backingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("ingredients")
    ArrayList<Ingredient> ingredients=new ArrayList<Ingredient>();
    @SerializedName("steps")
    ArrayList<Step> steps=new ArrayList<Step>();
    @SerializedName("servings")
    int servings;
    @SerializedName("image")
    String image;

    public Recipe(){
        id=-1;
        name="";
        ingredients=new ArrayList<Ingredient>();
        steps=new ArrayList<Step>();
        servings=0;
        image="";
    }

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients,ArrayList<Step> steps, int servings, String image){
        this.id=id;
        this.name=name;
        this.ingredients=ingredients;
        this.steps=steps;
        this.servings=servings;
        this.image=image;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        in.readTypedList(steps, Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
        name = in.readString();
    }

    public int getId(){
        return  id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }
    public void setIngredients(ArrayList<Ingredient> ingredients){
        this.ingredients=ingredients;
    }
    public ArrayList<Step> getSteps(){
        return  steps;
    }
    public void setSteps(ArrayList<Step> steps){
        this.steps=steps;
    }
    public int getServings(){
        return servings;
    }
    public void setServings(int servings){
        this.servings=servings;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image=image;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
        dest.writeString(name);
    }
}
