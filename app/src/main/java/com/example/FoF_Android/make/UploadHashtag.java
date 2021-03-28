package com.example.FoF_Android.make;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.DetailFragment;
import com.example.FoF_Android.search.HashSearchAdapter;
import com.example.FoF_Android.search.HashTagAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadHashtag extends AppCompatActivity {
    String TAG="MainActivity";
    SearchView hashtag;
    List<com.example.FoF_Android.make.HashSearch.Data> hashlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_hashtag);
        hashtag=findViewById(R.id.hashtag);
        hashtag.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RetrofitApi api = HttpClient.getRetrofit().create(RetrofitApi.class);
                api.getHashtag(new TokenManager(UploadHashtag.this).checklogin(UploadHashtag.this), newText).enqueue(new Callback<com.example.FoF_Android.make.HashSearch>() {
                    @Override
                    public void onResponse(Call<com.example.FoF_Android.make.HashSearch> call, Response<com.example.FoF_Android.make.HashSearch> response) {
                        HashSearch body = response.body();

/*

                        RecyclerView mRecyclerView = view.findViewById(R.id.hashClickRecycle);
                        mAdapter = new HashSearchAdapter(memeList, getContext());
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setAdapter(mAdapter);

                        mAdapter.setOnItemClickListener(new HashTagAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                HashSearch.Data.memeList item = mAdapter.getItem(position);
                                DetailFragment detail = new DetailFragment(item.getMemeIdx());
                                getFragmentManager().beginTransaction().addSharedElement(v.findViewById(R.id.imageView), ViewCompat.getTransitionName(v.findViewById(R.id.imageView)))
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null).replace(R.id.container, detail).commit();
                            }
                        });*/}


                    @Override
                    public void onFailure(Call<com.example.FoF_Android.make.HashSearch> call, Throwable t) {

                    }
                });
                return false;
            }
        });
    }
}