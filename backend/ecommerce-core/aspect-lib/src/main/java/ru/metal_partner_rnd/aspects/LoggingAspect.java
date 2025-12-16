package ru.metal_partner_rnd.aspects;

//import ru.metal_partner_rnd.ecommerce_core.utils.PageTextLoader;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.ProceedingJoinPoint;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.stream.Collectors;

import java.io.IOException;

@Aspect
public class LoggingAspect {

    static final Logger logger = LogManager.getLogger(LoggingAspect.class.getName());
    
    @Around("execution(public String ru.metal_partner_rnd.ecommerce_core.controllers.*.page*(..))")
    public Object controllerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) arguments[0];
        logger.info("Request is received on path \'{}\'", request.getRequestURL());
        Object returnedView = joinPoint.proceed();
        logger.info("\'{}\' was returned on that request", (String) returnedView);
        return returnedView;
    }

    @Around("execution(String[] ru.metal_partner_rnd.ecommerce_core.utils.PageTextLoader.loadPageText(String))")
    public String[] logTextLoading(ProceedingJoinPoint joinPoint) throws Throwable {
        String[] result = (String[]) joinPoint.proceed();
        if (result[0] != null) {
            logger.info(result[0]);
        } else {
            logger.info("A text for a page was successfully loaded");
        }
        return result;
    }

    @Around("call(boolean java.util.Map.containsKey(Object)) && cflow(execution(String ru.metal_partner_rnd.ecommerce_core.controllers.*.getTextForPage(..)))")
    public boolean logCache(ProceedingJoinPoint joinPoint) throws Throwable {
        String fileName = (String) joinPoint.getArgs()[0];
        boolean result = (boolean) joinPoint.proceed();
        if (result) {
            logger.info("Text for the name \'{}\' is taken form cache", fileName);
        } else {
            logger.info("There is no text for the name \'{}\' in cache", fileName);
        }
        return result;
    }

    @Around("call(boolean java.util.Map.containsKey(Object))")
    public boolean test(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean result = (boolean) joinPoint.proceed();
        logger.info("This function WORKS!");
        return result;
    }

    // @Around("execution(* ru.metal_partner_rnd.ecommerce_core.checkers.Checker.check*(..))")
    // public Map<String, String> checkLog(ProceedingJoinPoint joinPoint) throws Throwable {
    //     Map<String, String> result = (Map<String, String>) joinPoint.proceed();
    //     StringBuilder resultingMessage = new StringBuilder("The following values were checked for existance: ");
    //     result.forEach((key, value) -> resultingMessage.append(key + " : " + value + ", "));
    //     logger.info(resultingMessage.toString());
    //     return result;
    // }

    @Around("execution(* ru.metal_partner_rnd.ecommerce_core.services.StartupService.start(..))")
    public void applicationReadyLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("logger: ok");
        logger.info("Application context is ready");
        joinPoint.proceed();
    }

    // @Around("execution(* ru.metal_partner_rnd.ecommerce_core.mointors.*.monitor(..))")
    // public Map<String, Long> monitorLog(ProceedingJoinPoint joinPoint) throws Throwable {
    //     Map<String, Long> result = (Map<String, Long>) joinPoint.proceed();
    //     logger.info("Memory usage: {}", result.entrySet().stream().map(pair -> pair.getKey() + ": " + Long.toString(pair.getValue())).collect(Collectors.joining(" ")));
    //     return result;
    // }





    // @Around("execution(* ru.metal_partner_rnd.ecommerce_core.controllers.MainController.ok(..))")
    // public Boolean logTest(ProceedingJoinPoint joinPoint) throws Throwable {
    //     System.out.println("ok proxy is executing!");
    //     Boolean result = (Boolean) joinPoint.proceed();
    //     switch (result) {
    //         case true:
    //             logger.info("ok");
    //             break;
    //         case false:
    //             logger.warn("not ok");
    //             break;
    //     }
    //     return result;
    // }

}