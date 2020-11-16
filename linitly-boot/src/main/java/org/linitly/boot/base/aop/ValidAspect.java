package org.linitly.boot.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.valid.BindingResultUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import java.util.Arrays;

/**
 * @author: linxiunan
 * @date: 2020/10/12 13:52
 * @descrption:
 */
@Aspect
@Component
public class ValidAspect {

    @Pointcut("execution(public * org.linitly.boot.base.controller..*.*(..)))")
    public void baseAspect() {
    }

    @Pointcut("execution(public * org.linitly.boot.business.controller..*.*(..)))")
    public void businessAspect() {
    }

    @Before("baseAspect()")
    public void baseAspectBefore(JoinPoint joinPoint) {
        aspectAround(joinPoint);
    }

    @Before("businessAspect()")
    public void businessAspectBefore(JoinPoint joinPoint) {
        aspectAround(joinPoint);
    }

    private void aspectAround(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).filter(e -> e instanceof BindingResult).forEach(e -> validResult((BindingResult) e));
    }

    private void validResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new CommonException(GlobalConstant.GENERAL_ERROR, BindingResultUtil.getBindingResultErrMsg(bindingResult));
    }
}
