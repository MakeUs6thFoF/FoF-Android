package com.example.FoF_Android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.FoF_Android.home.HomeFragment;
import com.example.FoF_Android.make.UploadFragment;
import com.example.FoF_Android.my.MyFragment;
import com.example.FoF_Android.search.SearchFragment;
import com.example.FoF_Android.signup.StartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    String TAG="MainActivity";
    Fragment homeFragment;
    Fragment searchFragment;
    Fragment myFragment;
    Fragment makeFragment;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tokenManager=new TokenManager(MainActivity.this);
        String token=tokenManager.checklogin(MainActivity.this);

        if(token==null || token.length()<8){ //토큰 없으면 초기화면으로
            Intent mainintent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(mainintent);
            finish();
        }

        homeFragment=new HomeFragment();
        searchFragment=new SearchFragment();
        myFragment=new MyFragment();
        makeFragment=new UploadFragment();

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
         super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}