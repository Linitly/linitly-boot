package org.linitly.boot.base.service;

import java.util.List;
import org.linitly.boot.base.dao.SysDeptMapper;
import org.linitly.boot.base.dto.sys_dept.SysDeptDTO;
import org.linitly.boot.base.entity.SysDept;
import org.linitly.boot.base.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 10:41
 * @description: 
 */
@Service
public class SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public void insert(SysDeptDTO dto) {
        checkExist(dto.getParentId(), dto.getName(), dto.getId());
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dto, sysDept);
        sysDeptMapper.insert(sysDept);
    }

    public void updateById(SysDeptDTO dto) {
        checkExist(dto.getParentId(), dto.getName(), dto.getId());
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dto, sysDept);
        sysDeptMapper.updateById(sysDept);
    }

    public SysDept findById(Long id) {
        return sysDeptMapper.findById(id);
    }

    public List<SysDept> findAll(SysDept sysDept) {
        if (sysDept == null) {
            sysDept = new SysDept().setParentId(0L);
        } else if (sysDept.getParentId() == null) {
            sysDept.setParentId(0L);
        }
        return sysDeptMapper.findAll(sysDept);
    }

    public List<SysDept> findByParentId(Long parentId) {
        SysDept sysDept = new SysDept().setParentId(parentId);
        return sysDeptMapper.findAll(sysDept);
    }

    public void deleteById(Long id) {
        if (sysDeptMapper.countByParentId(id) > 0) {
            throw new CommonException("当前部门存在子部门，无法删除");
        }
        // TODO 判断部门下是否有用户

        sysDeptMapper.deleteById(id);
    }

    private void checkExist(Long parentId, String deptName, Long id) {
        if (sysDeptMapper.countByNameAndParentId(parentId, deptName, id) > 0) {
            throw new CommonException("同一层级下存在相同名字的部门");
        }
    }
}