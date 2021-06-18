package com.kriptops.wizarpos.cardlib.bridge;

import android.content.Context;

import com.cloudpos.POSTerminal;
import com.cloudpos.msr.MSRDevice;
import com.cloudpos.pinpad.PINPadDevice;
import com.cloudpos.printer.PrinterDevice;

public class Terminal {

    private POSTerminal posTerminal;

    public Terminal() {}

    public void init(Context context) {
        this.posTerminal = POSTerminal.getInstance(context);
    }

}
