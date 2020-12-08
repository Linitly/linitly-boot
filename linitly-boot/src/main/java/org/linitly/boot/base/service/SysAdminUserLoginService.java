package org.linitly.boot.base.service;

import com.google.common.collect.Sets;
import org.linitly.boot.base.dao.SysAdminUserMapper;
import org.linitly.boot.base.dto.SysAdminUserLoginDTO;
import org.linitly.boot.base.entity.SysAdminUser;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.enums.SystemEnum;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.Precondition;
import org.linitly.boot.base.utils.algorithm.EncryptionUtil;
import org.linitly.boot.base.utils.auth.AbstractAuth;
import org.linitly.boot.base.utils.jwt.AbstractJwtUtil;
import org.linitly.boot.base.utils.jwt.JwtUtilFactory;
import org.linitly.boot.base.vo.SysAdminUserLoginVO;
import org.linitly.boot.base.vo.SysPostDeptIdVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: linxiunan
 * @date: 2020/11/27 14:52
 * @descrption:
 */
@Lazy
@Service
public class SysAdminUserLoginService {

    @PostConstruct
    public void init() {
        jwtUtil = JwtUtilFactory.getJwtUtil(SystemEnum.ADMIN.getSystemCode());
        adminAuth = jwtUtil.getAbstractAuth();
    }

    @Autowired
    private SysAdminUserMapper sysAdminUserMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private HttpServletRequest request;

    private AbstractJwtUtil jwtUtil;
    private AbstractAuth adminAuth;

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
        Set<String> functionPermissionCodes = Sets.newHashSet();
        String[] tokens = getTokens(sysAdminUser);
        SysAdminUserLoginVO loginVO = new SysAdminUserLoginVO().setToken(tokens[0]).setRefreshToken(tokens[1]);
        BeanUtils.copyProperties(sysAdminUser, loginVO);
        loginVO.setMenuTree(sysRoleService.tree(null, request, functionPermissionCodes, sysAdminUser.getId()));
        adminAuth.loginRedisSet(sysAdminUser.getId().toString(), tokens[0], tokens[1], deptIds, postIds, roleCodes, functionPermissionCodes);
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

    private String[] getTokens(SysAdminUser sysAdminUser) {
        String token = jwtUtil.generateToken(sysAdminUser);
        String refreshToken = jwtUtil.generateRefreshToken(sysAdminUser);
        return new String[]{token, refreshToken};
    }

    public void logout(Long userId) {
        adminAuth.logoutRedisDel(userId.toString());
    }
}
