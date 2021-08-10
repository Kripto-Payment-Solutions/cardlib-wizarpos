package com.kriptops.wizarpos.cardlib.bridge;

import com.cloudpos.DeviceException;
import com.cloudpos.OperationListener;
import com.cloudpos.OperationResult;
import com.cloudpos.POSTerminal;
import com.cloudpos.TimeConstants;
import com.cloudpos.msr.MSRDevice;

import java.util.LinkedList;
import java.util.List;

public class Msr extends CloseableDeviceWrapper<MSRDevice> {

    public Msr(POSTerminal posTerminal) {
        super((MSRDevice) posTerminal.getDevice("cloudpos.device.msr"));
    }

    private List<MsrListener> trackListeners = new LinkedList<>();

    public void addTrackListener(MsrListener listener) {
        trackListeners.add(listener);
    }

    public void removeTrackListener(MsrListener listener) {
        trackListeners.remove(listener);
    }

    public void flushTrackListeners() {
        trackListeners = new LinkedList<>();
    }

    private void operationListener(OperationResult result){

    }

    public void read() {
        try {
            this.getDevice().listenForSwipe(this::operationListener, TimeConstants.FOREVER);
        } catch (DeviceException e) {
            throw new RuntimeException(e);
        }
    }

}
