package com.project.cvd.memory10.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

import com.project.cvd.memory10.R;

/**
 * Created by Christoph Fandrich on 12.01.2018.
 */

public class MemoryButton extends ImageButton  {

    protected boolean isFlipped;
    protected boolean isMatched;

    protected Bitmap front;
    protected Bitmap back;
    protected Drawable style;

    protected int triggerId;
    protected int pictureId;

    public MemoryButton(Context _context, Bitmap _front, int _triggerId, int _pictureId){
        super(_context);
        this.front = _front;
        this.back = BitmapFactory.decodeResource(getResources(), R.drawable.question);
        this.style = getResources().getDrawable(R.drawable.game_images);
        this.triggerId = _triggerId;
        this.pictureId = _pictureId;
        setImageBitmap(back);
        setBackground(style);
        setScaleType(ScaleType.CENTER_CROP);
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setMatched(boolean matched){
        this.isMatched = matched;
    }

    public void Flip(){
        if(isMatched){
            return;
        }

        if(isFlipped){
            setImageBitmap(back);
            setBackground(style);
            isFlipped = false;
        }
        else
        {
            setImageBitmap(front);
            setBackground(style);
            isFlipped = true;
        }
    }
}
