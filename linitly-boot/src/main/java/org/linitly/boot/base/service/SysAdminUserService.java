package org.linitly.boot.base.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;
import org.linitly.boot.base.constant.entity.SysAdminUserConstant;
import org.linitly.boot.base.dao.SysAdminUserMapper;
import org.linitly.boot.base.dto.sys_admin_user.SysAdminUserDTO;
import org.linitly.boot.base.dto.sys_admin_user.SysAdminUserSearchDTO;
import org.linitly.boot.base.entity.SysAdminUser;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.algorithm.EncryptionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 10:26
 * @description: 
 */
@Service
public class SysAdminUserService {

    @Autowired
    private SysAdminUserMapper sysAdminUserMapper;

    public void insert(SysAdminUserDTO dto) {
        checkSysAdminUser(dto.getMobileNumber(), dto.getUsername(), dto.getJobNumber(), dto.getId());
        SysAdminUser sysAdminUser = new SysAdminUser();
        BeanUtils.copyProperties(dto, sysAdminUser);
        String salt = RandomStringUtils.randomAlphanumeric(SysAdminUserConstant.MAX_SALT_SIZE);
        sysAdminUser = sysAdminUser.setSalt(salt).setPassword(EncryptionUtil.md5(SysAdminUserConstant.DEFAULT_PASSWORD, salt));
        sysAdminUserMapper.insertSelective(sysAdminUser);
    }

    private void checkSysAdminUser(String mobileNumber, String username, String jobNumber, Long id) {
        int count = sysAdminUserMapper.countByMobileOrUsernameOrJobNumber(null, username, null, id);
        if (count > 0) {
            throw new CommonException("用户名已被占用");
        }
        count = sysAdminUserMapper.countByMobileOrUsernameOrJobNumber(mobileNumber, null, null, id);
        if (count > 0) {
            throw new CommonException("手机号已被占用");
        }
        count = sysAdminUserMapper.countByMobileOrUsernameOrJobNumber(null, null, jobNumber, id);
        if (count > 0) {
            throw new CommonException("工号已被占用");
        }
    }

    public void updateById(SysAdminUserDTO dto) {
        checkSysAdminUser(dto.getMobileNumber(), dto.getUsername(), dto.getJobNumber(), dto.getId());
        SysAdminUser sysAdminUser = new SysAdminUser();
        BeanUtils.copyProperties(dto, sysAdminUser);
        sysAdminUserMapper.updateByIdSelective(sysAdminUser);
    }

    public SysAdminUser findById(Long id) {
        return sysAdminUserMapper.findById(id);
    }

    public List<SysAdminUser> findAll(SysAdminUserSearchDTO dto) {
        return sysAdminUserMapper.findAll(dto);
    }

    @Transactional
    public void deleteById(Long id) {
        // TODO 删除对应用户角色关联表
        sysAdminUserMapper.deleteById(id);
    }
}