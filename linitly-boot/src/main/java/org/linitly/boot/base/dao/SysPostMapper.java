package org.linitly.boot.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.entity.SysPost;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
public interface SysPostMapper {

    int deleteById(Long id);

    int insert(SysPost sysPost);

    SysPost findById(Long id);

    int updateById(SysPost sysPost);

    List<SysPost> findAll(SysPost sysPost);

    int insertSelective(SysPost sysPost);

    int updateByIdSelective(SysPost sysPost);

    int countByNameAndDeptId(@Param("name") String name, @Param("sysDeptId") Long sysDeptId, @Param("id") Long id);
}