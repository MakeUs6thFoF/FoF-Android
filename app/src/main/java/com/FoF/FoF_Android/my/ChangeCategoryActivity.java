package com.FoF.FoF_Android.my;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.FoF.FoF_Android.Category.Category;
import com.FoF.FoF_Android.HttpClient;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;
import com.FoF.FoF_Android.signup.SignUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeCategoryActivity extends AppCompatActivity {

    private Button finishBt;
    private ImageButton backBt;
    private ToggleButton togBt1;  private ToggleButton togBt2;  private ToggleButton togBt3;  private ToggleButton togBt4;
    private ToggleButton togBt5;  private ToggleButton togBt6;  private ToggleButton togBt7;
    RetrofitApi api;
    TokenManager gettoken;

    String titles[] = new String[7];
    int titleIdx[] = new int[7];
    HashMap<String, Integer> titleHash = new HashMap<String, Integer>();
    int maxSize = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_category);

        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        getCategory(api);
        gettoken=new TokenManager(getApplicationContext());

        finishBt = findViewById(R.id.finish_btn);   backBt = findViewById(R.id.backBt);
        togBt1 = findViewById(R.id.button1);  togBt2 = findViewById(R.id.button2);  togBt3 = findViewById(R.id.button3);   togBt4 = findViewById(R.id.button4);
        togBt5 = findViewById(R.id.button5);  togBt6 = findViewById(R.id.button6);  togBt7 = findViewById(R.id.button7);
        List<String> buttonList = new ArrayList<String>();

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()) { //눌렀을 때
                    if (!buttonList.contains(buttonView.getText().toString())) { // 체크 안된 상태이면
                        if (buttonList.size() < maxSize) { // 3개 이하로 체크했을 때
                            buttonList.add(buttonView.getText().toString()); //추가해주고
                            System.out.println(buttonList.size());
                            System.out.println(buttonView.getText().toString());
                        }
                        else { // 3개 이상 체크하면
                            Toast.makeText(getApplicationContext(), "3개까지 선택이 가능합니다", Toast.LENGTH_SHORT).show(); // 체크못하도록
                            buttonView.setChecked(false);
                        }
                    }
                }
                else //체크해제
                {
                    buttonList.remove(buttonView.getText().toString());
                    System.out.println(buttonList.size());
                    System.out.println(buttonView.getText().toString());
                }
            }

        };

        togBt1.setOnCheckedChangeListener(onCheckedChangeListener);  togBt2.setOnCheckedChangeListener(onCheckedChangeListener);  togBt3.setOnCheckedChangeListener(onCheckedChangeListener);
        togBt4.setOnCheckedChangeListener(onCheckedChangeListener);  togBt5.setOnCheckedChangeListener(onCheckedChangeListener);  togBt6.setOnCheckedChangeListener(onCheckedChangeListener);
        togBt7.setOnCheckedChangeListener(onCheckedChangeListener);

        finishBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "변경 되었습니다",Toast.LENGTH_SHORT).show();
                postCategory(api, buttonList);
            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("여기클릭");
                finish();
            }
        });
    }

    public void getCategory(RetrofitApi api){
        api.getCategory().enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                Category category =  response.body();
                for(int i=0; i<category.getData().size(); i++) {
                    titles[i] = category.getData().get(i).getTitle();
                    titleIdx[i] = category.getData().get(i).getCategoryIdx();
                    titleHash.put(titles[i], titleIdx[i]);
                }
                togBt1.setText(titles[0]); togBt1.setTextOff(titles[0]); togBt1.setTextOn(titles[0]);
                togBt2.setText(titles[1]); togBt2.setTextOff(titles[1]); togBt2.setTextOn(titles[1]);
                togBt3.setText(titles[2]); togBt3.setTextOff(titles[2]); togBt3.setTextOn(titles[2]);
                togBt4.setText(titles[3]); togBt4.setTextOff(titles[3]); togBt4.setTextOn(titles[3]);
                togBt5.setText(titles[4]); togBt5.setTextOff(titles[4]); togBt5.setTextOn(titles[4]);
                togBt6.setText(titles[5]); togBt6.setTextOff(titles[5]); togBt6.setTextOn(titles[5]);
                togBt7.setText(titles[6]); togBt7.setTextOff(titles[6]); togBt7.setTextOn(titles[6]);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"서버와의 연결이 끊겼습니다",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void postCategory(RetrofitApi api, List<String> buttonList){
        List<Integer> tmpList = new ArrayList<Integer>();
        for(String s : buttonList)
            tmpList.add(titleHash.get(s));
        String token = gettoken.checklogin(getApplicationContext());
        api.postCategory(token, tmpList).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                SignUp signUp = response.body();
                finish();
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {

            }
        });
    }
}