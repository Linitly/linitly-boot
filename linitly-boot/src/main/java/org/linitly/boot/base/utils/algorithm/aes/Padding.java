package org.linitly.boot.base.utils.algorithm.aes;

public enum Padding {

    NO_PADDING("NoPadding"),
    ZERO_PADDING("ZeroPadding"),
    PKCS5_PADDING("PKCS5Padding"),
    PKCS7_PADDING("PKCS7Padding"),
    ISO10126_PADDING("ISO10126Padding"),
    ;


    private String value;

    Padding(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
