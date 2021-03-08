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
        fragments.add(new FeelingFragment(3)); // 1이모티콘 2클립 3동물 4감정 5밈 6상황
        fragments.add(new FeelingFragment(3));  // 4 3 6 2 5 1 원래순서
        fragments.add(new FeelingFragment(3));
        fragments.add(new FeelingFragment(3));
        fragments.add(new FeelingFragment(3));
        fragments.add(new FeelingFragment(3));
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
