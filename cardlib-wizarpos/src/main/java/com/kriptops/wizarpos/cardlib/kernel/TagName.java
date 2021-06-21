package com.kriptops.wizarpos.cardlib.kernel;

public enum TagName {

    PAN("5A"),
    Transaction_Currency_Code("5F2A"),
    PAN_Sequence_Number("5F34"),
    Application_Interchange_Profile("82"),
    DF_Name("84"),
    Terminal_Verification_Results("95"),
    Transaction_Date("9A"),
    Transaction_Type("9C"),
    Amount_Authorised_Numeric("9F02"),
    Amount_Other_Numeric("9F03"),
    Application_Version_Number("9F09"),
    Issuer_Application_Data("9F10"),
    Terminal_Country_Code("9F1A"),
    Application_Cryptogram("9F26"),
    Cryptogram_Information_Data("9F27"),
    Terminal_Capabilities("9F33"),
    CVM_Results("9F34"),
    Terminal_Type("9F35"),
    Application_Transaction_Counter("9F36"),
    Unpredictable_Number("9F37"),
    Additional_Terminal_Capabilities("9F40"),
    Transaction_Sequence_Counter("9F41"),
    IFD_Serial_Number("9F1E");

    public final int number;
    public final String value;

    TagName(String value) {
        this.value = value;
        this.number = Integer.parseInt(value, 16);
    }

}
