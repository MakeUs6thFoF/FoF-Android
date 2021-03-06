package com.example.FoF_Android.search;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments = new ArrayList<>();

    public PagerAdapter(FragmentManager fm){
        super(fm);
        fragments.add(new FeelingFragment());
        fragments.add(new AnimalFragment());
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
