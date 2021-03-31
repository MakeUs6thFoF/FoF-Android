package com.FoF.FoF_Android.search;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.FoF.FoF_Android.HttpClient;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeelingFragment extends Fragment {

    RetrofitApi api;
    TokenManager gettoken;
    int CategoryIdx;
    ViewPager imageViewPager;
    View view;
    UltraViewPager ultraViewPager;

    int memeIdx[] = new int[5];
    List<String> imageUrl = new ArrayList<>();
    List<CategoryMeme.Data.MemeList> mList = new ArrayList<>();

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
        ultraViewPager = (UltraViewPager)view.findViewById(R.id.ultra_viewpager);
        getRank(api, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getRank(RetrofitApi api, View innerview){
        String token = gettoken.checklogin(getContext());
        imageUrl.clear();
        mList.clear();
        FragmentManager manager = getFragmentManager();
        api.getRank(token, CategoryIdx).enqueue(new Callback<CategoryMeme>() {
            @Override
            public void onResponse(Call<CategoryMeme> call, Response<CategoryMeme> response) {
                CategoryMeme ctm = response.body();
                for(int i=0; i< ctm.getData().getMemeList().size(); i++){
                    if(i >= 5)
                        break;
                    memeIdx[i] = ctm.getData().getMemeList().get(i).getMemeIdx();
                    mList.add(ctm.getData().getMemeList().get(i));

                    ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
                    PagerAdapter adapter = new RankPagerAdapter(true, mList, manager);
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
                            .setNormalColor(Color.BLACK)
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