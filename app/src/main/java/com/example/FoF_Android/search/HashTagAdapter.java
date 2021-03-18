package com.example.FoF_Android.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.FoF_Android.R;

import java.util.ArrayList;
import java.util.List;

public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.HashViewHolder> {

    private List<HashTag.Data.TagList> mList;
    private Context context;
    private OnItemClickListener mListener = null;

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {this.mListener = listener;}

    public HashTagAdapter(List<HashTag.Data.TagList> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public class HashViewHolder extends RecyclerView.ViewHolder {
        protected ImageView hashImage;
        protected TextView hashtv;

        public HashViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hashtv = itemView.findViewById(R.id.hashtv);

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
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public HashTag.Data.TagList getItem(int position){
        return mList.get(position);
    }

}
