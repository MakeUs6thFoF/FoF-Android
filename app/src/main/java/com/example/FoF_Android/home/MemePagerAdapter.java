package com.example.FoF_Android.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.FoF_Android.search.HashClickFragment;
import com.example.FoF_Android.search.HashTag;
import com.example.FoF_Android.search.HashTagAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class MemePagerAdapter extends PagerAdapter {
    private Context context;
    private List<Meme.Data> items;
    private SelectDialog selectDialog;
    private ModifyDialog modifyDialog;
    private GestureDetector gestureDetector = null;
    ImageButton copy,send;
    FrameLayout report;
    ToggleButton like_btn;
    TokenManager gettoken;
    String token;
    Integer useridx;
    ScrollView sv_scrollview;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    View.OnTouchListener gestureListener;

    private MemePagerAdapter.OnItemClickListener mListener = null;

    public interface OnItemClickListener{
        void onItemClick(View v, String position);
    }
    private OnItemClick mCallback;

    public void setOnItemClickListener(MemePagerAdapter.OnItemClickListener listener) {this.mListener = listener;}

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
        gestureDetector = new GestureDetector(context, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };



        float factor = context.getResources().getDisplayMetrics().density;
        Log.i("HomeFragmet","useridx="+useridx+"memeuseridx="+items.get(position).getUserIdx());
        int pixelw = (int) (66 * factor + 0.5f);
        int pixelh = (int) (26 * factor + 0.5f);
        LinearLayout Tag= (LinearLayout)view.findViewById(R.id.Tag);
        LinearLayout Tag2= (LinearLayout)view.findViewById(R.id.Tag2);
        TextView btn[] = new TextView[30];

            String hashtag=items.get(position).getTag();
            if(hashtag!=null){
        String[] array = hashtag.split(",");
        //     next= (View)view.findViewById(R.id.next);
        //   prev= (View)view.findViewById(R.id.prev);

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
            btn[i].setPadding(0,8,0,0);
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
                        mListener.onItemClick(v, array[finalI1]);
                    //Todo hashtag
                }
            });

        } }

        report=(FrameLayout) view.findViewById(R.id.report);
        like_btn=(ToggleButton) view.findViewById(R.id.like);
        TextView nick = (TextView) view.findViewById(R.id.nick);
        ImageView memeimg = (ImageView) view.findViewById(R.id.imageView);
        CircleImageView  profileimg = (CircleImageView) view.findViewById(R.id.imageView2);
        TextView copyright=(TextView) view.findViewById(R.id.copyright);

        Glide.with(context)
                .load(items.get(position).getImageUrl())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.meme2)
                .into(memeimg);

        copyright.setText(items.get(position).getCopyright());
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyDialog = new ModifyDialog(context,items.get(position).getCopyright(),items.get(position).getMemeIdx()); // 왼쪽 버튼 이벤트
                calldialog(modifyDialog);
            }
        });
        nick.setText(items.get(position).getNickname());
        Glide.with(context)
                .load(items.get(position).getProfileImage())
                .placeholder(R.drawable.logo_big2)
                .into(profileimg);
       // memeimg.setOnClickListener(MemePagerAdapter.this);


        container.addView(view);

        likebtnclick(position);
        selectbtnclick(position);

        view.setOnTouchListener(gestureListener);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        return view;

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

    public void calldialog(Dialog reportDialog){
        reportDialog.setCancelable(true);
        reportDialog.getWindow().setGravity(Gravity.CENTER);
        reportDialog.show();
    }

    public void selectbtnclick(int position){
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDialog = new SelectDialog(context,items.get(position).getUserIdx(),items.get(position).getMemeIdx()); // 왼쪽 버튼 이벤트
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
    }
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(context, "Left Swipe", Toast.LENGTH_SHORT).show();


                } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(context, "Right Swipe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}
