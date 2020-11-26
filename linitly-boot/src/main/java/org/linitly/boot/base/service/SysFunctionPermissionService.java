package org.linitly.boot.base.service;

import java.util.List;
import org.linitly.boot.base.dao.SysFunctionPermissionMapper;
import org.linitly.boot.base.dto.SysFunctionPermissionDTO;
import org.linitly.boot.base.entity.SysFunctionPermission;
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
    private SysFunctionPermissionMapper sysFunctionPermissionMapper;

    public void insert(SysFunctionPermissionDTO dto) {
        SysFunctionPermission sysFunctionPermission = new SysFunctionPermission();
        BeanUtils.copyProperties(dto, sysFunctionPermission);
        sysFunctionPermissionMapper.insertSelective(sysFunctionPermission);
    }

    public void updateById(SysFunctionPermissionDTO dto) {
        SysFunctionPermission sysFunctionPermission = new SysFunctionPermission();
        BeanUtils.copyProperties(dto, sysFunctionPermission);
        sysFunctionPermissionMapper.updateByIdSelective(sysFunctionPermission);
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
}