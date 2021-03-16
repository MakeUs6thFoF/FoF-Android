package com.example.FoF_Android.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.home.OnBackPressed;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class DetailFragment extends Fragment implements OnBackPressed {
    RetrofitApi api;
    RecyclerView similar;
    Integer i=0;
    List<Detail.Data.Similar> items;
    Detail.Data.memeDetail detail;
    ImageView memeimg;
    TextView title, copyright;
    SimilarAdapter adaptersim;
    String token;
    LinearLayout Tag,Tag2;
    ImageButton report,copy,send;
    ToggleButton like;
    public DetailFragment(int i) {
        this.i=i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.meme_detail, container, false);
        initUI(view);
        onclick();
        similarUI(view, i);
        getChildFragmentManager().popBackStack();
        return view;
    }


    public void initUI(ViewGroup view){
        memeimg = (ImageView) view.findViewById(R.id.imageView);
        title = (TextView) view.findViewById(R.id.title);
        copyright=(TextView)view.findViewById(R.id.copyright);
        similar=view.findViewById(R.id.similar);
        report=(ImageButton)view.findViewById(R.id.report);
        like=(ToggleButton) view.findViewById(R.id.like);
        copy=(ImageButton)view.findViewById(R.id.copy);
        send=(ImageButton)view.findViewById(R.id.send);

        Tag= (LinearLayout)view.findViewById(R.id.Tag);
        Tag2= (LinearLayout)view.findViewById(R.id.Tag2);
    }
    public void onclick(){
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {

                    /*api.postLike(token,items.get(i).getMemeIdx()).enqueue(new Callback<Like>() {
                        @Override
                        public void onResponse(Call<Like> call, Response<Like> response) {
                            if(response.isSuccessful()) {
                                Like like = response.body();

                                System.out.println("포스트확인2" + like.getCode());
                                System.out.println("포스트확인2" + like.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<Like> call, Throwable t) {

                        }
                    });*/
                }
                else
                {
                }
            }
        });
    }
    public void setUI(){

       // nick.setText(detail.getNickname());

        Glide.with(getContext())
                .load(detail.getImageUrl())
                .placeholder(R.drawable.meme2)
                .into(memeimg);
        title.setText(detail.getMemeTitle());
        copyright.setText(detail.getCopyright());

        TextView btn[] = new TextView[30];
        String hashtag=detail.getTag();

        String[] array = hashtag.split(",");

        float factor = getContext().getResources().getDisplayMetrics().density;

        int pixelw = (int) (66 * factor + 0.5f);
        int pixelh = (int) (26 * factor + 0.5f);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pixelw, ViewGroup.LayoutParams.MATCH_PARENT);
        params.width=pixelw;
        params.height=pixelh;
        params.rightMargin=4;
        params.gravity= Gravity.CENTER;

        for (int i = 0; i < array.length; i++) {
            btn[i] = new TextView(getContext());
            btn[i].setLayoutParams(params);
            btn[i].setText(array[i]);
            btn[i].setTextAlignment(TEXT_ALIGNMENT_CENTER);
            btn[i].setBackgroundResource(R.color.white);
            btn[i].setIncludeFontPadding(false);
            btn[i].setPadding(0,4,0,0);
            btn[i].setTextAppearance(R.style.basic_12dp_black);
            btn[i].setId(i);
            if (i < 4) {
                Tag.addView(btn[i]);
            }else Tag2.addView(btn[i]);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });}
    }


    public void similarUI(View view, int i) {
        HttpClient client = new HttpClient();
         api = client.getRetrofit().create(RetrofitApi.class);
        TokenManager gettoken = new TokenManager(getContext());
        token = gettoken.checklogin(getContext());
        System.out.println("확인" + token);
        Call<Detail> call = api.getsimilar(token, i);
        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if (response.isSuccessful()) {
                    RecyclerView similar = view.findViewById(R.id.similar);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    similar.setLayoutManager(layoutManager);
                    items = response.body().getdata().getData();
                    detail = response.body().getdata().getDetail();

                    Log.i("TAG", "onResponse: " + detail.getMemeTitle());
                    adaptersim = new SimilarAdapter(getContext(), items);
                    similar.setAdapter(adaptersim);
                    setUI();
                    // setupCurrentIndicator(0);
                } else
                    Log.i("TAG", "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {

                Log.d("MainActivity", t.toString());
            }
        });

    }@Override
    public void onBackPressed(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
    @Override
    public void onDestroy() {
      super.onDestroy();

        Log.d("ChildFragment", "onDestroy");
    }



}