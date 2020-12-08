package org.linitly.boot.base.utils.auth;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public void setRedisToken(String key, String token, long expireSecond) {
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(token))
            redisTemplate.opsForValue().set(key, token, expireSecond, TimeUnit.SECONDS);
    }

    @Override
    public void setRedisRefreshToken(String key, String refreshToken, long expireSecond) {
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(refreshToken))
            redisTemplate.opsForValue().set(key, refreshToken, expireSecond, TimeUnit.SECONDS);
    }

    @Override
    public void setRedisDepts(String key, Set depts, long expireSecond) {
        if (StringUtils.isNotBlank(key) && CollectionUtils.isNotEmpty(depts)) {
            redisTemplate.opsForSet().add(key, depts.toArray());
            expireRedisDepts(key, expireSecond);
        }
    }

    @Override
    public void setRedisPosts(String key, Set posts, long expireSecond) {
        if (StringUtils.isNotBlank(key) && CollectionUtils.isNotEmpty(posts)) {
            redisTemplate.opsForSet().add(key, posts.toArray());
            expireRedisPosts(key, expireSecond);
        }
    }

    @Override
    public void setRedisRoles(String key, Set roles, long expireSecond) {
        if (StringUtils.isNotBlank(key) && CollectionUtils.isNotEmpty(roles)) {
            redisTemplate.opsForSet().add(key, roles.toArray());
            expireRedisRoles(key, expireSecond);
        }
    }

    @Override
    public void setRedisFunctionPermissions(String key, Set functionPermissions, long expireSecond) {
        if (StringUtils.isNotBlank(key) && CollectionUtils.isNotEmpty(functionPermissions)) {
            redisTemplate.opsForSet().add(key, functionPermissions.toArray());
            expireRedisFunctionPermissions(key, expireSecond);
        }
    }

    protected void expireRedisDepts(String key, long expireSecond) {
        redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS);
    }

    protected void expireRedisPosts(String key, long expireSecond) {
        redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS);
    }

    protected void expireRedisRoles(String key, long expireSecond) {
        redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS);
    }

    protected void expireRedisFunctionPermissions(String key, long expireSecond) {
        redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS);
    }

    @Override
    public void delRedisToken(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delRedisRefreshToken(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delRedisDepts(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delRedisPosts(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delRedisRoles(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delRedisFunctionPermissions(String key) {
        redisTemplate.delete(key);
    }

    public abstract void newTokenRedisSet(String id, String token);

    public abstract void loginRedisSet(String id, String token, String refreshToken, Set deptIds, Set postIds,
                              Set roles, Set functionPermissions);

    public abstract void logoutRedisDel(String id);

    public abstract void updateRoles(String id, Set roles);

    public abstract void updateFunctionPermissions(String id);
}
