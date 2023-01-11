package com.uberkautilya.jpawithspringboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {
    @Around("@annotation(log)")
    public Object logMethod(ProceedingJoinPoint proceedingJoinPoint, JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            Signature signature = joinPoint.getSignature();
            joinPoint.getTarget();

            System.out.println("Logging from an advise in LoggingAspect. ");
            Object returnObj = proceedingJoinPoint.proceed();
            System.out.println("After executing the method annotated for aspect");
            return returnObj;
        } catch (Throwable e) {
            System.out.println("In the throwable catch block");
            throw new RuntimeException(e);
        }
    }
}
