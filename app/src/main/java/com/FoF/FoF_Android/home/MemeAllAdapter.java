package com.FoF.FoF_Android.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.home.model.Meme;
import com.FoF.FoF_Android.home.model.MemeCase;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.logging.Handler;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.FoF.FoF_Android.home.model.MemeCase.SMALL;

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
        else style=R.layout.meme_all_item_shimmer;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(style, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemeAllAdapter.ViewHolder viewHolder, int i) {
     /*   viewHolder.frameLayout.startShimmer();
        viewHolder.frameLayout.postDelayed(new Runnable() {
           @Override
           public void run() {


           }
       },3000);*/
      //  viewHolder.bind(items.get(i), listener);
        viewHolder.frameLayout.setVisibility(View.GONE);
        viewHolder.memeimg.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(items.get(i).getImageUrl()).placeholder(R.color.skyblue)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.placeholder)
                .into(viewHolder.memeimg);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView memeimg;
        private CircleImageView profileimg;
        ShimmerFrameLayout frameLayout;
        private TextView copyright;


        public ViewHolder(View view) {
            super(view);
            frameLayout=(ShimmerFrameLayout)view.findViewById(R.id.framelayout) ;
            memeimg = (ImageView) view.findViewById(R.id.imageView);
            profileimg = (CircleImageView) view.findViewById(R.id.imageView2);
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


