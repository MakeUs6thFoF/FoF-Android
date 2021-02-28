package com.example.FoF_Android.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.R;

import java.util.List;

public class MemeAdapter  extends RecyclerView.Adapter<MemeAdapter.MyViewHolder>{

    private Context c;
    private List<Meme.Data> items;

    public MemeAdapter(Context c, List<Meme.Data> items) {
        this.c = c;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.meme_all_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(c)
                .load(items.get(position).getImageUrl())
                .into(holder.name);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (ImageView)itemView.findViewById(R.id.imageView3);

        }
    }
}