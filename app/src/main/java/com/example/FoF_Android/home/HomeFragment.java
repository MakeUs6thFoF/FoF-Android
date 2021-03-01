package com.example.FoF_Android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.FoF_Android.R;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    TabLayout tabLayout;
    Fragment homeall;
    Fragment recmeme;

    public HomeFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        homeall=new HomeAllFragment();
        recmeme=new BlankFragment();

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
                replaceFragment(recmeme);
                break;
            case 1 :
                replaceFragment(homeall);
                break;
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}