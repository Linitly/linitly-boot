package org.linitly.boot.base.service;

import java.util.List;
import org.linitly.boot.base.dao.SysDataDictMapper;
import org.linitly.boot.base.dto.SysDataDictDTO;
import org.linitly.boot.base.entity.SysDataDict;
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
public class SysDataDictService {

    @Autowired
    private SysDataDictMapper sysDataDictMapper;
    @Autowired
    private SysDataDictItemCacheService sysDataDictItemCacheService;

    public void insert(SysDataDictDTO dto) {
        checkExist(dto.getName(), dto.getCode(), dto.getId());
        SysDataDict sysDataDict = new SysDataDict();
        BeanUtils.copyProperties(dto, sysDataDict);
        sysDataDictMapper.insertSelective(sysDataDict);
    }

    public void updateById(SysDataDictDTO dto) {
        SysDataDict beforeDataDict = sysDataDictMapper.findById(dto.getId());
        if (beforeDataDict == null) throw new CommonException("不存在该字典");
        checkExist(dto.getName(), dto.getCode(), dto.getId());
        SysDataDict sysDataDict = new SysDataDict();
        BeanUtils.copyProperties(dto, sysDataDict);
        sysDataDictMapper.updateByIdSelective(sysDataDict);
        sysDataDictItemCacheService.updateDictCache(beforeDataDict.getCode(), dto.getCode());
    }

    private void checkExist(String name, String code, Long id) {
        int count = sysDataDictMapper.countByNameOrCode(name, null, id);
        if (count > 0) throw new CommonException("存在相同名称的字典");
        count = sysDataDictMapper.countByNameOrCode(null, code, id);
        if (count > 0) throw new CommonException("存在相同编码的字典");
    }

    public SysDataDict findById(Long id) {
        return sysDataDictMapper.findById(id);
    }

    public List<SysDataDict> findAll(SysDataDict sysDataDict) {
        return sysDataDictMapper.findAll(sysDataDict);
    }

    public void deleteById(Long id) {
        sysDataDictItemCacheService.deleteDictCache(id);
        sysDataDictMapper.deleteById(id);
    }
}