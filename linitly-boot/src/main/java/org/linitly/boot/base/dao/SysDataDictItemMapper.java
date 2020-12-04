package org.linitly.boot.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.entity.SysDataDictItem;

/**
 * @author: linitly-generator
 * @date: 2020-12-04 11:48
 * @description: 
 */
public interface SysDataDictItemMapper {

    int deleteById(Long id);

    int insert(SysDataDictItem sysDataDictItem);

    SysDataDictItem findById(Long id);

    int updateById(SysDataDictItem sysDataDictItem);

    List<SysDataDictItem> findAll(SysDataDictItem sysDataDictItem);

    int insertSelective(SysDataDictItem sysDataDictItem);

    int updateByIdSelective(SysDataDictItem sysDataDictItem);

    List<SysDataDictItem> findByDictId(Long dictId);

    String findTextByDictIdAndValue(@Param("dictId") Long dictId, @Param("value") String value);

    int countByDictIdAndValue(@Param("dictId") Long dictId, @Param("value") String value, @Param("id") Long id);
}