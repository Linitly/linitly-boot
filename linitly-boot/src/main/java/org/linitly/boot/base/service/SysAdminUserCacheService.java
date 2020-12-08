package org.linitly.boot.base.service;

import org.apache.commons.collections.CollectionUtils;
import org.linitly.boot.base.dao.SysAdminUserMapper;
import org.linitly.boot.base.dao.SysPostMapper;
import org.linitly.boot.base.dao.SysRoleMapper;
import org.linitly.boot.base.utils.auth.AbstractAuth;
import org.linitly.boot.base.utils.auth.AuthFactory;
import org.linitly.boot.base.vo.SysPostDeptIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private HttpServletRequest request;
    private AbstractAuth auth;

    @Async
    public void updateAdminUser(Long sysAdminUserId) {
        auth = AuthFactory.getAuth(request);
        if (checkLogin(sysAdminUserId)) auth.logoutRedisDel(sysAdminUserId.toString());
    }

    @Async
    public void updateDeptCache(Long postId) {
        Set<Long> adminUserIdSet = sysPostMapper.findAdminUserIdByPostId(postId);
        if (CollectionUtils.isEmpty(adminUserIdSet)) return;
        auth = AuthFactory.getAuth(request);
        adminUserIdSet.forEach(this::updateRedisDeptCache);
    }

    @Async
    public void updateRoleBaseCache(Long roleId) {
        Set<Long> adminUserIdSet = findAdminUserIdByRoleId(roleId);
        if (CollectionUtils.isEmpty(adminUserIdSet)) return;
        auth = AuthFactory.getAuth(request);
        adminUserIdSet.forEach(e -> {if (checkLogin(e)) updateRedisRoleCache(e);});
    }

    @Async
    public void deleteRolePowerCache(Long roleId) {
        Set<Long> adminUserIdSet = findAdminUserIdByRoleId(roleId);
        if (CollectionUtils.isEmpty(adminUserIdSet)) return;
        auth = AuthFactory.getAuth(request);
        adminUserIdSet.forEach(e -> auth.updateFunctionPermissions(e.toString(), null));
    }

    private Set<Long> findAdminUserIdByRoleId(Long roleId) {
        return sysRoleMapper.findAdminUserIdByRoleId(roleId);
    }

    private boolean checkLogin(Long sysAdminUserId) {
        String token = auth.getToken(sysAdminUserId.toString());
        return token == null;
    }

    private void updateRedisRoleCache(Long sysAdminUserId) {
        Set<String> roleCodes = sysAdminUserMapper.findRoleCodesByAdminUserId(sysAdminUserId);
        auth.updateRoles(sysAdminUserId.toString(), roleCodes);
    }

    private void updateRedisDeptCache(Long sysAdminUserId) {
        Set<Long> deptIds = new HashSet<>();
        List<SysPostDeptIdVO> postDeptIdList = sysAdminUserMapper.findPostAndDeptIdsByAdminUserId(sysAdminUserId);
        postDeptIdList.forEach(e -> deptIds.add(e.getDeptId()));
        auth.updateDepts(sysAdminUserId.toString(), deptIds);
    }
}
