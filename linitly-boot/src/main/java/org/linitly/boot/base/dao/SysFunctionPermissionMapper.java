package org.linitly.boot.base.dao;

import java.util.List;
import org.linitly.boot.base.entity.SysFunctionPermission;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
public interface SysFunctionPermissionMapper {

    int deleteById(Long id);

    int insert(SysFunctionPermission sysFunctionPermission);

    SysFunctionPermission findById(Long id);

    int updateById(SysFunctionPermission sysFunctionPermission);

    List<SysFunctionPermission> findAll(SysFunctionPermission sysFunctionPermission);

    int insertSelective(SysFunctionPermission sysFunctionPermission);

    int updateByIdSelective(SysFunctionPermission sysFunctionPermission);
}