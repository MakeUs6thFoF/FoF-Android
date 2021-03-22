package com.example.FoF_Android.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.home.model.MemeCase;
import com.example.FoF_Android.search.HashSearch;
import com.example.FoF_Android.search.HashTag;
import com.example.FoF_Android.search.HashTagAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.FoF_Android.home.model.MemeCase.SMALL;

public class MemeAllAdapter extends RecyclerView.Adapter<MemeAllAdapter.ViewHolder> {
    private List<Meme.Data> items;
    private Context context;
    private MemeCase type;
    private MemeAllAdapter.OnItemClickListener mListener = null;
    Integer style;

    ActivityOptionsCompat options;

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
    public MemeAllAdapter(List<Meme.Data> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setOnItemClickListener(MemeAllAdapter.OnItemClickListener listener) {this.mListener = listener;}

    @Override
    public MemeAllAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if(type== SMALL) style=R.layout.meme_rec_item;
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

      //  viewHolder.bind(items.get(i), listener);
            Glide.with(context)
                    .load(items.get(i).getImageUrl())
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .error(R.drawable.meme2)
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
        private LinearLayout Tag;
        private LinearLayout Tag2;

        public ViewHolder(View view) {
            super(view);
            Tag = (LinearLayout) view.findViewById(R.id.Tag);
            Tag2 = (LinearLayout) view.findViewById(R.id.Tag2);
            nick = (TextView) view.findViewById(R.id.nick);
            memeimg = (ImageView) view.findViewById(R.id.imageView);
            profileimg = (CircleImageView) view.findViewById(R.id.imageView2);
            similar = (RecyclerView) view.findViewById(R.id.similar);
            title = (TextView) view.findViewById(R.id.title);
            copyright = (TextView) view.findViewById(R.id.copyright);

            //on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null)
                            mListener.onItemClick(v, position);
                    }
                }
            });
        }
    }

    public Meme.Data getItem(int position){
        return items.get(position);
    }

}


