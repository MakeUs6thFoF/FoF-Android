package com.FoF.FoF_Android.search;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.FoF.FoF_Android.HttpClient;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;
import com.FoF.FoF_Android.detail.DetailFragment;
import com.google.android.material.tabs.TabLayout;

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
    private TabLayout tabLayout;
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
        api.getSearchMeme(token, mParam1, getPage(), 8).enqueue(new Callback<MemeSearch>() {
            @Override
            public void onResponse(Call<MemeSearch> call, Response<MemeSearch> response) {
                MemeSearch body = response.body();
                mList = body.getData();
                //init addapter
                mAdapter = new MemeSearchAdapter(mList, getContext());
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setItemViewCacheSize(20);
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

                EndlessScrollListener scrollListener = new EndlessScrollListener(new EndlessScrollListener.RefreshList() {
                    @Override
                    public void onRefresh(int pageNumber) {
                        System.out.println("페이지테스트"+page);
                        api.getSearchMeme(token, mParam1, getPage(), 8).enqueue(new Callback<MemeSearch>() {
                            @Override
                            public void onResponse(Call<MemeSearch> call, Response<MemeSearch> response) {
                                MemeSearch next_body = response.body();
                                for(int i=0; i< next_body.getData().size(); i++)
                                {
                                    mList.add(next_body.getData().get(i));
                                }
                                mAdapter.notifyItemInserted(mList.size()-1);
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MemeSearch> call, Throwable t) {

                            }
                        });
                    }

                });
                mRecyclerView.addOnScrollListener(scrollListener);

            }

            @Override
            public void onFailure(Call<MemeSearch> call, Throwable t) {

            }
        });
    }

    public int getPage(){
        page++;
        return page;
    }


}