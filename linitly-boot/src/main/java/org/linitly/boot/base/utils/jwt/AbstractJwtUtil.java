package org.linitly.boot.base.utils.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.global.JwtConstant;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.auth.AbstractAuth;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;

/**
 * @author: linxiunan
 * @date: 2020/11/22 17:00
 * @descrption:
 */
@Slf4j
public abstract class AbstractJwtUtil implements JwtUtil {

    public AbstractAuth abstractAuth;

    protected SignatureAlgorithm algorithm;

    protected String salt;

    AbstractJwtUtil(SignatureAlgorithm algorithm, String salt, AbstractAuth abstractAuth) {
        this.algorithm = algorithm;
        this.salt = salt;
        this.abstractAuth = abstractAuth;
    }

    @Override
    public Key getKeyInstance() {
        salt = StringUtils.isBlank(salt) ? JwtConstant.DEFAULT_JWT_SALT : salt;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(salt);
        return new SecretKeySpec(apiKeySecretBytes, algorithm.getJcaName());
    }

    @Override
    public Map<String, Object> parseJwt(String jwt) {
        return StringUtils.isBlank(jwt) ? null :
                Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
    }

    @Override
    public Map<String, Object> parseToken(String token) {
        Map<String, Object> claims = null;
        try {
            claims = parseJwt(token);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            log.error("【token解密失败】，token为: " + token);
            throw new CommonException(ResultEnum.TOKEN_ANALYSIS_ERROR);
        }
        return claims;
    }

    @Override
    public Map<String, Object> parseRefreshToken(String refreshToken) {
        Map<String, Object> claims;
        try {
            claims = parseJwt(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new CommonException(ResultEnum.LOGIN_FAILURE);
        } catch (Exception e) {
            log.error("【refresh_token解密失败】，refresh_token为: " + refreshToken);
            throw new CommonException(ResultEnum.TOKEN_ANALYSIS_ERROR);
        }
        return claims;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    public String getSalt() {
        return salt;
    }

    public AbstractAuth getAbstractAuth() {
        return abstractAuth;
    }
}
