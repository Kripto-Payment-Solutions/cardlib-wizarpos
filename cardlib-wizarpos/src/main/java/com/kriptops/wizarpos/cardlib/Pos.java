package com.kriptops.wizarpos.cardlib;

import android.util.Log;

import com.cloudpos.DeviceException;
import com.cloudpos.OperationResult;
import com.cloudpos.POSTerminal;
import com.cloudpos.msr.MSRDevice;
import com.cloudpos.pinpad.PINPadDevice;
import com.cloudpos.pinpad.PINPadOperationResult;
import com.cloudpos.printer.PrinterDevice;
import com.kriptops.wizarpos.cardlib.android.PosApp;
import com.kriptops.wizarpos.cardlib.func.Consumer;
import com.kriptops.wizarpos.cardlib.func.BiConsumer;

import java.util.Collection;

public class Pos {

    private final POSTerminal terminal;
    private final Emv emv;
    private final Pinpad pinpad;
    private final Msr msr;
    private final PINPadDevice pinPadDevice;
    private final PrinterDevice printerDevice;
    private final MSRDevice msrDevice;
    private boolean pinpadCustomUI;
    private Runnable onPinRequested;
    private Runnable onPinCaptured;
    private BiConsumer<String, String> onError;
    private Consumer<Integer> digitsListener;
    private Consumer<TransactionData> goOnline;
    protected TransactionData data;

    public Pos(PosApp posApp) {
        this.terminal = POSTerminal.getInstance(posApp.getApplicationContext());
        this.pinPadDevice = (PINPadDevice) this.terminal.getDevice("cloudpos.device.pinpad");
        this.printerDevice = (PrinterDevice) this.terminal.getDevice("cloudpos.device.printer");
        this.msrDevice = (MSRDevice) this.terminal.getDevice("cloudpos.device.msr");
        //debe ir antes que la creacion del emv kernel
        this.msr = new Msr(msrDevice);
        this.emv = new Emv(this, posApp.getApplicationContext());
        this.pinpad = new Pinpad(pinPadDevice);
        this.withPinpad(this::configPinpad);
        //carga los AID y CAPK por defecto
        this.loadAids(Defaults.AIDS);
        this.loadCapks(Defaults.CAPKS);
        this.setTagList(Defaults.TAG_LIST);
        this.pinpad.setTimeout(Defaults.PINPAD_REQUEST_TIMEOUT);
        this.setPinpadCustomUI(false);
    }

    public String getSerialNumber() {
        return this.terminal.getTerminalSpec().getSerialNumber();
    }

    public void configTerminal(
            String merchantId,
            String merchantName,
            String terminalId,
            String clFloorLimit,
            String clTransactionLimit,
            String cvmLimit
    ) {
        this.emv.initParams(
                merchantId,
                merchantName,
                terminalId,
                getSerialNumber(),
                clFloorLimit,
                clTransactionLimit,
                cvmLimit
        );
    }

    protected void processOnline() {
        Log.d(Defaults.LOG_TAG, data.toString());
        int panSize = data.maskedPan.length();
        int las4index = panSize - 4;
        data.bin = data.maskedPan.substring(0, 6);
        data.maskedPan = "*******************".substring(0, las4index) + data.maskedPan.substring(las4index, panSize);
        withPinpad((p) -> {
            if (data.track2 != null) data.track2 = p.encryptHex(data.track2);
            if (data.emvData != null) data.emvData = p.encryptHex(data.emvData);
        });
        Log.d(Defaults.LOG_TAG, data.toString());
        //TODO elevar a otro handler de nivel aun mas superior
        if(goOnline != null) {
            goOnline.accept(data);
        } else {
            raiseError("pos", "online_handler_null");
        }

    }

    public void setPinpadTimeout(int timeout) {
        this.pinpad.setTimeout(timeout);
    }

    public void setTagList(int[] tagList) {
        this.emv.setTaglist(tagList);
    }

    public void loadAids(Collection<String> aids) {
        emv.initAIDS(aids.toArray(new String[aids.size()]));
    }

    public void loadCapks(Collection<String> capks) {
        emv.initCAPKS(capks.toArray(new String[capks.size()]));
    }

    public void loadAids(String[] aids) {
        emv.initAIDS(aids);
    }

    public void loadCapks(String[] capks) {
        emv.initCAPKS(capks);
    }

    private void configPinpad(Pinpad pinpad) {
        pinpad.setGUIConfiguration("sound", "true");
    }

    public Pinpad getPinpad() {
        return this.pinpad;
    }

    /**
     * Realiza una operacion atomica con el pinpad preservando el estado anterior.
     * Si el pinpad se encuentra abierto mantiene la sesion de trabajo abierta.
     *
     * @param consumer
     */
    public void withPinpad(Consumer<Pinpad> consumer) {
        boolean wasOpen = this.pinpad.isOpen();
        try {
            if (!wasOpen) this.getPinpad().open();
            consumer.accept(this.getPinpad());
        } finally {
            if (!wasOpen) this.getPinpad().close();
        }
    }

    /**
     * Realiza una operacion atomica de impresion.
     *
     * @param consumer
     */
    public void withPrinter(Consumer<Printer> consumer) {
        PrinterDevice device = printerDevice;
        try {
            device.open();
        } catch (DeviceException e) {
            //TODO convertir en excepciones nombradas y republicar
            Log.d(Defaults.LOG_TAG, "No se puede abrir la impresora", e);
            throw new RuntimeException(e);
        }
        consumer.accept(new Printer(device));
        try {
            device.close();
        } catch (DeviceException e) {
            Log.d(Defaults.LOG_TAG, "No se puede cerrar la impresora", e);
        }
    }

    public void beginTransaction(String date, String time, String tsc, String amount) {
        emv.reset();
        data = new TransactionData();
        emv.beginTransaction(
                date,
                time,
                tsc,
                amount
        );
    }


    /// para el control del pinpad

    private void waitForPinpad(String pan) {
        pinpad.open();

        if (!pinpad.listenForPinBlock(pan, this::pinpadEventResolved, this.digitsListener)) {
            onError.accept("pin", "startFailed");
            pinpad.close();
        }
    }

    private void pinpadEventResolved(OperationResult operationResult) {
        int code = operationResult.getResultCode();
        switch (code) {
            case OperationResult.SUCCESS:
                // cuando ha logrado tener el pinblock
                PINPadOperationResult pinPadOperationResult = (PINPadOperationResult) operationResult;
                byte [] data = pinPadOperationResult.getEncryptedPINBlock();
                String pinblock = Util.toHexString(data);
                this.data.pinblock = pinblock;
                if (this.pinpadCustomUI) {
                    if (this.onPinCaptured != null) {
                        this.onPinCaptured.run();
                    } else {
                        raiseError("pin", "request_handler_null");
                    }
                } else {
                    continueAfterPin();
                }
                break;
            case OperationResult.CANCEL:
                raiseError("pin", "cancel");
                break;
            case OperationResult.ERR_TIMEOUT:
                raiseError("pin", "timeout");
                break;
            default:
                raiseError("pin", ""+code);
                break;
        }
    }

    public void callPin() {
        if (data.maskedPan == null) this.data.maskedPan = emv.readTag(0x5A);
        String pan = this.data.maskedPan;
        this.waitForPinpad(pan);
    }

    protected void  requestPinToUser() {
        if(!this.isPinpadCustomUI()) {
            raiseError("pin","custom_ui_false");
        } else if (this.onPinRequested == null) {
            raiseError("pin","request_handler_null");
        } else {
            this.onPinRequested.run();
        }
    }

    public void continueAfterPin() {
        if ("msr".equals(data.captureType)) {
            processOnline();
        } else {
            this.emv.next();
        }
    }

    public void setOnPinRequested(Runnable onPinRequested) {
        this.onPinRequested = onPinRequested;
    }

    public void setOnPinCaptured(Runnable onPinCaptured) {
        this.onPinCaptured = onPinCaptured;
    }


    public void setOnError(BiConsumer<String, String> onError) {
        this.onError = onError;
    }

    protected void raiseError(String source, String payload) {
        Log.d(Defaults.LOG_TAG, "error: " + source + " " + payload);
        if (this.onError != null) onError.accept(source, payload);
    }

    protected boolean isPinpadCustomUI() {
        return pinpadCustomUI;
    }

    protected Msr getMsr() {
        return msr;
    }

    public void setPinpadCustomUI(boolean pinpadCustomUI) {
        this.pinpadCustomUI = pinpadCustomUI;
    }

    public void setDigitsListener(Consumer<Integer> digitsListener) {
        this.digitsListener = digitsListener;
    }

    public void setGoOnline(Consumer<TransactionData> goOnline) {
        this.goOnline = goOnline;
    }
}
