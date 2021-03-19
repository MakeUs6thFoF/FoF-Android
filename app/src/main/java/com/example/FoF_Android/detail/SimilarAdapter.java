package com.example.FoF_Android.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.R;
import com.example.FoF_Android.detail.model.Detail;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.ViewHolder> {
    private List<Detail.Data.Similar> items;
    private Context context;

    public SimilarAdapter(Context applicationContext, List<Detail.Data.Similar> itemArrayList) {
        this.context = applicationContext;
        this.items = itemArrayList;
    }

    @Override
    public SimilarAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meme_all_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimilarAdapter.ViewHolder viewHolder, int i) {

        Glide.with(context)
                .load(items.get(i).getImageUrl())
                .placeholder(R.drawable.meme2)
                .into(viewHolder.memeimg);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView memeimg;
        private CircleImageView profileimg;
        private TextView nick;


        public ViewHolder(View view) {
            super(view);
            nick=(TextView)view.findViewById(R.id.nick);
            memeimg = (ImageView) view.findViewById(R.id.imageView);
            profileimg = (CircleImageView) view.findViewById(R.id.imageView2);

            //on item click
            //TODO 아래->위 스와이프 이벤트+애니메이션
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Detail.Data.Similar clickedDataItem = items.get(pos);

                      /*  Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("login", items.get(pos).getLogin());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
               */
                        
                       // recfragment.getFragmentManager().beginTransaction().add(context,detail).commit();
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getMemeIdx(), Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }

}