package com.project.cvd.memory10.Logic;

import android.graphics.Bitmap;

import com.project.cvd.memory10.Interfaces.ILogicHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Heero on 12.01.2018.
 */

//Helper Klasse zum übergeben und zwischenspeichern der Daten
public class LogicHelper implements ILogicHelper {

    //Globale Liste mit ausgewählten Bitmaps, Zugriff von jeder View möglich.
    public static List<Bitmap> bitmapList;

    public List<Bitmap> GetList()
    {
        return bitmapList;
    }

    public void SetList(List<Bitmap> _bitmapList)
    {
        this.bitmapList = new ArrayList<>();
        this.bitmapList = _bitmapList;
    }
}
