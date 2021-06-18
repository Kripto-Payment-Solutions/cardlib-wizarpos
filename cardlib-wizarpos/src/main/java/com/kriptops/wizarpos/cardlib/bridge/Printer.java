package com.kriptops.wizarpos.cardlib.bridge;

import com.cloudpos.POSTerminal;
import com.cloudpos.printer.PrinterDevice;
import com.kriptops.wizarpos.cardlib.android.AbstractCloseable;

public class Printer extends AbstractCloseable<PrinterDevice> {

    protected Printer(POSTerminal posTerminal) {
        super((PrinterDevice) posTerminal.getDevice("cloudpos.device.printer"));
    }
}
