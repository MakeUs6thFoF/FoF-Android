package com.FoF.FoF_Android.search;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MagnifyAnimPageAdapter extends PagerAdapter {

    List<String> datas = new ArrayList<String>();
    Context context;
    private LayoutInflater inflater;

    public MagnifyAnimPageAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        /*inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.)*/


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, container.getLayoutParams().height);
        params.gravity = Gravity.CENTER_VERTICAL;


        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(params);

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setBackgroundColor(Color.parseColor("#BEDCFF"));
        imageView.setMaxHeight(2000);
        imageView.setMaxWidth(2000);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setTag(position);
        imageView.requestLayout();
        ll.addView(imageView);

        if(imageView!=null)
            container.addView(ll);
        return ll;
    }

    @Override
    public int getCount(){
        return datas.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == object;
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
