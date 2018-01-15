package com.project.cvd.memory10;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.project.cvd.memory10.Logic.LogicHelper;
import com.project.cvd.memory10.Logic.PlaySound;
import com.project.cvd.memory10.Models.MemoryButton;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameScreen extends AppCompatActivity implements View.OnClickListener, AnimationListener {

    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;

    private List<MemoryButton> memoryButtonList = new ArrayList<>();

    private int matchingAll = 0;

    private GridLayout gameLayout;

    private Animation animation1;
    private Animation animation2;
    private boolean animationRunning = false;
    private MemoryButton actualAnimation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        animation2.setAnimationListener(this);


        int numColumns = 0;
        int numRows = 0;

        //TableLayout zuweisen
        gameLayout = findViewById(R.id.GameGrid);

        if (LogicHelper.bitmapList == null) {
            Toast.makeText(this, "Liste leer", Toast.LENGTH_SHORT).show();
        }

        //Abfrage um welche Anzahl von Karten es sich handelt.
        if (LogicHelper.bitmapList.size() == 4) {
            numColumns = 4;
            numRows = 2;
        } else if (LogicHelper.bitmapList.size() == 5) {
            numColumns = 5;
            numRows = 2;
        } else if (LogicHelper.bitmapList.size() == 6) {
            numColumns = 4;
            numRows = 3;
        }

        //Setzen des GameLayouts
        gameLayout.setColumnCount(numColumns);
        gameLayout.setRowCount(numRows);

        //Methodenaufruf um die Karten zuzuorden.
        ShuffelButtonGraphics();

    }

    private void ShuffelButtonGraphics() {

        int trigger = -1;
        for (int i = 0; i < LogicHelper.bitmapList.size(); i++) {
            trigger++;
            memoryButtonList.add(CreateButtonView(LogicHelper.bitmapList.get(i), trigger, i));
        }
        for (int i = 0; i < LogicHelper.bitmapList.size(); i++) {
            trigger++;
            memoryButtonList.add(CreateButtonView(LogicHelper.bitmapList.get(i), trigger, i));
        }

        //Shuffeln der ButtonList
        Collections.shuffle(memoryButtonList);

        for (int i = 0; i < memoryButtonList.size(); i++) {
            gameLayout.addView(memoryButtonList.get(i));
        }
    }

    private MemoryButton CreateButtonView(Bitmap bitmap, int _triggerId, int _pictureId) {
        MemoryButton btn = new MemoryButton(this, bitmap, _triggerId, _pictureId);
        btn.setOnClickListener(this);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams();

        // Abfrage der Spielart um dann die entsprechende Größe für die Buttons zu erhalten.
        if (LogicHelper.bitmapList.size() == 4) {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 140;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 140;
        } else if (LogicHelper.bitmapList.size() == 5) {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 110;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 110;
        } else if (LogicHelper.bitmapList.size() == 6) {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 100;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 100;
        }

        //Margins festlegen
        tempParams.leftMargin = 15;
        tempParams.rightMargin = 15;
        tempParams.topMargin = 15;
        tempParams.bottomMargin = 15;

        //LayoutParams werden auf den Button angewendet
        btn.setLayoutParams(tempParams);

        return btn;
    }

    public void onClick(View view) {
        MemoryButton m = (MemoryButton) view;

        PlaySound.PlayClick(this);

        //Wenn selectedButton1 noch null ist.
        if (selectedButton1 == null) {
            //Karte 1 setzten und drehen
            selectedButton1 = m;

            m.clearAnimation();
            m.setAnimation(animation1);
            m.startAnimation(animation1);
            actualAnimation = m;

        }
        //Wenn selectedButton1 nicht null ist und selectedButton2 null und die Animation nicht läuft.
        else if (selectedButton1 != null && selectedButton2 == null && !animationRunning) {
            //Karte 2 setzten und drehen
            selectedButton2 = m;

            //Animation für Karte eins starten
            m.clearAnimation();
            m.setAnimation(animation1);
            m.startAnimation(animation1);
            actualAnimation = m;

            //
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Abfrage ob Karte 1 und Karte 2 gleich sind.
                    if (selectedButton1.getPictureId() == selectedButton2.getPictureId()) {
                        selectedButton1.setVisibility(View.INVISIBLE);
                        selectedButton2.setVisibility(View.INVISIBLE);
                        selectedButton1 = null;
                        selectedButton2 = null;
                        matchingAll++;
                    } else {
                        PlaySound.PlayFail(GameScreen.this);
                        selectedButton1.setMatched(false);
                        selectedButton2.setMatched(false);
                        selectedButton1.Flip();
                        selectedButton2.Flip();
                        selectedButton1 = null;
                        selectedButton2 = null;
                    }

                    if (matchingAll == LogicHelper.bitmapList.size()) {
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

    @Override
    public void onAnimationStart(Animation animation) {
        animationRunning = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animation1) {
            actualAnimation.Flip();
            actualAnimation.clearAnimation();
            actualAnimation.setAnimation(animation2);
            actualAnimation.startAnimation(animation2);
        }
        animationRunning = false;
        actualAnimation = null;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
