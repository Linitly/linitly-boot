package org.linitly.boot.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.entity.SysDataDict;

/**
 * @author: linitly-generator
 * @date: 2020-12-04 11:48
 * @description: 
 */
public interface SysDataDictMapper {

    int deleteById(Long id);

    int insert(SysDataDict sysDataDict);

    SysDataDict findById(Long id);

    int updateById(SysDataDict sysDataDict);

    List<SysDataDict> findAll(SysDataDict sysDataDict);

    int insertSelective(SysDataDict sysDataDict);

    int updateByIdSelective(SysDataDict sysDataDict);

    int countByNameOrCode(@Param("name") String name, @Param("code") String code, @Param("id") Long id);
}