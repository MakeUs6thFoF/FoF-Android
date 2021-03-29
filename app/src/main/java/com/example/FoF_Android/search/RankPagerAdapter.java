package com.example.FoF_Android.search;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.R;
import com.example.FoF_Android.detail.DetailFragment;

import java.util.ArrayList;
import java.util.List;

public class RankPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    List<CategoryMeme.Data.MemeList> mList = new ArrayList<>();
    private AdapterView.OnItemClickListener mListener = null;
    private FragmentManager manager;

    public RankPagerAdapter(boolean isMultiScr, List<CategoryMeme.Data.MemeList> mList, FragmentManager manager) {
        this.isMultiScr = isMultiScr;
        this.mList = mList;
        this.manager = manager;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_feeling, null);
        ImageView imageView = linearLayout.findViewById(R.id.innerImage);
        TextView ranktv = linearLayout.findViewById(R.id.ranktv);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("프린트"+position);
                DetailFragment detail = new DetailFragment(mList.get(position).getMemeIdx());
                manager.beginTransaction().addSharedElement(imageView, ViewCompat.getTransitionName(imageView))
                        .setReorderingAllowed(true)
                        .addToBackStack(null).replace(R.id.container, detail).commit();
            }
        });
        //new LinearLayout(container.getContext());
        //linearLayout.setId(R.id.item_id);

        switch (position) {
            case 0:
                Glide.with(container.getContext()).load(mList.get(0).getImageUrl()).into(imageView);
                ranktv.setText("1");
                ranktv.setVisibility(View.VISIBLE);
                break;
            case 1:
                Glide.with(container.getContext()).load(mList.get(1).getImageUrl()).into(imageView);
                ranktv.setText("2");
                ranktv.setVisibility(View.VISIBLE);
                break;
            case 2:
                Glide.with(container.getContext()).load(mList.get(2).getImageUrl()).into(imageView);
                ranktv.setText("3");
                ranktv.setVisibility(View.VISIBLE);
                break;
            case 3:
                Glide.with(container.getContext()).load(mList.get(3).getImageUrl()).into(imageView);
                ranktv.setText("4");
                ranktv.setVisibility(View.VISIBLE);
                break;
            case 4:
                Glide.with(container.getContext()).load(mList.get(4).getImageUrl()).into(imageView);
                ranktv.setText("5");
                ranktv.setVisibility(View.VISIBLE);
                break;
        }
        container.addView(linearLayout);

        return linearLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
