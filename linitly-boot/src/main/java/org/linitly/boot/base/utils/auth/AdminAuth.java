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

    private AdminAuth() {}

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
        String encryptId = EncryptionUtil.md5(id, SysAdminUserConstant.TOKEN_ID_SALT);
        setRedisToken(AdminCommonConstant.ADMIN_TOKEN_PREFIX + encryptId, token, AdminCommonConstant.ADMIN_TOKEN_EXPIRE_SECOND);
        expireRedisDepts(AdminCommonConstant.ADMIN_DEPTS_PREFIX + encryptId, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND);
        expireRedisDepts(AdminCommonConstant.ADMIN_POSTS_PREFIX + encryptId, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND);
        expireRedisDepts(AdminCommonConstant.ADMIN_ROLES_PREFIX + encryptId, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND);
        expireRedisDepts(AdminCommonConstant.ADMIN_FUNCTION_PERMISSIONS_PREFIX + encryptId, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND);
    }

    @Override
    public void loginRedisSet(String id, String token, String refreshToken, Set deptIds, Set postIds,
                              Set roles, Set functionPermissions) {
        String encryptId = EncryptionUtil.md5(id, SysAdminUserConstant.TOKEN_ID_SALT);
        setRedisToken(AdminCommonConstant.ADMIN_TOKEN_PREFIX + encryptId, token, AdminCommonConstant.ADMIN_TOKEN_EXPIRE_SECOND);
        setRedisRefreshToken(AdminCommonConstant.ADMIN_REFRESH_TOKEN_PREFIX + encryptId, refreshToken, AdminCommonConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND);
        setRedisDepts(AdminCommonConstant.ADMIN_DEPTS_PREFIX + encryptId, deptIds, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND);
        setRedisPosts(AdminCommonConstant.ADMIN_POSTS_PREFIX + encryptId, postIds, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND);
        setRedisRoles(AdminCommonConstant.ADMIN_ROLES_PREFIX + encryptId, roles, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND);
        setRedisFunctionPermissions(AdminCommonConstant.ADMIN_FUNCTION_PERMISSIONS_PREFIX + encryptId, functionPermissions, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND);
    }

    @Override
    public void logoutRedisDel(String id) {
        String encryptId = EncryptionUtil.md5(id, SysAdminUserConstant.TOKEN_ID_SALT);
        delRedisToken(AdminCommonConstant.ADMIN_TOKEN_PREFIX + encryptId);
        delRedisRefreshToken(AdminCommonConstant.ADMIN_REFRESH_TOKEN_PREFIX + encryptId);
        delRedisDepts(AdminCommonConstant.ADMIN_DEPTS_PREFIX + encryptId);
        delRedisPosts(AdminCommonConstant.ADMIN_POSTS_PREFIX + encryptId);
        delRedisRoles(AdminCommonConstant.ADMIN_ROLES_PREFIX + encryptId);
        delRedisFunctionPermissions(AdminCommonConstant.ADMIN_FUNCTION_PERMISSIONS_PREFIX + encryptId);
    }
}