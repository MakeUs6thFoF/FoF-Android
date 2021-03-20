package com.example.FoF_Android;


import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    SharedPreferences preferences;
    SharedPreferences preferences1;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;

    Context context;

    int privatemode = 0 ;
    private static final String PREF_NAME="JWTTOKEN";
    private static final String KeyName= "keyname";
    private static final String keyIdx= "useridx";

    private static final Integer Idx= 0;

    public TokenManager(Context context)
    {
        this.context =context;
        preferences = context.getSharedPreferences(PREF_NAME,privatemode);
        preferences1 = context.getSharedPreferences(keyIdx,privatemode);
        editor = preferences.edit();
        editor1 = preferences1.edit();
    }

    public void createLoginSession(String keyname, Integer Idx )
    {
        editor.putString(PREF_NAME,keyname);
        editor1.putInt(keyIdx,Idx);
        editor.commit();
        editor1.commit();
    }

    public String checklogin(Context context) {
        String token = "";
        preferences = context.getSharedPreferences(PREF_NAME,privatemode);
        String value = preferences.getString(PREF_NAME, token);
        return value;
    }

    public Integer checkIdx(Context context) {
        Integer Idx=0;
        preferences1 = context.getSharedPreferences(keyIdx,privatemode);
        Integer value = preferences1.getInt(keyIdx,Idx);
        return value;
    }

    public void logout()
    {


    }



}
