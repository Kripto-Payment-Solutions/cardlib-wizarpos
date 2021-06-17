package com.kriptops.wizarpos.cardlib;

public enum Padding {
    ZERO() {
        @Override
        public String pad(String text) {
            if (text.length() % 2 == 1)
                throw new IllegalArgumentException("Length must be octect compatible");
            while (text.length() % 16 != 0) {
                text += '0';
            }
            return text;
        }
    },
    F() {
        @Override
        public String pad(String text) {
            if (text.length() % 2 == 1)
                throw new IllegalArgumentException("Length must be octect compatible");
            while (text.length() % 16 != 0) {
                text += 'F';
            }
            return text;
        }
    },
    PKCS5() {
        @Override
        public String pad(String text) {
            if (text.length() % 2 == 1) {
                throw new IllegalArgumentException("Length must be octect compatible");
            }
            int octect_size = text.length() / 2;
            int pad = 8 - octect_size % 8;
            String padString = "0" + pad;
            for (int i = 0; i < pad; i++) text += padString;
            return text;
        }
    };

    public abstract String pad(String text);
}
