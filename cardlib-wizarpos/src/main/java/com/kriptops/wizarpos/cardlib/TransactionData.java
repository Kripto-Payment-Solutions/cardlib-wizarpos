package com.kriptops.wizarpos.cardlib;

public class TransactionData {

    public String captureType;

    //protected data
    public String emvData;
    public String pinblock;
    public String track2;

    //clear data
    public String maskedPan;
    public String bin;

    @Override
    public String toString() {
        return "TransactionData{" +
                "captureType='" + captureType + '\'' +
                ", emvData='" + emvData + '\'' +
                ", pinblock='" + pinblock + '\'' +
                ", track2='" + track2 + '\'' +
                ", maskedPan='" + maskedPan + '\'' +
                ", bin='" + bin + '\'' +
                '}';
    }
}
