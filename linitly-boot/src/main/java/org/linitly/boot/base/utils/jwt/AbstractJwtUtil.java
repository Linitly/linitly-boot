package org.linitly.boot.base.utils.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.global.JwtConstant;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;

/**
 * @author: linxiunan
 * @date: 2020/11/22 17:00
 * @descrption:
 */
public abstract class AbstractJwtUtil implements JwtUtil {

    protected SignatureAlgorithm algorithm;

    protected String salt;

    public AbstractJwtUtil(SignatureAlgorithm algorithm, String salt) {
        this.algorithm = algorithm;
        this.salt = salt;
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

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    public String getSalt() {
        return salt;
    }
}
