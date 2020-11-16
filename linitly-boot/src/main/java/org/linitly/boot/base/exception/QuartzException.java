package org.linitly.boot.base.exception;

import org.linitly.boot.base.enums.ExceptionResultEnum;
import org.linitly.boot.base.constant.global.GlobalConstant;

/**
 * @author linxiunan
 * @date 10:17 2020/6/30
 * @description
 */
public class QuartzException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer state;

    public QuartzException(String message) {
        super(message);
        this.state = GlobalConstant.GENERAL_ERROR;
    }

    public QuartzException(Integer state, String message) {
        super(message);
        this.state = state;
    }

    public QuartzException(ExceptionResultEnum exceptionResultEnum) {
        super(exceptionResultEnum.getMessage());
        this.state = exceptionResultEnum.getCode();
    }

    public Integer getCode() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
