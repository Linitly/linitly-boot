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
        logAspect.aspectAfterReturn(joinPoint);
    }

    @AfterReturning("businessAspect()")
    public void businessAfter(JoinPoint joinPoint) {
        logAspect.aspectAfterReturn(joinPoint);
    }

    @Async
    public void aspectAfterReturn(JoinPoint joinPoint) {
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
        LogHelper helper = MyBatisConstant.LOG_HELPER.get();
        if (helper == null) return;

        try {
            switch (helper.getCommandTypeEnum()) {
                case INSERT:
                    dealInsert(helper, mapperClassName);
                    break;
                case UPDATE:
                    dealUpdate(helper, mapperClassName);
                    break;
                case DELETE:
                    dealDelete(helper, mapperClassName);
                    break;
                default:
                    break;
            }
            MyBatisConstant.LOG_HELPER.remove();
        } catch (Exception e) {
            e.printStackTrace();
            MyBatisConstant.LOG_HELPER.remove();
            log.error("记录日志出错：" + JSON.toJSONString(helper));
        }
    }

    private void dealInsert(LogHelper helper, String mapperClassName) {
        List<BaseEntity> changeEntities = helper.getChangeEntities();
        List<BaseLog> baseLogList = new ArrayList<>();
        String logTableName = ClassUtil.getLogTableName(mapperClassName);
        for (BaseEntity baseEntity : changeEntities) {
            baseLogList.add(GeneratorHelperUtil.generatorInsertLog(baseEntity, helper));
        }
        baseBeanMapper.insertAllLog(logTableName, baseLogList);
    }

    private void dealUpdate(LogHelper helper, String mapperClassName) {
        List<BaseEntity> changeEntities = helper.getChangeEntities();
        List<BaseLog> baseLogList = new ArrayList<>();
        String logTableName = ClassUtil.getLogTableName(mapperClassName);
        for (BaseEntity baseEntity : changeEntities) {
            baseLogList.add(GeneratorHelperUtil.generatorUpdateLog(baseEntity, helper));
        }
        baseBeanMapper.insertAllLog(logTableName, baseLogList);
    }

    private void dealDelete(LogHelper helper, String mapperClassName) {
        List<BaseLog> baseLogList = new ArrayList<>();
        String logTableName = ClassUtil.getLogTableName(mapperClassName);
        if (!helper.getEntityIds().isEmpty()) {
            List<Long> entityIds = helper.getEntityIds();
            for (Long entityId : entityIds) {
                baseLogList.add(GeneratorHelperUtil.generatorDeleteLog(entityId, helper));
            }
        }
        if (!helper.getChangeEntities().isEmpty()) {
            List<BaseEntity> changeEntities = helper.getChangeEntities();
            for (BaseEntity baseEntity : changeEntities) {
                baseLogList.add(GeneratorHelperUtil.generatorDeleteLog(baseEntity, helper));
            }
        }
        baseBeanMapper.insertAllLog(logTableName, baseLogList);
    }
}
