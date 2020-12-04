package org.linitly.boot.base.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.linitly.boot.base.annotation.LogIgnore;
import org.linitly.boot.base.constant.global.MyBatisConstant;
import org.linitly.boot.base.dao.BaseBeanMapper;
import org.linitly.boot.base.helper.entity.BaseEntity;
import org.linitly.boot.base.helper.entity.BaseLog;
import org.linitly.boot.base.helper.entity.LogHelper;
import org.linitly.boot.base.utils.db.ClassUtil;
import org.linitly.boot.base.utils.db.GeneratorHelperUtil;
import org.linitly.boot.base.utils.db.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/12/2 15:06
 * @descrption:
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private BaseBeanMapper baseBeanMapper;
    @Lazy
    @Autowired
    private LogAspect logAspect;

    @Pointcut("execution(public * org.linitly.boot.base.dao..*.*(..)))")
    public void baseAspect() {
    }

    @Pointcut("execution(public * org.linitly.boot.business.dao..*.*(..)))")
    public void businessAspect() {
    }

    @AfterReturning("baseAspect()")
    public void baseAfter(JoinPoint joinPoint) {
        checkAndGet(joinPoint);
    }

    @AfterReturning("businessAspect()")
    public void businessAfter(JoinPoint joinPoint) {
        checkAndGet(joinPoint);
    }

    private void checkAndGet(JoinPoint joinPoint) {
        LogHelper helper = MyBatisConstant.LOG_HELPER.get();
        try {
            if (helper == null) return;
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String mapperClassName = methodSignature.getDeclaringTypeName();
            if (mapperClassName.equals(BaseBeanMapper.class.getName())) {
                MyBatisConstant.LOG_HELPER.remove();
                return;
            }
            if (methodSignature.getMethod().isAnnotationPresent(LogIgnore.class)) {
                MyBatisConstant.LOG_HELPER.remove();
                return;
            }
            BaseLog baseLog = LogUtil.generatorCommonBaseLog(helper);
            logAspect.aspectAfterReturn(helper, mapperClassName, baseLog);
            MyBatisConstant.LOG_HELPER.remove();
        } catch (Exception e) {
            e.printStackTrace();
            MyBatisConstant.LOG_HELPER.remove();
            log.error("记录日志前检查出错：" + JSON.toJSONString(helper));
        }
    }

    @Async
    public void aspectAfterReturn(LogHelper helper, String mapperClassName, BaseLog baseLog) {
        MyBatisConstant.MYBATIS_INTERCEPT_PASS.set(true);
        try {
            switch (helper.getCommandTypeEnum()) {
                case INSERT:
                    dealInsert(helper, mapperClassName, baseLog);
                    break;
                case UPDATE:
                    dealUpdate(helper, mapperClassName, baseLog);
                    break;
                case DELETE:
                    dealDelete(helper, mapperClassName, baseLog);
                    break;
                default:
                    break;
            }
            MyBatisConstant.MYBATIS_INTERCEPT_PASS.remove();
        } catch (Exception e) {
            e.printStackTrace();
            MyBatisConstant.MYBATIS_INTERCEPT_PASS.remove();
            log.error("记录日志出错：" + JSON.toJSONString(helper));
        }
    }

    private void dealInsert(LogHelper helper, String mapperClassName, BaseLog baseLog) {
        List<BaseEntity> changeEntities = helper.getChangeEntities();
        List<BaseLog> baseLogList = new ArrayList<>();
        String logTableName = ClassUtil.getLogTableName(mapperClassName);
        for (BaseEntity baseEntity : changeEntities) {
            baseLogList.add(GeneratorHelperUtil.generatorInsertLog(baseEntity, helper, baseLog));
        }
        baseBeanMapper.insertAllLog(logTableName, baseLogList);
    }

    private void dealUpdate(LogHelper helper, String mapperClassName, BaseLog baseLog) {
        List<BaseEntity> changeEntities = helper.getChangeEntities();
        List<BaseLog> baseLogList = new ArrayList<>();
        String logTableName = ClassUtil.getLogTableName(mapperClassName);
        for (BaseEntity baseEntity : changeEntities) {
            baseLogList.add(GeneratorHelperUtil.generatorUpdateLog(baseEntity, baseLog));
        }
        baseBeanMapper.insertAllLog(logTableName, baseLogList);
    }

    private void dealDelete(LogHelper helper, String mapperClassName, BaseLog baseLog) {
        List<BaseLog> baseLogList = new ArrayList<>();
        String logTableName = ClassUtil.getLogTableName(mapperClassName);
        if (!helper.getEntityIds().isEmpty()) {
            List<Long> entityIds = helper.getEntityIds();
            for (Long entityId : entityIds) {
                baseLogList.add(GeneratorHelperUtil.generatorDeleteLog(entityId, helper, baseLog));
            }
        }
        if (!helper.getChangeEntities().isEmpty()) {
            List<BaseEntity> changeEntities = helper.getChangeEntities();
            for (BaseEntity baseEntity : changeEntities) {
                baseLogList.add(GeneratorHelperUtil.generatorDeleteLog(baseEntity, helper, baseLog));
            }
        }
        baseBeanMapper.insertAllLog(logTableName, baseLogList);
    }
}
