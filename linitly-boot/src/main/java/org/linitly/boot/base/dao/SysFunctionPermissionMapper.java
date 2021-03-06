package org.linitly.boot.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.annotation.DeleteBackup;
import org.linitly.boot.base.entity.SysFunctionPermission;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
public interface SysFunctionPermissionMapper {

    @DeleteBackup
    int deleteById(Long id);

    int insert(SysFunctionPermission sysFunctionPermission);

    SysFunctionPermission findById(Long id);

    int updateById(SysFunctionPermission sysFunctionPermission);

    List<SysFunctionPermission> findAll(SysFunctionPermission sysFunctionPermission);

    int insertSelective(SysFunctionPermission sysFunctionPermission);

    int updateByIdSelective(SysFunctionPermission sysFunctionPermission);

    int countByNameOrCode(@Param("name") String name, @Param("code") String code, @Param("id") Long id);

    List<SysFunctionPermission> findBySysMenuId(Long sysMenuId);
}