package com.example.FoF_Android.search;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.R;

import java.util.ArrayList;
import java.util.List;

public class RankPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    List<String> datas = new ArrayList<>();

    public RankPagerAdapter(boolean isMultiScr, List<String> datas) {
        this.isMultiScr = isMultiScr;
        this.datas = datas;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_feeling, null);
        ImageView imageView = linearLayout.findViewById(R.id.innerImage);
        TextView ranktv = linearLayout.findViewById(R.id.ranktv);
        //new LinearLayout(container.getContext());
        //linearLayout.setId(R.id.item_id);

        switch (position) {
            case 0:
                Glide.with(container.getContext()).load(datas.get(0)).into(imageView);
                ranktv.setText("1");
                ranktv.setVisibility(View.VISIBLE);
                break;
            case 1:
                Glide.with(container.getContext()).load(datas.get(1)).into(imageView);
                ranktv.setText("2");
                ranktv.setVisibility(View.VISIBLE);
                break;
            case 2:
                Glide.with(container.getContext()).load(datas.get(2)).into(imageView);
                ranktv.setText("3");
                ranktv.setVisibility(View.VISIBLE);
                break;
            case 3:
                Glide.with(container.getContext()).load(datas.get(3)).into(imageView);
                ranktv.setText("4");
                ranktv.setVisibility(View.VISIBLE);
                break;
            case 4:
                Glide.with(container.getContext()).load(datas.get(4)).into(imageView);
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
        return datas.size();
    }
}
