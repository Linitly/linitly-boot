package org.linitly.boot.base.dao;

import java.util.List;
import java.util.Set;

import io.lettuce.core.dynamic.annotation.Param;
import org.linitly.boot.base.annotation.LogIgnore;
import org.linitly.boot.base.dto.SysAdminUserSearchDTO;
import org.linitly.boot.base.entity.SysAdminUser;
import org.linitly.boot.base.vo.SysPostDeptIdVO;

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

    SysAdminUser findByUsername(String username);

    SysAdminUser findByMobileNumber(String mobileNumber);

    int insertSelective(SysAdminUser sysAdminUser);

    int updateByIdSelective(SysAdminUser sysAdminUser);

    int countByMobileOrUsernameOrJobNumber(@Param("mobileNumber") String mobileNumber, @Param("username") String username, @Param("jobNumber") String jobNumber, @Param("id") Long id);


    @LogIgnore
    void deleteRolesByAdminUserId(Long id);

    @LogIgnore
    void deletePostsByAdminUserId(Long id);

    void insertAdminUserRole(@Param("id") Long id, @Param("roleIds") List<Long> roleIds);

    void insertAdminUserPost(@Param("id") Long id, @Param("postIds") List<Long> postIds);

    List<SysPostDeptIdVO> findPostAndDeptIdsByAdminUserId(Long id);

    Set<String> findRoleCodesByAdminUserId(Long id);

    Set<String> findFunctionPermissionCodesByAdminUserId(Long id);
}