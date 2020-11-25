package org.linitly.boot.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.entity.SysRole;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 14:02
 * @description: 
 */
public interface SysRoleMapper {

    int deleteById(Long id);

    int insert(SysRole sysRole);

    SysRole findById(Long id);

    int updateById(SysRole sysRole);

    List<SysRole> findAll(SysRole sysRole);

    int insertSelective(SysRole sysRole);

    int updateByIdSelective(SysRole sysRole);

    int countByNameOrCode(@Param("name") String name, @Param("code") String code, @Param("id") Long id);
}