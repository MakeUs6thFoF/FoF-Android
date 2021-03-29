package com.example.FoF_Android.make;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.search.HashSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.TEXT_ALIGNMENT_CENTER;


public class UploadHashtag extends AppCompatActivity {
    TextView next;
    EditText hashtag;
    com.example.FoF_Android.make.HashTagAdapter madapter;
    List<UpHashSearch.Data> hashlist = new ArrayList<>();
    LinearLayout Tag;
    Integer i=0;
    Integer k=0;
    String tagname="";
    TextView btn[] = new TextView[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_hashtag);
        hashtag=findViewById(R.id.hashtag);
        Tag=findViewById(R.id.Tag);
        next=findViewById(R.id.next);
        hashtag.setText("#");

        hashtag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RetrofitApi api = HttpClient.getRetrofit().create(RetrofitApi.class);

                Log.i("Upload",s.toString());
                api.getHashtag(new TokenManager(UploadHashtag.this).checklogin(UploadHashtag.this), s.toString()).enqueue(new Callback<UpHashSearch>() {
                    @Override
                    public void onResponse(Call<UpHashSearch> call, Response<UpHashSearch> response) {
                        if(response.isSuccessful()){
                            UpHashSearch tag = response.body();
                            List<UpHashSearch.Data> items=tag.getData();
                            Log.i("Upload",tag.getMessage());
                        if(tag!=null){
                            RecyclerView mRecyclerView = findViewById(R.id.hashtag_recycler);
                            LinearLayoutManager mLinearLayoutmanager = new LinearLayoutManager(UploadHashtag.this);
                            mRecyclerView.setLayoutManager(mLinearLayoutmanager);
                            madapter = new HashTagAdapter( items,UploadHashtag.this);
                            mRecyclerView.setAdapter(madapter);


                            madapter.setOnItemClickListener(new com.example.FoF_Android.make.HashTagAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    if(k<7){
                                        hashtag.setText("#");
                                        hashtag.setSelection(hashtag.length());
                                        String name="#"+tag.getData().get(position).getTagName();
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                        params.rightMargin=4;
                                        params.gravity= Gravity.CENTER;
                                        btn[i] = new TextView(UploadHashtag.this);
                                        btn[i].setLayoutParams(params);
                                        btn[i].setText(name);
                                        btn[i].setTextAlignment(TEXT_ALIGNMENT_CENTER);
                                        btn[i].setBackgroundResource(R.color.green);
                                        btn[i].setIncludeFontPadding(false);
                                        btn[i].setPadding(5,8,5,0);
                                        btn[i].setTextAppearance(R.style.basic_12dp_black);
                                        btn[i].setId(i);
                                        Tag.addView(btn[i]);
                                        k=k+1;
                                        i=i+1;
                                    for(int j=0;j<i;j++){
                                        int finalI = j;
                                        btn[j].setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Tag.removeView(btn[finalI]);
                                                btn[finalI].setText("");
                                                k--;
                                            }
                                        });
                                    }}
                                    else Toast.makeText(UploadHashtag.this, "해시태그는 최대 6개까지만 가능합니다", Toast.LENGTH_SHORT).show();
                                }
                            });}}else  Log.i("Upload",response.message());

                    }


                    @Override
                    public void onFailure(Call<UpHashSearch> call, Throwable t) {
                        Log.i("Upload",t.toString());
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            for(int j=0;j<i;j++)
                    tagname=tagname+btn[j].getText()+" ";
            intent.putExtra("tagname", tagname);
            setResult(UploadNextFragment.RESULT_CODE, intent);
            finish();
        }
    });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}