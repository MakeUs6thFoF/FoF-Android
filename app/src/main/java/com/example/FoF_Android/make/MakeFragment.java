package com.example.FoF_Android.make;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;


public class MakeFragment extends Fragment {
    RetrofitApi api;
    Button image;
    String imagePath;
    ImageView imageview;
    Uri imageUri;
    File f;
    private int SELECT_FILE = 3;
    private String userChoosenTask;
    public MakeFragment() {
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_make, container, false);
        imageview=(ImageView)view.findViewById(R.id.image);
        image=(Button)view.findViewById(R.id.image_btn);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = { "사진 가져오기",
                        "취소"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("사진가져오기");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        boolean result = Utility.checkPermission(getContext());

                     if (items[item].equals("사진 가져오기")) {
                            userChoosenTask = "사진 가져오기";
                         userChoosenTask = "사진 가져오기";
                         if (result)
                             galleryIntent();
                        } else if (items[item].equals("취소")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
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
            bm = getResizedBitmap(decodeUri(data.getData()), getResources().getDimensionPixelSize(R.dimen.idcard_pic_height), getResources().getDimensionPixelSize(R.dimen.idcard_pic_width));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (imagetype == SELECT_FILE) {
            f = new File(imagePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Glide.with(getContext())
                    .load(stream.toByteArray())
                    .placeholder(R.drawable.meme2)
                    .into(imageview);
          //  imageview.setImageBitmap(bm);
        }
    }
    public static Bitmap getResizedBitmap(Bitmap image, int newHeight, int newWidth) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        if (Build.VERSION.SDK_INT <= 19) {
            //matrix.postRotate(90);
        }
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getContext().getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getContext().getContentResolver().openInputStream(selectedImage), null, o2);
    }
}
