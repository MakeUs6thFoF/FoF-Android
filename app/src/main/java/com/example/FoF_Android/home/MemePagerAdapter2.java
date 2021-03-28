package com.example.FoF_Android.home;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
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
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.model.Like;
import com.example.FoF_Android.dialog.DeleteDialog;
import com.example.FoF_Android.dialog.ModifyDialog;
import com.example.FoF_Android.dialog.SelectDialog;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.home.model.MemeCase;
import com.example.FoF_Android.signup.SignUp;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static com.example.FoF_Android.home.model.MemeCase.SMALL;

public class MemePagerAdapter2 extends RecyclerView.Adapter<MemePagerAdapter2.ViewHolder> {
    private Context context;
    private List<Meme.Data> items;
    private DeleteDialog deleteDialog;
    private SelectDialog selectDialog;
    private ModifyDialog modifyDialog;
    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mNegativeListener;



    ImageButton copy,send;
    FrameLayout report;
    ToggleButton like_btn;
    TokenManager gettoken;
    String token;
    Integer useridx;

    private MemePagerAdapter.OnItemClickListener mListener = null;
    private MemePagerAdapter.OnTouchListener gestureListener = null;

    public interface OnItemClickListener{
        void onItemClick(View v, String position);
    }
    public interface OnTouchListener{
        boolean onTouch(View v, Integer position, MotionEvent event);
    }
    private OnItemClick mCallback;

    public MemePagerAdapter2(Context context,Integer UserIdx, List<Meme.Data> items,  OnItemClick listener)
    {
        this.mCallback = listener;
        this.context = context;
        this.items = items;
        this.useridx=UserIdx;
    }
    public void setOnItemClickListener(MemePagerAdapter.OnItemClickListener listener) {this.mListener = listener;}
    public void setOnTouchListener(MemePagerAdapter.OnTouchListener gestureListener) {this.gestureListener = gestureListener;}

    @Override
    public MemePagerAdapter2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meme_rec_item, viewGroup, false);
        return new MemePagerAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemePagerAdapter2.ViewHolder viewHolder, int position) {
        newbutton(viewHolder,position);
        Glide.with(context)
                .load(items.get(position).getImageUrl())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.placeholder)
                .into( viewHolder.memeimg);

        viewHolder.copyright.setText(items.get(position).getCopyright());
        viewHolder.copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyDialog = new ModifyDialog(context,items.get(position).getCopyright(),items.get(position).getMemeIdx()); // 왼쪽 버튼 이벤트
                calldialog(modifyDialog);
            }
        });
        viewHolder.nick.setText(items.get(position).getNickname());
        Glide.with(context)
                .load(items.get(position).getProfileImage())
                .placeholder(R.drawable.logo_big2)
                .into( viewHolder.profileimg);

        onclickbtn(viewHolder,position);

        likebtnclick(viewHolder,position);

        selectbtnclick(viewHolder,position);
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
                deleteDialog.dismiss();
                deletememe(position);
                notifyDataSetChanged();
                notifyItemRemoved(position);
            }
        };

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView memeimg;
        private CircleImageView profileimg;
        private TextView nick;
        private TextView copyright;

        LinearLayout Tag2;
        LinearLayout Tag;
        ImageButton copy,send;
        FrameLayout report;
        ToggleButton like_btn;


        public ViewHolder(View view) {
            super(view);

            nick = (TextView) view.findViewById(R.id.nick);
            memeimg = (ImageView) view.findViewById(R.id.imageView);
            profileimg = (CircleImageView) view.findViewById(R.id.imageView2);
            send=(ImageButton) view.findViewById(R.id.send);
            copy=(ImageButton) view.findViewById(R.id.copy);
            report=(FrameLayout) view.findViewById(R.id.report);
            like_btn=(ToggleButton) view.findViewById(R.id.like);

            copyright=(TextView) view.findViewById(R.id.copyright);
            Tag= (LinearLayout)view.findViewById(R.id.Tag);
            Tag2= (LinearLayout)view.findViewById(R.id.Tag2);

        }
        }
    public void newbutton(MemePagerAdapter2.ViewHolder viewHolder,int position){
        float factor = context.getResources().getDisplayMetrics().density;
        int pixelw = (int) (66 * factor + 0.5f);
        int pixelh = (int) (26 * factor + 0.5f);
        int pixelb = (int) (4 * factor + 0.5f);

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
                btn[i].setPadding(0,pixelb,0,0);
                btn[i].setTextAppearance(R.style.basic_12dp_black);
                btn[i].setId(i);
                if (i < 4) {
                    viewHolder.Tag.addView(btn[i]);
                }else  viewHolder.Tag2.addView(btn[i]);
                int finalI1 = i;
                btn[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mListener != null)
                            mListener.onItemClick(v, array[finalI1]);
                    }
                });

            }

        }

    }

    public Meme.Data getItem(int position){
            return items.get(position);
        }
        public void onclickbtn(MemePagerAdapter2.ViewHolder viewHolder,int position){
            viewHolder.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);

                    Uri myUri = Uri.parse(items.get(position).getImageUrl());

                    ClipData clip = ClipData.newUri(context.getContentResolver(),"URI" ,myUri);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(context, "복사하였습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    public void selectbtnclick(MemePagerAdapter2.ViewHolder viewHolder,int position){
        viewHolder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDialog = new SelectDialog(context,items.get(position).getUserIdx(),items.get(position).getMemeIdx(),mPositiveListener); // 왼쪽 버튼 이벤트
                calldialog(selectDialog);
            }
        });
    }
    public void calldialog(Dialog reportDialog){
        reportDialog.setCancelable(true);
        reportDialog.getWindow().setGravity(Gravity.CENTER);
        reportDialog.show();
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
    public void likebtnclick(MemePagerAdapter2.ViewHolder viewHolder,int position){
        gettoken=new TokenManager(context);
        HttpClient client=new HttpClient();
        RetrofitApi api = client.getRetrofit().create(RetrofitApi.class);
        token = gettoken.checklogin(context);
        viewHolder.like_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

}


