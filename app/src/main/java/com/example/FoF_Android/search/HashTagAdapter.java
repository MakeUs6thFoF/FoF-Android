package com.example.FoF_Android.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.HashViewHolder> {

    private List<HashTag.Data.TagList> mList;
    private Context context;
    private OnItemClickListener mListener = null;
    private RetrofitApi api;
    private String token;

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {this.mListener = listener;}

    public HashTagAdapter(List<HashTag.Data.TagList> mList, Context context, RetrofitApi api, String token) {
        this.mList = mList;
        this.context = context;
        this.api = api;
        this.token = token;
    }

    public class HashViewHolder extends RecyclerView.ViewHolder {
        protected RecyclerView mRecyclerView;
        protected TextView hashtv;

        public HashViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hashtv = itemView.findViewById(R.id.hashtv);
            this.mRecyclerView = itemView.findViewById(R.id.innerRecycler);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null)
                            mListener.onItemClick(v, position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public HashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_itemlist, parent, false);
        HashViewHolder viewHolder = new HashViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HashViewHolder holder, int position) {
        holder.hashtv.setText(mList.get(position).getTagName());
        //getTagIdx를 해서 이것을 hashsearch하듯이 옮겨주면 된다.
        api.getHashSearch(token, mList.get(position).getTagIdx()).enqueue(new Callback<HashSearch>() {
            @Override
            public void onResponse(Call<HashSearch> call, Response<HashSearch> response) {
                HashSearch body = response.body();
                List<HashSearch.Data.memeList> memeList = body.getData().getMemeList();
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
                holder.mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                HashSearchAdapter mAdapter = new HashSearchAdapter(memeList, context, 1);
                holder.mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<HashSearch> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public HashTag.Data.TagList getItem(int position){
        return mList.get(position);
    }

}
