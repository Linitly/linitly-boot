package org.linitly.boot.base.dao;

import java.util.List;
import org.linitly.boot.base.entity.SysAdminUser;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 21:26
 * @description: 
 */
public interface SysAdminUserMapper {

    int deleteById(Long id);

    int insert(SysAdminUser sysAdminUser);

    SysAdminUser findById(Long id);

    int updateById(SysAdminUser sysAdminUser);

    List<SysAdminUser> findAll(SysAdminUser sysAdminUser);

    int insertSelective(SysAdminUser sysAdminUser);

    int updateByIdSelective(SysAdminUser sysAdminUser);
}