package com.example.FoF_Android.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.home.model.Similar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.FoF_Android.home.MemeCase.SMALL;

public class MemeDetailActivity extends Fragment {

    LinearLayout wrap;
    LinearLayout wrap2;
    RecyclerView similar;
    Integer i=0;
    List<Similar.Data> items;

    public MemeDetailActivity(int i) {
        this.i=i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.meme_item, container, false);
        initUI(view);
        similarUI(view, i);
        return view;
    }


    public void initUI(ViewGroup view){
        wrap=view.findViewById(R.id.wrap);
        wrap2=view.findViewById(R.id.wrap_1);
        similar=view.findViewById(R.id.similar);
        wrap.setVisibility(View.VISIBLE);
        wrap2.setVisibility(View.GONE);
        similar.setVisibility(View.VISIBLE);
    }

    public void setUI(){
/*
        nick.setText(items.get(i).getNickname());
        Glide.with(context)
                .load(items.get(i).getProfileImage())
                .placeholder(R.drawable.meme2)
                .into(viewHolder.profileimg);

        Glide.with(context)
                .load(items.get(i).getImageUrl())
                .placeholder(R.drawable.meme2)
                .into(viewHolder.memeimg);
 */   }


    public void similarUI(View view, int i) {
        HttpClient client = new HttpClient();
        RetrofitApi api = client.getRetrofit().create(RetrofitApi.class);
        TokenManager gettoken = new TokenManager(getContext());
        String token = gettoken.checklogin(getContext());
        System.out.println("확인" + token);
        Call<Similar> call = api.getsimilar(token, i);
        call.enqueue(new Callback<Similar>() {
            @Override
            public void onResponse(Call<Similar> call, Response<Similar> response) {
                if (response.isSuccessful()) {
                    RecyclerView similar = view.findViewById(R.id.similar);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    similar.setLayoutManager(layoutManager);
                    items = response.body().getdata();

                    Log.i("TAG", "onResponse: " + items.size());
                    SimilarAdapter adaptersim;
                    adaptersim = new SimilarAdapter(getContext(), items);
                    similar.setAdapter(adaptersim);

                    // setupCurrentIndicator(0);
                } else
                    Log.i("TAG", "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<Similar> call, Throwable t) {

                Log.d("MainActivity", t.toString());
            }
        });

    }
}