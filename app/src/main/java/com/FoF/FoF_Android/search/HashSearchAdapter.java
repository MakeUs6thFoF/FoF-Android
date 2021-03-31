package com.FoF.FoF_Android.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.FoF.FoF_Android.R;

import java.util.List;

public class HashSearchAdapter extends RecyclerView.Adapter<HashSearchAdapter.HashSearchViewHolder> {

    private List<HashSearch.Data.memeList> mList;
    private Context context;
    private HashTagAdapter.OnItemClickListener mListener = null;
    private int mode = 0;

    public interface OnItemClickListener{
        void onItemClick(View v, int position, ImageView memeimg);
    }

    public void setOnItemClickListener(HashTagAdapter.OnItemClickListener listener) {this.mListener = listener;}

    public HashSearchAdapter(List<HashSearch.Data.memeList> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }
    public HashSearchAdapter(List<HashSearch.Data.memeList> mList, Context context, int mode) {
        this.mList = mList;
        this.context = context;
        this.mode = mode;
    }

    public class HashSearchViewHolder extends RecyclerView.ViewHolder {
        protected ImageView hashImage;

        public HashSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hashImage = itemView.findViewById(R.id.imageView);

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
    public HashSearchAdapter.HashSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(mode == 0)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_all_item, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_item_stagger, parent, false);

        HashSearchViewHolder viewHolder = new HashSearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HashSearchAdapter.HashSearchViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getImageUrl()).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(holder.hashImage);

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public HashSearch.Data.memeList getItem(int position) { return mList.get(position); }

}
