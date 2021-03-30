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
import com.bumptech.glide.request.target.Target;
import com.example.FoF_Android.R;
import com.example.FoF_Android.detail.model.Detail;
import com.example.FoF_Android.detail.model.Similar;
import com.example.FoF_Android.home.MemeAllAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.ViewHolder> {
    private List<Similar.Data> items;
    private Context context;
    private SimilarAdapter.OnItemClickListener mListener = null;

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public SimilarAdapter(Context applicationContext, List<Similar.Data> itemArrayList) {
        this.context = applicationContext;
        this.items = itemArrayList;
    }

    public void setOnItemClickListener(SimilarAdapter.OnItemClickListener listener) {this.mListener = listener;}

    @Override
    public SimilarAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meme_all_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimilarAdapter.ViewHolder viewHolder, int i) {

        Glide.with(context)
                .load(items.get(i).getImageUrl())
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
                        Similar.Data clickedDataItem = items.get(pos);

                        if (pos != RecyclerView.NO_POSITION) {
                            if (mListener != null)
                                mListener.onItemClick(v, clickedDataItem.getMemeIdx());
                        }
                       // recfragment.getFragmentManager().beginTransaction().add(context,detail).commit();
                      //  Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getMemeIdx(), Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }

}