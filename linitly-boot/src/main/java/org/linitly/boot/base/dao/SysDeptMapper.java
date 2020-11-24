package org.linitly.boot.base.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.entity.SysDept;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 10:41
 * @description: 
 */
public interface SysDeptMapper {

    int deleteById(Long id);

    int insert(SysDept sysDept);

    SysDept findById(Long id);

    int updateById(SysDept sysDept);

    List<SysDept> findAll(SysDept sysDept);

    int countByNameAndParentId(@Param("parentId") Long parentId, @Param("name") String name, @Param("id") Long id);

    int countByParentId(Long parentId);
}