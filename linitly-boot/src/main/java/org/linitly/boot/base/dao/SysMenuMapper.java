package org.linitly.boot.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.entity.SysMenu;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
public interface SysMenuMapper {

    int deleteById(Long id);

    int insert(SysMenu sysMenu);

    SysMenu findById(Long id);

    int updateById(SysMenu sysMenu);

    List<SysMenu> findAll(SysMenu sysMenu);

    int insertSelective(SysMenu sysMenu);

    int updateByIdSelective(SysMenu sysMenu);

    int countByNameAndParentId(@Param("name") String name, @Param("parentId") Long parentId, @Param("id") Long id);

    int countByParentId(Long parentId);

    void updateChildNumberIncrById(Long id);

    void updateChildNumberDecrById(Long id);
}