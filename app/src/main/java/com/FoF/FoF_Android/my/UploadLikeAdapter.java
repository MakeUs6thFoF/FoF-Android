package com.FoF.FoF_Android.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.search.HashTagAdapter;

import java.util.List;

public class UploadLikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<UploadLike.Data> mList;
    private Context context;
    private HashTagAdapter.OnItemClickListener mListener = null;

    public interface OnItemClickListener{
        void onItemClick(View v, int position, ImageView memeimg);
    }

    public void setOnItemClickListener(HashTagAdapter.OnItemClickListener listener) {this.mListener = listener;}

    public UploadLikeAdapter(List<UploadLike.Data> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public class UploadLikeViewHolder extends RecyclerView.ViewHolder {
        protected ImageView hashImage;

        public UploadLikeViewHolder(@NonNull View itemView) {
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

    public class MemeLoadingViewHolder extends RecyclerView.ViewHolder{
        protected ProgressBar progressBar;

        public MemeLoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_all_item, parent, false);
            UploadLikeViewHolder viewHolder = new UploadLikeViewHolder(view);
            return viewHolder;
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new MemeLoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof UploadLikeViewHolder)
        {
            Glide.with(context).load(mList.get(position).getImageUrl()).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    System.out.println(e.getMessage()+mList.get(position).getImageUrl());
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).into(((UploadLikeViewHolder) holder).hashImage);
            System.out.print("되는거"+mList.get(position).getImageUrl());
        }

        else if(holder instanceof MemeLoadingViewHolder)
            showLoadingView((MemeLoadingViewHolder) holder, position);
    }

    private void showLoadingView(MemeLoadingViewHolder holder, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public UploadLike.Data getItem(int position) { return mList.get(position); }

}
