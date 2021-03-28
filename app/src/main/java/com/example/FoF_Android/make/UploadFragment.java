package com.example.FoF_Android.make;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.DetailFragment;
import com.example.FoF_Android.dialog.DeleteDialog;
import com.example.FoF_Android.dialog.LogoutDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class UploadFragment extends Fragment {
    RetrofitApi api;
    Button image;
    String imagePath;
    ImageView imageview;
    LinearLayout wrap;
    Uri imageUri;
    TextView next, cancel;
    private View.OnClickListener mNegativeListener;
    InputStream in;
    File f;
    boolean result;
    ByteArrayOutputStream stream;
    private int SELECT_FILE = 3;
    private   LogoutDialog logoutDialog;
    private String userChoosenTask;
    public UploadFragment() {
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_upload, container, false);
        imageview=(ImageView)view.findViewById(R.id.image);
        next=(TextView)view.findViewById(R.id.next);
        cancel=(TextView)view.findViewById(R.id.cancel);
        wrap=view.findViewById(R.id.wrap);
        result = Utility.checkPermission(getContext());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(in!=null) {
                    logoutDialog = new LogoutDialog(1, getContext(), mnegativtlistenr);
                    logoutDialog.setCancelable(true);
                    logoutDialog.getWindow().setGravity(Gravity.CENTER);

                    logoutDialog.show();

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(in!=null){
                Log.i("dkdk", f.getName());
                UploadNextFragment detail = new UploadNextFragment(f);
                getFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null).add(R.id.container, detail).commit();

            }}
        });

        image=(Button)view.findViewById(R.id.image_btn);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* final CharSequence[] items = { "사진 가져오기",
                        "취소"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("사진가져오기");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {*/

                if(result)  galleryIntent();
              /*       if (items[item].equals("사진 가져오기")) {
                            userChoosenTask = "사진 가져오기";
                         userChoosenTask = "사진 가져오기";
                         if (result)

                        } else if (items[item].equals("취소")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();*/
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(result)  galleryIntent();
            }
        });
        return view;
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                     if (userChoosenTask.equals("사진 가져오기"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data, SELECT_FILE);
            }
            }

        }
        private void onSelectFromGalleryResult(Intent data, int imagetype) {

        Bitmap bm = null;
        imageUri = data.getData();
        if (Build.VERSION.SDK_INT < 11) {
            imagePath = RealPathUtil.getRealPathFromURI_BelowAPI11(getContext(), imageUri);

        } else if (Build.VERSION.SDK_INT < 19) {

            imagePath = RealPathUtil.getRealPathFromURI_API11to18(getContext(), imageUri);
        } else {
            imagePath = RealPathUtil.getRealPathFromURI_API19(getContext(), imageUri);
        }


        try {
            in = getActivity().getContentResolver().openInputStream(data.getData());
            bm = BitmapFactory.decodeStream(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (imagetype == SELECT_FILE) {
            f = new File(imagePath);
            stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            wrap.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(stream.toByteArray())
                 //   .placeholder(R.drawable.meme2)
                    .into(imageview);
          //  imageview.setImageBitmap(bm);
        }
    }
    public View.OnClickListener mnegativtlistenr=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Drawable drawable = getResources().getDrawable(R.drawable.upload_bg);
            wrap.setVisibility(View.VISIBLE);
            imageview.setImageDrawable(drawable);
            image.setVisibility(View.GONE);
            in=null;
            logoutDialog.dismiss();
        }
    };

}
