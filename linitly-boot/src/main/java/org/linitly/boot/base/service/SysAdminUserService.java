package org.linitly.boot.base.service;

import java.util.List;
import org.linitly.boot.base.dao.SysAdminUserMapper;
import org.linitly.boot.base.dto.sys_admin_user.SysAdminUserDTO;
import org.linitly.boot.base.entity.SysAdminUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 21:26
 * @description: 
 */
@Service
public class SysAdminUserService {

    @Autowired
    private SysAdminUserMapper sysAdminUserMapper;

    public void insert(SysAdminUserDTO dto) {
        SysAdminUser sysAdminUser = new SysAdminUser();
        BeanUtils.copyProperties(dto, sysAdminUser);
        sysAdminUserMapper.insert(sysAdminUser);
    }

    public void updateById(SysAdminUserDTO dto) {
        SysAdminUser sysAdminUser = new SysAdminUser();
        BeanUtils.copyProperties(dto, sysAdminUser);
        sysAdminUserMapper.updateById(sysAdminUser);
    }

    public SysAdminUser findById(Long id) {
        return sysAdminUserMapper.findById(id);
    }

    public List<SysAdminUser> findAll(SysAdminUser sysAdminUser) {
        return sysAdminUserMapper.findAll(sysAdminUser);
    }

    public void deleteById(Long id) {
        sysAdminUserMapper.deleteById(id);
    }
}