package com.FoF.FoF_Android.make;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.FoF.FoF_Android.Category.Category;
import com.FoF.FoF_Android.HttpClient;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;
import com.FoF.FoF_Android.dialog.LogoutDialog;
import com.FoF.FoF_Android.dialog.UploadSuccessDialog;
import com.FoF.FoF_Android.dialog.model.CancelDialog;
import com.FoF.FoF_Android.signup.SignUp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadNextFragment extends Fragment {
    public static final int RESULT_CODE = 12;
    String titles[] = new String[7];
    int titleIdx[] = new int[7];
    private int SELECT_FILE = 3;
    EditText hashtag;
    EditText title;
    EditText copyright;
    CognitoCachingCredentialsProvider credentialsProvider;
    AmazonS3 s3;
    LogoutDialog logoutDialog;
    TransferUtility transferUtility;
    CancelDialog canceldialog;
    Integer useridx;
    String token;
    TokenManager gettoken;
    FrameLayout back;
    //카테고리
    RadioButton togBt1;
    RadioButton togBt2;
    RadioButton togBt3;
    RadioButton togBt4;
    RadioButton togBt5;
    RadioButton togBt6;
    RadioButton togBt7;
    RetrofitApi api;
    MemeUpload meme;
    File f;
    String gethashtag;
    private RadioGroup line1;
    private RadioGroup line2;
    TextView next;

    HashMap<String, Integer> titleHash = new HashMap<String, Integer>();

    public UploadNextFragment(File f) {
        this.f=f;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_upload_next, container, false);
        togBt1 = view.findViewById(R.id.button1);
        togBt2 = view.findViewById(R.id.button2);
        togBt3 = view.findViewById(R.id.button3);
        togBt4 = view.findViewById(R.id.button4);
        togBt5 = view.findViewById(R.id.button5);
        togBt6 = view.findViewById(R.id.button6);
        togBt7 = view.findViewById(R.id.button7);
        title=view.findViewById(R.id.title);
        copyright=view.findViewById(R.id.copyright);
        hashtag=view.findViewById(R.id.hashtag);

        HttpClient client = new HttpClient();
        api = client.getRetrofit().create(RetrofitApi.class);
        getCategory(api);

        gettoken = new TokenManager(getContext());
        token = gettoken.checklogin(getContext());
        useridx=gettoken.checkIdx(getContext());

        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog = new LogoutDialog(1, getContext(), mnegativtlistenr);
                logoutDialog.setCancelable(true);
                logoutDialog.getWindow().setGravity(Gravity.CENTER);

                logoutDialog.show();
            }
        });
        line1=view.findViewById(R.id.line1);
        line2=view.findViewById(R.id.line2);

        line1.clearCheck();
        line1.setOnCheckedChangeListener(listener1);

        line2.clearCheck();
        line2.setOnCheckedChangeListener(listener2);
        hashtag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                Intent hashintent = new Intent(getActivity(), UploadHashtag.class);

                startActivityForResult(hashintent, SELECT_FILE);
            }}
        });


        setUpload(view);
    return view;
}

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                line2.setOnCheckedChangeListener(null);
                line2.clearCheck();
                line2.setOnCheckedChangeListener(listener2);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                line1.setOnCheckedChangeListener(null);
                line1.clearCheck();
                line1.setOnCheckedChangeListener(listener1);
            }
        }
    };
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
                togBt1.setText(titles[0]);
                togBt2.setText(titles[1]);
                togBt3.setText(titles[2]);
                togBt4.setText(titles[3]);
                togBt5.setText(titles[4]);
                togBt6.setText(titles[5]);
                togBt7.setText(titles[6]);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(getContext(),"서버와의 연결이 끊겼습니다",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String[] getInsertTag(){
        String hashstring=hashtag.getText().toString().replace(" ","");
        String[] temp = hashstring.split("#");
        if(temp.length > 0) {
            String[] result = new String[temp.length - 1];
            for (int i = 0; i < result.length; i++) {
                result[i] = temp[i + 1].trim();
            }
            return result;
        }else{
            return temp;
        }
    }
    public void setUpload(ViewGroup view){
        next=view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {


            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                String copytxt=copyright.getText().toString();
                String titletxt=title.getText().toString();
                String[] hashTagArray=null;

                String imgurl="https://fofuploadtest.s3.ap-northeast-2.amazonaws.com/"+useridx.toString()+f.getName();

                if(getInsertTag()!=null)
                    hashTagArray = getInsertTag();

                String selectedResult ="";
                Integer categoryIdx=0;
                if (line1.getCheckedRadioButtonId() >0 ){
                    View radioButton = line1.findViewById(line1.getCheckedRadioButtonId());
                    int radioId = line1.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) line1.getChildAt(radioId);
                    selectedResult = (String) btn.getText();
                    categoryIdx=titleHash.get(selectedResult);

                } else if (line2.getCheckedRadioButtonId() > 0 ){
                    View radioButton = line2.findViewById(line2.getCheckedRadioButtonId());
                    int radioId = line2.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) line2.getChildAt(radioId);
                    selectedResult = (String) btn.getText();
                    categoryIdx=titleHash.get(selectedResult);
                }

                List listData = new ArrayList();
                List<String> tmpList = new ArrayList<>();
                for(String s : hashTagArray)
                    tmpList.add(s);

                Log.i("Uploadnext", tmpList.toString());
                if (titletxt==null)Toast.makeText(getContext(), "제목을 적어주세요", Toast.LENGTH_LONG).show();
                else if (copytxt==null)Toast.makeText(getContext(), "저작권자를 적어주세요", Toast.LENGTH_LONG).show();
                else if(tmpList.size()<2)Toast.makeText(getContext(), "해시태그값은 최소 두개 이상 적어주세요", Toast.LENGTH_LONG).show();
                else if(categoryIdx==null) Toast.makeText(getContext(), "카테고리를 선택해주세요", Toast.LENGTH_LONG).show();
                else doPost(titletxt,tmpList,imgurl,copytxt,categoryIdx,api);

            }

        });

    }
    public void uploadImg(){
        credentialsProvider = new CognitoCachingCredentialsProvider(
                getContext(),
                "ap-northeast-2:e0ea2090-8c8e-4b43-a765-348b4fb30098", // 자격 증명 풀 ID
                Regions.AP_NORTHEAST_2 // 리전
        );
        s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, getContext());
        s3.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
        s3.setEndpoint("s3.ap-northeast-2.amazonaws.com");

        transferUtility = new TransferUtility(s3, getContext());

        TransferObserver observer = transferUtility.upload("fofuploadtest",useridx.toString()+f.getName(),f);

    }


    public void doPost(String title, List<String> hashtag, String imageUrl, String copyright, Integer categoryIdx, RetrofitApi api){
        HttpClient client = new HttpClient();
        api = client.getRetrofit().create(RetrofitApi.class);
        List<String> test =new ArrayList<String>();
        uploadImg();
        api.postMeme(token, title,imageUrl,copyright,hashtag,categoryIdx).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                if (response.isSuccessful()){
                    SignUp signup = response.body();
                    System.out.println("확인"+signup.getCode()+signup.getMessage());
                    if(signup.getCode()!=200)Toast.makeText(getContext(), signup.getMessage(), Toast.LENGTH_LONG).show();
                    else {
                     //   Toast.makeText(getContext(),"업로드 성공" , Toast.LENGTH_LONG).show();
                        UploadSuccessDialog successDialog=new UploadSuccessDialog(getContext());
                        successDialog.setCancelable(true);
                        successDialog.getWindow().setGravity(Gravity.CENTER);
                        successDialog.show();

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container,new UploadFragment()).commit();
                        //

                    }
                }
                else
       ;
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
            //    Toast.makeText(getContext(),signup.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3 && resultCode == RESULT_CODE) {
            String testResult = data.getStringExtra("tagname");
            hashtag.setText(testResult);
        }


    }
    public View.OnClickListener mnegativtlistenr=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Drawable drawable = getResources().getDrawable(R.drawable.upload_bg);
            logoutDialog.dismiss();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(UploadNextFragment.this).commit();
            fragmentManager.popBackStack();
        }
    };
}
