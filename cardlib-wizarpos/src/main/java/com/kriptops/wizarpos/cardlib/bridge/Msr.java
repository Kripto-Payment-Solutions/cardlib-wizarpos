package com.kriptops.wizarpos.cardlib.bridge;

import com.cloudpos.POSTerminal;
import com.cloudpos.msr.MSRDevice;
import com.kriptops.wizarpos.cardlib.android.AbstractCloseable;

public class Msr extends AbstractCloseable<MSRDevice> {

    public Msr(POSTerminal posTerminal) {
        super((MSRDevice) posTerminal.getDevice("cloudpos.device.msr"));
    }
}
