package com.example.FoF_Android.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.FoF_Android.R;

class HomeAllFragment extends Fragment {

    private RecyclerView recycle;
    MemeAdapter adapter;

    public HomeAllFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.meme_all, container, false);

        initUI(view);

        return view;
    }


    private void initUI(ViewGroup view){

        recycle=view.findViewById((R.id.recycler));

        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(layoutManager);

        adapter= new MemeAdapter();

        //데이터베이스에서 불러오기
        adapter.addItem(new Meme());


        recycle.setAdapter(adapter);
    }
}