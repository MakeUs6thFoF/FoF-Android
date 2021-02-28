package com.example.FoF_Android.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAllFragment extends Fragment {

    private RecyclerView recycle;
    MemeAdapter adapter;
    Meme meme;
    RetrofitApi api;

    public HomeAllFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.meme_all, container, false);

        initUI(view);

        return view;
    }


    private void initUI(ViewGroup view){

        recycle=view.findViewById((R.id.recycler));

        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(layoutManager);


        HttpClient client=new HttpClient();
        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        Call<MemeResponse> call = api.getdata(1,10);//수정예정
        call.enqueue(new Callback<MemeResponse>() {

            @Override
            public void onResponse(Call<MemeResponse> call, Response<MemeResponse> response) {
                if(response.isSuccessful()){
                List<Meme.Data> items = response.body().getItems();
                recycle.setAdapter(new MemeAdapter(getActivity(),items));
                recycle.smoothScrollToPosition(0);
              //  swipeContainer.setRefreshing(false);
             //   pd.hide();
            }
            else
                    Log.i("TAG", "onResponse: "+response.code());
            }

            @Override
            public void onFailure(Call<MemeResponse> call, Throwable t) {

                Log.d("MainActivity", t.toString());
            }
        });

        recycle.setAdapter(adapter);
    }
}