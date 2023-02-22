package com.uberkautilya.jpawithspringboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("@annotation(log)")
    public Object logMethod(ProceedingJoinPoint proceedingPoint) {
        try {
            Object[] args = proceedingPoint.getArgs();
            Signature signature = proceedingPoint.getSignature();
            proceedingPoint.getTarget();

            System.out.println("Logging from an advise in LoggingAspect. ");
            Object returnObj = proceedingPoint.proceed();
            System.out.println("After executing the method annotated for aspect");
            return returnObj;
        } catch (Throwable e) {
            System.out.println("In the throwable catch block");
            throw new RuntimeException(e);
        }
    }
}
