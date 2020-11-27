package org.linitly.boot.base.service;

import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dao.SysAdminUserMapper;
import org.linitly.boot.base.dto.SysAdminUserLoginDTO;
import org.linitly.boot.base.entity.SysAdminUser;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.Precondition;
import org.linitly.boot.base.utils.algorithm.EncryptionUtil;
import org.linitly.boot.base.utils.jwt.AbstractJwtUtil;
import org.linitly.boot.base.utils.jwt.JwtUtilFactory;
import org.linitly.boot.base.vo.SysAdminUserLoginVO;
import org.linitly.boot.base.vo.SysPostDeptIdVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: linxiunan
 * @date: 2020/11/27 14:52
 * @descrption:
 */
@Service
public class SysAdminUserLoginService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysAdminUserMapper sysAdminUserMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public SysAdminUserLoginVO login(SysAdminUserLoginDTO dto) {
        SysAdminUser sysAdminUser = check(dto);
        Set<Long> postIds = new HashSet<>();
        Set<Long> deptIds = new HashSet<>();
        List<SysPostDeptIdVO> postDeptIdList = sysAdminUserMapper.findPostAndDeptIdsByAdminUserId(sysAdminUser.getId());
        postDeptIdList.forEach(e -> {
            postIds.add(e.getPostId());
            deptIds.add(e.getDeptId());
        });
        Set<String> roleCodes = sysAdminUserMapper.findRoleCodesByAdminUserId(sysAdminUser.getId());
        Set<String> functionPermissionCodes = sysAdminUserMapper.findFunctionPermissionCodesByAdminUserId(sysAdminUser.getId());
        String[] tokens = getToken(sysAdminUser);
        setRedis(postIds, deptIds, roleCodes, functionPermissionCodes, tokens, sysAdminUser.getId());
        SysAdminUserLoginVO loginVO = new SysAdminUserLoginVO().setToken(tokens[0]).setRefreshToken(tokens[1]);
        BeanUtils.copyProperties(sysAdminUser, loginVO);
        return loginVO;
    }

    private SysAdminUser check(SysAdminUserLoginDTO dto) {
        SysAdminUser sysAdminUser = sysAdminUserMapper.findByUsername(dto.getUsername());
        if (sysAdminUser == null) {
            sysAdminUser = sysAdminUserMapper.findByMobileNumber(dto.getUsername());
        }
        Precondition.checkNotNull(sysAdminUser, ResultEnum.USERNAME_ERROR);
        if (!sysAdminUser.getPassword().equals(EncryptionUtil.md5(dto.getPassword(), sysAdminUser.getSalt()))) {
            throw new CommonException(ResultEnum.PASSWORD_ERROR);
        }
        return sysAdminUser;
    }

    private String[] getToken(SysAdminUser sysAdminUser) {
        AbstractJwtUtil jwtUtil = JwtUtilFactory.getJwtUtil(request);
        return jwtUtil.generateToken(sysAdminUser);
    }

    private void setRedis(Set<Long> postIds, Set<Long> deptIds, Set<String> roleCodes, Set<String> functionPermissionCodes, String[] tokens, Long id) {
        redisTemplate.opsForValue().set(AdminCommonConstant.ADMIN_TOKEN_PREFIX + id, tokens[0], AdminCommonConstant.ADMIN_TOKEN_EXPIRE_SECOND);
        redisTemplate.opsForValue().set(AdminCommonConstant.ADMIN_REFRESH_TOKEN_PREFIX + id, tokens[1], AdminCommonConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND);
        redisTemplate.opsForSet().add(AdminCommonConstant.ADMIN_POSTS_PREFIX + id, postIds.toArray());
        redisTemplate.opsForSet().add(AdminCommonConstant.ADMIN_DEPTS_PREFIX + id, deptIds.toArray(), AdminCommonConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND);
        redisTemplate.opsForSet().add(AdminCommonConstant.ADMIN_ROLES_PREFIX + id, roleCodes.toArray(), AdminCommonConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND);
        redisTemplate.opsForSet().add(AdminCommonConstant.ADMIN_FUNCTION_PERMISSIONS_PREFIX + id, functionPermissionCodes.toArray(), AdminCommonConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND);
        redisTemplate.expire(AdminCommonConstant.ADMIN_POSTS_PREFIX + id, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND, TimeUnit.SECONDS);
        redisTemplate.expire(AdminCommonConstant.ADMIN_DEPTS_PREFIX + id, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND, TimeUnit.SECONDS);
        redisTemplate.expire(AdminCommonConstant.ADMIN_ROLES_PREFIX + id, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND, TimeUnit.SECONDS);
        redisTemplate.expire(AdminCommonConstant.ADMIN_FUNCTION_PERMISSIONS_PREFIX + id, AdminCommonConstant.ADMIN_RPPD_EXPIRE_SECOND, TimeUnit.SECONDS);
    }
}
