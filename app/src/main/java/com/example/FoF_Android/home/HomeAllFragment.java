package com.example.FoF_Android.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAllFragment extends Fragment {

    private RecyclerView recycle;
    MemeAdapter adapter;
    RetrofitApi api;
    List<Meme.Data> items;
    TokenManager gettoken;

    public HomeAllFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    //    showItemList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.meme_all, container, false);
        recycle = view.findViewById((R.id.recycler));
        gettoken=new TokenManager(getContext());
        initUI(view);

        return view;
    }


    private void initUI(ViewGroup view) {
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
        recycle.setAdapter(adapter);

    }

    public void setadapter(List<Meme.Data> items) {
        adapter = new MemeAdapter(getActivity(), items);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);
    }

}