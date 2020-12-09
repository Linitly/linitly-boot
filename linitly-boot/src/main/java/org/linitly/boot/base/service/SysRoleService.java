package org.linitly.boot.base.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.linitly.boot.base.dao.SysRoleMapper;
import org.linitly.boot.base.dto.SysRoleDTO;
import org.linitly.boot.base.dto.SysRoleEmpowerDTO;
import org.linitly.boot.base.entity.SysRole;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.LinitlyUtil;
import org.linitly.boot.base.utils.jwt.JwtUtilFactory;
import org.linitly.boot.base.vo.SysFunctionPermissionTreeVO;
import org.linitly.boot.base.vo.SysMenuTreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 14:02
 * @description: 
 */
@Service
public class SysRoleService {

    private static final Long ROOT_ID = 0L;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysAdminUserCacheService sysAdminUserCacheService;

    public void insert(SysRoleDTO dto) {
        checkExists(dto.getName(), dto.getCode(), dto.getId());
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRoleMapper.insertSelective(sysRole);
    }

    private void checkExists(String name, String code, Long id) {
        int count = sysRoleMapper.countByNameOrCode(name, null, id);
        if (count > 0) {
            throw new CommonException("存在相同角色名");
        }
        count = sysRoleMapper.countByNameOrCode(null, code, id);
        if (count > 0) {
            throw new CommonException("存在相同角色代码");
        }
    }

    public void updateById(SysRoleDTO dto) {
        checkExists(dto.getName(), dto.getCode(), dto.getId());
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRoleMapper.updateByIdSelective(sysRole);
        sysAdminUserCacheService.updateRoleBaseCache(dto.getId());
    }

    public SysRole findById(Long id) {
        return sysRoleMapper.findById(id);
    }

    public List<SysRole> findAll(SysRole sysRole) {
        return sysRoleMapper.findAll(sysRole);
    }

    public void deleteById(Long id) {
        sysRoleMapper.deleteById(id);
    }

    @Transactional
    public void empower(SysRoleEmpowerDTO dto) {
        sysRoleMapper.deleteMenusByRoleId(dto.getId());
        sysRoleMapper.deleteFunctionPermissionsByRoleId(dto.getId());
        sysRoleMapper.insertRoleMenus(dto.getId(), dto.getSysMenuIds());
        sysRoleMapper.insertRoleFunctionPermissions(dto.getId(), dto.getFunctionPermissionIds());
        sysAdminUserCacheService.deleteRolePowerCache(dto.getId());
    }

    public List<SysMenuTreeVO> tree(Long roleId, Set<String> functionPermissionCodes, Long userId) {
        userId = userId == null ? LinitlyUtil.getCurrentUserId() : userId;
        List<SysMenuTreeVO> menus = sysRoleMapper.findMenusByAdminUserId(userId);
        List<SysFunctionPermissionTreeVO> functionPermissions = sysRoleMapper.findFunctionPermissionsByAdminUserId(userId);

        Set<Long> menuIds = null;
        Set<Long> functionPermissionIds = null;
        if (roleId != null) {
            menuIds = sysRoleMapper.findMenuIdByRoleId(roleId);
            functionPermissionIds = sysRoleMapper.findFunctionPermissionIdByRoleId(roleId);
        }

        Multimap<Long, SysMenuTreeVO> menuMultimap = ArrayListMultimap.create();
        Multimap<Long, SysFunctionPermissionTreeVO> functionPermissionMultimap = ArrayListMultimap.create();

        ArrayList<SysMenuTreeVO> menuTreeList = Lists.newArrayList();
        for (SysFunctionPermissionTreeVO functionPermission : functionPermissions) {
            if (CollectionUtils.isNotEmpty(functionPermissionIds) && functionPermissionIds.contains(functionPermission.getId()))
                functionPermission.setSelected(1);
            if (functionPermissionCodes != null) functionPermissionCodes.add(functionPermission.getCode());
            functionPermissionMultimap.put(functionPermission.getSysMenuId(), functionPermission);
        }
        for (SysMenuTreeVO menu : menus) {
            if (CollectionUtils.isNotEmpty(menuIds) && menuIds.contains(menu.getId()))
                menu.setSelected(1);
            menuMultimap.put(menu.getParentId(), menu);
            if (menu.getParentId().equals(ROOT_ID)) {
                menuTreeList.add(menu);
            }
        }
        menuTreeList.sort(menuSeqComparator);
        transformTree(menuTreeList, menuMultimap, functionPermissionMultimap);
        return menuTreeList;
    }

    private void transformTree(List<SysMenuTreeVO> menuTreeList, Multimap<Long, SysMenuTreeVO> menuMultimap, Multimap<Long, SysFunctionPermissionTreeVO> functionPermissionMultimap) {
        for (SysMenuTreeVO menuTree : menuTreeList) {
            if (menuTree.getChildNumber() < 1) {
                menuTree.setFunctionPermissions((List<SysFunctionPermissionTreeVO>) functionPermissionMultimap.get(menuTree.getId()));
                continue;
            }
            List<SysMenuTreeVO> tempMenuTreeList = (List<SysMenuTreeVO>) menuMultimap.get(menuTree.getId());
            if (CollectionUtils.isEmpty(tempMenuTreeList)) continue;
            tempMenuTreeList.sort(menuSeqComparator);
            menuTree.setChilds(tempMenuTreeList);
            transformTree(tempMenuTreeList, menuMultimap, functionPermissionMultimap);
        }
    }

    private Comparator<SysMenuTreeVO> menuSeqComparator = Comparator.comparing(SysMenuTreeVO::getSort);
}