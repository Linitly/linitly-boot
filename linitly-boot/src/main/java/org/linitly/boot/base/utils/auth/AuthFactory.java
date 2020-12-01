package org.linitly.boot.base.utils.auth;

import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.enums.SystemEnum;
import org.linitly.boot.base.exception.CommonException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: linxiunan
 * @date: 2020/11/30 16:37
 * @descrption:
 */
public class AuthFactory {

    public static AbstractAuth getAuth(Integer type) {
        if (SystemEnum.ADMIN.getSystemCode().equals(type)) {
            return AdminAuth.getInstance();
        }
        throw new CommonException(ResultEnum.FACTORY_GET_ERROR);
    }

    public static AbstractAuth getAuth(HttpServletRequest request) {
        String header = request.getHeader(GlobalConstant.SYSTEM_CODE_KEY);
        if (StringUtils.isBlank(header)) throw new CommonException(ResultEnum.SYSTEM_CODE_ERROR);
        return getAuth(Integer.valueOf(header));
    }
}
