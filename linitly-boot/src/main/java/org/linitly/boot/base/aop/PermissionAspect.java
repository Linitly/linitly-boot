package org.linitly.boot.base.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.linitly.boot.base.annotation.RequirePermission;
import org.linitly.boot.base.annotation.RequireRole;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.utils.RedisOperator;
import org.linitly.boot.base.utils.jwt.JwtAdminUtil;
import org.linitly.boot.base.constant.admin.AdminJwtConstant;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.permission.PermissionAnnotationUtil;
import org.linitly.boot.base.utils.permission.RoleAndPermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Set;

@Aspect
@Component
public class PermissionAspect {

	@Autowired
	private RedisOperator redisOperator;

	@Pointcut("@annotation(org.linitly.boot.base.annotation.RequirePermission)")
	public void permissionAspect() {
	}

	@Pointcut("@annotation(org.linitly.boot.base.annotation.RequireRole)")
	public void roleAspect() {
	}

	@Around("permissionAspect()")
	public Object permissionAspectAround(ProceedingJoinPoint joinPoint) throws Throwable {
		return aspectAround(RequirePermission.class, joinPoint);
	}

	@Around("roleAspect()")
	public Object roleAspectAround(ProceedingJoinPoint joinPoint) throws Throwable {
		return aspectAround(RequireRole.class, joinPoint);
	}

	private Object aspectAround(Class targetClass, ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			Object target = joinPoint.getTarget();
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
			HttpServletRequest request = servletRequestAttributes.getRequest();

			String token = JwtAdminUtil.getAdminRefreshToken(request);
			Set<String> rolesOrPermissions = targetClass == RequireRole.class ?
					redisOperator.setMembers(AdminJwtConstant.ADMIN_ROLES_PREFIX + token) :
					redisOperator.setMembers(AdminJwtConstant.ADMIN_PERMISSIONS_PREFIX + token);

			String[] requireRolesOrPermissions = PermissionAnnotationUtil.parseRoleOrPermission(target.getClass(),
					methodSignature.getName(), methodSignature.getParameterTypes(), targetClass);
			if (requireRolesOrPermissions != null && requireRolesOrPermissions.length > 0) {
				boolean hasRoleOrPermission = RoleAndPermissionUtil.hasRoleOrPermission(new ArrayList<>(rolesOrPermissions), requireRolesOrPermissions,
						target.getClass(), methodSignature.getName(), methodSignature.getParameterTypes(), targetClass);
				if (!hasRoleOrPermission)
					throw new CommonException(ResultEnum.NO_PERMISSION);
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new CommonException(ResultEnum.CLASS_METHOD_ERROR);
		}
		return joinPoint.proceed();
	}
}
