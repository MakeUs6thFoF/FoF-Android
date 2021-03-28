package com.example.FoF_Android.detail;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.Target;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.model.Detail;
import com.example.FoF_Android.detail.model.Like;
import com.example.FoF_Android.detail.model.Similar;
import com.example.FoF_Android.dialog.ModifyDialog;
import com.example.FoF_Android.home.HashClickFragment;
import com.example.FoF_Android.home.MemeAllAdapter;
import com.example.FoF_Android.home.OnBackPressed;
import com.example.FoF_Android.dialog.DeleteDialog;
import com.example.FoF_Android.dialog.SelectDialog;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.make.UploadNextFragment;
import com.example.FoF_Android.signup.SignUp;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class DetailFragment extends Fragment implements OnBackPressed {
    RetrofitApi api;
    RecyclerView similar;
    private ModifyDialog modifyDialog;
    private SelectDialog selectDialog;
    private DeleteDialog deleteDialog;
    List<Detail.Data.Similar> items;
    Detail.Data.memeDetail detail;
    ImageView memeimg;
    TextView title, copyright;
    FrameLayout report, back;
    LinearLayout Tag,Tag2;
    ImageButton copy,send;
    ToggleButton like_btn;
    TokenManager gettoken;
    String token;
    Integer position=0;
    Uri myurl=null;
    private Integer i=0;

    String[] array;
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
        btnclick(i);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Bitmap drawable=null;
                GifDrawable drawable1=null;
                //imgBitmap=getImage(items.get(position).getImageUrl());
                if(memeimg.getDrawable() instanceof GlideBitmapDrawable){
                    drawable = ((GlideBitmapDrawable)memeimg.getDrawable()).getBitmap();}
                else if(memeimg.getDrawable() instanceof GifDrawable)
                    drawable1 = ((GifDrawable)memeimg.getDrawable());



                if(drawable1!=null){

                    // myurl=getImageUri(context,);
                } else  myurl=getImageUri(getContext(), drawable);
                //  String name=saveBitmapToJpeg(context,drawable,"임시");

                //Uri screenshotUri = Uri.parse(name);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, myurl);
                getContext().startActivity(Intent.createChooser(sharingIntent, "Share image using"));
            }
        });


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(items.get(position).getImageUrl()));
                ClipboardManager clipboard = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newIntent("Intent",appIntent);
                clipboard.setPrimaryClip(clip);
                Bitmap drawable=null;
                drawable = ((GlideBitmapDrawable)memeimg.getDrawable()).getBitmap();
                Toast.makeText(getContext(), "이미지를 저장하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        onclickbtn(i);
        return view;
    }


    public void initUI(ViewGroup view){
        memeimg = (ImageView) view.findViewById(R.id.imageView);
        title = (TextView) view.findViewById(R.id.title);
        copyright=(TextView)view.findViewById(R.id.copyright);
        similar=view.findViewById(R.id.similar);
        report=(FrameLayout) view.findViewById(R.id.report);
        like_btn=(ToggleButton) view.findViewById(R.id.like);
        copy=(ImageButton)view.findViewById(R.id.copy);
        send=(ImageButton)view.findViewById(R.id.send);
        Tag= (LinearLayout)view.findViewById(R.id.Tag);
        Tag2= (LinearLayout)view.findViewById(R.id.Tag2);
        back=(FrameLayout)view.findViewById(R.id.back);
    }

    public void calldialog(Dialog reportDialog){
        // 오른쪽 버튼 이벤트

        //요청 이 다이어로그를 종료할 수 있게 지정함
        reportDialog.setCancelable(true);
        reportDialog.getWindow().setGravity(Gravity.CENTER);
        reportDialog.show();
    }

    public void onclick(){
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyDialog = new ModifyDialog(getContext(),detail.getCopyright(),detail.getMemeIdx()); // 왼쪽 버튼 이벤트
                calldialog(modifyDialog);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDialog = new SelectDialog(getContext(),detail.getUserIdx(),detail.getMemeIdx(),mPositiveListener); // 왼쪽 버튼 이벤트
                calldialog(selectDialog);
            }
        });


    }

  public void onclickbtn(int position){


    }
    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void setUI(){

       // nick.setText(detail.getNickname());

        Glide.with(getContext())
                .load(detail.getImageUrl())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).placeholder(R.drawable.placeholder)
                .into(memeimg);

        title.setText(detail.getMemeTitle());
        copyright.setText(detail.getCopyright());
        if(detail.getLikeStatus()==1)like_btn.setChecked(true);
        TextView btn[] = new TextView[30];
        String hashtag=detail.getTag();

        if(hashtag!=null) {array = hashtag.split(",");


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
            btn[i].setPadding(0,8,0,0);
            btn[i].setTextAppearance(R.style.basic_12dp_black);
            btn[i].setId(i);
            if (i < 4) {
                Tag.addView(btn[i]);
            }else Tag2.addView(btn[i]);
            int finalI = i;
            int finalI1 = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position=finalI;
                    newhash();
                }
            });
          }}


    }
public void newhash(){
    HashClickFragment hashclick= HashClickFragment.newInstance(array[position]);
    hashclick.setEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.slide_right).setDuration(200));
    getFragmentManager().beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.container, hashclick).commit();

}

    public void similarUI(View view, int i) {
        HttpClient client = new HttpClient();
         api = client.getRetrofit().create(RetrofitApi.class);
        TokenManager gettoken = new TokenManager(getContext());
        String token = gettoken.checklogin(getContext());
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
                    SimilarAdapter adaptersim = new SimilarAdapter(getContext(), items);
                    adaptersim.setOnItemClickListener(new SimilarAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {

                            DetailFragment detail = new DetailFragment(position);

                            detail.setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.image_shared_element_transition).setDuration(100));
                            detail.setEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.fade).setDuration(50));

                            getFragmentManager().beginTransaction().addSharedElement(v.findViewById(R.id.imageView), ViewCompat.getTransitionName(v.findViewById(R.id.imageView)))
                                    .setReorderingAllowed(true)
                                    .addToBackStack(null).add(R.id.container, detail).commit();
                        }
                    });
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
        Log.d("ChildFragment", "onDestroy");
        super.onDestroy();
    }



    public void btnclick( int position){
        gettoken=new TokenManager(getContext());
        HttpClient client=new HttpClient();
        api = client.getRetrofit().create(RetrofitApi.class);
        token = gettoken.checklogin(getContext());

        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    api.postLike(token,position).enqueue(new Callback<Like>() {
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
                    });
                }

            });
            }



    private View.OnClickListener mPositiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            selectDialog.dismiss();
            deleteDialog= new DeleteDialog(getContext(),detail.getMemeIdx(),mNegativeListener);
            deleteDialog.setCancelable(true);
            deleteDialog.getWindow().setGravity(Gravity.CENTER);
            deleteDialog.show();
        }
    };


    private View.OnClickListener mNegativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            deletememe(api);
            deleteDialog.dismiss();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(DetailFragment.this).commit();
            fragmentManager.popBackStack();
            Toast.makeText(getContext(),"삭제되었습니다." , Toast.LENGTH_LONG).show();
        }
    };

    public void deletememe(RetrofitApi api){
        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken=new TokenManager(getContext());
        String token=gettoken.checklogin(getContext());

        api.deleteMeme(token,detail.getMemeIdx()).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                SignUp report = response.body();

                Log.i("TAG", "onResponse: " + report.getMessage());
            }
            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                Toast.makeText(getContext(),"서버와의 연결이 끊겼습니다",Toast.LENGTH_SHORT).show();

            }
        });
    }
}