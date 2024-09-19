package com.meghana.store.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAdvice {

    @Pointcut(value = "execution(* com.meghana.store.*.*.*(..) )")
    public void invokePointcut() {

    }

    @Around(value = "invokePointcut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] params = pjp.getArgs();
        log.info("Class : {}, Method invoked : {}(), arguments : {}.", className, methodName, params);
        Object proceed = pjp.proceed();
        log.info("Class : {}, Method : {}(), Response : {}", className, methodName, proceed);
        return proceed;
    }
}
