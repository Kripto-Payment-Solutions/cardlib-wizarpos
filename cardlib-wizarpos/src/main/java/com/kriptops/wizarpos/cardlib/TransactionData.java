package com.kriptops.wizarpos.cardlib;

public class TransactionData {

    public String captureType;

    //protected data
    public String emvData;
    public String emvDataClear;
    public String pinblock;
    public String track2;
    public String panSequenceNumber;

    //clear data
    public String maskedPan;
    public String bin;

    @Override
    public String toString() {
        return "TransactionData{" +
                "captureType='" + captureType + '\'' +
                ", emvData='" + emvData + '\'' +
                ", emvDataClear='" + emvDataClear + '\'' +
                ", pinblock='" + pinblock + '\'' +
                ", track2='" + track2 + '\'' +
                ", panSequenceNumber='" + panSequenceNumber + '\'' +
                ", maskedPan='" + maskedPan + '\'' +
                ", bin='" + bin + '\'' +
                '}';
    }
}
