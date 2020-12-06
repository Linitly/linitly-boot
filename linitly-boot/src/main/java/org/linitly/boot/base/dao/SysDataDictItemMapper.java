package org.linitly.boot.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.annotation.DeleteBackup;
import org.linitly.boot.base.entity.SysDataDictItem;
import org.linitly.boot.base.vo.SysDataDictItemCacheVO;

/**
 * @author: linitly-generator
 * @date: 2020-12-04 11:48
 * @description: 
 */
public interface SysDataDictItemMapper {

    @DeleteBackup
    int deleteById(Long id);

    int insert(SysDataDictItem sysDataDictItem);

    SysDataDictItem findById(Long id);

    int updateById(SysDataDictItem sysDataDictItem);

    List<SysDataDictItem> findAll(SysDataDictItem sysDataDictItem);

    int insertSelective(SysDataDictItem sysDataDictItem);

    int updateByIdSelective(SysDataDictItem sysDataDictItem);

    List<SysDataDictItem> findByDictId(Long dictId);

    String findTextByDictCodeAndValue(@Param("code") String code, @Param("value") String value);

    int countByDictIdAndValue(@Param("dictId") Long dictId, @Param("value") String value, @Param("id") Long id);

    List<SysDataDictItemCacheVO> findDictItemCache(@Param("code") String code, @Param("dictId") Long dictId, @Param("value") String value, @Param("dictItemId") Long dictItemId);
}