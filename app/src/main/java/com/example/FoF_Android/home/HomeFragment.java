package com.example.FoF_Android.home;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.DetailFragment;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.home.model.MemeResponse;
import com.example.FoF_Android.home.view.CustomSwipeableViewPager;
import com.example.FoF_Android.home.view.DragLayout;
import com.example.FoF_Android.home.view.StackPageTransformer;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnItemClick {
    TabLayout tabLayout;
    private RecyclerView recycle;
    RetrofitApi api;
    List<Meme.Data> items;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    MemeAllAdapter adapter;
    FrameLayout container;
    FrameLayout pagercontainer;
    TokenManager gettoken;
    CustomSwipeableViewPager myviewpager;
    MemePagerAdapter padapter;
    GestureDetector gestureDetector;
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
/*
        recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if(lastPosition == totalCount){
                    //아이템 추가
                }
            }
        });*/
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
        Call<MemeResponse> call = api.getdata(token, "all",1, 10); //page설정
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
                    padapter.setOnItemClickListener(new MemePagerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, String position) {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,HashClickFragment.newInstance(position)).addToBackStack(null).commit();
                        }
                    });

                    gestureDetector = new GestureDetector(getContext(), new MyGestureDetector());
                    padapter.setOnTouchListener(new MemePagerAdapter.OnTouchListener(){
                        @Override
                        public void onTouch(View v, Integer position, MotionEvent event) {
                           gestureDetector.onTouchEvent(event);

                        }
                    });

                    myviewpager.setOffscreenPageLimit(5);
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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, HashClickFragment.newInstance(value)).addToBackStack(null).commit();

    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(getContext(), "Left Swipe", Toast.LENGTH_SHORT).show();


                } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(getContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}