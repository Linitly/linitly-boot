package org.linitly.boot.base.exception;

import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.enums.ResultEnum;

/**
 * 通用异常类
 *
 * @author linxiunan
 * @date 2018年7月25日
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer state;

    public CommonException(String message) {
        super(message);
        this.state = GlobalConstant.GENERAL_ERROR;
    }

    public CommonException(Integer state, String message) {
        super(message);
        this.state = state;
    }

    public CommonException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.state = resultEnum.getCode();
    }

    public Integer getCode() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
