package org.linitly.boot.base.utils.auth;

import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.constant.entity.SysAdminUserConstant;
import org.linitly.boot.base.utils.algorithm.EncryptionUtil;

import java.util.Set;

/**
 * @author: linxiunan
 * @date: 2020/11/30 13:15
 * @descrption:
 */
public class AdminAuth extends AbstractAuth {

    private static AdminAuth adminAuth;

    private AdminAuth() {
        super(SysAdminUserConstant.TOKEN_ID_SALT, AdminCommonConstant.ADMIN_TOKEN_PREFIX, AdminCommonConstant.ADMIN_REFRESH_TOKEN_PREFIX,
                AdminCommonConstant.ADMIN_DEPTS_PREFIX, AdminCommonConstant.ADMIN_POSTS_PREFIX, AdminCommonConstant.ADMIN_ROLES_PREFIX,
                AdminCommonConstant.ADMIN_FUNCTION_PERMISSIONS_PREFIX, AdminCommonConstant.ADMIN_TOKEN_EXPIRE_SECOND,
                AdminCommonConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND,
                AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND,AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND,
                AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND);
    }

    protected static AdminAuth getInstance() {
        if (adminAuth == null) {
            synchronized (AdminAuth.class) {
                if (adminAuth == null) {
                    adminAuth = new AdminAuth();
                }
            }
        }
        return adminAuth;
    }

    @Override
    public void newTokenRedisSet(String id, String token) {
        setRedisToken(id, token);
        expireRedisDepts(id);
        expireRedisPosts(id);
        expireRedisRoles(id);
        expireRedisFunctionPermissions(id);
    }

    @Override
    public void loginRedisSet(String id, String token, String refreshToken, Set deptIds, Set postIds,
                              Set roles, Set functionPermissions) {
        setRedisToken(id, token);
        setRedisRefreshToken(id, refreshToken);
        setRedisDepts(id, deptIds);
        setRedisPosts(id, postIds);
        setRedisRoles(id, roles);
        setRedisFunctionPermissions(id, functionPermissions);
    }

    @Override
    public void logoutRedisDel(String id) {
        delRedisToken(id);
        delRedisRefreshToken(id);
        delRedisDepts(id);
        delRedisPosts(id);
        delRedisRoles(id);
        delRedisFunctionPermissions(id);
    }

    @Override
    public void updateFunctionPermissions(String id, Set functionPermissions) {
        logoutRedisDel(id);
    }
}