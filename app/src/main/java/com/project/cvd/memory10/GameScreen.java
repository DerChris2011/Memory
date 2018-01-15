package com.project.cvd.memory10;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.cvd.memory10.Logic.LogicHelper;
import com.project.cvd.memory10.Models.MemoryButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen extends AppCompatActivity implements View.OnClickListener {


    private int numberOfElements;
    private MemoryButton[] buttons;
    private int[] buttonGraphicLocations;
    private int[] buttonGraphics;

    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;

    private boolean isBusy = false;

    private int matchingAll=0;

    private List<Bitmap> GamePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        int numColumns=0;
        int numRows=0;

        if(LogicHelper.bitmapList==null){
            Toast.makeText(this, "Liste leer", Toast.LENGTH_SHORT).show();
        }

        GridLayout gridLayout = findViewById(R.id.GameGrid);

        if(LogicHelper.bitmapList.size()==4)
        {
            numColumns = 4;
            numRows = 2;
        }
        else if(LogicHelper.bitmapList.size()==5)
        {
            numColumns = 5;
            numRows = 2;
        }
        else if(LogicHelper.bitmapList.size()==6)
        {
            numColumns = 4;
            numRows = 3;
        }

        gridLayout.setColumnCount(numColumns);
        gridLayout.setRowCount(numRows);

        numberOfElements = numColumns * numRows;

        buttons = new MemoryButton[numberOfElements];

        buttonGraphics = new int[numberOfElements / 2];

        ShuffelButtonGraphics(gridLayout);
    }

    private void ShuffelButtonGraphics(GridLayout gl){

        int trigger = -1;
        for (int i = 0;i<LogicHelper.bitmapList.size();i++)
        {
            trigger++;
            gl.addView(CreateButtonView(LogicHelper.bitmapList.get(i),trigger,i));
        }

        for (int i = 0;i<LogicHelper.bitmapList.size();i++)
        {
            trigger++;
            gl.addView(CreateButtonView(LogicHelper.bitmapList.get(i),trigger,i));
        }
    }

    private MemoryButton CreateButtonView(Bitmap bitmap, int _triggerId, int _pictureId){
        MemoryButton btn = new MemoryButton(this, bitmap, _triggerId, _pictureId);
        btn.setOnClickListener(this);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams();
        tempParams.width = (int) getResources().getDisplayMetrics().density*100;
        tempParams.height = (int) getResources().getDisplayMetrics().density*100;
        btn.setMinHeight(tempParams.width);
        tempParams.leftMargin=15;
        tempParams.rightMargin=15;
        tempParams.topMargin=15;
        tempParams.bottomMargin=15;
        btn.setLayoutParams(tempParams);

        return btn;
    }

    public void onClick(View v){
        MemoryButton m = (MemoryButton) v;


        //Wenn selectedButton1 noch null ist.
        if(selectedButton1 == null)
        {
            //Karte 1 setzten und drehen
            selectedButton1 = m;
            selectedButton1.Flip();
        }
        //Wenn selectedButton1 nicht null ist und selectedButton2 null ist.
        else if(selectedButton1!=null && selectedButton2==null)
        {
            //Karte 2 setzten und drehen
            selectedButton2 = m;
            selectedButton2.Flip();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Abfrage ob Karte 1 und Karte 2 gleich sind.
                    if(selectedButton1.getPictureId()==selectedButton2.getPictureId())
                    {
                        selectedButton1.setVisibility(View.INVISIBLE);
                        selectedButton2.setVisibility(View.INVISIBLE);
                        selectedButton1=null;
                        selectedButton2=null;
                        matchingAll++;
                    }
                    else
                    {
                        selectedButton1.setMatched(false);
                        selectedButton2.setMatched(false);
                        selectedButton1.Flip();
                        selectedButton2.Flip();
                        selectedButton1=null;
                        selectedButton2=null;
                    }

                    if(matchingAll==LogicHelper.bitmapList.size())
                    {
                        Intent i = new Intent(GameScreen.this, MainActivity.class);
                        startActivity(i);
                    }
                }
            }, 1000);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
