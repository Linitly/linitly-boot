package org.linitly.boot.base.dao;

import java.util.List;

import io.lettuce.core.dynamic.annotation.Param;
import org.linitly.boot.base.dto.sys_admin_user.SysAdminUserSearchDTO;
import org.linitly.boot.base.entity.SysAdminUser;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 10:26
 * @description: 
 */
public interface SysAdminUserMapper {

    int deleteById(Long id);

    int insert(SysAdminUser sysAdminUser);

    SysAdminUser findById(Long id);

    int updateById(SysAdminUser sysAdminUser);

    List<SysAdminUser> findAll(SysAdminUserSearchDTO dto);

    int insertSelective(SysAdminUser sysAdminUser);

    int updateByIdSelective(SysAdminUser sysAdminUser);

    int countByMobileOrUsernameOrJobNumber(@Param("mobileNumber") String mobileNumber, @Param("username") String username, @Param("jobNumber") String jobNumber, @Param("id") Long id);
}