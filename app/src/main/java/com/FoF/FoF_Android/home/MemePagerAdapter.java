package com.FoF.FoF_Android.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;

import com.FoF.FoF_Android.make.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifencoder.AnimatedGifEncoder;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.Target;
import com.FoF.FoF_Android.HttpClient;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;
import com.FoF.FoF_Android.detail.model.Like;
import com.FoF.FoF_Android.dialog.DeleteDialog;
import com.FoF.FoF_Android.dialog.ModifyCopyrightActivity;
import com.FoF.FoF_Android.dialog.ModifyDialog;
import com.FoF.FoF_Android.dialog.SelectDialog;
import com.FoF.FoF_Android.home.model.Meme;

import com.FoF.FoF_Android.signup.SignUp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class MemePagerAdapter extends PagerAdapter {
    private static Context context;

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
        int pixelw = (int) (16 * factor + 0.5f);
        int pixelh = (int) (26 * factor + 0.5f);
        int pixelb = (int) (4 * factor + 0.5f);
        int pixelp=(int) (12 * factor + 0.5f);

        LinearLayout Tag= (LinearLayout)view.findViewById(R.id.Tag);
        LinearLayout Tag2= (LinearLayout)view.findViewById(R.id.Tag2);
        TextView btn[] = new TextView[30];

        String hashtag=items.get(position).getTag();
        if(hashtag!=null){
        String[] array = hashtag.split(",");


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
     //   params.width=pixelw;
        params.height=pixelh;
        params.rightMargin=pixelb;
        params.gravity=Gravity.CENTER;
        for (int i = 0; i < array.length; i++) {
            btn[i] = new TextView(context);
            btn[i].setLayoutParams(params);
            btn[i].setText(array[i]);
            btn[i].setTextAlignment(TEXT_ALIGNMENT_CENTER);
            btn[i].setBackgroundResource(R.color.white);
            btn[i].setIncludeFontPadding(false);
            btn[i].setPadding(pixelw,pixelb,pixelw,0);
            if(array[i].length()>4)    btn[i].setPadding(pixelb*2,pixelb,pixelb*2,0);
            else if(array[i].length()>3)    btn[i].setPadding(pixelp,pixelb,pixelp,0);
            btn[i].setTextAppearance(R.style.basic_12dp_black);
            btn[i].setId(i);
            if (i < 4) {
                if(array[i].length()>7) Tag2.addView(btn[i]);
                else Tag.addView(btn[i]);
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
                GifDecoder drawable1=null;
                if(memeimg.getDrawable() instanceof GlideBitmapDrawable){
                    drawable = ((GlideBitmapDrawable)memeimg.getDrawable()).getBitmap(); }
                else if(memeimg.getDrawable() instanceof GifDrawable)
                    drawable1 = ((GifDrawable)memeimg.getDrawable()).getDecoder();
                Uri myurl=null;
                if(drawable1!=null){
                    Toast.makeText(context, "이미지 전송을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    //TODO GIF처리
                } else { myurl= saveBitmaptoJpeg(drawable,"FOF","download"+items.get(position).getMemeIdx());

                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, myurl);
                context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                //context.getContentResolver().delete(myurl,null,null);
            }}
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.checkPermission(context);
                Bitmap drawable=null;
                GifDrawable drawable1=null;

                if(memeimg.getDrawable() instanceof GlideBitmapDrawable){
                    drawable = ((GlideBitmapDrawable)memeimg.getDrawable()).getBitmap(); }
                else if(memeimg.getDrawable() instanceof GifDrawable)
                    drawable1 = ((GifDrawable)memeimg.getDrawable());
                if(drawable1!=null){
                    //TODO GIF처리
                    Toast.makeText(context, "이미지 저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    //ByteBuffer byteBuffer=drawable1.buffer();
                } else{ drawable = ((GlideBitmapDrawable)memeimg.getDrawable()).getBitmap();
                saveImage(drawable,"download"+items.get(position).getMemeIdx());

                Toast.makeText(context, "이미지를 저장하였습니다.", Toast.LENGTH_SHORT).show();}
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
    public static String saveImage(Bitmap bitmap, String name){
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath(); // Get Absolute Path in External Sdcard
        String folder_name = "/FoF/";
        String file_name = name+".jpg";
        String string_path = ex_storage+folder_name;
        File file_path;
        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            } FileOutputStream out = new FileOutputStream(string_path+file_name);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); out.close();
        }catch(FileNotFoundException exception){ Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }return string_path+file_name;
    }

    public void calldialog(Dialog reportDialog){
        reportDialog.setCancelable(true);
        reportDialog.getWindow().setGravity(Gravity.CENTER);

        reportDialog.show();
    }

    public static Uri saveBitmaptoJpeg(Bitmap bitmap,String folder, String name){
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context, "com.mydomain.fileprovider", file);

        } catch (IOException e) {
            Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
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
