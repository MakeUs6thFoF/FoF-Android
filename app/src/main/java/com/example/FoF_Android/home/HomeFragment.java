package com.example.FoF_Android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.FoF_Android.R;
import com.example.FoF_Android.search.HashClickFragment;
import com.example.FoF_Android.search.SearchFragment;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    TabLayout tabLayout;
    Fragment homeall;
    Fragment recmeme;
    private int mNumber = 0;


    public HomeFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        homeall=new HomeAllFragment();
        recmeme=new HomeRecFragment();

        getChildFragmentManager().beginTransaction().add(R.id.container, recmeme).commit();

        tabLayout =view.findViewById(R.id.tabLayout) ;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                setCurrentTabFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
              //  getChildFragmentManager().beginTransaction().replace(R.id.container, recmeme).commit();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    public void setCurrentTabFragment(int position){

        switch (position)
        {
            case 0 :
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, HomeRecFragment.newInstance()).addToBackStack(null);
                ft.commit();
                break;
            case 1 :
                FragmentManager fm1 = getChildFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction();
                ft1.replace(R.id.container, HomeAllFragment.newInstance()).addToBackStack(null);
                ft1.commit();
                break;
        }
    }


}