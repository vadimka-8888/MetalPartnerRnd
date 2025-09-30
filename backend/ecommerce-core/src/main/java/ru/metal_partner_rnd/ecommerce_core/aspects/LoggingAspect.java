package ru.metal_partner_rnd.ecommerce_core.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.metal_partner_rnd.ecommerce_core.controllers.LoggingController;

@Aspect
@Component
public class LoggingAspect {

    static final Logger logger = LogManager.getLogger(LoggingController.class.getName());
    
    @Around("execution(* controllers.*.*(..))")
    public void log(ProceedingJoinPoint joinPoint) throws Throwable {
        Object [] arguments = joinPoint.getArgs();
        logger.info(String.format("Request is received on path \'%s\'", arguments[0]));
        Object returnedView = joinPoint.proceed();
        logger.info(String.format("\'%s\' was returned on that request", ((HttpServletRequest) returnedView).getRequestURI()));
    }
}