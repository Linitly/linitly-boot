package org.linitly.boot.base.utils.auth;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.utils.algorithm.EncryptionUtil;
import org.linitly.boot.base.utils.bean.SpringBeanUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: linxiunan
 * @date: 2020/11/30 13:11
 * @descrption:
 */
public abstract class AbstractAuth implements AuthRedis {

    protected static RedisTemplate<String, Object> redisTemplate = SpringBeanUtil.getBean("redisTemplate", RedisTemplate.class);
    protected String idSalt;
    protected String tokenKeyPrefix;
    protected String refreshTokenKeyPrefix;
    protected String lastExpiredTokenKeyPrefix;
    protected String deptKeyPrefix;
    protected String postKeyPrefix;
    protected String roleKeyPrefix;
    protected String functionPermissionKeyPrefix;
    protected long tokenExpireTime;
    protected long refreshTokenExpireTime;
    protected long lastExpiredTokenExpireTime;
    protected long deptExpireTime;
    protected long postExpireTime;
    protected long roleExpireTime;
    protected long functionPermissionExpireTime;

    protected AbstractAuth(String idSalt, String tokenKeyPrefix, String refreshTokenKeyPrefix, String lastExpiredTokenKeyPrefix,
                           String deptKeyPrefix, String postKeyPrefix, String roleKeyPrefix, String functionPermissionKeyPrefix,
                           long tokenExpireTime, long refreshTokenExpireTime, long lastExpiredTokenExpireTime, long deptExpireTime,
                           long postExpireTime, long roleExpireTime, long functionPermissionExpireTime) {
        this.idSalt = idSalt;
        this.tokenKeyPrefix = tokenKeyPrefix;
        this.refreshTokenKeyPrefix = refreshTokenKeyPrefix;
        this.lastExpiredTokenKeyPrefix = lastExpiredTokenKeyPrefix;
        this.deptKeyPrefix = deptKeyPrefix;
        this.postKeyPrefix = postKeyPrefix;
        this.roleKeyPrefix = roleKeyPrefix;
        this.functionPermissionKeyPrefix = functionPermissionKeyPrefix;
        this.tokenExpireTime = tokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.lastExpiredTokenExpireTime = lastExpiredTokenExpireTime;
        this.deptExpireTime = deptExpireTime;
        this.postExpireTime = postExpireTime;
        this.roleExpireTime = roleExpireTime;
        this.functionPermissionExpireTime = functionPermissionExpireTime;
    }

    @Override
    public void setRedisToken(String id, String token) {
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(token))
            redisTemplate.opsForValue().set(getTokenKey(id), token, tokenExpireTime, TimeUnit.SECONDS);
    }

    @Override
    public void setRedisRefreshToken(String id, String refreshToken) {
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(refreshToken))
            redisTemplate.opsForValue().set(getRefreshTokenKey(id), refreshToken, refreshTokenExpireTime, TimeUnit.SECONDS);
    }

    @Override
    public void setLastExpiredToken(String id, String lastExpiredToken) {
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(lastExpiredToken))
            redisTemplate.opsForValue().set(getLastExpiredTokenKey(id), lastExpiredToken, lastExpiredTokenExpireTime, TimeUnit.SECONDS);
    }

    @Override
    public void setRedisDepts(String id, Set depts) {
        if (StringUtils.isNotBlank(id) && CollectionUtils.isNotEmpty(depts)) {
            redisTemplate.opsForSet().add(getDeptKey(id), depts.toArray());
            expireRedisDepts(id);
        }
    }

    @Override
    public void setRedisPosts(String id, Set posts) {
        if (StringUtils.isNotBlank(id) && CollectionUtils.isNotEmpty(posts)) {
            redisTemplate.opsForSet().add(getPostKey(id), posts.toArray());
            expireRedisPosts(id);
        }
    }

    @Override
    public void setRedisRoles(String id, Set roles) {
        if (StringUtils.isNotBlank(id) && CollectionUtils.isNotEmpty(roles)) {
            redisTemplate.opsForSet().add(getRoleKey(id), roles.toArray());
            expireRedisRoles(id);
        }
    }

    @Override
    public void setRedisFunctionPermissions(String id, Set functionPermissions) {
        if (StringUtils.isNotBlank(id) && CollectionUtils.isNotEmpty(functionPermissions)) {
            redisTemplate.opsForSet().add(getFunctionPermissionKey(id), functionPermissions.toArray());
            expireRedisFunctionPermissions(id);
        }
    }

    @Override
    public void expireRedisDepts(String id) {
        redisTemplate.expire(getDeptKey(id), deptExpireTime, TimeUnit.SECONDS);
    }

    @Override
    public void expireRedisPosts(String id) {
        redisTemplate.expire(getPostKey(id), postExpireTime, TimeUnit.SECONDS);
    }

    @Override
    public void expireRedisRoles(String id) {
        redisTemplate.expire(getRoleKey(id), roleExpireTime, TimeUnit.SECONDS);
    }

    @Override
    public void expireRedisFunctionPermissions(String id) {
        redisTemplate.expire(getFunctionPermissionKey(id), functionPermissionExpireTime, TimeUnit.SECONDS);
    }

    @Override
    public void delRedisToken(String id) {
        redisTemplate.delete(getTokenKey(id));
    }

    @Override
    public void delRedisRefreshToken(String id) {
        redisTemplate.delete(getRefreshTokenKey(id));
    }

    @Override
    public void delRedisDepts(String id) {
        redisTemplate.delete(getDeptKey(id));
    }

    @Override
    public void delRedisPosts(String id) {
        redisTemplate.delete(getPostKey(id));
    }

    @Override
    public void delRedisRoles(String id) {
        redisTemplate.delete(getRoleKey(id));
    }

    @Override
    public void delRedisFunctionPermissions(String id) {
        redisTemplate.delete(getFunctionPermissionKey(id));
    }

    @Override
    public void updateDepts(String id, Set depts) {
        delRedisDepts(id);
        setRedisDepts(id, depts);
    }

    @Override
    public void updatePosts(String id, Set posts) {
        delRedisPosts(id);
        setRedisPosts(id, posts);
    }

    @Override
    public void updateRoles(String id, Set roles) {
        delRedisRoles(id);
        setRedisRoles(id, roles);
    }

    @Override
    public void updateFunctionPermissions(String id, Set functionPermissions) {
        delRedisFunctionPermissions(id);
        setRedisFunctionPermissions(id, functionPermissions);
    }

    private String getEncryptId(String id) {
        return EncryptionUtil.md5(id, idSalt);
    }

    @Override
    public String getTokenKey(String id) {
        return tokenKeyPrefix + getEncryptId(id);
    }

    @Override
    public String getRefreshTokenKey(String id) {
        return refreshTokenKeyPrefix + getEncryptId(id);
    }

    @Override
    public String getLastExpiredTokenKey(String id) {
        return lastExpiredTokenKeyPrefix + getEncryptId(id);
    }

    @Override
    public String getDeptKey(String id) {
        return deptKeyPrefix + getEncryptId(id);
    }

    @Override
    public String getPostKey(String id) {
        return postKeyPrefix + getEncryptId(id);
    }

    @Override
    public String getRoleKey(String id) {
        return roleKeyPrefix + getEncryptId(id);
    }

    @Override
    public String getFunctionPermissionKey(String id) {
        return functionPermissionKeyPrefix + getEncryptId(id);
    }

    @Override
    public String getToken(String id) {
        Object result = redisTemplate.opsForValue().get(getTokenKey(id));
        return result == null ? null : result.toString();
    }

    @Override
    public String getRefreshToken(String id) {
        Object result = redisTemplate.opsForValue().get(getRefreshTokenKey(id));
        return result == null ? null : result.toString();
    }

    @Override
    public String getLastExpiredToken(String id) {
        Object result = redisTemplate.opsForValue().get(getLastExpiredTokenKey(id));
        return result == null ? null : result.toString();
    }

    @Override
    public Set getDepts(String id) {
        return redisTemplate.opsForSet().members(getDeptKey(id));
    }

    @Override
    public Set getPosts(String id) {
        return redisTemplate.opsForSet().members(getPostKey(id));
    }

    @Override
    public Set getRoles(String id) {
        return redisTemplate.opsForSet().members(getRoleKey(id));
    }

    @Override
    public Set getFunctionPermissions(String id) {
        return redisTemplate.opsForSet().members(getFunctionPermissionKey(id));
    }

    public abstract String newTokenRedisSet(String id, String token);

    public abstract void loginRedisSet(String id, String token, String refreshToken, Set deptIds, Set postIds,
                                       Set roles, Set functionPermissions);

    public abstract void logoutRedisDel(String id);
}
