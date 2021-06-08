package com.kriptops.wizarpos.demoapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.kriptops.wizarpos.cardlib.Pos;
import com.kriptops.wizarpos.cardlib.android.PosApp;

public class MainApp extends Application implements PosApp {

    private Pos pos;

    public Pos getPos() {
        return pos;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.pos = new Pos(this);
        this.pos.setPinLength(4);
        //this.pos.setPinLength(4, 6);
    }

}
