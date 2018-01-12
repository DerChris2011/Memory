package com.project.cvd.memory10;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PictureSelector_3x4 extends AppCompatActivity {

    //Deklaration der ImageViews.
    private ImageView img_1;
    private ImageView img_2;
    private ImageView img_3;
    private ImageView img_4;
    private ImageView img_5;
    private ImageView img_6;

    //Deklaration des Start Buttons
    private Button btn_start;

    private static int IMG_RESULT = 1;

    //Liste in die alle Bilder geschrieben werden.
    private List<Bitmap> pictureList = new ArrayList<>();
    private List<Uri> checkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector_3x4);

        img_1=findViewById(R.id.imgView1);
        img_2=findViewById(R.id.imgView2);
        img_3=findViewById(R.id.imgView3);
        img_4=findViewById(R.id.imgView4);
        img_5=findViewById(R.id.imgView5);
        img_6=findViewById(R.id.imgView6);

        btn_start = findViewById(R.id.btn_Start);

        //Button auf inaktiv setzen.
        btn_start.setEnabled(false);
    }

    //OnClick Event für die ImageViews.
    public void Open_1(View view)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, IMG_RESULT);


    }

    //Sobald ein Bild ausgewählt wurde wird es in dieser Methode abgefangen und in den entsprechenden ImageView sowie in die Liste geschrieben.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == IMG_RESULT && resultCode == RESULT_OK && data != null && data.getData() != null)
            {
                Uri uri = data.getData();

                if(!CheckIfPictureIsSelected(uri))
                {
                    checkList.add(uri);

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        pictureList.add(bitmap);

                        if (pictureList.size() == 1)
                        {
                            img_1.setImageBitmap(pictureList.get(pictureList.size() - 1));
                            img_1.setBackgroundColor(Color.TRANSPARENT);
                            img_1.setMaxHeight(img_1.getWidth());
                        }
                        else if (pictureList.size() == 2)
                        {
                            img_2.setImageBitmap(pictureList.get(pictureList.size() - 1));
                            img_2.setBackgroundColor(Color.TRANSPARENT);
                            img_2.setMaxHeight(img_2.getWidth());
                        }
                        else if (pictureList.size() == 3)
                        {
                            img_3.setImageBitmap(pictureList.get(pictureList.size() - 1));
                            img_3.setBackgroundColor(Color.TRANSPARENT);
                            img_3.setMaxHeight(img_3.getWidth());
                        }
                        else if (pictureList.size() == 4)
                        {
                            img_4.setImageBitmap(pictureList.get(pictureList.size() - 1));
                            img_4.setBackgroundColor(Color.TRANSPARENT);
                            img_4.setMaxHeight(img_4.getWidth());
                        }
                        else if (pictureList.size() ==5)
                        {
                            img_5.setImageBitmap(pictureList.get(pictureList.size()-1));
                            img_5.setBackgroundColor(Color.TRANSPARENT);
                            img_5.setMaxHeight(img_5.getWidth());



                        }
                        else if (pictureList.size() ==6)
                        {
                            img_6.setImageBitmap(pictureList.get(pictureList.size()-1));
                            img_6.setBackgroundColor(Color.TRANSPARENT);
                            img_6.setMaxHeight(img_6.getWidth());


                            //Button aktivieren.
                            btn_start.setEnabled(true);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, R.string.try_again, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this, R.string.already_selected_picture, Toast.LENGTH_SHORT).show();
                }
            }
            //Sollte es zu einem Fehler gekommen sein wird ein Toast ausgegeben
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, R.string.try_again, Toast.LENGTH_LONG).show();
        }
    }

    //Prüft ob ausgewähltes Bild schon vorher ausgewählt wurde.
    private boolean CheckIfPictureIsSelected(Uri _uri)
    {
        //In dieser Schleife wird die Liste mit den schon zugefügten Bildern durchlaufen.
        for (int i=0; i<checkList.size();i++)
        {
            //Abfrage ob das aktuell ausgewählte Bild gleich dem ist wo gerade die Schleife steht.
            if(_uri.equals(checkList.get(i)))
            {
                //Wenn Bild schon vorhanden gibt er true zurück und beendet damit sofort die Methode.
                return true;
            }
        }
        return false;
    }

}

