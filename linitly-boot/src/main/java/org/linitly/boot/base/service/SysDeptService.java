package org.linitly.boot.base.service;

import java.util.List;
import org.linitly.boot.base.dao.SysDeptMapper;
import org.linitly.boot.base.dto.SysDeptDTO;
import org.linitly.boot.base.entity.SysDept;
import org.linitly.boot.base.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 10:41
 * @description: 
 */
@Service
public class SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Transactional
    public void insert(SysDeptDTO dto) {
        checkExist(dto.getParentId(), dto.getName(), dto.getId());
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dto, sysDept);
        sysDeptMapper.insertSelective(sysDept);
        if (sysDept.getParentId() > 0) {
            sysDeptMapper.updateChildNumberIncrById(sysDept.getParentId());
        }
    }

    @Transactional
    public void updateById(SysDeptDTO dto) {
        checkExist(dto.getParentId(), dto.getName(), dto.getId());
        if (dto.getId().equals(dto.getParentId())) {
            throw new CommonException("父部门不可以指向自己");
        }
        SysDept before = sysDeptMapper.findById(dto.getId());
        if (!before.getParentId().equals(dto.getParentId()) && dto.getParentId() > 0) {
            sysDeptMapper.updateChildNumberIncrById(dto.getParentId());
        }
        if (!before.getParentId().equals(dto.getParentId()) && before.getParentId() > 0) {
            sysDeptMapper.updateChildNumberDecrById(before.getParentId());
        }
        BeanUtils.copyProperties(dto, before);
        sysDeptMapper.updateByIdSelective(before);
    }

    public SysDept findById(Long id) {
        return sysDeptMapper.findById(id);
    }

    public List<SysDept> findAll(SysDept sysDept) {
        if (sysDept == null) {
            sysDept = new SysDept().setParentId(0L);
        }
        return sysDeptMapper.findAll(sysDept);
    }

    public void deleteById(Long id) {
        if (sysDeptMapper.countByParentId(id) > 0) {
            throw new CommonException("当前部门存在子部门，无法删除");
        }
        // TODO 先不做删除

        sysDeptMapper.deleteById(id);
    }

    private void checkExist(Long parentId, String deptName, Long id) {
        if (sysDeptMapper.countByNameAndParentId(parentId, deptName, id) > 0) {
            throw new CommonException("同一层级下存在相同名字的部门");
        }
    }
}