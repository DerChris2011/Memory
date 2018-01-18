package com.project.cvd.memory10;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.cvd.memory10.Interfaces.ILogicHelper;
import com.project.cvd.memory10.Logic.LogicHelper;
import com.project.cvd.memory10.Logic.PlaySound;
import com.project.cvd.memory10.Models.MemoryButton;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Christoph Fandrich on 12.01.2018.
 */

public class GameScreen extends AppCompatActivity implements View.OnClickListener, AnimationListener {

    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;

    private List<MemoryButton> memoryButtonList = new ArrayList<>();
    private List<Bitmap> bitmapList = new ArrayList<>();

    private int matchingAll = 0;

    private GridLayout gameLayout;

    private Animation animation1;
    private Animation animation2;
    private boolean animationRunning = false;
    private MemoryButton actualAnimation = null;
    private boolean sound;

    //InterfaceLogicHelper
    ILogicHelper _logicInterface = new LogicHelper();

    //Alles für den Timer
    private long startTime = 0L;
    private Handler timerHandler = new Handler();
    long timeInMillis = 0L;
    long timeSwap = 0L;
    long updatedTime = 0L;
    private TextView timer;
    private String timeAsString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_screen);

        sound = getIntent().getExtras().getBoolean("Sound");

        bitmapList = _logicInterface.GetList();

        animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        animation2.setAnimationListener(this);


        int numColumns = 0;
        int numRows = 0;

        //TableLayout zuweisen
        gameLayout = findViewById(R.id.GameGrid);

        if (bitmapList == null) {
            Toast.makeText(this, "Liste leer", Toast.LENGTH_SHORT).show();
            this.onBackPressed();
        }

        //Abfrage um welche Anzahl von Karten es sich handelt.
        if (bitmapList.size() == 4) {
            numColumns = 4;
            numRows = 2;
        } else if (bitmapList.size() == 5) {
            numColumns = 5;
            numRows = 2;
        } else if (bitmapList.size() == 6) {
            numColumns = 4;
            numRows = 3;
        }

        //Zuweisen des timers
        timer = findViewById(R.id.timer);

        //Setzen des GameLayouts
        gameLayout.setColumnCount(numColumns);
        gameLayout.setRowCount(numRows);

        //Methodenaufruf um die Karten zuzuorden.
        ShuffelButtonGraphics();

        startTime = SystemClock.uptimeMillis();
        timerHandler.postDelayed(TimerThread, 0);
    }

    //Methode um die Karten zufällig zu verteilen.
    private void ShuffelButtonGraphics() {

        int trigger = -1;
        for (int i = 0; i < bitmapList.size(); i++) {
            trigger++;
            memoryButtonList.add(CreateButtonView(bitmapList.get(i), trigger, i));
        }
        for (int i = 0; i < bitmapList.size(); i++) {
            trigger++;
            memoryButtonList.add(CreateButtonView(bitmapList.get(i), trigger, i));
        }

        //Shuffeln der ButtonList
        Collections.shuffle(memoryButtonList);

        for (int i = 0; i < memoryButtonList.size(); i++) {
            gameLayout.addView(memoryButtonList.get(i));
        }
    }

    //Erzeugen der MemoryButtons
    private MemoryButton CreateButtonView(Bitmap bitmap, int _triggerId, int _pictureId) {
        MemoryButton btn = new MemoryButton(this, bitmap, _triggerId, _pictureId);
        btn.setOnClickListener(this);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams();

        // Abfrage der Spielart um dann die entsprechende Größe für die Buttons zu erhalten.
        if (bitmapList.size() == 4) {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 140;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 140;
        } else if (bitmapList.size() == 5) {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 110;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 110;
        } else if (bitmapList.size() == 6) {
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

    //OnClick Event für die MemoryButtons.
    public void onClick(View view) {
        MemoryButton m = (MemoryButton) view;

        if(sound)
        {
            PlaySound.PlayClick(this);
        }

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
                    if (selectedButton1.getPictureId() == selectedButton2.getPictureId())
                    {
                        selectedButton1.setVisibility(View.INVISIBLE);
                        selectedButton2.setVisibility(View.INVISIBLE);
                        selectedButton1 = null;
                        selectedButton2 = null;
                        matchingAll++;
                    }
                    else
                    {
                        if(sound)
                        {
                            PlaySound.PlayFail(GameScreen.this);
                        }
                        selectedButton1.setMatched(false);
                        selectedButton2.setMatched(false);
                        selectedButton1.Flip();
                        selectedButton2.Flip();
                        selectedButton1 = null;
                        selectedButton2 = null;
                    }

                    if (matchingAll == bitmapList.size())
                    {
                        timerHandler.removeCallbacks(TimerThread);
                        CustomDialog();
                    }
                }
            }, 1000);
        }
    }

    private void CustomDialog(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View nextDialogView = factory.inflate(R.layout.custom_dialog, null);
        final AlertDialog nextDialog = new AlertDialog.Builder(this).create();
        nextDialog.setView(nextDialogView);

        TextView dialogText = nextDialogView.findViewById(R.id.txt_time);
        dialogText.setText(getResources().getString(R.string.your_time) + " " + timeAsString);
        nextDialogView.setBackgroundColor(Color.TRANSPARENT);
        nextDialogView.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameScreen.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                nextDialog.dismiss();
            }
        });

        nextDialog.show();
    }

    private Runnable TimerThread = new Runnable() {

        public void run() {

            timeInMillis = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwap + timeInMillis;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));

            timeAsString="" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds);

            timerHandler.postDelayed(this, 0);
        }

    };

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
