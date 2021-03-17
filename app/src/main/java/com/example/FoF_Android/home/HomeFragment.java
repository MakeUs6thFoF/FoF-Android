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

public class HomeFragment extends Fragment implements OnBackPressed{
    TabLayout tabLayout;
    Fragment homeall;
    Fragment recmeme;
    private int mNumber = 0;
    private FragmentManager.OnBackStackChangedListener mListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            FragmentManager fragmentManager = getChildFragmentManager();
            int count = 0;
            for (Fragment f : fragmentManager.getFragments()) {
                if (f != null) {
                    count++;
                }
            }
            mNumber = count;
        }
    };
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
    @Override
    public void onBackPressed() {

        // If the fragment exists and has some back-stack entry
        if (recmeme != null && recmeme.getChildFragmentManager().getBackStackEntryCount() > 0){
            // Get the fragment fragment manager - and pop the backstack
            recmeme.getChildFragmentManager().popBackStack();
        }
        // Else, nothing in the direct fragment back stack
        else{
            // Let super handle the back press
         //   super.onBackPressed();
        }
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
        ft.replace(R.id.container, fragment).addToBackStack(null);
        ft.commit();
    }

}