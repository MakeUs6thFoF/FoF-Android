package com.example.FoF_Android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.FoF_Android.R;

public class MemeDetailActivity extends Fragment {

    LinearLayout wrap;
    LinearLayout wrap2;
    RecyclerView similar;

    public MemeDetailActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.meme_item, container, false);
        initUI(view);

        return view;
    }


    public void initUI(ViewGroup view){
        wrap=view.findViewById(R.id.wrap);
        wrap2=view.findViewById(R.id.wrap_1);
        similar=view.findViewById(R.id.similar);
        wrap.setVisibility(View.VISIBLE);
        wrap2.setVisibility(View.GONE);
        similar.setVisibility(View.VISIBLE);
    }
}