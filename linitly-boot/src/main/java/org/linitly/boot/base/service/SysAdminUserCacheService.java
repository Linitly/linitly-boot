package org.linitly.boot.base.service;

import org.apache.commons.collections.CollectionUtils;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.constant.entity.SysAdminUserConstant;
import org.linitly.boot.base.dao.SysAdminUserMapper;
import org.linitly.boot.base.dao.SysRoleMapper;
import org.linitly.boot.base.utils.algorithm.EncryptionUtil;
import org.linitly.boot.base.utils.auth.AbstractAuth;
import org.linitly.boot.base.utils.auth.AuthFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author: linxiunan
 * @date: 2020/12/8 8:53
 * @descrption:
 */
@Service
public class SysAdminUserCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysAdminUserMapper sysAdminUserMapper;
    @Autowired
    private HttpServletRequest request;
    private AbstractAuth auth;

    @Async
    public void updateRoleBaseCache(Long roleId) {
        List<Long> adminUserIdList = findAdminUserIdByRoleId(roleId);
        if (CollectionUtils.isEmpty(adminUserIdList)) return;
        auth = AuthFactory.getAuth(request);
        adminUserIdList.forEach(this::checkLogin);
    }

    @Async
    public void deleteRolePowerCache(Long roleId) {
        List<Long> adminUserIdList = findAdminUserIdByRoleId(roleId);
        if (CollectionUtils.isEmpty(adminUserIdList)) return;
        adminUserIdList.forEach(e -> auth.updateFunctionPermissions(e.toString(), null));
    }

    private List<Long> findAdminUserIdByRoleId(Long roleId) {
        return sysRoleMapper.findAdminUserIdByRoleId(roleId);
    }

    private void checkLogin(Long sysAdminUserId) {
        String encryptId = EncryptionUtil.md5(sysAdminUserId.toString(), SysAdminUserConstant.TOKEN_ID_SALT);
        Object token = redisTemplate.opsForValue().get(AdminCommonConstant.ADMIN_TOKEN_PREFIX + encryptId);
        if (token == null) return;
        updateRedisRoleCache(sysAdminUserId);
    }

    private void updateRedisRoleCache(Long sysAdminUserId) {
        Set<String> roleCodes = sysAdminUserMapper.findRoleCodesByAdminUserId(sysAdminUserId);
        auth.updateRoles(sysAdminUserId.toString(), roleCodes);
    }
}
