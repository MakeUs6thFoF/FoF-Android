package com.example.FoF_Android;

import com.example.FoF_Android.login.Login;
import com.example.FoF_Android.signup.SignUp;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApi {
    String URL = "https://test.fofapp.shop/";

    @FormUrlEncoded
    @POST("/signup")
    Call<SignUp> postSignUp(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/login")
    Call<Login> postLogin(@FieldMap HashMap<String, Object> param);

    @POST("/post/{userId}")
    Call<SignUp> getLogin(@Path("userId") String userId);

}
