package com.example.FoF_Android.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.R;
import com.example.FoF_Android.home.dialog.DeleteDialog;
import com.example.FoF_Android.home.dialog.SelectDialog;
import com.example.FoF_Android.home.model.Meme;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class MemePagerAdapter extends PagerAdapter {
    private Context context;
    private List<Meme.Data> items;
    private SelectDialog reportDialog;
    private DeleteDialog deleteDialog;

    public MemePagerAdapter(Context context, List<Meme.Data> items)
    {
        this.context = context;
        this.items = items;
    }


    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.meme_item, null);

        String hashtag=items.get(position).getTag();

        String[] array = hashtag.split(",");

        float factor = context.getResources().getDisplayMetrics().density;

        int pixelw = (int) (66 * factor + 0.5f);
        int pixelh = (int) (26 * factor + 0.5f);

        LinearLayout Tag= (LinearLayout)view.findViewById(R.id.Tag);
        LinearLayout Tag2= (LinearLayout)view.findViewById(R.id.Tag2);
        TextView btn[] = new TextView[30];

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
            btn[i].setPadding(0,4,0,0);
            btn[i].setTextAppearance(R.style.basic_12dp_black);
            btn[i].setId(i);
            if (i < 4) {
                Tag.addView(btn[i]);
            }else Tag2.addView(btn[i]);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });}



        TextView nick = (TextView) view.findViewById(R.id.nick);
        ImageView memeimg = (ImageView) view.findViewById(R.id.imageView);
        CircleImageView  profileimg = (CircleImageView) view.findViewById(R.id.imageView2);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView copyright=(TextView) view.findViewById(R.id.copyright);

        copyright.setText(items.get(position).getCopyright());
        nick.setText(items.get(position).getNickname());
        Glide.with(context)
                .load(items.get(position).getProfileImage())
                .placeholder(R.drawable.meme2)
                .into(profileimg);

        Glide.with(context)
        .load(items.get(position).getImageUrl())
        .placeholder(R.drawable.meme2)
                .into(memeimg);

        ImageButton report=(ImageButton)view.findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportDialog = new SelectDialog(context,leftListener,cancellistener); // 왼쪽 버튼 이벤트
                calldialog(reportDialog);
            }
        });
        container.addView(view);

        return view;
    }
    public void calldialog(Dialog reportDialog){
        // 오른쪽 버튼 이벤트

        //요청 이 다이어로그를 종료할 수 있게 지정함
        reportDialog.setCancelable(true);
        reportDialog.getWindow().setGravity(Gravity.CENTER);
        reportDialog.show();
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



    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {

            reportDialog.dismiss();
            deleteDialog= new DeleteDialog(context,cancellistener);
            calldialog(deleteDialog);
        }
    };
    private View.OnClickListener cancellistener = new View.OnClickListener() {
        public void onClick(View v) {
            reportDialog.dismiss();
         if(deleteDialog!=null)deleteDialog.dismiss();
        }
    };
}