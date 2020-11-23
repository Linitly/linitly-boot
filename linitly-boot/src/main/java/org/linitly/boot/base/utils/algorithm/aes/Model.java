package org.linitly.boot.base.utils.algorithm.aes;

public enum Model {

    CBC("CBC"),
    ECB("ECB"),
    CFB("CFB"),
    CTR("CTR"),
    OFB("OFB"),
    ;

    private String value;

    Model(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
