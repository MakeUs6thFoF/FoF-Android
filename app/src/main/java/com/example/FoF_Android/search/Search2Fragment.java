package com.example.FoF_Android.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.DetailFragment;
import com.example.FoF_Android.detail.model.Detail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int page = 0;
    RetrofitApi api;
    TokenManager gettoken;
    MemeSearchAdapter mAdapter;
    RecyclerView mRecyclerView;
    private EditText searchEdit;
    private ImageButton searchIb;
    private List<MemeSearch.Data> mList;
    private boolean isLoading = false;

    public Search2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Search2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Search2Fragment newInstance(String param1) {
        Search2Fragment fragment = new Search2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken = new TokenManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search2, container, false);
        searchEdit = view.findViewById(R.id.searchEdit2);
        searchIb = view.findViewById(R.id.searchIb2);
        searchIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, Search2Fragment.newInstance(searchEdit.getText().toString())).commit();
            }
        });
        mRecyclerView = view.findViewById(R.id.hashtag_recycler2);
        loadFirstPost(api, view);
        return view;
    }

    public void loadFirstPost(RetrofitApi api, View view){
        String token = gettoken.checklogin(getContext());
        page=0;
        api.getSearchMeme(token, mParam1, getPage(), 10).enqueue(new Callback<MemeSearch>() {
            @Override
            public void onResponse(Call<MemeSearch> call, Response<MemeSearch> response) {
                MemeSearch body = response.body();
                mList = body.getData();
                //init addapter
                mAdapter = new MemeSearchAdapter(mList, getContext());
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

                //스크롤 처리
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int pastVisibleItems;
                        int[] firstVisibleItems = null;

                        // 스크롤시 로딩 구현예정 ( 이미지 많이 등록 후 실험 )
                        if(!isLoading){
                            firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                            if(firstVisibleItems != null && firstVisibleItems.length > 0)
                                pastVisibleItems = firstVisibleItems[0];
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<MemeSearch> call, Throwable t) {

            }
        });
    }

    public void loadMorePost(RetrofitApi api, View view){

    }

    public int getPage(){
        page++;
        return page;
    }
}