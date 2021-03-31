package com.FoF.FoF_Android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.FoF.FoF_Android.HttpClient;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;
import com.FoF.FoF_Android.detail.DetailFragment;
import com.FoF.FoF_Android.search.EndlessScrollListener;
import com.FoF.FoF_Android.search.HashTagAdapter;
import com.FoF.FoF_Android.search.MemeSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HashClickFragment extends Fragment {
    private int page = 0;
    ImageView hashImage;
    TextView hashText;
    private static final int MAX_SIZE = 20;
    TextView hashCnt;
    TokenManager gettoken;
    RetrofitApi api;    String token;
    HashSearchAdapter mAdapter;
    RecyclerView mRecyclerView;
    int memeCount;    Integer j=0;

    List<MemeSearch.Data> memeList = new ArrayList<>();

    private static final String ARG_PARAM2 = "param2";


    private String mParam2; //tagName

    public HashClickFragment() {

        // Required empty public constructor
    }
    public HashClickFragment(String position) {
        this.mParam2=position;
        // Required empty public constructor
    }

    public static HashClickFragment newInstance( String param2) {
        HashClickFragment fragment = new HashClickFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam2 = getArguments().getString(ARG_PARAM2);
            gettoken=new TokenManager(getContext());
            token = gettoken.checklogin(getContext());
            //   System.out.println("확인" + token);

            HttpClient client = new HttpClient();
            api = client.getRetrofit().create(RetrofitApi.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hash_click, container, false);
        hashText = view.findViewById(R.id.hashNametv);
        hashCnt = view.findViewById(R.id.hashCnttv);
        hashText.setText(mParam2);

        mRecyclerView = view.findViewById(R.id.hashClickRecycle);
        setRecyclerView(api, view);

        return view;
    }
    public int getPage(int flag){
        j ++;
        return j;
    }
    public void setRecyclerView(RetrofitApi api, View view){
       token = gettoken.checklogin(getContext());

        api.getSearchMeme(token, mParam2,  getPage(0), 50).enqueue(new Callback<MemeSearch>() {
            @Override
            public void onResponse(Call<MemeSearch> call, Response<MemeSearch> response) {
                MemeSearch body = response.body();
                memeList = body.getData();
                memeCount = body.getData().size();
                hashCnt.setText(String.valueOf(memeCount)+" 게시물");
                if(memeCount==50)  hashCnt.setText(String.valueOf(memeCount)+"+ 게시물");
                setAdapter(memeList);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setItemViewCacheSize(20);
                plushashtag();
            }

            @Override
            public void onFailure(Call<MemeSearch> call, Throwable t) {

            }
        });

}
public  void plushashtag(){
    setAdapter(memeList);
    EndlessScrollListener scrollListener = new EndlessScrollListener(new EndlessScrollListener.RefreshList() {
        @Override
        public void onRefresh(int pageNumber) {
            Call<MemeSearch> call = api.getSearchMeme(token, mParam2, getPage(0), MAX_SIZE); //page설정
            call.enqueue(new Callback<MemeSearch>() {
                @Override
                public void onResponse(Call<MemeSearch> call, Response<MemeSearch> response) {
                    if (response.isSuccessful()) {
                        List<MemeSearch.Data> plusrecycler = response.body().getData();
                        memeList.addAll(plusrecycler);
                        mAdapter.notifyItemInserted(memeList.size() - 1);
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<MemeSearch> call, Throwable t) {
                }
            });
        }
    });
    mRecyclerView.addOnScrollListener(scrollListener);
}



public void setAdapter(List<MemeSearch.Data> pmemeList){

        mAdapter = new HashSearchAdapter(pmemeList, getContext());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new HashTagAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MemeSearch.Data item = mAdapter.getItem(position);
                DetailFragment detail = new DetailFragment(item.getMemeIdx());
                getFragmentManager().beginTransaction().addSharedElement(v.findViewById(R.id.imageView), ViewCompat.getTransitionName(v.findViewById(R.id.imageView)))
                        .setReorderingAllowed(true)
                        .addToBackStack(null).replace(R.id.container, detail).commit();
            }
        });
    }
}