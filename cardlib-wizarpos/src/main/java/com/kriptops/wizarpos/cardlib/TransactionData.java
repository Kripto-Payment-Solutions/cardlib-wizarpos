package com.kriptops.wizarpos.cardlib;

public class TransactionData {

    public String captureType;

    //protected data
    public String emvData;
    public String emvDataClear;
    public String pinblock;
    public String track2;
    public String track2PKCS5;
    public String track2Bin;
    public String track2BinPKCS5;
    public String panSequenceNumber;

    //clear data
    public String maskedPan;
    public String bin;

    @Override
    public String toString() {
        return "TransactionData{" +
                "captureType='" + captureType + '\'' +
                ", track2='" + track2 + '\'' +
                ", track2PKCS5='" + track2 + '\'' +
                ", track2Pure='" + track2 + '\'' +
                ", track2PurePKCS5='" + track2 + '\'' +
                ", emvData='" + emvData + '\'' +
                ", emvDataClear='" + emvDataClear + '\'' +
                ", pinblock='" + pinblock + '\'' +
                ", panSequenceNumber='" + panSequenceNumber + '\'' +
                ", maskedPan='" + maskedPan + '\'' +
                ", bin='" + bin + '\'' +
                '}';
    }
}
