package com.project.cvd.memory10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    //Declaration CheckBox
    private CheckBox cb_sound;

    private boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cb_sound = findViewById(R.id.cb_sound);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void Start_Easy(View view)
    {
        Intent i1 = new Intent(this, PictureSelector_2x4.class);
        i1.putExtra("sound", cb_sound.isChecked());
        startActivity(i1);
    }

    public void Start_Medium(View view)
    {
        Intent i2= new Intent( this, PictureSelector_2x5.class);
        i2.putExtra("sound", cb_sound.isChecked());
        startActivity(i2);
    }

    public void Start_Hard(View view)
    {
        Intent i3= new Intent( this, PictureSelector_3x4.class);
        i3.putExtra("sound", cb_sound.isChecked());
        startActivity(i3);
    }

    public void ChangeSound(View view)
    {
        this.sound = cb_sound.isChecked();
    }
}
