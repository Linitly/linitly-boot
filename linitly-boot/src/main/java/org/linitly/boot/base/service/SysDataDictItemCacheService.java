package org.linitly.boot.base.service;

import org.apache.commons.collections.CollectionUtils;
import org.linitly.boot.base.constant.entity.SysDataDictItemConstant;
import org.linitly.boot.base.dao.SysDataDictItemMapper;
import org.linitly.boot.base.dao.SysDataDictMapper;
import org.linitly.boot.base.entity.SysDataDict;
import org.linitly.boot.base.vo.SysDataDictItemCacheVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: linitly-generator
 * @date: 2020-12-04 11:48
 * @description:
 */
@Service
public class SysDataDictItemCacheService {

    @Autowired
    private SysDataDictMapper sysDataDictMapper;
    @Autowired
    private SysDataDictItemMapper sysDataDictItemMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Async
    public void updateDictCache(String beforeCode, String newCode) {
        deleteDictCache(beforeCode);
        List<SysDataDictItemCacheVO> cacheVOList = sysDataDictItemMapper.findDictItemCache(newCode, null, null, null);
        if (CollectionUtils.isEmpty(cacheVOList)) return;
        cacheVOList.forEach(this::setDictItemCache);
    }

    public void deleteDictCache(Long sysDataDictId) {
        SysDataDict dataDict = sysDataDictMapper.findById(sysDataDictId);
        if (dataDict == null) return;
        deleteDictCache(dataDict.getCode());
    }

    @Async
    public void insertDictItemCache(Long sysDataDictId, String value) {
        List<SysDataDictItemCacheVO> cacheVOList = sysDataDictItemMapper.findDictItemCache(null, sysDataDictId, value, null);
        if (CollectionUtils.isEmpty(cacheVOList)) return;
        cacheVOList.forEach(this::setDictItemCache);
    }

    @Async
    public void updateDictItemCache(Long beforeSysDataDictId, String beforeValue, Long sysDataDictId, String value, String text) {
        SysDataDict beforeDataDict = sysDataDictMapper.findById(beforeSysDataDictId);
        if (beforeDataDict == null) return;
        deleteDictItemCache(beforeDataDict.getCode(), beforeValue);
        SysDataDict dataDict = sysDataDictMapper.findById(sysDataDictId);
        if (dataDict == null) return;
        setDictItemCache(dataDict.getCode(), value, text);
    }

    public void deleteDictItemCache(Long sysDictItemId) {
        List<SysDataDictItemCacheVO> cacheVOList = sysDataDictItemMapper.findDictItemCache(null, null, null, sysDictItemId);
        if (CollectionUtils.isEmpty(cacheVOList)) return;
        cacheVOList.forEach(e -> deleteDictItemCache(e.getCode(), e.getValue()));
    }

    public void setAllCache() {
        List<SysDataDictItemCacheVO> cacheVOList = sysDataDictItemMapper.findDictItemCache(null, null, null, null);
        if (CollectionUtils.isEmpty(cacheVOList)) return;
        cacheVOList.forEach(this::setDictItemCache);
    }

    public String getItemCache(String code, String value) {
        Object result = redisTemplate.opsForHash().get(SysDataDictItemConstant.CACHE_KEY_PREFIX + code, value);
        if (result == null) {
            String text = sysDataDictItemMapper.findTextByDictCodeAndValue(code, value);
            setDictItemCache(code, value, text);
            return text;
        }
        return result.toString();
    }

    public void setDictItemCache(String code, String value, String text) {
        redisTemplate.opsForHash().put(SysDataDictItemConstant.CACHE_KEY_PREFIX + code, value, text);
    }

    private void setDictItemCache(SysDataDictItemCacheVO cacheVO) {
        redisTemplate.opsForHash().put(SysDataDictItemConstant.CACHE_KEY_PREFIX + cacheVO.getCode(),
                cacheVO.getValue(), cacheVO.getText());
    }

    private void deleteDictItemCache(String code, String value) {
        redisTemplate.opsForHash().delete(SysDataDictItemConstant.CACHE_KEY_PREFIX + code, value);
    }

    private void deleteDictCache(String code) {
        redisTemplate.delete(SysDataDictItemConstant.CACHE_KEY_PREFIX + code);
    }
}