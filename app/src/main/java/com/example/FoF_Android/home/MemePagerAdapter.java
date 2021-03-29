package com.example.FoF_Android.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.Target;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.model.Like;
import com.example.FoF_Android.dialog.DeleteDialog;
import com.example.FoF_Android.dialog.ModifyCopyrightActivity;
import com.example.FoF_Android.dialog.ModifyDialog;
import com.example.FoF_Android.dialog.ReportDialog;
import com.example.FoF_Android.dialog.SelectDialog;
import com.example.FoF_Android.home.model.Meme;

import com.example.FoF_Android.signup.SignUp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class MemePagerAdapter extends PagerAdapter {
    private Context context;
    private List<Meme.Data> items;
    private DeleteDialog deleteDialog;
    private SelectDialog selectDialog;
    private ModifyDialog modifyDialog;
    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mNegativeListener;
    Integer thisposition;
    private long baseId = 0;
    private ArrayList<View> views = new ArrayList<View>();
    ImageButton copy,send;
    FrameLayout report;
    ToggleButton like_btn;
    TokenManager gettoken;
    String token;
    Integer useridx;
    View.OnClickListener mModifyListener;

    private MemePagerAdapter.OnItemClickListener mListener = null;
    private MemePagerAdapter.OnTouchListener gestureListener = null;

    public interface OnItemClickListener{
        void onItemClick(View v, String position);
    }
    public interface OnTouchListener{
        boolean onTouch(View v, Integer position, MotionEvent event);
    }
    private OnItemClick mCallback;

    public void setOnItemClickListener(MemePagerAdapter.OnItemClickListener listener) {this.mListener = listener;}
    public void setOnTouchListener(MemePagerAdapter.OnTouchListener gestureListener) {this.gestureListener = gestureListener;}

    public MemePagerAdapter(Context context,Integer UserIdx, List<Meme.Data> items,  OnItemClick listener)
    {
        this.mCallback = listener;
        this.context = context;
        this.items = items;
        this.useridx=UserIdx;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.meme_rec_item, null);


        float factor = context.getResources().getDisplayMetrics().density;
        Log.i("HomeFragmet","useridx="+useridx+"memeuseridx="+items.get(position).getUserIdx());
        int pixelw = (int) (66 * factor + 0.5f);
        int pixelh = (int) (26 * factor + 0.5f);
        int pixelb = (int) (4 * factor + 0.5f);
        LinearLayout Tag= (LinearLayout)view.findViewById(R.id.Tag);
        LinearLayout Tag2= (LinearLayout)view.findViewById(R.id.Tag2);
        TextView btn[] = new TextView[30];

        String hashtag=items.get(position).getTag();
        if(hashtag!=null){
        String[] array = hashtag.split(",");


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pixelw, LayoutParams.MATCH_PARENT);
        params.width=pixelw;
        params.height=pixelh;
        params.rightMargin=4;
        params.gravity=Gravity.CENTER;
        for (int i = 0; i < array.length; i++) {
            btn[i] = new TextView(context);
            btn[i].setLayoutParams(params);
            btn[i].setText(array[i]);
            btn[i].setTextAlignment(TEXT_ALIGNMENT_CENTER);
            btn[i].setBackgroundResource(R.color.white);
            btn[i].setIncludeFontPadding(false);
            btn[i].setPadding(0,pixelb,0,0);
            btn[i].setTextAppearance(R.style.basic_12dp_black);
            btn[i].setId(i);
            if (i < 4) {
                Tag.addView(btn[i]);
            }else Tag2.addView(btn[i]);
            int finalI1 = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null)
                        if(finalI1!=0) mListener.onItemClick(v, array[finalI1]);
                        else {
                            String category=array[finalI1].replaceFirst("#","");
                            mListener.onItemClick(v, category);
                        }
                }
            });

        } }
        send=(ImageButton) view.findViewById(R.id.send);
        copy=(ImageButton) view.findViewById(R.id.copy);
        report=(FrameLayout) view.findViewById(R.id.report);
        like_btn=(ToggleButton) view.findViewById(R.id.like);
        TextView nick = (TextView) view.findViewById(R.id.nick);
        ImageView memeimg = (ImageView) view.findViewById(R.id.imageView);
        CircleImageView  profileimg = (CircleImageView) view.findViewById(R.id.imageView2);
        TextView copyright=(TextView) view.findViewById(R.id.copyright);

        Glide.with(context)
                .load(items.get(position).getImageUrl())
                .error(R.drawable.placeholder)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(memeimg);

        copyright.setText(items.get(position).getCopyright());
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // modifyDialog = new ModifyDialog(context,items.get(position).getCopyright(),items.get(position).getMemeIdx(),mModifyListener); // 왼쪽 버튼 이벤트
                //calldialog(modifyDialog);
            }
        });

        mModifyListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent modifyintent=new Intent(context, ModifyCopyrightActivity.class);

                modifyintent.putExtra("memeIdx",items.get(position).getMemeIdx());
                context.startActivity(modifyintent);
                // Intent intent = new Intent(    , Register.class);

                modifyDialog.dismiss();
            }
        };
        nick.setText(items.get(position).getNickname());
        Glide.with(context)
                .load(items.get(position).getProfileImage())
                .error(R.drawable.logo_big2)
                .into(profileimg);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Bitmap drawable=null;
                GifDrawable drawable1=null;
                if(memeimg.getDrawable() instanceof GlideBitmapDrawable){
                    drawable = ((GlideBitmapDrawable)memeimg.getDrawable()).getBitmap();}
                else if(memeimg.getDrawable() instanceof GifDrawable)
                    drawable1 = ((GifDrawable)memeimg.getDrawable());

                Uri myurl=null;

                if(drawable1!=null){
                } else  myurl=getImageUri(context, drawable);
                //  String name=saveBitmapToJpeg(context,drawable,"임시");

                //Uri screenshotUri = Uri.parse(name);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, myurl);
                context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                //context.getContentResolver().delete(myurl,null,null);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Bitmap drawable = ((GlideBitmapDrawable)memeimg.getDrawable()).getBitmap();
            //    Uri myurl=getImageUri(context, drawable);
                Uri copyuri = Uri.parse(items.get(position).getImageUrl());

                //  String name=saveBitmapToJpeg(context,drawable,"임시");


                Toast.makeText(context, "이미지를 저장하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);

        likebtnclick(position);
        selectbtnclick(position);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return gestureListener.onTouch(v, position,event);
            }
        });


        return view;

    }


    public void calldialog(Dialog reportDialog){
        reportDialog.setCancelable(true);
        reportDialog.getWindow().setGravity(Gravity.CENTER);

        reportDialog.show();
    }


    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap getImage(String strUrl){
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        Bitmap bm = null;


        try {
            url = new URL(strUrl);
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);

            conn.connect();

            is = conn.getInputStream();
            bis = new BufferedInputStream(is);

            bm = BitmapFactory.decodeStream(bis);

        } catch (Exception e) {
            //throw e;
            Log.i("meme", e.toString());
            return null;
        }finally{
            try{
                if(is != null) is.close();
                if(bis != null) bis.close();
            }catch(Exception e){}
        }

        return bm;
    }



    public static String saveBitmapToJpeg(Context context,Bitmap bitmap, String name){
        File storage = context.getCacheDir(); // 이 부분이 임시파일 저장 경로
        String fileName = name + ".jpg";  // 파일이름은 마음대로!
        File tempFile = new File(storage,fileName);
        try{
            tempFile.createNewFile();  // 파일을 생성해주고
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90 , out);  // 넘거 받은 bitmap을 jpeg(손실압축)으로 저장해줌
            out.close(); // 마무리로 닫아줍니다.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile.getAbsolutePath();   // 임시파일 저장경로를 리턴해주면 끝!
    }


    public void selectbtnclick(int position){
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thisposition=position;
                selectDialog = new SelectDialog(context,items.get(position).getUserIdx(),items.get(position).getMemeIdx(),mPositiveListener); // 왼쪽 버튼 이벤트
                calldialog(selectDialog);
            }
        });
    }


    public void likebtnclick(int position){
        gettoken=new TokenManager(context);
        HttpClient client=new HttpClient();
        RetrofitApi api = client.getRetrofit().create(RetrofitApi.class);
        token = gettoken.checklogin(context);
        like_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    api.postLike(token,items.get(position).getMemeIdx()).enqueue(new Callback<Like>() {
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
        mPositiveListener = new View.OnClickListener() {
            public void onClick(View v) {
                selectDialog.dismiss();
                deleteDialog= new DeleteDialog(context,items.get(position).getMemeIdx(),mNegativeListener);
                deleteDialog.setCancelable(true);
                deleteDialog.getWindow().setGravity(Gravity.CENTER);
                deleteDialog.show();
            }
        };
        mNegativeListener = new View.OnClickListener() {
            public void onClick(View v) {
               // Log.i("test",items.get(position).getMemeIdx().toString());
                deleteDialog.dismiss();
                deletememe(thisposition);
                boolean remove = items.remove(thisposition);

                Log.i("Mane"," "+remove);
                Toast.makeText(context,"삭제되었습니다." , Toast.LENGTH_LONG).show();
                notifyDataSetChanged();

            }
        };

    }
    public void deletememe( int position){
        RetrofitApi api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken=new TokenManager(context);
        String token=gettoken.checklogin(context);

        api.deleteMeme(token,items.get(position).getMemeIdx()).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                SignUp report = response.body();

                Log.i("TAG", "onResponse: " + report.getMessage());
            }
            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                Toast.makeText(context,"서버와의 연결이 끊겼습니다",Toast.LENGTH_SHORT).show();

            }
        });
    }

    //this is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition (Object object)
    {
        int index = views.indexOf (object);
        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }




    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (View)o);
    }

}
