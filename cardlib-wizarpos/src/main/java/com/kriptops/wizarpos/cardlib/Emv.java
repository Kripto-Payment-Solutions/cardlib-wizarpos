package com.kriptops.wizarpos.cardlib;

import android.content.Context;
import android.util.Log;

import com.cloudpos.OperationListener;
import com.cloudpos.OperationResult;
import com.cloudpos.jniinterface.EMVJNIInterface;
import com.cloudpos.jniinterface.IFuntionListener;
import com.cloudpos.msr.MSROperationResult;
import com.cloudpos.msr.MSRTrackData;
import com.kriptops.wizarpos.cardlib.func.Consumer;
import com.wizarpos.emvsample.constant.Constant;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.cloudpos.jniinterface.EMVJNIInterface.emv_get_tag_data;
import static com.cloudpos.jniinterface.EMVJNIInterface.emv_is_tag_present;

public class Emv {

    private static final int EMV_READER_ICC = 1;
    private static final int EMV_READER_NFC = 2;
    private static final int EMV_READER_ONLINE_ONLY = 2;

    ExecutorService emvCallbacks = Executors.newFixedThreadPool(4);

    private boolean iccOpenned = false;
    private boolean nfcOpenned = false;
    private boolean msrOpenned = false;
    private boolean cardInserted = false;
    private int[] taglist;
    private Consumer<TransactionData> processOnline;
    private Pos pos;

    public int[] getTaglist() {
        return taglist;
    }

    public void setTaglist(int[] taglist) {
        this.taglist = taglist;
    }

    protected void setProcessOnline(Consumer<TransactionData> consumer) {
        this.processOnline = consumer;
    }


    //debe ser llamado unsa sola vez en la creacion del app
    public Emv(Pos pos, Context context) {
        this.pos = pos;
        //lib path
        String tmpEmvLibDir = getLibPath(context.getApplicationContext());
        byte[] data = tmpEmvLibDir.getBytes();
        EMVJNIInterface.registerFunctionListener(new EmvListener());
        pos.getMsr().setListener(new MsrListener());
        try {
            int res = EMVJNIInterface.loadEMVKernel(data, data.length);
            //on first init res is 0
            if (res == 0) {
                EMVJNIInterface.emv_kernel_initialize();
                EMVJNIInterface.emv_set_kernel_attr(new byte[]{0x20}, 1);
                EMVJNIInterface.emv_terminal_param_set_drl(new byte[]{0x00}, 1);
                EMVJNIInterface.emv_set_force_online(1);
                Log.i(Defaults.LOG_TAG, "kernel id:" + EMVJNIInterface.emv_get_kernel_id());
                Log.i(Defaults.LOG_TAG, "process type:" + EMVJNIInterface.emv_get_process_type());
            }
        } catch (Exception ex) {
            Log.d(Defaults.LOG_TAG, "no se puede inicializar el kernel en loademv kernel");
            throw new RuntimeException("No se puede inicializar el kernel EMV", ex);
        }
    }

    private void performAntishake() {
        Log.d(Defaults.LOG_TAG, "PERFORM THE ANTISHAKE");
        emvCallbacks.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(Defaults.LOG_TAG, "Esperando Otras tarjetas");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Emv.this.cardInserted) {
                    Log.d(Defaults.LOG_TAG, "No procede NFC, otra interfaz tiene la tarjeta");
                    EMVJNIInterface.emv_anti_shake_finish(1);
                } else {
                    Log.d(Defaults.LOG_TAG, "Va adelante NFC, no se ha sensado tarjeta en ICC o MSR");
                    EMVJNIInterface.emv_anti_shake_finish(0);
                }
            }
        });
    }

    private void processCardInserted() {
        Log.d(Defaults.LOG_TAG, "CARD INSERTED");
        this.cardInserted = true;
        this.setCaptureType();
        this.next();
    }

    public void initCAPKS(String[] capks) {
        EMVJNIInterface.emv_capkparam_clear();
        for (String capk : capks) {
            Log.d(Defaults.LOG_TAG, "Agregando CAPK " + capk);
            byte[] buffer = Util.toByteArray(capk);
            EMVJNIInterface.emv_capkparam_add(buffer, buffer.length);
        }
    }

    public void initAIDS(String[] aids) {
        EMVJNIInterface.emv_aidparam_clear();
        for (String aid : aids) {
            Log.d(Defaults.LOG_TAG, "Agregando AID " + aid);
            byte[] buffer = Util.toByteArray(aid);
            EMVJNIInterface.emv_aidparam_add(buffer, buffer.length);
        }
    }

    /**
     * @param merchantId         9F16 identificador del comercio maximo 15 caracteres completado con espacio al final
     * @param merchantName       9F4E nombre del comercio ancho variable en caracteres
     * @param terminalId         9F1C identificador del pos dentro del comercio maximo 15 caracteres completado con espacio al final
     * @param serialNumber
     * @param clFloorLimit
     * @param clTransactionLimit
     * @param cvmLimit
     */
    public void initParams(String merchantId, String merchantName, String terminalId, String serialNumber, String clFloorLimit, String clTransactionLimit, String cvmLimit) {
        if (merchantId == null) merchantId = "";
        merchantId = (merchantId + "               ").substring(0, 15);
        if (terminalId == null) terminalId = "";
        terminalId = (terminalId + "               ").substring(8);
        if (serialNumber == null) serialNumber = "0";
        serialNumber = "00000000" + serialNumber;
        serialNumber = serialNumber.substring(serialNumber.length() - 8, serialNumber.length());
        Tag[] tags = new Tag[]{
                new Tag("5F2A", "0604"),
                new Tag("5F36", "02"),
                // merchant id should be padded with ' ' to 15 or truncated
                new Tag("9F16", Util.toHexString(merchantId.getBytes())),
                new Tag("9F1A", "0604"),
                new Tag("9F1C", Util.toHexString(terminalId.getBytes())),
                new Tag("9F1E", Util.toHexString(serialNumber.getBytes())),
                new Tag("9F33", "E0F8C8"),
                new Tag("9F35", "22"),
                new Tag("9F40", "FF80F0A001"),
                new Tag("9F4E", Util.toHexString(merchantName.getBytes())),
                new Tag("9F66", "36"),
                new Tag("DF19", clFloorLimit),
                new Tag("DF20", clTransactionLimit),
                new Tag("DF21", cvmLimit),
                //Privado
                new Tag("EF01", "00")
        };
        String tlvHex = Tag.compile(tags);
        Log.d(Defaults.LOG_TAG, "cargando parametros " + tlvHex);
        byte[] data = Util.toByteArray(tlvHex);
        int response = EMVJNIInterface.emv_terminal_param_set_tlv(data, data.length);
        Log.d(Defaults.LOG_TAG, "respuesta de carga de parametros " + response);
    }

    public void loadCAPKs(String[] capks) {
        EMVJNIInterface.emv_capkparam_clear();
        for (String capk : capks) {
            byte[] data = Util.toByteArray(capk);
            EMVJNIInterface.emv_capkparam_add(data, data.length);
        }
    }

    public void loadAIDs(String[] aids) {
        EMVJNIInterface.emv_aidparam_clear();
        for (String aid : aids) {
            byte[] data = Util.toByteArray(aid);
            EMVJNIInterface.emv_aidparam_add(data, data.length);
        }
    }

    /**
     * @param amount                     monto en formato numerico sin decimales (los centavos son los dos ultimos digitos)
     * @param date                       en texto en formato yyMMdd
     * @param time                       en texto en formato HHmmss
     * @param transactionSequenceCounter contador de transaccion en formato 00000000
     * @param cashback                   indica si es una reversa
     */
    public void beginTransaction(String date, String time, String transactionSequenceCounter, String amount, boolean cashback) {
        EMVJNIInterface.emv_trans_initialize();
        //BIENES Y SERVICIOS
        this.setTag(0x9a, date);
        this.setTag(0x9f21, time);
        this.setTag(0x9f41, transactionSequenceCounter);
        if (cashback) {
            EMVJNIInterface.emv_set_trans_type(Constant.EMV_TRANS_CASHBACK);
        } else {
            EMVJNIInterface.emv_set_trans_type(Constant.EMV_TRANS_GOODS_SERVICE);
        }
        this.setAmount(amount);

        EMVJNIInterface.emv_set_force_online(EMV_READER_ONLINE_ONLY);

        if (!iccOpenned) {
            iccOpenned = true;
            EMVJNIInterface.open_reader(EMV_READER_ICC);
            Log.d(Defaults.LOG_TAG, "Reading Contact");
        }

        if (!nfcOpenned) {
            nfcOpenned = true;
            EMVJNIInterface.emv_set_anti_shake(1);
            EMVJNIInterface.open_reader(EMV_READER_NFC);
            Log.d(Defaults.LOG_TAG, "Reading Contactless");
        }

        if (!msrOpenned) {
            msrOpenned = true;
            pos.getMsr().open();
            pos.getMsr().waitForTrack();
            Log.d(Defaults.LOG_TAG, "Reading MSR");
        }
    }

    public int setAmount(String amount) {
        byte[] amountBuffer = amount.getBytes();
        byte[] buffer = new byte[amount.length() + 1];
        System.arraycopy(amountBuffer, 0, buffer, 0, amountBuffer.length);
        return EMVJNIInterface.emv_set_trans_amount(buffer);
    }

    public int setTag(int tag, String hexPayload) {
        byte[] buffer = Util.toByteArray(hexPayload);
        return EMVJNIInterface.emv_set_tag_data(tag, buffer, buffer.length);
    }

    public int next() {
        Log.d(Defaults.LOG_TAG, "======> EMV NEXT ");
        return EMVJNIInterface.emv_process_next();
    }

    public int setCaptureType() {
        CardType cardType = this.getCardType();
        Log.d(Defaults.LOG_TAG, "kernel para " + cardType);
        switch (cardType) {
            case ICC:
                pos.data.captureType = "icc";
                EMVJNIInterface.close_reader(2);
                pos.getMsr().close();
                return EMVJNIInterface.emv_set_kernel_type(Constant.CONTACT_EMV_KERNAL);
            case NFC:
                pos.data.captureType = "nfc";
                EMVJNIInterface.close_reader(1);
                pos.getMsr().close();
                return EMVJNIInterface.emv_set_kernel_type(Constant.CONTACTLESS_EMV_KERNAL);
        }
        return -1;
    }

    public CardType getCardType() {
        switch (EMVJNIInterface.get_card_type()) {
            case 1:
                return CardType.ICC;
            case 2:
                return CardType.NFC;
        }
        return null;
    }

    public enum CardType {
        MSR,
        ICC,
        NFC
    }


    public void reset() {
        EMVJNIInterface.close_reader(1);
        EMVJNIInterface.close_reader(2);
        pos.getMsr().close();
        cardInserted = false;
        iccOpenned = false;
        nfcOpenned = false;
    }

    private void readAppData() {
        pos.data.maskedPan = this.readTag(0x5A);
        pos.data.track2 = this.readTag(0x57);
        pos.data.panSequenceNumber = this.readTag(0x5F34);
    }

    private void requestPin() {
        if (pos.isPinpadCustomUI()) {
            pos.requestPinToUser();
        } else {
            pos.callPin();
        }
    }

    private void processOnline() {
        TransactionData data = pos.data;
        if ("nfc".equals(data.captureType) || "icc".equals(data.captureType)) {
            int[] adjustedTagList = new int[taglist.length + 3];
            adjustedTagList[0] = 0x57;
            adjustedTagList[1] = 0x5A;
            adjustedTagList[1] = 0x5F34;
            System.arraycopy(taglist, 0, adjustedTagList, 2, taglist.length);
            data.emvData = readTags(adjustedTagList);
            if (data.emvData.startsWith("57")) {
                int length = Util.toByteArray(data.emvData.substring(2, 4))[0] * 2;
                String track2 = data.emvData.substring(4, 4 + length);
                data.emvData = data.emvData.substring(4 + length);
                if (data.track2 == null) data.track2 = track2;
            }
            if (data.emvData.startsWith("5A")) {
                int length = Util.toByteArray(data.emvData.substring(2, 4))[0] * 2;
                String pan = data.emvData.substring(4, 4 + length);
                data.emvData = data.emvData.substring(4 + length);
                if (data.maskedPan == null) data.maskedPan = pan;
            }
            if (data.emvData.startsWith("5F34")) {
                int length = Util.toByteArray(data.emvData.substring(4, 6))[0] * 2;
                String psn = data.emvData.substring(6, 6 + length);
                data.emvData = data.emvData.substring(6 + length);
                if (data.panSequenceNumber == null) data.panSequenceNumber = psn;
            }
            if (data.maskedPan == null && data.track2 != null) {
                data.maskedPan = data.track2.split("[D=]")[0];
            }
        }
        pos.processOnline();
    }

    private String readTags(int[] tags) {
        byte[] buffer = new byte[10240];
        int readSize = EMVJNIInterface.emv_get_tag_list_data(
                tags,
                tags.length,
                buffer,
                buffer.length
        );
        byte[] tagsMaterial = new byte[readSize];
        System.arraycopy(buffer, 0, tagsMaterial, 0, readSize);
        buffer = null;
        return Util.toHexString(tagsMaterial);
    }

    protected String readTag(int tag) {
        if (isTagPresent(tag)) {
            byte[] buffer = new byte[1024];
            int read_length = emv_get_tag_data(tag, buffer, buffer.length);
            if (read_length < 0) return null;
            if (read_length == 0) return "";
            byte[] tagData = new byte[read_length];
            System.arraycopy(buffer, 0, tagData, 0, read_length);
            return Util.toHexString(tagData);
        }
        return null;
    }

    private boolean isTagPresent(int tag) {
        return emv_is_tag_present(tag) >= 0;
    }

    private class MsrListener implements OperationListener {

        @Override
        public void handleResult(OperationResult operationResult) {
            pos.getMsr().close();
            msrOpenned = false;
            int code = operationResult.getResultCode();
            switch (code) {
                case OperationResult.SUCCESS:
                    MSROperationResult result = (MSROperationResult) operationResult;
                    MSRTrackData data = result.getMSRTrackData();
                    if (data.getTrackError(1) == MSRTrackData.NO_ERROR) {
                        cardInserted = true;
                        pos.data.captureType = "msr";
                        EMVJNIInterface.close_reader(0);
                        EMVJNIInterface.close_reader(1);
                        String track2ascii = new String(data.getTrackData(1), StandardCharsets.ISO_8859_1);
                        String track2Hex = new String(track2ascii).trim().replace('=', 'D');
                        pos.data.track2 = track2Hex;
                        pos.data.maskedPan = track2Hex.split("D")[0];

                        //Strip cardnumber && strip countryCode
                        String serviceCode = track2ascii.substring(track2ascii.indexOf('='));
                        if (track2ascii.startsWith("59")) serviceCode = serviceCode.substring(3);
                        while (serviceCode.startsWith("="))
                            serviceCode = serviceCode.substring(1);//remueve la fecha si no esta presente
                        serviceCode = serviceCode.substring(0, 3);

                        Log.d(Defaults.LOG_TAG, serviceCode);
                        char sc1 = serviceCode.charAt(0);
                        switch (sc1) {
                            case '2':
                            case '6':
                                pos.raiseError("msr", "require_emv");
                                return;
                            case '7':
                                pos.raiseError("msr", "not_available");
                                return;
                        }
                        char sc3 = serviceCode.charAt(2);
                        switch (sc3) {
                            case '3': //ATM
                            case '4': //CASH WITHDRAWL
                                pos.raiseError("msr", "not_available");
                                return;
                            case '0': //NR Pin Mandatory
                            case '5': //G&S Pin Mandatory
                            case '6': //NR Pin Feasible
                            case '7': //G&S Pin Feasible
                                Emv.this.requestPin();
                                return;
                        }
                        //falta decidir si debemos ir al online o a pedir pin, para eso se usa el service code
                        //falta decidir si la tarjeta debe pasar por el chip
                        processOnline();
                    }
                    break;
                case OperationResult.ERR_TIMEOUT:
                    pos.raiseError("msr", "timeout");
                    break;
                default:
                    pos.raiseError("msr", "" + code);
            }
        }
    }

    private class EmvListener implements IFuntionListener {
        @Override
        public void emvProcessCallback(byte[] bytes) {
            byte status = bytes[0];
            byte code = bytes[1];
            switch (status) {
                case 0x00:
                    switch (code) {
                        case 10:
                            pos.raiseError("emv", "nfc_icc_fallbackk");
                            break;
                        case 12:
                            pos.raiseError("emv", "retry");
                            break;
                        default:
                            pos.raiseError("emv", "" + code);
                            break;
                    }
                    // error cerrar los lectores y permitir reiniciar la operacion
                    Log.d(Defaults.LOG_TAG, "EMV ERROR " + code);
                    //TODO enviar al error handler
                    break;
                case 0x01:
                    // aun hay mas pasos que hacer esperar y evaluar
                    Log.d(Defaults.LOG_TAG, "EMV STAGE IS " + code);
                    switch (code) {
                        case Constant.EMV_READ_APP_DATA:
                            Emv.this.emvCallbacks.execute(Emv.this::next);
                            Emv.this.readAppData();
                            break;
                        case Constant.EMV_ONLINE_ENC_PIN:
                            Emv.this.requestPin();
                            break;
                        case Constant.EMV_PROCESS_ONLINE:
                            Emv.this.processOnline();
                            break;
                        case Constant.EMV_CANDIDATE_LIST:
                        case Constant.EMV_APP_SELECTED:
                        case Constant.EMV_DATA_AUTH:
                        case Constant.EMV_OFFLINE_PIN:
                        default:
                            Emv.this.emvCallbacks.execute(Emv.this::next);
                            break;
                    }
                case 0x02:
                    //ha terminado la transaccion
                    Log.d(Defaults.LOG_TAG, "TRANSACTION RESULT " + code);
                    //TODO enviar al success o failed handler
                    break;
            }
        }

        @Override
        public void cardEventOccured(int i) {
            switch (i) {
                case Constant.SMART_CARD_EVENT_INSERT_CARD:
                    Emv.this.processCardInserted();
                    break;
                case Constant.SMART_CARD_EVENT_CONTALESS_ANTI_SHAKE:
                    Emv.this.performAntishake();
                    break;
                case Constant.SMART_CARD_EVENT_REMOVE_CARD:
                    Log.d(Defaults.LOG_TAG, "CARD REMOVED");
                    break;
                case Constant.SMART_CARD_EVENT_POWERON_ERROR:
                    Log.d(Defaults.LOG_TAG, "CARD ERROR");
                    break;
                default:
                    Log.d(Defaults.LOG_TAG, "CARD EVENT " + i);
            }
        }
    }

    private static String getLibPath(Context context) {
        //get app path
        String libPath = context.getDir("", 0).getAbsolutePath();
        //trim to base path
        int end = libPath.lastIndexOf('/');
        libPath = libPath.substring(0, end);
        //append library name
        libPath += "/lib/libEMVKernal.so";
        return libPath;
    }

}
