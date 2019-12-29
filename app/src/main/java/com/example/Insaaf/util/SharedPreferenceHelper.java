package com.example.Insaaf.util;

import android.content.Context;
import android.content.SharedPreferences;

public enum SharedPreferenceHelper {
    INSTANCE;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    String NAME = "name";
    String CONTACT_NUMBER = "contactNumber";
    String ID = "ID";
    String IMAGE_URL = "image_url";

    public void initialize(Context context){
        sharedPreferences = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }



    public void saveName(String name){
        editor.putString(NAME, name);
        editor.apply();
    }

    public String getName(){
        return sharedPreferences.getString(NAME, "");
    }


    public void saveContactNumber(String contactNumber){
        editor.putString(CONTACT_NUMBER, contactNumber);
        editor.apply();
    }

    public String getContactNumber(){
        return sharedPreferences.getString(CONTACT_NUMBER, "");
    }


    public String getId(){
        return sharedPreferences.getString(ID, "");
    }

    public void saveId(String id){
        editor.putString(ID, id);
        editor.apply();
    }


    public void saveImageUrl(String url){
        editor.putString(IMAGE_URL, url);
        editor.apply();
    }

    public String getUrl() {
        return sharedPreferences.getString(IMAGE_URL, "");
    }




}
