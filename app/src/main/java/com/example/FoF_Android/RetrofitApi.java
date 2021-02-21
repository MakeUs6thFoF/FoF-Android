package com.example.FoF_Android;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApi {
    String URL = "https://test.fofapp.shop/";

    @FormUrlEncoded
    @POST("/posts/{userId}")
    Call<SignUp> postSignUp(@FieldMap HashMap<String, Object> param);

    @GET("/posts/{userId}")
    Call<SignUp> getData(@Path("userId") String userId);


}
