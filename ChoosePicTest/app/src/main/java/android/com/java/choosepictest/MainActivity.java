package android.com.java.choosepictest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends Activity {
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    private Button takePhoto;
    private ImageView picture;
    private Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePhoto = (Button)findViewById(R.id.take_photo);
        picture = (ImageView)findViewById(R.id.picture);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.getExternalStorageDirectory(),"output_image.jpg");
                try{
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                android.util.Log.e("YPC","uri = "+imageUri);
                try {
                    Bitmap mBitmap = BitmapFactory.decodeStream((getContentResolver().openInputStream(imageUri)));
                    Log.e("YPC","bitmap = "+mBitmap);
                    Log.e("YPC","getContentResolver().openInputStream(imageUri) = "+getContentResolver().openInputStream(imageUri));
                    picture.setImageBitmap(mBitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                Intent intent = new Intent ("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (requestCode == RESULT_OK) {
                    Intent intent = new Intent ("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    try {
                        Bitmap mBitmap = BitmapFactory.decodeStream((getContentResolver().openInputStream(imageUri)));
                        Log.e("YPC","bitmap = "+mBitmap);
                        Log.e("YPC","getContentResolver().openInputStream(imageUri) = "+getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(mBitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    startActivityForResult(intent,CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream((getContentResolver().openInputStream(imageUri)));
                        picture.setImageBitmap(bitmap);
                        android.util.Log.e("YPC","bitmap = "+bitmap);

                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:break;
        }
    }
}
