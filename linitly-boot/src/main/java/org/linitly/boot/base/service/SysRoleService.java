package org.linitly.boot.base.service;

import java.util.List;
import org.linitly.boot.base.dao.SysRoleMapper;
import org.linitly.boot.base.dto.sys_admin_user.SysRoleDTO;
import org.linitly.boot.base.entity.SysRole;
import org.linitly.boot.base.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 14:02
 * @description: 
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public void insert(SysRoleDTO dto) {
        checkExists(dto.getName(), dto.getCode(), dto.getId());
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRoleMapper.insertSelective(sysRole);
    }

    private void checkExists(String name, String code, Long id) {
        int count = sysRoleMapper.countByNameOrCode(name, null, id);
        if (count > 0) {
            throw new CommonException("存在相同角色名");
        }
        count = sysRoleMapper.countByNameOrCode(null, code, id);
        if (count > 0) {
            throw new CommonException("存在相同角色代码");
        }
    }

    public void updateById(SysRoleDTO dto) {
        checkExists(dto.getName(), dto.getCode(), dto.getId());
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRoleMapper.updateByIdSelective(sysRole);
    }

    public SysRole findById(Long id) {
        return sysRoleMapper.findById(id);
    }

    public List<SysRole> findAll(SysRole sysRole) {
        return sysRoleMapper.findAll(sysRole);
    }

    public void deleteById(Long id) {
        sysRoleMapper.deleteById(id);
    }
}