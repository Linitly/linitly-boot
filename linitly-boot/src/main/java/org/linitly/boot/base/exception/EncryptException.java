/**
 * @author: linxiunan
 * @date: 2020/4/29 10:33
 * @descrption:
 */
package org.linitly.boot.base.exception;

import org.linitly.boot.base.enums.ExceptionResultEnum;
import org.linitly.boot.base.constant.global.GlobalConstant;

/**
 * @author: linxiunan
 * @date: 2020/4/29 10:33
 * @descrption:
 */
public class EncryptException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer state;

    public EncryptException(String message) {
        super(message);
        this.state = GlobalConstant.GENERAL_ERROR;
    }

    public EncryptException(Integer state, String message) {
        super(message);
        this.state = state;
    }

    public EncryptException(ExceptionResultEnum exceptionResultEnum) {
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
