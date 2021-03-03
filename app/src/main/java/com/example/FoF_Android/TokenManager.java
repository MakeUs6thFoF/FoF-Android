package com.example.FoF_Android;


import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;
    int privatemode = 0 ;
    private static final String PREF_NAME="JWTTOKEN";
    private static final String KeyName= "keyname";
    private static final String Name= "name";

    public TokenManager(Context context)
    {
        this.context =context;
        preferences = context.getSharedPreferences(PREF_NAME,privatemode);
        editor = preferences.edit();

    }

    public void createLoginSession(String keyname )
    {
        editor.putString(PREF_NAME,keyname);
        editor.commit();

    }

    public String checklogin(Context context) {
        String token = "";
        preferences = context.getSharedPreferences(PREF_NAME,privatemode);
        String value = preferences.getString(PREF_NAME, token);
        return value;
    }

    public void logout()
    {


    }



}
