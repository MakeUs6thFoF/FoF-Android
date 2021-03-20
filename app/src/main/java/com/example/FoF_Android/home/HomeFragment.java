package com.example.FoF_Android.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.DetailFragment;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.home.model.MemeResponse;
import com.example.FoF_Android.search.HashTag;
import com.example.FoF_Android.search.SearchFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnItemClick{
    TabLayout tabLayout;
    private RecyclerView recycle;
    RetrofitApi api;
    List<Meme.Data> items;

    MemeAllAdapter adapter;
    FrameLayout container;
    FrameLayout pagercontainer;
    TokenManager gettoken;
    CustomSwipeableViewPager myviewpager;
    MemePagerAdapter padapter;

    private int mNumber = 0;


    public HomeFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        recycle = view.findViewById((R.id.recycler));
        gettoken=new TokenManager(getContext());

        myviewpager=view.findViewById(R.id.myviewpager);
        initPagerUI();
        initUI();

        myviewpager.setPageTransformer(true, new StackPageTransformer(myviewpager));

        tabLayout =view.findViewById(R.id.tabLayout) ;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                setCurrentTabFragment(position,view);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        return view;
    }

    public void setCurrentTabFragment(int position, ViewGroup view){
        pagercontainer=(FrameLayout)view.findViewById(R.id.pagercontainer);
        container=(FrameLayout)view.findViewById(R.id.container);
      //  container.setVisibility(View.GONE);
        switch (position)
        {
            case 0 :
                container.setVisibility(View.GONE);
                pagercontainer.setVisibility(View.VISIBLE);
                break;
            case 1 :
                container.setVisibility(View.VISIBLE);
                pagercontainer.setVisibility(View.GONE);
                break;
        }
    }


    public void setadapter(List<Meme.Data> items) {
        adapter = new MemeAllAdapter(items,getContext());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new MemeAllAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Meme.Data item = adapter.getItem(position);
                DetailFragment detail = new DetailFragment(item.getMemeIdx());
                getFragmentManager().beginTransaction().addSharedElement(v.findViewById(R.id.imageView), ViewCompat.getTransitionName(v.findViewById(R.id.imageView)))
                        .setReorderingAllowed(true)
                        .addToBackStack(null).add(R.id.container, detail).commit();
            }
        });


    }
    private void initUI() {
        String token = gettoken.checklogin(getContext());
        System.out.println("확인" + token);

        HttpClient client = new HttpClient();
        api = client.getRetrofit().create(RetrofitApi.class);
        Call<MemeResponse> call = api.getdata(token, "all",1, 10);
        call.enqueue(new Callback<MemeResponse>() {
            @Override
            public void onResponse(Call<MemeResponse> call, Response<MemeResponse> response) {
                if (response.isSuccessful()) {
                    items = response.body().getItems();
                    setadapter(items);
                    Log.i("TAG", "onResponse: " + response.code());

                } else
                    Log.i("TAG", "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<MemeResponse> call, Throwable t) {
                Log.d("MainActivity", t.toString());

            }
        });

    }
    private void initPagerUI() {
        HttpClient client=new HttpClient();
        api = client.getRetrofit().create(RetrofitApi.class);
        String token = gettoken.checklogin(getContext());
        Integer idx= gettoken.checkIdx(getContext());

        System.out.println("확인"+token);
        Call<MemeResponse> call = api.getdata(token,"recommend",1,10);
        call.enqueue(new Callback<MemeResponse>() {
            @Override
            public void onResponse(Call<MemeResponse> call, Response<MemeResponse> response) {
                if(response.isSuccessful()){
                    List<Meme.Data> items = response.body().getItems();

                    Log.i("TAG", "onResponse: "+items.size());

                    padapter=new MemePagerAdapter(getContext(),idx,items,HomeFragment.this::onClick);
                  //  myviewpager.setOnDragListener();

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

    @Override
    public void onClick(String value) {
       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, HashClickFragment.newInstance(value)).addToBackStack(null).commit();

    }
}