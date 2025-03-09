package com.socialmediaplatform.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class  LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut for all controller methods
    @Pointcut("execution(* com.socialmediaplatform.controller.*.*(..))")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {}",  joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info("Request Argument: {}", arg);
        }
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {

        logger.info("Method executed: {}", joinPoint.getSignature());
        if (result != null) {
            logger.info("Response Class: {}", result.getClass().getName()); // Debugging response type
        }
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            Object responseBody = responseEntity.getBody();  // Extracting the actual response

            if (responseBody != null) {
                logger.info("Response: {}", responseBody);
            } else {
                logger.info("Response Body is null");
            }
        }

        else {
            logger.info("Response: {}", result);
        }


    }


    @AfterThrowing(pointcut="controllerMethods()", throwing="exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {}" , joinPoint.getSignature(), exception);
    }

}

