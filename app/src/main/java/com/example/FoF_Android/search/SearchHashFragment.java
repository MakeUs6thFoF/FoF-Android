package com.example.FoF_Android.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchHashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchHashFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText searchEdit;
    private ImageButton searchIb;
    private TextView diamond1; private TextView diamond2; private TextView diamond3; private TextView diamond4; private TextView diamond5; private TextView diamond6; private TextView diamond7; private TextView diamond8;

    RetrofitApi api;
    TokenManager gettoken;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchHashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchHashFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchHashFragment newInstance(String param1, String param2) {
        SearchHashFragment fragment = new SearchHashFragment();
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
        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken = new TokenManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_hash, container, false);
        getHashTag(api, view);
        searchIb = view.findViewById(R.id.searchIb);
        searchEdit = view.findViewById(R.id.searchEdit);
        // 임시로 하드코딩
        diamond1 = view.findViewById(R.id.diamond1); diamond2 = view.findViewById(R.id.diamond2); diamond3 = view.findViewById(R.id.diamond3); diamond4 = view.findViewById(R.id.diamond4);
        diamond5 = view.findViewById(R.id.diamond5); diamond6 = view.findViewById(R.id.diamond6); diamond7 = view.findViewById(R.id.diamond7); diamond8 = view.findViewById(R.id.diamond8);
        diamond1.setOnClickListener(this);

        searchIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, Search2Fragment.newInstance(searchEdit.getText().toString())).commit();
            }
        });
        return view;
    }

    public void getHashTag(RetrofitApi api, View view){
        String token = gettoken.checklogin(getContext());
        api.getRandomTag().enqueue(new Callback<RandomTag>() {
            @Override
            public void onResponse(Call<RandomTag> call, Response<RandomTag> response) {
                RandomTag body = response.body();
                List<RandomTag.Data> mList = body.getData();
                //하드코딩->변경예정
                diamond1.setText(mList.get(0).tagName); diamond2.setText(mList.get(1).tagName); diamond3.setText(mList.get(2).tagName); diamond4.setText(mList.get(3).tagName);
                diamond5.setText(mList.get(4).tagName); diamond6.setText(mList.get(5).tagName); diamond7.setText(mList.get(6).tagName); diamond8.setText(mList.get(7).tagName);
            }

            @Override
            public void onFailure(Call<RandomTag> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.diamond1:
                searchEdit.setText(diamond1.getText().toString());
                break;
            case R.id.diamond2:
                searchEdit.setText(diamond2.getText().toString());
                break;
            case R.id.diamond3:
                searchEdit.setText(diamond3.getText().toString());
                break;
            case R.id.diamond4:
                searchEdit.setText(diamond4.getText().toString());
                break;
            case R.id.diamond5:
                searchEdit.setText(diamond5.getText().toString());
                break;
            case R.id.diamond6:
                searchEdit.setText(diamond6.getText().toString());
                break;
            case R.id.diamond7:
                searchEdit.setText(diamond7.getText().toString());
                break;
            case R.id.diamond8:
                searchEdit.setText(diamond8.getText().toString());
                break;
        }
    }
}