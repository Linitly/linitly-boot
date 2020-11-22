package org.linitly.boot.base.utils.jwt;

import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.enums.SystemEnum;
import org.linitly.boot.base.exception.CommonException;

/**
 * @author: linxiunan
 * @date: 2020/11/22 20:27
 * @descrption:
 */
public class JwtUtilFactory {

    public static AbstractJwtUtil getJwtUtil(Integer type) {
        if (SystemEnum.ADMIN.getSystemCode().equals(type)) {
            return JwtAdminUtil.getInstance();
        }
        throw new CommonException(ResultEnum.FACTORY_GET_ERROR);
    }
}
