package org.linitly.boot.base.utils.algorithm.rsa;

public enum KeySize {

    L512(512),
    L1024(1024),
    L2048(2048),
    L4096(4096),
    ;

    private Integer value;

    KeySize(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
