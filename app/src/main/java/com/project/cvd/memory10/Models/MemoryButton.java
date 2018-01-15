package com.project.cvd.memory10.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Button;
import android.widget.GridLayout;

import com.project.cvd.memory10.R;

/**
 * Created by Heero on 12.01.2018.
 */

public class MemoryButton extends android.support.v7.widget.AppCompatButton {

    protected boolean isFlipped;
    protected boolean isMatched;

    protected Bitmap front;
    protected Bitmap back;

    protected int triggerId;
    protected int pictureId;


    public MemoryButton(Context _context, Bitmap _front, int _triggerId, int _pictureId){
        super(_context);
        this.front = _front;
        this.back = BitmapFactory.decodeResource(getResources(), R.drawable.question);
        this.triggerId = _triggerId;
        this.pictureId = _pictureId;

        setBackground(new BitmapDrawable(back));
    }

    public int getTriggerId() {
        return triggerId;
    }

    public int getPictureId() {
        return pictureId;
    }

    public boolean isMatched(){
        return isMatched;
    }

    public void setMatched(boolean matched){
        this.isMatched = matched;
    }

    public Bitmap getFront() {
        return front;
    }

    public void Flip(){
        if(isMatched){
            return;
        }

        if(isFlipped){
            setBackground(new BitmapDrawable(back));
            isFlipped = false;
        }
        else
        {
            setBackground(new BitmapDrawable(front));
            isFlipped = true;
        }
    }


}
