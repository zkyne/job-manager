package com.zkyne.jobmanager.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: LogAspect
 * @Description: 统一记录Http访问日志
 * @Author: zkyne
 * @Date: 2018/6/22 11:11
 */
@Slf4j
@Component
@Scope
@Aspect
public class LogAspect {
    /**层切点*/
    @Pointcut("@annotation(com.zkyne.jobmanager.aspect.LogHandle)")
    public void logAspect() {
    }

    @Before("logAspect()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            HttpServletRequest request = requestAttributes.getRequest();
            Signature signature = joinPoint.getSignature();
            log.info("访问接口URL={},访问类_方法={},访问参数Arsg={}", request.getRequestURL(),
                    signature.getDeclaringTypeName() + "." + signature.getName(),joinPoint.getArgs());
        }
    }

    @AfterReturning(returning = "object",pointcut = "logAspect()")
    public void doAfterReturn(Object object) {
        log.info("接口返回值={}",object.toString());
    }
}
