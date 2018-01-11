package com.project.cvd.memory10;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game_4x4 extends AppCompatActivity {

    private ImageView img_1;
    private ImageView img_2;
    private ImageView img_3;
    private ImageView img_4;

    private int imgCounter=0;
    private static int IMG_RESULT = 1;
    private List<Bitmap> pictureList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_4x4);

        img_1=findViewById(R.id.imgView1);
        img_2=findViewById(R.id.imgView2);
        img_3=findViewById(R.id.imgView3);
        img_4=findViewById(R.id.imgView4);
    }

    public void Open_1(View view)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, IMG_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == IMG_RESULT && resultCode == RESULT_OK && data != null && data.getData() != null)
            {
                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    pictureList.add(bitmap);

                    if(pictureList.size()==1)
                    {
                        img_1.setImageBitmap(pictureList.get(pictureList.size()-1));
                    }
                    else if(pictureList.size()==2)
                    {
                        img_2.setImageBitmap(pictureList.get(pictureList.size()-1));
                    }
                    else if(pictureList.size()==3)
                    {
                        img_3.setImageBitmap(pictureList.get(pictureList.size()-1));
                    }
                    else if(pictureList.size()==4)
                    {
                        img_4.setImageBitmap(pictureList.get(pictureList.size()-1));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e)
        {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG).show();
        }
    }
}
