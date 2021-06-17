package com.kriptops.wizarpos.cardlib;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;

import com.cloudpos.DeviceException;
import com.cloudpos.OperationResult;
import com.cloudpos.POSTerminal;
import com.cloudpos.msr.MSRDevice;
import com.cloudpos.pinpad.PINPadDevice;
import com.cloudpos.pinpad.PINPadOperationResult;
import com.cloudpos.printer.PrinterDevice;
import com.kriptops.wizarpos.cardlib.android.PosApp;
import com.kriptops.wizarpos.cardlib.db.IVController;
import com.kriptops.wizarpos.cardlib.db.MapIVController;
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
    private final IVController ivController;
    private boolean pinpadCustomUI;
    private Runnable onPinRequested;
    private Runnable onPinCaptured;
    private BiConsumer<String, String> onError;
    private BiConsumer<String, String> onWarning;
    private Consumer<Integer> digitsListener;
    private Consumer<TransactionData> goOnline;
    protected TransactionData data;
    private ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 100);


    public Pos(PosApp posApp) {
        this(posApp, new PosOptions());
    }

    public void beep() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }

    public Pos(PosApp posApp, PosOptions posOptions) {
        if (posOptions == null) {
            throw new IllegalArgumentException("posOptions is null");
        }
        // inicializa el manejador de vectores de inicializacion
        if (posOptions.getIvController() == null) {
            this.ivController = new MapIVController();
        } else {
            this.ivController = posOptions.getIvController();
        }

        this.terminal = POSTerminal.getInstance(posApp.getApplicationContext());
        this.pinPadDevice = (PINPadDevice) this.terminal.getDevice("cloudpos.device.pinpad");
        this.printerDevice = (PrinterDevice) this.terminal.getDevice("cloudpos.device.printer");
        this.msrDevice = (MSRDevice) this.terminal.getDevice("cloudpos.device.msr");
        //debe ir antes que la creacion del emv kernel
        this.msr = new Msr(msrDevice);
        this.emv = new Emv(this, posApp.getApplicationContext());
        this.pinpad = new Pinpad(pinPadDevice, this.ivController);
        this.withPinpad(this::configPinpad);
        //carga los AID y CAPK por defecto
        this.loadAids(Defaults.AIDS);
        this.loadCapks(Defaults.CAPKS);
        this.setTagList(Defaults.TAG_LIST);
        this.pinpad.setTimeout(Defaults.PINPAD_REQUEST_TIMEOUT);
        this.setPinpadCustomUI(false);
    }


    public void setPinLength(int minLen, int maxLen) {
        this.pinpad.setPinLength(minLen, maxLen);
    }

    public void setPinLength(int lenPin) {
        this.pinpad.setPinLength(lenPin, lenPin);
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
            if (data.track2 != null) {
                //Pure unmodified track2
                data.track2Bin = data.track2;
                //ASCII compatible track2 0 padded to octet
                if (data.track2.endsWith("F")) {
                    data.track2 = data.track2.substring(0, data.track2.length() - 1);
                }
                if (data.track2.length()%2==1){
                    data.track2 += "0";
                }
                if (data.track2Bin.length()%2==1){
                    data.track2Bin += "F";
                }
                data.track2PKCS5 = data.track2;
                data.track2BinPKCS5 = data.track2Bin;

                data.track2 = p.encryptHex(data.track2, Padding.F);
                data.track2PKCS5 = p.encryptHex(data.track2PKCS5, Padding.PKCS5);
                data.track2Bin = p.encryptHex(data.track2Bin, Padding.F);
                data.track2BinPKCS5 = p.encryptHex(data.track2BinPKCS5, Padding.PKCS5);
            }
            if (data.emvData != null) {
                data.emvDataClear = data.emvData;
                data.emvData = p.encryptHex(data.emvData);
            }
        });
        Log.d(Defaults.LOG_TAG, data.toString());
        //TODO elevar a otro handler de nivel aun mas superior
        if (goOnline != null) {
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

    /**
     * Inicia una transaccion.
     *
     * @param date     fecha en formato YYMM
     * @param time     hora en formato HHMMSS
     * @param tsc      contador de transaccion
     * @param amount   monto en formato ex2, por ejemplo 1000 representa 10.00
     * @param cashback indica si es una reversa
     */
    public void beginTransaction(String date, String time, String tsc, String amount, boolean cashback) {
        emv.reset();
        data = new TransactionData();
        emv.beginTransaction(
                date,
                time,
                tsc,
                amount,
                cashback
        );
    }

    /**
     * Inicia una transaccion.
     *
     * @param date   fecha en formato YYMM
     * @param time   hora en formato HHMMSS
     * @param tsc    contador de transaccion
     * @param amount monto en formato ex2, por ejemplo 1000 representa 10.00
     */
    public void beginTransaction(String date, String time, String tsc, String amount) {
        this.beginTransaction(date, time, tsc, amount, false);
    }


    /// para el control del pinpad

    /**
     * Accede al uso del pinpad y coordina su ingreso con funciones del pos
     *
     * @param pan
     */
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
                byte[] data = pinPadOperationResult.getEncryptedPINBlock();
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
                raiseError("pin", "" + code);
                break;
        }
    }

    /**
     * Se usa para iniciar el proceso de pedido de pin.
     */
    public void callPin() {
        if (data.maskedPan == null) this.data.maskedPan = emv.readTag(0x5A);
        String pan = this.data.maskedPan;
        this.waitForPinpad(pan);
    }

    protected void requestPinToUser() {
        if (!this.isPinpadCustomUI()) {
            raiseError("pin", "custom_ui_false");
        } else if (this.onPinRequested == null) {
            raiseError("pin", "request_handler_null");
        } else {
            this.onPinRequested.run();
        }
    }

    /**
     * Se usa para continuar procesando la transaccion despues de pedir el pin.
     */
    public void continueAfterPin() {
        if ("msr".equals(data.captureType)) {
            processOnline();
        } else {
            this.emv.next();
        }
    }

    /**
     * Configura el evento de escucha cuando se ha requerido el pin.
     *
     * @param onPinRequested
     */
    public void setOnPinRequested(Runnable onPinRequested) {
        this.onPinRequested = onPinRequested;
    }

    /**
     * Configura el evento de escucha cuando se ha capturado el pin.
     *
     * @param onPinCaptured
     */
    public void setOnPinCaptured(Runnable onPinCaptured) {
        this.onPinCaptured = onPinCaptured;
    }

    /**
     * Configura el evento de escucha cuando se ha generado un error.
     *
     * @param onError
     */
    public void setOnError(BiConsumer<String, String> onError) {
        this.onError = onError;
    }

    protected void raiseError(String source, String payload) {
        Log.d(Defaults.LOG_TAG, "error: " + source + " " + payload);
        if (this.onError != null) onError.accept(source, payload);
    }

    public void setOnWarning(BiConsumer<String, String> onWarning) {
        this.onWarning = onWarning;
    }

    protected void raiseWarning(String source, String payload) {
        Log.d(Defaults.LOG_TAG, "warning: " + source + " " + payload);
        if (this.onWarning != null) onWarning.accept(source, payload);
    }

    protected boolean isPinpadCustomUI() {
        return pinpadCustomUI;
    }

    protected Msr getMsr() {
        return msr;
    }

    /**
     * Indica que se usara un customUI para controlar el background del pin.
     *
     * @param pinpadCustomUI
     */
    public void setPinpadCustomUI(boolean pinpadCustomUI) {
        this.pinpadCustomUI = pinpadCustomUI;
    }

    /**
     * Configura el escucha de la cantidad de digitos ingresados del pin.
     *
     * @param digitsListener
     */
    public void setDigitsListener(Consumer<Integer> digitsListener) {
        this.digitsListener = digitsListener;
    }

    /**
     * Envia la respuesta para poder iniciar el proceso en linea.
     *
     * @param goOnline
     */
    public void setGoOnline(Consumer<TransactionData> goOnline) {
        this.goOnline = goOnline;
    }


}
