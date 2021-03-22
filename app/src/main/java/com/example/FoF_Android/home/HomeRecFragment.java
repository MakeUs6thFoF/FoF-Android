package com.example.FoF_Android.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.FoF_Android.detail.DetailFragment;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.home.model.MemeResponse;
import com.example.FoF_Android.home.view.StackPageTransformer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRecFragment extends Fragment  {
    MemePagerAdapter padapter;
   //MemeAllAdapter adapter;
    TokenManager gettoken;
    RetrofitApi api;
    ViewPager myviewpager;
    DetailFragment recmeme;
    private MemeAllAdapter.OnItemClickListener mListener = null;
   // CardStackView myviewpager;
   // CardStackLayoutManager layoutManager;
    Integer idx;

    public HomeRecFragment() {
    }



    public static HomeRecFragment newInstance() {
        HomeRecFragment fragment = new HomeRecFragment();

        return fragment;
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
       // layoutManager = new CardStackLayoutManager(requireContext(), this);
     //   layoutManager.setScaleInterval(0.95f);

        initUI(view);
        myviewpager.setPageTransformer(true, new StackPageTransformer(myviewpager));

      /*  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                        .setDirection(Direction.Left)
                        .setDuration(Duration.Slow.duration)
                        .setInterpolator(new OvershootInterpolator())
                        .build();
                layoutManager.setOverlayInterpolator(new OvershootInterpolator());

                layoutManager.setRewindAnimationSetting(setting);
                myviewpager.rewind();
            }
        });
        layoutManager.setCanScrollVertical(false);
        layoutManager.setDirections(List.of(Direction.Left));

        myviewpager.setLayoutManager(layoutManager);*/
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

               /*     adapter=new MemeAllAdapter(getContext(),items, MemeCase.SMALL,new MemeAllAdapter.OnItemClickListener() {
                        @Override public void onItemClick(Meme.Data item, ImageView memeimg) {
                            recmeme=new DetailFragment(item.getMemeIdx());

                            //  recmeme.setArguments(options.toBundle());
                         getFragmentManager().beginTransaction().add(R.id.container, recmeme).addToBackStack(null).commit();
                        }
                    });*/
                 //   padapter=new MemePagerAdapter(getContext(),items);
                    myviewpager.setOffscreenPageLimit(3);
                    myviewpager.setAdapter(padapter);

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


}