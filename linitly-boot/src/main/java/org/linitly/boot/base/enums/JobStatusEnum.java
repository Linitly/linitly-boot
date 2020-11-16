package org.linitly.boot.base.enums;

/**
 * @author: linxiunan
 * @date: 2020/5/27 9:45
 * @descrption:
 */
public enum JobStatusEnum {

    RUNNING(1),
    PAUSED(2),
    ;

    private Integer status;

    JobStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
}
