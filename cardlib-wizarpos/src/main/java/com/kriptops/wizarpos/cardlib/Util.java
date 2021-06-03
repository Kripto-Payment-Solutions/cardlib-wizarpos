package com.kriptops.wizarpos.cardlib;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Util {

    /**
     * Convierte un hex string en un arreglo de bytes
     *
     * @param hex cadena hex
     * @return arreglo de bytes
     */
    public static byte[] toByteArray(String hex) {
        return toByteArray(hex, false);
    }

    /**
     * Convierte un hex string en un arreglo de bytes y agrega FF padding hasta completar un octeto.
     *
     * @param hex         cadena hex
     * @param withPadding indicador de completar padding
     * @return arreglo de bytes completado al octeto con 0xF
     */
    public static byte[] toByteArray(String hex, boolean withPadding) {
        if (hex == null) return null;
        if (withPadding) hex = padHex(hex);
        try {
            return Hex.decodeHex(hex.toLowerCase().toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param text texto a codificar bytes en hex
     * @return
     */
    public static String toHexString(String text) {
        return toHexString(text, false);
    }

    /**
     *
     * @param data
     * @return
     */
    public static String toHexString(byte[] data) {
        return toHexString(data, false);
    }

    /**
     *
     * @param text texto a codificar bytes en hex
     * @param withPadding indica si se completa el hex string con F hasta completar un octeto
     * @return
     */
    public static String toHexString(String text, boolean withPadding) {
        if (text == null) return null;
        return toHexString(text.getBytes(), withPadding);
    }

    /**
     *
     * @param data
     * @param withPadding
     * @return
     */
    public static String toHexString(byte[] data, boolean withPadding) {
        if (data == null) return null;
        String hex = new String(Hex.encodeHex(data)).toUpperCase();
        if (withPadding) hex = padHex(hex);
        return hex;
    }

    /**
     *
     * @param hex
     * @return
     */
    public static String padHex(String hex) {
        while (hex.length() == 0 || hex.length() % 16 != 0) hex += "F";
        return hex;
    }

}
