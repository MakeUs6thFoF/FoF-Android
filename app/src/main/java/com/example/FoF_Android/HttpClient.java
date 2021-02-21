package com.example.FoF_Android;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if(retrofit == null)
        {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(RetrofitApi.URL);
            builder.addConverterFactory(GsonConverterFactory.create());

            retrofit = builder.build();
        }

        return retrofit;
    }
}
