package org.linitly.boot.base.service;

import java.util.List;

import org.linitly.boot.base.dao.SysDataDictItemMapper;
import org.linitly.boot.base.dto.SysDataDictItemDTO;
import org.linitly.boot.base.entity.SysDataDictItem;
import org.linitly.boot.base.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: linitly-generator
 * @date: 2020-12-04 11:48
 * @description:
 */
@Service
public class SysDataDictItemService {

    @Autowired
    private SysDataDictItemMapper sysDataDictItemMapper;

    public void insert(SysDataDictItemDTO dto) {
        checkExist(dto.getSysDataDictId(), dto.getValue(), dto.getId());
        SysDataDictItem sysDataDictItem = new SysDataDictItem();
        BeanUtils.copyProperties(dto, sysDataDictItem);
        sysDataDictItemMapper.insertSelective(sysDataDictItem);
    }

    public void updateById(SysDataDictItemDTO dto) {
        checkExist(dto.getSysDataDictId(), dto.getValue(), dto.getId());
        SysDataDictItem sysDataDictItem = new SysDataDictItem();
        BeanUtils.copyProperties(dto, sysDataDictItem);
        sysDataDictItemMapper.updateByIdSelective(sysDataDictItem);
    }

    private void checkExist(Long sysDataDictId, String value, Long id) {
        int count = sysDataDictItemMapper.countByDictIdAndValue(sysDataDictId, value, id);
        if (count > 0) throw new CommonException("该字典已存在此值");
    }

    public SysDataDictItem findById(Long id) {
        return sysDataDictItemMapper.findById(id);
    }

    public List<SysDataDictItem> findAll(SysDataDictItem sysDataDictItem) {
        return sysDataDictItemMapper.findAll(sysDataDictItem);
    }

    public List<SysDataDictItem> findByDictId(Long dictId) {
        return sysDataDictItemMapper.findByDictId(dictId);
    }

    public void deleteById(Long id) {
        sysDataDictItemMapper.deleteById(id);
    }

    public String findTextByDictCodeAndValue(String code, String value) {
        return sysDataDictItemMapper.findTextByDictCodeAndValue(code, value);
    }
}