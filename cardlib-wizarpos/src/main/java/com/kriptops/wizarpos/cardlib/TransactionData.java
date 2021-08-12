package com.kriptops.wizarpos.cardlib;

public class TransactionData {

    public String captureType;

    //clear data
    public String maskedPan;
    public String bin;

    //protected data
    public String pinblock;
    public String panSequenceNumber;
    public String track2;
    public String track2Clear;
    public String emvData;
    public String emvDataClear;
    public String aid;


    @Override
    public String toString() {
        return "TransactionData{\n" +
                "    captureType:'" + captureType + "',\n" +
                "    maskedPan:'" + maskedPan + "',\n" +
                "    bin:'" + bin + "',\n" +
                "    panSequenceNumber:'" + panSequenceNumber + "',\n" +
                "    track2:'" + track2 + "',\n" +
                "    aid:'" + aid + "',\n" +
                "    emvData:'" + emvData + "',\n" +
                "    emvDataClear:'" + emvDataClear + "'\n" +
                "}";
    }
}
