package org.linitly.boot.base.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private SysDataDictItemCacheService sysDataDictItemCacheService;

    public void insert(SysDataDictItemDTO dto) {
        checkExist(dto.getSysDataDictId(), dto.getValue(), dto.getId());
        SysDataDictItem sysDataDictItem = new SysDataDictItem();
        BeanUtils.copyProperties(dto, sysDataDictItem);
        sysDataDictItemMapper.insertSelective(sysDataDictItem);
        sysDataDictItemCacheService.insertDictItemCache(dto.getSysDataDictId(), dto.getValue());
    }

    public void updateById(SysDataDictItemDTO dto) {
        SysDataDictItem dictItem = sysDataDictItemMapper.findById(dto.getId());
        if (dictItem == null) throw new CommonException("不存在该字典，唯一键错误");
        checkExist(dto.getSysDataDictId(), dto.getValue(), dto.getId());
        SysDataDictItem sysDataDictItem = new SysDataDictItem();
        BeanUtils.copyProperties(dto, sysDataDictItem);
        sysDataDictItemMapper.updateByIdSelective(sysDataDictItem);
        sysDataDictItemCacheService.updateDictItemCache(dictItem.getSysDataDictId(), dictItem.getValue(), dto.getSysDataDictId(), dto.getValue(), dto.getText());
    }

    private void checkExist(Long sysDataDictId, String value, Long id) {
        int count = sysDataDictItemMapper.countByDictIdAndValue(sysDataDictId, value, id);
        if (count > 0) throw new CommonException("该字典已存在");
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
        sysDataDictItemCacheService.deleteDictItemCache(id);
        sysDataDictItemMapper.deleteById(id);
    }
}