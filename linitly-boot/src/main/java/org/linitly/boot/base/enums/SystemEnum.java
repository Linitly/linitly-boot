package org.linitly.boot.base.enums;

/**
 * @author: linxiunan
 * @date: 2020/11/22 20:24
 * @descrption:
 */
public enum SystemEnum {

    ADMIN(101, "/admin"),
    ;

    private Integer systemCode;

    private String urlPrefix;

    SystemEnum(Integer systemCode, String urlPrefix) {
        this.systemCode = systemCode;
        this.urlPrefix = urlPrefix;
    }

    public Integer getSystemCode() {
        return systemCode;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }
}
