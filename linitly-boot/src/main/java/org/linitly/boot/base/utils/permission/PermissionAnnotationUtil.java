package org.linitly.boot.base.utils.permission;


import org.linitly.boot.base.annotation.RequirePermission;
import org.linitly.boot.base.annotation.RequireRole;
import org.linitly.boot.base.enums.Logical;

import java.lang.reflect.Method;

public class PermissionAnnotationUtil {

	public static String[] parseRoleOrPermission(Class<?> targetClass, String methodName, Class<?>[] parameterTypes, Class<?> annotationClass) throws NoSuchMethodException, SecurityException {
		Method method = getMethod(targetClass, methodName, parameterTypes);
		
		if (annotationClass.equals(RequireRole.class)) {
			RequireRole requireRole = method.getAnnotation(RequireRole.class);
			return requireRole == null ? null : requireRole.value();
		} else {
			RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);
			return requirePermission == null ? null : requirePermission.value();
		}
	}
	
	public static Logical getRoleOrPermissionLogical(Class<?> targetClass, String methodName, Class<?>[] parameterTypes, Class<?> annotationClass) throws NoSuchMethodException, SecurityException {
		Method method = getMethod(targetClass, methodName, parameterTypes);
		
		if (annotationClass.equals(RequireRole.class)) {
			RequireRole requireRole = method.getAnnotation(RequireRole.class);
			return requireRole == null ? null : requireRole.logical();
		} else {
			RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);
			return requirePermission == null ? null : requirePermission.logical();
		}
	}
	
	public static boolean AssertPermissionAnnotation(Class<?> targetClass, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException, SecurityException {
		Method method = getMethod(targetClass, methodName, parameterTypes);
		if (method.isAnnotationPresent(RequirePermission.class)) {
			return true;
		}
		return false;
	}
	
	public static boolean AssertRoleAnnotation(Class<?> targetClass, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException, SecurityException {
		Method method = getMethod(targetClass, methodName, parameterTypes);
		if (method.isAnnotationPresent(RequireRole.class)) {
			return true;
		}
		return false;
	}
	
	private static Method getMethod(Class<?> targetClass, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException, SecurityException {
		Method method = targetClass.getMethod(methodName, parameterTypes);
		return method;
	}

	public static boolean hasAnnotation(Class<?> targetClass, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException, SecurityException {
		return (AssertPermissionAnnotation(targetClass, methodName, parameterTypes) | AssertRoleAnnotation(targetClass, methodName, parameterTypes));
	}

}
