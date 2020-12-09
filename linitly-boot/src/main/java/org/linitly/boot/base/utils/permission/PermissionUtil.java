package org.linitly.boot.base.utils.permission;

import org.linitly.boot.base.annotation.RequirePermission;
import org.linitly.boot.base.annotation.RequireRole;
import org.linitly.boot.base.enums.Logical;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.exception.CommonException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * @author: linxiunan
 * @date: 2020/12/9 10:59
 * @descrption:
 */
public class PermissionUtil {

    public static boolean hasPower(Method method, Class<? extends Annotation> annotationClass, String[] requirePermissions, Set<String> havePermissions) {
        Logical logical = getRoleOrPermissionLogical(method, annotationClass);
        if (logical == null) throw new CommonException(ResultEnum.NO_PERMISSION);
        switch (logical) {
            case AND:
                return havePermissions.containsAll(Arrays.asList(requirePermissions));
            case OR:
                havePermissions.retainAll(Arrays.asList(requirePermissions));
                return havePermissions.size() > 0;
            default:
                return false;
        }
    }

    public static String[] getRequireRoleOrPermission(Method method, Class<? extends Annotation> annotationClass) {

        if (annotationClass.equals(RequireRole.class)) {
            RequireRole requireRole = method.getAnnotation(RequireRole.class);
            return requireRole == null ? null : requireRole.value();
        } else {
            RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);
            return requirePermission == null ? null : requirePermission.value();
        }
    }

    private static Logical getRoleOrPermissionLogical(Method method, Class<? extends Annotation> annotationClass) {
        if (annotationClass.equals(RequireRole.class)) {
            RequireRole requireRole = method.getAnnotation(RequireRole.class);
            return requireRole == null ? null : requireRole.logical();
        } else {
            RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);
            return requirePermission == null ? null : requirePermission.logical();
        }
    }
}
