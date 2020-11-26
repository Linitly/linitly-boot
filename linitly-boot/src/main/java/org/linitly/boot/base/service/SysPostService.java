package org.linitly.boot.base.service;

import java.util.List;
import org.linitly.boot.base.dao.SysPostMapper;
import org.linitly.boot.base.dto.SysPostDTO;
import org.linitly.boot.base.entity.SysPost;
import org.linitly.boot.base.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
@Service
public class SysPostService {

    @Autowired
    private SysPostMapper sysPostMapper;

    public void insert(SysPostDTO dto) {
        checkExists(dto.getName(), dto.getSysDeptId(), dto.getId());
        SysPost sysPost = new SysPost();
        BeanUtils.copyProperties(dto, sysPost);
        sysPostMapper.insertSelective(sysPost);
    }

    public void updateById(SysPostDTO dto) {
        checkExists(dto.getName(), dto.getSysDeptId(), dto.getId());
        SysPost sysPost = new SysPost();
        BeanUtils.copyProperties(dto, sysPost);
        sysPostMapper.updateByIdSelective(sysPost);
    }

    private void checkExists(String name, Long sysDeptId, Long id) {
        int count = sysPostMapper.countByNameAndDeptId(name, sysDeptId, id);
        if (count > 0) {
            throw new CommonException("同一部门下存在相同名称的岗位");
        }
    }

    public SysPost findById(Long id) {
        return sysPostMapper.findById(id);
    }

    public List<SysPost> findAll(SysPost sysPost) {
        return sysPostMapper.findAll(sysPost);
    }

    public void deleteById(Long id) {
        sysPostMapper.deleteById(id);
    }
}