package org.linitly.boot.base.service;

import org.apache.commons.collections.CollectionUtils;
import org.linitly.boot.base.dao.SysAdminUserMapper;
import org.linitly.boot.base.dao.SysPostMapper;
import org.linitly.boot.base.dao.SysRoleMapper;
import org.linitly.boot.base.utils.LinitlyUtil;
import org.linitly.boot.base.vo.SysPostDeptIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPostMapper sysPostMapper;
    @Autowired
    private SysAdminUserMapper sysAdminUserMapper;

    @Async
    public void updateAdminUser(Long sysAdminUserId) {
        if (checkLogin(sysAdminUserId)) LinitlyUtil.logoutCacheDel(sysAdminUserId);
    }

    @Async
    public void updateDeptCache(Long postId) {
        Set<Long> adminUserIdSet = sysPostMapper.findAdminUserIdByPostId(postId);
        if (CollectionUtils.isEmpty(adminUserIdSet)) return;
        adminUserIdSet.forEach(this::updateRedisDeptCache);
    }

    @Async
    public void updateRoleBaseCache(Long roleId) {
        Set<Long> adminUserIdSet = findAdminUserIdByRoleId(roleId);
        if (CollectionUtils.isEmpty(adminUserIdSet)) return;
        adminUserIdSet.forEach(e -> {if (checkLogin(e)) updateRedisRoleCache(e);});
    }

    @Async
    public void deleteRolePowerCache(Long roleId) {
        Set<Long> adminUserIdSet = findAdminUserIdByRoleId(roleId);
        if (CollectionUtils.isEmpty(adminUserIdSet)) return;
        adminUserIdSet.forEach(e -> LinitlyUtil.updateCacheFunctionPermissions(e, null));
    }

    private Set<Long> findAdminUserIdByRoleId(Long roleId) {
        return sysRoleMapper.findAdminUserIdByRoleId(roleId);
    }

    private boolean checkLogin(Long sysAdminUserId) {
        String token = LinitlyUtil.getCacheToken(sysAdminUserId);
        return token == null;
    }

    private void updateRedisRoleCache(Long sysAdminUserId) {
        Set<String> roleCodes = sysAdminUserMapper.findRoleCodesByAdminUserId(sysAdminUserId);
        LinitlyUtil.updateCacheRoles(sysAdminUserId, roleCodes);
    }

    private void updateRedisDeptCache(Long sysAdminUserId) {
        Set<Long> deptIds = new HashSet<>();
        List<SysPostDeptIdVO> postDeptIdList = sysAdminUserMapper.findPostAndDeptIdsByAdminUserId(sysAdminUserId);
        postDeptIdList.forEach(e -> deptIds.add(e.getDeptId()));
        LinitlyUtil.updateCacheDepts(sysAdminUserId, deptIds);
    }
}
