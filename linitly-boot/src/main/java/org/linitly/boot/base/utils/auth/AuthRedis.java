package org.linitly.boot.base.utils.auth;

import java.util.Set;

public interface AuthRedis {

    void setRedisToken(String key, String token, long expireSecond);

    void setRedisRefreshToken(String key, String refreshToken, long expireSecond);

    void setRedisDepts(String key, Set depts, long expireSecond);

    void setRedisPosts(String key, Set posts, long expireSecond);

    void setRedisRoles(String key, Set roles, long expireSecond);

    void setRedisFunctionPermissions(String key, Set functionPermissions, long expireSecond);

    void delRedisToken(String key);

    void delRedisRefreshToken(String key);

    void delRedisDepts(String key);

    void delRedisPosts(String key);

    void delRedisRoles(String key);

    void delRedisFunctionPermissions(String key);
}
