package com.example.FoF_Android.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.FoF_Android.detail.DetailFragment;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.home.model.MemeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRecFragment extends Fragment {
    MemePagerAdapter adapter;
    TokenManager gettoken;
    RetrofitApi api;
    ViewPager myviewpager;
    DetailFragment recmeme;
    ImageButton report,copy,send;
    ToggleButton like;


    public HomeRecFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.meme_rec, container, false);
        gettoken=new TokenManager(getContext());
        myviewpager=view.findViewById(R.id.myviewpager);

       // myviewpager.setPageTransformer(true, new CascadingPageTransformer());
        initUI(view);
        myviewpager.setPageTransformer(true, new StackPageTransformer(myviewpager));

      /*  myviewpager.setPageTransformer(RecPageTransform.getBuild()//建造者模式
                .addAnimationType(PageTransformerConfig.ROTATION)//默认动画 default animation rotation  旋转  当然 也可以一次性添加两个  后续会增加更多动画
                .setRotation(-45)//旋转角度
                .addAnimationType(PageTransformerConfig.ALPHA)//默认动画 透明度 暂时还有问题
                .setViewType(PageTransformerConfig.LEFT)//view的类型
                .setOnPageTransformerListener(new OnPageTransformerListener() {
                    @Override
                    public void onPageTransformerListener(View page, float position) {

                    }
                })
                .setTranslationOffset(40)
                .setScaleOffset(80)
                .create());*/

        return view;
    }

    private void initUI(ViewGroup view) {
        HttpClient client=new HttpClient();
        api = client.getRetrofit().create(RetrofitApi.class);
        String token = gettoken.checklogin(getContext());
        System.out.println("확인"+token);
        Call<MemeResponse> call = api.getdata(token,"recommend",1,10);
        call.enqueue(new Callback<MemeResponse>() {
            @Override
            public void onResponse(Call<MemeResponse> call, Response<MemeResponse> response) {
                if(response.isSuccessful()){
                    List<Meme.Data> items = response.body().getItems();

                    Log.i("TAG", "onResponse: "+items.size());

                 /*   adapter=new MemeAdapter(getContext(),items, MemeCase.SMALL,new MemeAdapter.OnItemClickListener() {
                        @Override public void onItemClick(Meme.Data item, ImageView memeimg) {
                            recmeme=new DetailFragment(item.getMemeIdx());

                            //  recmeme.setArguments(options.toBundle());
                            getFragmentManager().beginTransaction().replace(R.id.container, recmeme).addToBackStack(null).commit();
                        }
                    });*/
                    adapter=new MemePagerAdapter(getContext(),items);
                    myviewpager.setOffscreenPageLimit(3);
                    myviewpager.setAdapter(adapter);

                   // setupCurrentIndicator(0);
                }
                else
                    Log.i("TAG", "onResponse: "+response.code());
            }

            @Override
            public void onFailure(Call<MemeResponse> call, Throwable t) {

                Log.d("MainActivity", t.toString());
            }
        });


    }
    public void onclickbutton(ViewGroup view){


        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {

                    /*api.postLike(token,items.get(i).getMemeIdx()).enqueue(new Callback<Like>() {
                        @Override
                        public void onResponse(Call<Like> call, Response<Like> response) {
                            if(response.isSuccessful()) {
                                Like like = response.body();

                                System.out.println("포스트확인2" + like.getCode());
                                System.out.println("포스트확인2" + like.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<Like> call, Throwable t) {

                        }
                    });*/
                }
                else
                {
                }
            }
        });
    }

}