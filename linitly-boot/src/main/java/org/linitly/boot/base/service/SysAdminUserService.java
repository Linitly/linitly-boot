package org.linitly.boot.base.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.entity.SysAdminUserConstant;
import org.linitly.boot.base.dao.SysAdminUserMapper;
import org.linitly.boot.base.dto.SysAdminUserChangePasswordDTO;
import org.linitly.boot.base.dto.SysAdminUserDTO;
import org.linitly.boot.base.dto.SysAdminUserSearchDTO;
import org.linitly.boot.base.entity.SysAdminUser;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.helper.entity.BaseEntity;
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
    @Autowired
    private SysAdminUserCacheService sysAdminUserCacheService;

    public void changePassword(SysAdminUserChangePasswordDTO dto) {
        SysAdminUser adminUser = sysAdminUserMapper.findById(dto.getId());
        if (!dto.getPassword().equals(dto.getConfirmPassword())) throw new CommonException("两次密码输入不一致");
        if (adminUser == null) throw new CommonException("不存在该用户");
        String password = EncryptionUtil.md5(dto.getBeforePassword(), adminUser.getSalt());
        if (!adminUser.getPassword().equals(password)) throw new CommonException("原密码输入错误");
        SysAdminUser sysAdminUser = new SysAdminUser();
        BeanUtils.copyProperties(dto, sysAdminUser);
        sysAdminUserMapper.changePassword(sysAdminUser);
    }

    @Transactional
    public void insert(SysAdminUserDTO dto) {
        checkSysAdminUser(dto.getMobileNumber(), dto.getUsername(), dto.getJobNumber(), dto.getId());
        SysAdminUser sysAdminUser = new SysAdminUser();
        BeanUtils.copyProperties(dto, sysAdminUser);
        String salt = RandomStringUtils.randomAlphanumeric(SysAdminUserConstant.MAX_SALT_SIZE);
        String password = StringUtils.isBlank(sysAdminUser.getPassword()) ? SysAdminUserConstant.DEFAULT_PASSWORD : sysAdminUser.getPassword();
        sysAdminUser = sysAdminUser.setSalt(salt).setPassword(EncryptionUtil.md5(password, salt));
        sysAdminUserMapper.insertSelective(sysAdminUser);
        empower(sysAdminUser.getId(), dto.getRoleIds(), dto.getPostIds(), false);
    }

    @Transactional
    public void updateById(SysAdminUserDTO dto) {
        checkSysAdminUser(dto.getMobileNumber(), dto.getUsername(), dto.getJobNumber(), dto.getId());
        SysAdminUser sysAdminUser = new SysAdminUser();
        BeanUtils.copyProperties(dto, sysAdminUser);
        sysAdminUserMapper.updateByIdSelective(sysAdminUser);
        empower(sysAdminUser.getId(), dto.getRoleIds(), dto.getPostIds(), true);
        sysAdminUserCacheService.updateAdminUser(dto.getId());
    }

    public SysAdminUser findById(Long id) {
        return sysAdminUserMapper.findById(id);
    }

    public List<SysAdminUser> findAll(SysAdminUserSearchDTO dto) {
        return sysAdminUserMapper.findAll(dto);
    }

    @Transactional
    public void deleteById(Long id) {
        sysAdminUserMapper.deleteById(id);
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

    private void empower(Long id, List<Long> roleIds, List<Long> postIds, boolean update) {
        if (update) {
            sysAdminUserMapper.deletePostsByAdminUserId(id);
            sysAdminUserMapper.deleteRolesByAdminUserId(id);
        }
        sysAdminUserMapper.insertAdminUserRole(id, roleIds);
        sysAdminUserMapper.insertAdminUserPost(id, postIds);
    }
}