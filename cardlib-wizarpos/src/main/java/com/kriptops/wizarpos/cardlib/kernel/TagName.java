package com.kriptops.wizarpos.cardlib.kernel;

public enum TagName {
    TRANSACTION_CURRENCY_CODE("5F2A"),
    TRANSACTION_CURRENCY_EXPONENT("5F36"),
    MERCHANT_IDENTIFIER("9F16"),
    TERMINAL_COUNTRY_CODE("9F1A"),
    TERMINAL_IDENTIFICATION("9F1C"),
    IFD_SERIAL_NUMBER("9F1E"),
    TERMINAL_CAPABILITIES("9F33"),
    TERMINAL_TYPE("9F35"),
    ADDITIONAL_TERMINAL_CAPABILITIES("9F40"),
    MERCHANT_NAME_AND_LOCATION("9F4E"),
    TTQ_1("9F66"),
    WP_CONTACTLESS_FLOOR_LIMIT("DF19"),
    WP_CONTACTLESS_TRANSACTION_LIMIT("DF20"),
    WP_CONTACTLESS_CVM_LIMIT("DF21"),
    WP_STATUS_CHECK_SUPPORT("EF01"),
    RID("9F06"),
    CAPK_INDEX("9F22"),
    CAPK_EXPIRATION_DATE("DF05"),
    CAPK_HASH_ALGORITHM_INDICATOR("DF06"),
    CAPK_ALGORITHM_INDICATOR("DF07"),
    CAPK_MODULUS("DF02"),
    CAPK_EXPONENT("DF04"),
    CAPK_CHECKSUM("DF03");

    public final int number;
    public final String value;

    TagName(String value) {
        this.value = value;
        this.number = Integer.parseInt(value, 16);
    }

    public Tag tag(String value) {
        return new Tag(this.value, value);
    }

}
