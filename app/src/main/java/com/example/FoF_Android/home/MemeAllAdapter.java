package com.example.FoF_Android.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.FoF_Android.R;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.home.model.MemeCase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.FoF_Android.home.model.MemeCase.SMALL;

public class MemeAllAdapter extends RecyclerView.Adapter<MemeAllAdapter.ViewHolder> {
    private List<Meme.Data> items;
    private Context context;
    private MemeCase type;
    private final OnItemClickListener listener;
    Integer style;
    ActivityOptionsCompat options;

    public interface OnItemClickListener {
        void onItemClick(Meme.Data item, ImageView memeimg);
    }

    public MemeAllAdapter(Context applicationContext, List<Meme.Data> itemArrayList, MemeCase type, OnItemClickListener listener) {
        this.context = applicationContext;
        this.items = itemArrayList;
        this.listener = listener;
        this.type=type;
    }

    @Override
    public MemeAllAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if(type== SMALL) style=R.layout.meme_item;
        else style=R.layout.meme_all_item;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(style, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemeAllAdapter.ViewHolder viewHolder, int i) {
        if(type== SMALL){
        viewHolder.copyright.setText(items.get(i).getCopyright());
        viewHolder.nick.setText(items.get(i).getNickname());
        Glide.with(context)
                .load(items.get(i).getProfileImage())
                .placeholder(R.drawable.meme2)
                .into(viewHolder.profileimg);
        }

        viewHolder.bind(items.get(i), listener);
            Glide.with(context)
                    .load(items.get(i).getImageUrl())
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
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
        private TextView title;
        private TextView copyright;
        private RecyclerView similar;


        public ViewHolder(View view) {
            super(view);

            nick = (TextView) view.findViewById(R.id.nick);
            memeimg = (ImageView) view.findViewById(R.id.imageView);
            profileimg = (CircleImageView) view.findViewById(R.id.imageView2);
            similar = (RecyclerView) view.findViewById(R.id.similar);
            title = (TextView) view.findViewById(R.id.title);
            copyright=(TextView) view.findViewById(R.id.copyright);

            //on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Meme.Data clickedDataItem = items.get(pos);

                         options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                (Activity) context, memeimg, ViewCompat.getTransitionName(memeimg));
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getMemeIdx(), Toast.LENGTH_SHORT).show();

                    }
                }

            });

        }

        public void bind(final Meme.Data item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item,memeimg);
                }
            });

        }
    }}