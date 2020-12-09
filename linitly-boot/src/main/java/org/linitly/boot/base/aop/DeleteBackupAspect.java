package org.linitly.boot.base.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.linitly.boot.base.config.GeneratorConfig;
import org.linitly.boot.base.constant.global.MyBatisConstant;
import org.linitly.boot.base.dao.BaseBeanMapper;
import org.linitly.boot.base.helper.entity.BaseEntity;
import org.linitly.boot.base.helper.entity.DeleteHelper;
import org.linitly.boot.base.utils.LinitlyUtil;
import org.linitly.boot.base.utils.db.ClassUtil;
import org.linitly.boot.base.utils.db.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author linxiunan
 * @date 10:12 2020/12/3
 * @description
 */
@Slf4j
@Aspect
@Component
public class DeleteBackupAspect {

    @Autowired
    private BaseBeanMapper baseBeanMapper;
    @Autowired
    private HttpServletRequest request;
    @Lazy
    @Autowired
    private DeleteBackupAspect deleteBackupAspect;
    @Autowired
    private GeneratorConfig generatorInfo;

    @Pointcut("@annotation(org.linitly.boot.base.annotation.DeleteBackup)")
    public void deleteBackupAspect() {
    }

    @Before("deleteBackupAspect()")
    public void deleteBackupBefore(JoinPoint joinPoint) {
        aspectBefore(joinPoint);
    }

    @AfterReturning("deleteBackupAspect()")
    public void deleteBackupAfterReturn(JoinPoint joinPoint) {
        if (!generatorInfo.deleteBackupRecord()) return;
        DeleteHelper deleteHelper = MyBatisConstant.DELETE_HELPER.get();
        if (deleteHelper == null || CollectionUtils.isEmpty(deleteHelper.getDeleteData())) return;
        deleteBackupAspect.aspectAfterReturn(joinPoint, deleteHelper);
    }

    @Async
    public void aspectAfterReturn(JoinPoint joinPoint, DeleteHelper deleteHelper) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        try {
            MyBatisConstant.MYBATIS_INTERCEPT_PASS.set(true);
            baseBeanMapper.insertDeleteData(deleteHelper.getDeleteTableName(), deleteHelper.getDeleteData().get(0), deleteHelper.getDeleteData());
            MyBatisConstant.MYBATIS_INTERCEPT_PASS.remove();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("备份删除数据AfterReturn出错：拦截class为{}，方法为{}，参数为{}", methodSignature.getDeclaringTypeName(), methodSignature.getMethod().getName(), Arrays.toString(args));
        }
    }

    private void aspectBefore(JoinPoint joinPoint) {
        if (!generatorInfo.deleteBackupRecord()) return;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        try {

            String mapperClassName = methodSignature.getDeclaringTypeName();
            String tableName = ClassUtil.getTableName(mapperClassName);
            String deleteTableName = ClassUtil.getDeleteTableName(mapperClassName);

            List<Long> ids = getIdList(args);
            List<Map<String, Object>> deleteData = null;
            if (CollectionUtils.isNotEmpty(ids)) {
                deleteData = baseBeanMapper.findByIds(tableName, ids);
            }
            if (CollectionUtils.isNotEmpty(deleteData)) {
                deleteData.forEach(e -> {
                    e.put(MyBatisConstant.SYSTEM_CODE_COLUMN, RequestUtil.getSystemCode(request));
                    e.put(MyBatisConstant.DELETED_USER_ID_COLUMN, LinitlyUtil.getCurrentUserId());
                });
            }
            MyBatisConstant.DELETE_HELPER.set(new DeleteHelper().setDeleteTableName(deleteTableName).setDeleteData(deleteData));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("备份删除数据Before出错：拦截class为{}，方法为{}，参数为{}", methodSignature.getDeclaringTypeName(), methodSignature.getMethod().getName(), Arrays.toString(args));
        }
    }

    private List<Long> getIdList(Object[] args) {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof BaseEntity) {
                Long id = ((BaseEntity) args[i]).getId();
                if (id != null) ids.add(id);
            }
            if (args[i] instanceof Long) {
                ids.add((Long) args[i]);
            }
            if (args[i] instanceof List) {
                List list = (List) args[i];
                if (list.get(0) instanceof Long) {
                    ids.addAll(list);
                }
                if (list.get(0) instanceof BaseEntity) {
                    list.forEach(e -> ids.add(((BaseEntity) e).getId()));
                }
            }
        }
        return ids;
    }
}
