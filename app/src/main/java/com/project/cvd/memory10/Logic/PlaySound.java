package com.project.cvd.memory10.Logic;

import android.content.Context;
import android.media.MediaPlayer;
import com.project.cvd.memory10.R;

/**
 * Created by Sinan Akpinar on 11.01.2018.
 * In dieser Klasse werden die einzelnen Sounds abgespielt.
 */

public class PlaySound{

    public static void PlayClick(Context context)
    {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.sound_button);
        mp.start();
    }

    public static void PlayFail(Context context)
    {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.fail);
        mp.start();
    }
}
