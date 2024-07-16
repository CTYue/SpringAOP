package com.example.springaop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class WebAspect {
    private static final Logger logger = LoggerFactory.getLogger(WebAspect.class);

    // 定义一个切入点表达式，拦截com.example.service包中的所有方法
    @Pointcut("execution(* com.example.springaop.service.*.*(..))")
    public void serviceLayer() {
        // 切入点方法没有实现，因为它只是一个标识
    }

    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Inside WebAspect @Before");
        logger.info("Executing method: " + joinPoint.getSignature().getName());
        logger.info("Method arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Inside WebAspect @AfterReturning");
        logger.info("Method: " + joinPoint.getSignature().getName() + " returned with value: " + result);
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.info("Inside WebAspect @AfterThrowing");
        logger.error("Method: " + joinPoint.getSignature().getName() + " threw an exception: " + error);
    }

    @Around("serviceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Inside WebAspect @Around");
        logger.info("Method: " + joinPoint.getSignature().getName() + " is about to execute");
        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed(); // 执行目标方法
        } catch (Throwable throwable) {
            logger.error("Method: " + joinPoint.getSignature().getName() + " threw an exception: " + throwable);
            throw throwable; // 重新抛出异常以确保原有逻辑不变
        }

        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Method: " + joinPoint.getSignature().getName() + " executed in: " + timeTaken + "ms");
        logger.info("Method: " + joinPoint.getSignature().getName() + " returned with value: " + result);

        return result; // 返回目标方法的返回值
    }

}
