package org.linitly.boot.base.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.linitly.boot.base.annotation.RequirePermission;
import org.linitly.boot.base.annotation.RequireRole;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.LinitlyUtil;
import org.linitly.boot.base.utils.permission.PermissionUtil;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;

@Aspect
@Component
public class PermissionAspect {

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

	private Object aspectAround(Class<? extends Annotation> annotationClass, ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

			Set<String> rolesOrPermissions = annotationClass == RequireRole.class ?
					LinitlyUtil.getCurrentCacheRoles() : LinitlyUtil.getCurrentCacheFunctionPermissions();

			String[] requireRolesOrPermissions = PermissionUtil.getRequireRoleOrPermission(methodSignature.getMethod(), annotationClass);

			if (requireRolesOrPermissions != null && requireRolesOrPermissions.length > 0) {
				if (!PermissionUtil.hasPower(methodSignature.getMethod(), annotationClass, requireRolesOrPermissions, rolesOrPermissions))
					throw new CommonException(ResultEnum.NO_PERMISSION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException(ResultEnum.NO_PERMISSION);
		}
		return joinPoint.proceed();
	}
}
