package org.linitly.boot.base.exception;

import org.linitly.boot.base.enums.ResultEnum;

/**
 * @author: linxiunan
 * @date: 2020/12/10 11:50
 * @descrption: 文件转换异常
 */
public class FileResolverException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private Integer state;

    public FileResolverException(Integer state, String message) {
        super(message);
        this.state = state;
    }

    public FileResolverException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.state = resultEnum.getCode();
    }

    public Integer getState() {
        return state;
    }
}
