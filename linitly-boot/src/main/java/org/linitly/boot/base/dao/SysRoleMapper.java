package org.linitly.boot.base.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.annotation.DeleteBackup;
import org.linitly.boot.base.entity.SysRole;
import org.linitly.boot.base.vo.SysFunctionPermissionTreeVO;
import org.linitly.boot.base.vo.SysMenuTreeVO;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 14:02
 * @description: 
 */
public interface SysRoleMapper {

    @DeleteBackup
    int deleteById(Long id);

    int insert(SysRole sysRole);

    SysRole findById(Long id);

    int updateById(SysRole sysRole);

    List<SysRole> findAll(SysRole sysRole);

    int insertSelective(SysRole sysRole);

    int updateByIdSelective(SysRole sysRole);

    int countByNameOrCode(@Param("name") String name, @Param("code") String code, @Param("id") Long id);

    void deleteMenusByRoleId(Long id);

    void deleteFunctionPermissionsByRoleId(Long id);

    void insertRoleMenus(@Param("id") Long id, @Param("menuIds") List<Long> menuIds);

    void insertRoleFunctionPermissions(@Param("id") Long id, @Param("permissionIds") List<Long> permissionIds);

    List<Long> findAdminUserIdByRoleId(Long roleId);

    List<SysMenuTreeVO> findMenusByAdminUserId(Long adminUserId);

    List<SysFunctionPermissionTreeVO> findFunctionPermissionsByAdminUserId(Long adminUserId);

    Set<Long> findMenuIdByRoleId(Long roleId);

    Set<Long> findFunctionPermissionIdByRoleId(Long roleId);
}