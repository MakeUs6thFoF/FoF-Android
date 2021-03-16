package com.example.FoF_Android.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.FoF_Android.R;
import com.google.android.material.tabs.TabLayout;
import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerView;

import java.util.List;

public class SearchFragment extends Fragment {
    TabLayout tabLayout;
    NonSwipeViewPager viewPager;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);


        tabLayout = (TabLayout)view.findViewById(R.id.searchTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("감정"));
        tabLayout.addTab(tabLayout.newTab().setText("동물"));
        tabLayout.addTab(tabLayout.newTab().setText("상황"));
        tabLayout.addTab(tabLayout.newTab().setText("클립"));
        tabLayout.addTab(tabLayout.newTab().setText("텍스트"));
        tabLayout.addTab(tabLayout.newTab().setText("이모티콘"));

        viewPager = (NonSwipeViewPager)view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getFragmentManager()));
        viewPager.setPagingEnabled(false);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.bottomLayout);
        

        return view;
    }
}