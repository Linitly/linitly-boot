package org.linitly.boot.base.utils.jwt;

import org.linitly.boot.base.constant.admin.AdminJwtConstant;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;

/**
 * @author linxiunan
 * @Description JWT加密工具类
 * @date 2018年12月25日
 */
public class JwtCommonUtil {

    public static Key getKeyInstance() {
        // Base64编码后的secretKey
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(AdminJwtConstant.JWT_SALT);
        Key secretKey = new SecretKeySpec(apiKeySecretBytes, AdminJwtConstant.ADMIN_ALGORITHM.getJcaName());
        return secretKey;
    }

    public static Map<String, Object> parseJwt(String jwt) {
        if (StringUtils.isBlank(jwt)) {
            return null;
        }
        return Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
    }

    public static void main(String[] args) {
        Map<String, Object> claims = parseJwt("eyJhbGciOiJIUzI1NiJ9.eyJvcGVuSWQiOiJvMnMyTndneWFMNUlZa1B4UHBYdFNFdmNkTFpvIiwidXNlclR5cGUiOjIsInV1aWQiOiIzYzQ5YzM2ZS00OTZhLTQ4ZDItODVkZC02YzhmOTM5OWY1NDEifQ.0HoBbUXuoRE0O9BDvskmUAGD6jyXXhMwmGxjwyrlAag");
        System.out.println(claims);
//        String token = JwtAdminUtil.generateProxyJwt("o2s2NwgyaL5IYkPxPpXtSEvcdLZo");
//        System.out.println(token);
    }
}
