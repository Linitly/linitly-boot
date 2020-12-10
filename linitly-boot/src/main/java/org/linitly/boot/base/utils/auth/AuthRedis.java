package org.linitly.boot.base.utils.auth;

import java.util.Set;

public interface AuthRedis {

    void setRedisToken(String id, String token);

    void setRedisRefreshToken(String id, String refreshToken);

    void setLastExpiredToken(String id, String lastExpiredToken);

    void setRedisDepts(String id, Set depts);

    void setRedisPosts(String id, Set posts);

    void setRedisRoles(String id, Set roles);

    void setRedisFunctionPermissions(String id, Set functionPermissions);

    void expireRedisDepts(String id);

    void expireRedisPosts(String id);

    void expireRedisRoles(String id);

    void expireRedisFunctionPermissions(String id);

    void updateDepts(String id, Set depts);

    void updatePosts(String id, Set posts);

    void updateRoles(String id, Set roles);

    void updateFunctionPermissions(String id, Set functionPermissions);

    void delRedisToken(String id);

    void delRedisRefreshToken(String id);

    void delRedisDepts(String id);

    void delRedisPosts(String id);

    void delRedisRoles(String id);

    void delRedisFunctionPermissions(String id);

    String getTokenKey(String id);

    String getRefreshTokenKey(String id);

    String getLastExpiredTokenKey(String id);

    String getDeptKey(String id);

    String getPostKey(String id);

    String getRoleKey(String id);

    String getFunctionPermissionKey(String id);

    String getToken(String id);

    String getRefreshToken(String id);

    String getLastExpiredToken(String id);

    Set getDepts(String id);

    Set getPosts(String id);

    Set getRoles(String id);

    Set getFunctionPermissions(String id);
}
