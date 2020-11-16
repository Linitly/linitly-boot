package org.linitly.boot.base.exception;

import org.linitly.boot.base.enums.ExceptionResultEnum;

/**
 * 通用异常类
 *
 * @author linxiunan
 * @date 2018年7月25日
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer state;

    public CommonException(Integer state, String message) {
        super(message);
        this.state = state;
    }

    public CommonException(ExceptionResultEnum exceptionResultEnum) {
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
