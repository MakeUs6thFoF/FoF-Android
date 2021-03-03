package com.example.FoF_Android.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.MainActivity;
import com.example.FoF_Android.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemerecAdapter extends RecyclerView.Adapter<MemerecAdapter.ViewHolder> {
    private List<Meme.Data> items;
    private Context context;

    public MemerecAdapter(Context applicationContext, List<Meme.Data> itemArrayList) {
        this.context = applicationContext;
        this.items = itemArrayList;
    }

    @Override
    public MemerecAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meme_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemerecAdapter.ViewHolder viewHolder, int i) {
        viewHolder.nick.setText(items.get(i).getNickname());
        Glide.with(context)
                .load(items.get(i).getImageUrl())
                .placeholder(R.drawable.meme2)
                .into(viewHolder.memeimg);
        Glide.with(context)
                .load(items.get(i).getProfileImage())
                .placeholder(R.drawable.meme2)
                .into(viewHolder.profileimg);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView memeimg;
        private CircleImageView profileimg;
        private TextView nick;
        HomeFragment fragment;
        MemeDetailActivity detail;
        HomeRecFragment recfragment;

        public ViewHolder(View view) {
            super(view);
            nick=(TextView)view.findViewById(R.id.textView);
            memeimg = (ImageView) view.findViewById(R.id.imageView);
            profileimg = (CircleImageView) view.findViewById(R.id.imageView2);
            fragment=new HomeFragment();
            detail=new MemeDetailActivity();
            recfragment=new HomeRecFragment();

            //on item click
            //TODO 아래->위 스와이프 이벤트+애니메이션
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Meme.Data clickedDataItem = items.get(pos);
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