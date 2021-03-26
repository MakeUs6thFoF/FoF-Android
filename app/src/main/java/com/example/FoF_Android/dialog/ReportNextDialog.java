package com.example.FoF_Android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.dialog.model.Report;
import com.example.FoF_Android.signup.SignUp;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportNextDialog extends Dialog {

    private Button reportbutton;
    private ImageButton mNegativeButton;
    String report_name;
    Integer reportIdx;
    Integer memeIdx;
    private View.OnClickListener mModifyListener;
    private View.OnClickListener mNegativeListener;


    RetrofitApi api;
    TokenManager gettoken;

    String titles[] = new String[7];
    int titleIdx[] = new int[7];
    HashMap<String, Integer> titleHash = new HashMap<String, Integer>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_report_next);
        TextView reportbtn = (TextView) findViewById(R.id.report_name);
        reportbtn.setText(report_name);


        //셋팅
        reportbutton = (Button) findViewById(R.id.report_btn);
        mNegativeButton = (ImageButton) findViewById(R.id.cancel);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)

        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken = new TokenManager(getContext());


        List<String> buttonList = new ArrayList<String>();

        reportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient client=new HttpClient();
                api = client.getRetrofit().create(RetrofitApi.class);
                postReport(api);
            }
        });


        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
    }

    public void postReport(RetrofitApi api){
        Log.i("report","postReport 시작");
        String token = gettoken.checklogin(getContext());
        Log.i("Report",memeIdx.toString()+reportIdx.toString());
        api.postReport(token,memeIdx,reportIdx).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                if (response.isSuccessful()) {
                    SignUp signUp = response.body();
                    System.out.println("포스트확인2" + signUp.getCode());
                    dismiss();
                }
            }
            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                System.out.println("연결 실패");
            }
        });
    }


    //생성자 생성
    public ReportNextDialog(Context context, Integer memeIdx, String report_name, Integer reportIdx) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.reportIdx=reportIdx;
        this.report_name=report_name;
        this.memeIdx=memeIdx;

    }




}