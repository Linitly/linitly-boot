package org.linitly.boot.base.service;

import java.util.List;
import org.linitly.boot.base.dao.SysFunctionPermissionMapper;
import org.linitly.boot.base.dao.SysMenuMapper;
import org.linitly.boot.base.dto.SysFunctionPermissionDTO;
import org.linitly.boot.base.entity.SysFunctionPermission;
import org.linitly.boot.base.entity.SysMenu;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.Precondition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
@Service
public class SysFunctionPermissionService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysFunctionPermissionMapper sysFunctionPermissionMapper;

    public void insert(SysFunctionPermissionDTO dto) {
        check(dto);
        SysFunctionPermission sysFunctionPermission = new SysFunctionPermission();
        BeanUtils.copyProperties(dto, sysFunctionPermission);
        sysFunctionPermissionMapper.insertSelective(sysFunctionPermission);
    }

    public void updateById(SysFunctionPermissionDTO dto) {
        check(dto);
        SysFunctionPermission sysFunctionPermission = new SysFunctionPermission();
        BeanUtils.copyProperties(dto, sysFunctionPermission);
        sysFunctionPermissionMapper.updateByIdSelective(sysFunctionPermission);
    }

    private void check(SysFunctionPermissionDTO dto) {
        SysMenu sysMenu = sysMenuMapper.findById(dto.getSysMenuId());
        Precondition.checkNotNull(sysMenu, "所选菜单不存在");
        if (sysMenu.getChildNumber() > 0) {
            throw new CommonException("所选菜单非叶子菜单，不可直接添加权限");
        }
        int count = sysFunctionPermissionMapper.countByNameOrCode(dto.getName(), null, dto.getId());
        if (count > 0) {
            throw new CommonException("存在相同名称的权限");
        }
        count = sysFunctionPermissionMapper.countByNameOrCode(null, dto.getCode(), dto.getId());
        if (count > 0) {
            throw new CommonException("存在相同代码的权限");
        }
    }

    public SysFunctionPermission findById(Long id) {
        return sysFunctionPermissionMapper.findById(id);
    }

    public List<SysFunctionPermission> findAll(SysFunctionPermission sysFunctionPermission) {
        return sysFunctionPermissionMapper.findAll(sysFunctionPermission);
    }

    public void deleteById(Long id) {
        sysFunctionPermissionMapper.deleteById(id);
    }

    public List<SysFunctionPermission> findBySysMenuId(Long sysMenuId) {
        return sysFunctionPermissionMapper.findBySysMenuId(sysMenuId);
    }
}