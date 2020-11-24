package org.linitly.boot.base.utils;

import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.exception.CommonException;

import javax.annotation.Nullable;

/**
 * @author: linxiunan
 * @date: 2020/11/24 11:14
 * @descrption:
 */
public class Precondition {

    public static <T> T checkNotNull(T reference, @Nullable String errorMessage) {
        if (reference == null) {
            throw new CommonException(StringUtils.isBlank(errorMessage) ? "空指针" : String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, ResultEnum resultEnum) {
        if (reference == null) {
            throw new CommonException(resultEnum);
        } else {
            return reference;
        }
    }
}
