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


    @Override
    public String toString() {
        return "TransactionData{" +
                "captureType='" + captureType + '\'' +
                ", maskedPan='" + maskedPan + '\'' +
                ", bin='" + bin + '\'' +
                ", pinblock='" + pinblock + '\'' +
                ", panSequenceNumber='" + panSequenceNumber + '\'' +
                ", track2='" + track2 + '\'' +
                ", track2Clear='" + track2Clear + '\'' +
                ", emvData='" + emvData + '\'' +
                ", emvDataClear='" + emvDataClear + '\'' +
                '}';
    }
}
