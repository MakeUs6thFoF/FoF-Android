package com.example.FoF_Android.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.FoF_Android.R;

import java.util.ArrayList;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.ViewHolder>  {
    ArrayList<Meme> items= new ArrayList<>();



    @NonNull
    @Override
    public MemeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());
        View itemView= inflater.inflate(R.layout.meme_all_item, viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemeAdapter.ViewHolder holder, int position) {
        Meme item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(Meme item){
        items.add(item);
    }
    public void setItems(ArrayList<Meme> items){
        this.items=items;
    }
    public Meme getItem(int position){
        return items.get(position);
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layout;
        ImageView imv;
        TextView tv_title;
        TextView tv_date;
        TextView tv_host;

        public ViewHolder(View itemView){
            super(itemView);
            //   tv_date=itemView.findViewById(R.id.tv_status);
            //   tv_host=itemView.findViewById(R.id.tv_host);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();


                }
            });

        }
        public void setItem(Meme item){
           /* String title=item.getTitle();
            String imgURL=item.getImgURL();
            String host=item.getHost();
            imv.setImageResource(R.drawable.broke_image);

            if(imgURL!=null && !imgURL.equals("")){
                //   imv.setImageURI(Uri.parse("file://"+imgURL));
            }else{
                //   imv.setVisibility(View.GONE);

            }
            tv_title.setText(title);
            //tv_host.setText(host);
            //imv.setImageURI(Uri.parse(item.getImgURL()));
      */
        }
    }
}