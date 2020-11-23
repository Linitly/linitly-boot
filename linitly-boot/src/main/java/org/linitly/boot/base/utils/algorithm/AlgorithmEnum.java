package org.linitly.boot.base.utils.algorithm;

/**
 * @author: linxiunan
 * @date: 2020/11/23 10:32
 * @descrption:
 */
public enum AlgorithmEnum {

    AES("AES"),
    RSA("RSA"),
    ;

    private String value;

    AlgorithmEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
