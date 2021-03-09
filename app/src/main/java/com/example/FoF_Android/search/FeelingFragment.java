package com.example.FoF_Android.search;

import android.graphics.Color;
import android.media.session.MediaSession;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.Category.Category;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.UltraViewPagerAdapter;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeelingFragment extends Fragment {

    RetrofitApi api;
    TokenManager gettoken;
    int CategoryIdx;
    ViewPager imageViewPager;


    int memeIdx[] = new int[5];
    List<String> imageUrl = new ArrayList<>();
    int view[] = new int[5];


    public FeelingFragment(int idx) {
        CategoryIdx = idx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken = new TokenManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feeling, container, false);
        getRank(api, view);

        return view;
    }

    public void getRank(RetrofitApi api, View innerview){
        String token = gettoken.checklogin(getContext());
        imageUrl.clear();
        api.getRank(token, CategoryIdx).enqueue(new Callback<CategoryMeme>() {
            @Override
            public void onResponse(Call<CategoryMeme> call, Response<CategoryMeme> response) {
                CategoryMeme ctm = response.body();
                for(int i=0; i< ctm.getData().getMemeList().size(); i++){
                    memeIdx[i] = ctm.getData().getMemeList().get(i).getMemeIdx();
                    imageUrl.add(ctm.getData().getMemeList().get(i).getImageUrl());
                    view[i] = ctm.getData().getMemeList().get(i).getView();
                    UltraViewPager ultraViewPager = (UltraViewPager)innerview.findViewById(R.id.ultra_viewpager);
                    ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
                    PagerAdapter adapter = new RankPagerAdapter(true, imageUrl);
                    ultraViewPager.setBackgroundResource(R.drawable.backgroundgra);
                    ultraViewPager.setAdapter(adapter);
                    ultraViewPager.setMultiScreen(0.6f);
                    ultraViewPager.setItemRatio(1.0f);
                    ultraViewPager.setRatio(2.0f);
                    ultraViewPager.setMaxHeight(800);
                    ultraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());
                    ultraViewPager.setAutoMeasureHeight(true);
                    ultraViewPager.initIndicator();
                    ultraViewPager.getIndicator()
                            .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                            .setFocusColor(Color.RED)
                            .setNormalColor(Color.WHITE)
                            .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                    ultraViewPager.getIndicator().build();
                    ultraViewPager.setInfiniteLoop(false);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<CategoryMeme> call, Throwable t) {

            }
        });

    }
}