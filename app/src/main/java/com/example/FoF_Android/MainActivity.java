package com.example.FoF_Android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.FoF_Android.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    String TAG="MainActivity";
    Fragment homeFragment;
    Fragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment=new HomeFragment();
        searchFragment=new SearchFragment();

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
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        Log.i(TAG,"my");
                        break;
                    case R.id.navigation_make:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
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
    }
