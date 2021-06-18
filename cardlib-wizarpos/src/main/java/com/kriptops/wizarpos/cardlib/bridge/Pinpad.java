package com.kriptops.wizarpos.cardlib.bridge;

import com.cloudpos.POSTerminal;
import com.cloudpos.pinpad.extend.PINPadExtendDevice;
import com.kriptops.wizarpos.cardlib.android.AbstractCloseable;

public class Pinpad extends AbstractCloseable<PINPadExtendDevice> {

    public Pinpad(POSTerminal posTerminal) {
        super((PINPadExtendDevice) posTerminal.getDevice("cloudpos.device.pinpad"));
    }

}
