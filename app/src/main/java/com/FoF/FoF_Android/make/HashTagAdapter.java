package com.FoF.FoF_Android.make;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FoF.FoF_Android.R;

import java.util.List;

public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.HashViewHolder> {

    private List<UpHashSearch.Data> mList;
    private Context context;
    private OnItemClickListener mListener = null;

    private String token;

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {this.mListener = listener;}

    public HashTagAdapter(List<UpHashSearch.Data> mList, Context context) {
        this.mList = mList;
        this.context = context;

    }

    public class HashViewHolder extends RecyclerView.ViewHolder {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_hash_item, parent, false);
        HashViewHolder viewHolder = new HashViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HashViewHolder holder, int position) {
        holder.hashtv.setText(mList.get(position).getTagName());
        //getTagIdx를 해서 이것을 hashsearch하듯이 옮겨주면 된다.

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public UpHashSearch.Data getItem(int position){
        return mList.get(position);
    }

}
