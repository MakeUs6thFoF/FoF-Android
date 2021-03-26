package com.example.FoF_Android;

import com.example.FoF_Android.Category.Category;

import com.example.FoF_Android.dialog.model.Copyright;
import com.example.FoF_Android.detail.model.Detail;
import com.example.FoF_Android.detail.model.Like;
import com.example.FoF_Android.dialog.model.Report;
import com.example.FoF_Android.home.model.MemeResponse;
import com.example.FoF_Android.make.MemeUpload;
import com.example.FoF_Android.login.Login;
import com.example.FoF_Android.search.CategoryMeme;
import com.example.FoF_Android.search.HashSearch;
import com.example.FoF_Android.search.HashTag;
import com.example.FoF_Android.search.MemeSearch;
import com.example.FoF_Android.search.RandomTag;
import com.example.FoF_Android.signup.SignUp;


import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {
    String URL = "https://test.fofapp.shop";

    @FormUrlEncoded
    @POST("/signup")
    Call<SignUp> postSignUp(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/login")
    Call<Login> postLogin(@FieldMap HashMap<String, Object> param);

    @POST("/post/{userId}")
    Call<SignUp> getLogin(@Path("userId") String userId);

    @GET("/meme?") //수정 예정
    Call<MemeResponse> getdata(@Header("x-access-token") String token,
                               @Query("filter") String filter,
                               @Query("page") Integer page, @Query("size") Integer size);

    @GET("/search/meme?")
    Call<MemeSearch> getSearchMeme(@Header("x-access-token") String token,
                                   @Query("word") String word, @Query("page") Integer page, @Query("size") Integer size);

    @GET("/category")
    Call<Category> getCategory();

    @GET("/report-tag")
    Call<Report> getReportTag();


    @POST("/meme/{memeIdx}/report/{reportTagIdx}")
    Call<SignUp> postReport(@Header("x-access-token") String token, @Path("memeIdx") Integer memeIdx, @Path("reportTagIdx") Integer reportTagIdx);


    @FormUrlEncoded
    @POST("/meme")
    Call<SignUp> postMeme(@Header("x-access-token") String token, @Field("title") String title,
                          @Field("imageUrl") String ImageUrl,@Field("copyright") String copyright, @Field("tag")List<String> tag, @Field("categoryIdx")Integer categoryIdx);

    @FormUrlEncoded
    @POST("/user/meme")
    Call<SignUp> postCategory(@Header("x-access-token") String token, @Field("categoryIdx") List<Integer> list);



    @POST("/meme/{memeidx}/good")
    Call<Like> postLike(@Header("x-access-token") String token, @Path("memeidx") Integer memeidx);

    @FormUrlEncoded
    @PATCH("/meme/{memeidx}/copyright")
    Call<Copyright> modifycopy(@Header("x-access-token") String token, @Path("memeidx") Integer memeidx, @Field("copyright") String copyright);


    @GET("/meme/{memeidx}")
    Call<Detail> getsimilar(@Header("x-access-token") String token,
                            @Path("memeidx") Integer memeidx);
    @DELETE("/meme/{memeidx}")
    Call<SignUp> deleteMeme(@Header("x-access-token") String token,
                            @Path("memeidx") Integer memeidx);

    @GET("/meme/trend/category/{categoryIdx}")
    Call<CategoryMeme> getRank(@Header("x-access-token") String token, @Path("categoryIdx") Integer categoryIdx);

    @GET("/tag/trend")
    Call<HashTag> getTag(@Header("x-access-token") String token);

    @GET("/meme/tag/{tagIdx}")
    Call<HashSearch> getHashSearch(@Header("x-access-token") String token, @Path("tagIdx") Integer tagIdx);


    @GET("/tag")
    Call<RandomTag> getRandomTag();

    @GET("/search/meme/word/{tagName}")
    Call<HashSearch> getHashSearch1(@Header("x-access-token") String token, @Path("tagName") String tagName);



}
