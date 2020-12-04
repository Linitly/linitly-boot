package org.linitly.boot;

import org.linitly.boot.base.helper.entity.BaseEntity;
import org.linitly.boot.base.utils.jwt.AbstractJwtUtil;
import org.linitly.boot.base.utils.jwt.JwtUtilFactory;

/**
 * @author: linxiunan
 * @date: 2020/11/22 22:15
 * @descrption:
 */
public class JwtTest {

    public static void main(String[] args) {
        AbstractJwtUtil jwtUtil = JwtUtilFactory.getJwtUtil(101);
        String token = jwtUtil.generateToken(new BaseEntity().setId(1l));
        String refreshToken = jwtUtil.generateRefreshToken(new BaseEntity().setId(1l));
        System.out.println(token);
        System.out.println(refreshToken);
    }
}
