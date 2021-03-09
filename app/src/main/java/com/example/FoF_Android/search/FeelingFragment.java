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
        getRank(api);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feeling, container, false);

        UltraViewPager ultraViewPager = (UltraViewPager)view.findViewById(R.id.ultra_viewpager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        List<String>tmpList = new ArrayList<>();
        tmpList.add("https://image.onstove.com/850x0/d3kxs6kpbh59hp.cloudfront.net/community/COMMUNITY/4825283c0edc42229c3ab3706ccfd913/dfdffe9994d44302a9c1727db9aaaef4_1572945740.png");
        tmpList.add("https://i.pinimg.com/originals/fd/3c/cd/fd3ccd7b49e366b4206f5ac7f8fa8dac.gif");
        PagerAdapter adapter = new RankPagerAdapter(true, tmpList);
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

        ultraViewPager.setInfiniteLoop(true);
        ultraViewPager.setAutoScroll(2000);

        return view;
    }

    public void getRank(RetrofitApi api){
        String token = gettoken.checklogin(getContext());
        api.getRank(token, CategoryIdx).enqueue(new Callback<CategoryMeme>() {
            @Override
            public void onResponse(Call<CategoryMeme> call, Response<CategoryMeme> response) {
                CategoryMeme ctm = response.body();
                for(int i=0; i< ctm.getData().getMemeList().size(); i++){
                    memeIdx[i] = ctm.getData().getMemeList().get(i).getMemeIdx();
                    imageUrl.add(ctm.getData().getMemeList().get(i).getImageUrl());
                    view[i] = ctm.getData().getMemeList().get(i).getView();
                    System.out.println(i+"번째 이미지 "+imageUrl.get(i));

                    //Glide.with(getActivity()).load(imageUrl.get(0)).into(imageView3);
                }

            }

            @Override
            public void onFailure(Call<CategoryMeme> call, Throwable t) {

            }
        });

    }
}