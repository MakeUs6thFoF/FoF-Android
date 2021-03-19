package com.example.FoF_Android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.FoF_Android.home.HomeFragment;
import com.example.FoF_Android.make.MakeFragment;
import com.example.FoF_Android.my.MyFragment;
import com.example.FoF_Android.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String TAG="MainActivity";
    Fragment homeFragment;
    Fragment searchFragment;
    Fragment myFragment;
    Fragment makeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment=new HomeFragment();
        searchFragment=new SearchFragment();
        myFragment=new MyFragment();
        makeFragment=new MakeFragment();

        setDefaultFragment();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        Log.i(TAG,"home");
                        break;
                    case R.id.navigation_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();
                        Log.i(TAG,"search");
                        break;
                    case R.id.navigation_my:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,myFragment).commit();
                        Log.i(TAG,"my");
                        break;
                    case R.id.navigation_make:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,makeFragment).commit();
                        Log.i(TAG,"make");
                        break;
                }
                return true;
            }
        });
    }
    public void setDefaultFragment(){
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, homeFragment);
        transaction.commit();
    }




    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment parentFragment = fragmentManager.findFragmentByTag("TAG_PARENT");
        if (parentFragment != null && parentFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
            parentFragment.getChildFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}