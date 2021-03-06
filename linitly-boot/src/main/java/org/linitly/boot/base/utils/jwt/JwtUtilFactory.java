package org.linitly.boot.base.utils.jwt;

import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.enums.SystemEnum;
import org.linitly.boot.base.exception.CommonException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: linxiunan
 * @date: 2020/12/1 9:00
 * @descrption:
 */
public class JwtUtilFactory {

    public static AbstractJwtUtil getJwtUtil(Integer type) {
        if (SystemEnum.ADMIN.getSystemCode().equals(type)) {
            return JwtAdminUtil.getInstance();
        }
        throw new CommonException(ResultEnum.FACTORY_GET_ERROR);
    }

    public static AbstractJwtUtil getJwtUtil(HttpServletRequest request) {
        String header = request.getHeader(GlobalConstant.SYSTEM_CODE_KEY);
        if (StringUtils.isBlank(header)) throw new CommonException(ResultEnum.SYSTEM_CODE_ERROR);
        return getJwtUtil(Integer.valueOf(header));
    }
}
