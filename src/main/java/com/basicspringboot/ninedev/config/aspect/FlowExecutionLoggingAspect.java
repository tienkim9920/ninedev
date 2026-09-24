package com.basicspringboot.ninedev.config.aspect;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Aspect 
@Component 
@Slf4j 
public class FlowExecutionLoggingAspect {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) || " +
              "within(@org.springframework.stereotype.Service *) || " +
              "within(@org.springframework.stereotype.Repository *)")
    public void applicationLayerPointcut() {}

    @Around("applicationLayerPointcut()") // <-- Nhớ có dấu ngoặc tròn () ở đây
    public Object logExecutionFlow(ProceedingJoinPoint joinPoint) throws Throwable {
        String targetClass = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("===> [START] {}.{}() | Input: {}", targetClass, methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            log.info("===> [SUCCESS] {}.{}() | Time: {} ms", targetClass, methodName, executionTime);
            return result;
        } catch (Throwable ex) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("<XX [EXCEPTION] {}.{}() | Time: {} ms | Error: {}", targetClass, methodName, executionTime, ex.getMessage());
            throw ex;
        }
    }
}