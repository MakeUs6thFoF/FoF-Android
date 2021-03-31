package com.FoF.FoF_Android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.FoF.FoF_Android.HttpClient;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;
import com.FoF.FoF_Android.dialog.model.Report;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDialog extends Dialog {

    private Button reportbutton;
    private ImageButton mNegativeButton;
    String button_num="";
    private View.OnClickListener mModifyListener;
    private View.OnClickListener mNegativeListener;
    private Integer memeidx;
    private ReportNextDialog reportnext;
    ToggleButton togBt1;
    ToggleButton togBt2;
    ToggleButton togBt3;
    ToggleButton togBt4;
    ToggleButton togBt5;
    ToggleButton togBt6;
    ToggleButton togBt7;
    ToggleButton togBt8;
    RetrofitApi api;
    TokenManager gettoken;

    String titles[] = new String[8];
    int titleIdx[] = new int[8];
    HashMap<String, Integer> titleHash = new HashMap<String, Integer>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_report);

        //셋팅
        reportbutton=(Button)findViewById(R.id.withdrawal_btn);
        mNegativeButton=(ImageButton) findViewById(R.id.cancel);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)

        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        getCategory(api);
        gettoken=new TokenManager(getContext());

        togBt1 = findViewById(R.id.button1);
        togBt2 = findViewById(R.id.button2);
        togBt3 = findViewById(R.id.button3);
        togBt4 = findViewById(R.id.button4);
        togBt5 = findViewById(R.id.button5);
        togBt6 = findViewById(R.id.button6);
        togBt7 = findViewById(R.id.button7);
        togBt8 = findViewById(R.id.button8);



        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()) { //눌렀을 때
                    if (button_num=="") { // 체크 안된 상태이면
                            button_num=buttonView.getText().toString(); //추가해주고
                              System.out.println(button_num.toString());
                    }else{
                        clearbtn(buttonView);
                        button_num=buttonView.getText().toString(); //추가해주고
                        System.out.println(button_num.toString());
                    }
                }
                else //체크해제
                {
                    button_num="";
                    System.out.println(button_num.toString());
                }
            }

        };

        togBt1.setOnCheckedChangeListener(onCheckedChangeListener);
        togBt2.setOnCheckedChangeListener(onCheckedChangeListener);
        togBt3.setOnCheckedChangeListener(onCheckedChangeListener);
        togBt4.setOnCheckedChangeListener(onCheckedChangeListener);
        togBt5.setOnCheckedChangeListener(onCheckedChangeListener);
        togBt6.setOnCheckedChangeListener(onCheckedChangeListener);
        togBt7.setOnCheckedChangeListener(onCheckedChangeListener);
        togBt8.setOnCheckedChangeListener(onCheckedChangeListener);


        reportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reportnext= new ReportNextDialog(getContext(),memeidx,button_num, titleHash.get(button_num));
                Log.i("Report",memeidx.toString()+titleHash.get(button_num).toString());
                dismiss();
                reportnext.setCancelable(true);
                reportnext.getWindow().setGravity(Gravity.CENTER);
                reportnext.show();
            }
        });


        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });

    }


    public void clearbtn(CompoundButton button){
        togBt1.setChecked(false);
        togBt2.setChecked(false);
        togBt3.setChecked(false);
        togBt4.setChecked(false);
        togBt5.setChecked(false);
        togBt6.setChecked(false);
        togBt7.setChecked(false);
        togBt8.setChecked(false);
        button.setChecked(true);
    }
    public void getCategory(RetrofitApi api){
        api.getReportTag().enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                Report report =  response.body();
                for(int i=0; i<report.getData().size(); i++) {
                    titles[i] = report.getData().get(i).getTitle();
                    titleIdx[i] = report.getData().get(i).getReportidx();
                    titleHash.put(titles[i], titleIdx[i]);
                }
                togBt1.setText(titles[0]); togBt1.setTextOff(titles[0]); togBt1.setTextOn(titles[0]);
                togBt2.setText(titles[1]); togBt2.setTextOff(titles[1]); togBt2.setTextOn(titles[1]);
                togBt3.setText(titles[2]); togBt3.setTextOff(titles[2]); togBt3.setTextOn(titles[2]);
                togBt4.setText(titles[3]); togBt4.setTextOff(titles[3]); togBt4.setTextOn(titles[3]);
                togBt5.setText(titles[4]); togBt5.setTextOff(titles[4]); togBt5.setTextOn(titles[4]);
                togBt6.setText(titles[5]); togBt6.setTextOff(titles[5]); togBt6.setTextOn(titles[5]);
                togBt7.setText(titles[6]); togBt7.setTextOff(titles[6]); togBt7.setTextOn(titles[6]);
                togBt8.setText(titles[7]); togBt8.setTextOff(titles[7]); togBt8.setTextOn(titles[7]);
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                Toast.makeText(getContext(),"서버와의 연결이 끊겼습니다",Toast.LENGTH_SHORT).show();

            }
        });
    }




    //생성자 생성
    public ReportDialog(Context context, Integer memeIdx) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.memeidx=memeIdx;

    }




}