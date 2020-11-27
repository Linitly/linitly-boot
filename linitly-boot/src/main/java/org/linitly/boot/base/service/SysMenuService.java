package org.linitly.boot.base.service;

import java.util.List;
import org.linitly.boot.base.dao.SysMenuMapper;
import org.linitly.boot.base.dto.SysMenuDTO;
import org.linitly.boot.base.entity.SysMenu;
import org.linitly.boot.base.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
@Service
public class SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Transactional
    public void insert(SysMenuDTO dto) {
        checkExists(dto.getName(), dto.getParentId(), dto.getId());
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(dto, sysMenu);
        sysMenuMapper.insertSelective(sysMenu);
        if (sysMenu.getParentId() > 0) {
            sysMenuMapper.updateChildNumberIncrById(sysMenu.getParentId());
        }
    }

    @Transactional
    public void updateById(SysMenuDTO dto) {
        checkExists(dto.getName(), dto.getParentId(), dto.getId());
        SysMenu before = sysMenuMapper.findById(dto.getId());
        if (!before.getParentId().equals(dto.getParentId()) && before.getParentId() > 0) {
            sysMenuMapper.updateChildNumberDecrById(before.getParentId());
        }
        if (!before.getParentId().equals(dto.getParentId()) && dto.getParentId() > 0) {
            sysMenuMapper.updateChildNumberIncrById(dto.getParentId());
        }
        BeanUtils.copyProperties(dto, before);
        sysMenuMapper.updateByIdSelective(before);
    }

    private void checkExists(String name, Long parentId, Long id) {
        int count = sysMenuMapper.countByNameAndParentId(name, parentId, id);
        if (count > 0) {
            throw new CommonException("存在相同名称的菜单");
        }
    }

    public SysMenu findById(Long id) {
        return sysMenuMapper.findById(id);
    }

    public List<SysMenu> findAll(SysMenu sysMenu) {
        sysMenu = sysMenu == null ? new SysMenu().setParentId(0L) : sysMenu;
        return sysMenuMapper.findAll(sysMenu);
    }

    public void deleteById(Long id) {
        sysMenuMapper.deleteById(id);
    }
}