package com.project.cvd.memory10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    //Declaration Buttons
    private Button btn_easy;
    private Button btn_medium;
    private Button btn_hard;
    private Button btn_hardcore;

    //Declaration CheckBox
    private CheckBox cb_sound;
    private CheckBox cb_music;

    //Declaraition for Options
    private boolean sound=false;
    private boolean music = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_easy = findViewById(R.id.btn_easy);
        btn_medium = findViewById(R.id.btn_medium);
        btn_hard = findViewById(R.id.btn_hard);
        btn_hardcore = findViewById(R.id.btn_hardcore);

        cb_sound = findViewById(R.id.cb_sound);
        cb_music = findViewById(R.id.cb_music);


    }

    //Button Events
    public void Start_4x4(View view)
    {
        //Öffnen der 4x4 Activity
        Intent i = new Intent(this, PictureSelector_2x4.class);
        startActivity(i);
    }

    public void Start_5x5(View view)
    {
        //Todo: Hier bitte die 5x5 Activity öffnen.
    }

    public void Start_6x6(View view)
    {
        //Todo: Hier bitte die 6x6 Activity öffnen.
    }

    public void Start_8x8(View view)
    {
        //Todo: Hier bitte die 8x8 Activity öffnen.
    }

    //Checkbox Events
    public void ChangeSound(View view)
    {
        sound = cb_sound.isChecked();
    }

    public void ChangeMusic(View view)
    {
        music = cb_music.isChecked();
    }

}
