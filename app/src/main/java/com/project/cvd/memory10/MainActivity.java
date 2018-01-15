package com.project.cvd.memory10;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.project.cvd.memory10.Logic.LogicHelper;

public class MainActivity extends AppCompatActivity {

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

        cb_sound = findViewById(R.id.cb_sound);
        cb_music = findViewById(R.id.cb_music);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        LogicHelper.bitmapList.clear();
    }

    public void Start_Easy(View view)
    {
        Intent i1 = new Intent(this, PictureSelector_2x4.class);
        startActivity(i1);
    }

    public void Start_Medium(View view)
    {
        Intent i2= new Intent( this, PictureSelector_2x5.class);
        startActivity(i2);
    }

    public void Start_Hard(View view)
    {
        Intent i3= new Intent( this, PictureSelector_3x4.class);
        startActivity(i3);
    }

    public void Start_Hardcore(View view)
    {
        Intent i4= new Intent( this, PictureSelector_Hardcore.class);
        startActivity(i4);
    }

    private void MakeSound(){
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound_button);
        mp.start();
    }

    //Checkbox Events
    public void ChangeSound(View view)
    {
        sound = cb_sound.isChecked();
        MakeSound();
    }

    public void ChangeMusic(View view)
    {
        music = cb_music.isChecked();
    }


}
