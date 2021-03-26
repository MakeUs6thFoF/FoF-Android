package com.example.FoF_Android.home;

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

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.DetailFragment;
import com.example.FoF_Android.search.HashSearch;
import com.example.FoF_Android.search.HashTagAdapter;
import com.example.FoF_Android.search.MemeSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HashClickFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HashClickFragment extends Fragment {
    private int page = 0;
    ImageView hashImage;
    TextView hashText;
    TextView hashCnt;
    TokenManager gettoken;
    RetrofitApi api;
    HashSearchAdapter mAdapter;

    int memeCount;
    List<MemeSearch.Data> memeList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String mParam2; //tagName

    public HashClickFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment HashClickFragment.
     */
    // TODO: Rename and change types and number of parameters
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
            api = HttpClient.getRetrofit().create(RetrofitApi.class);
            gettoken = new TokenManager(getContext());
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
        setRecyclerView(api, view);

        return view;
    }

    public void setRecyclerView(RetrofitApi api, View view){
        String token = gettoken.checklogin(getContext());
        //TODO 해시태그값으로 검색 모델 수정
        api.getSearchMeme(token, mParam2, getPage(), 10).enqueue(new Callback<MemeSearch>() {
            @Override
            public void onResponse(Call<MemeSearch> call, Response<MemeSearch> response) {
                MemeSearch body = response.body();
                memeList = body.getData();
                memeCount = body.getData().size();
                hashCnt.setText(String.valueOf(memeCount)+" 게시물");
                if(memeCount==0) hashText.setText("0 게시물");
                RecyclerView mRecyclerView = view.findViewById(R.id.hashClickRecycle);
                mAdapter = new HashSearchAdapter(memeList, getContext());
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

            @Override
            public void onFailure(Call<MemeSearch> call, Throwable t) {

            }
        });



    }public int getPage(){
        page++;
        return page;
    }
}