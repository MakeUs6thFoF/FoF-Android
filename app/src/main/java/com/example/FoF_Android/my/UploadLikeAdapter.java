package com.example.FoF_Android.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.FoF_Android.R;
import com.example.FoF_Android.search.HashSearch;
import com.example.FoF_Android.search.HashTagAdapter;
import com.example.FoF_Android.search.MemeSearch;
import com.example.FoF_Android.search.MemeSearchAdapter;

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
            Glide.with(context).load(mList.get(position).getImageUrl()).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(((UploadLikeViewHolder) holder).hashImage);
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
