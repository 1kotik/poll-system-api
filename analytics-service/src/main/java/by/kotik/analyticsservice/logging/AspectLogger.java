package by.kotik.analyticsservice.logging;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AspectLogger {
    @Pointcut("within(by.kotik.analyticsservice.service.*)")
    public void isServiceLayer(){}
    @Pointcut("within(by.kotik.analyticsservice.controller.*)")
    public void isControllerLayer(){}

    @AfterReturning(value = "isServiceLayer() || isControllerLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result){
        log.info("Invoked {}. Returned: {}", joinPoint.getSignature().getName(), result.toString());
    }

    @AfterThrowing(value = "isServiceLayer() || isControllerLayer()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex){
        log.error("Invoked {}. Thrown: {}. Message {}",
                joinPoint.getSignature().getName(), ex.getClass(), ex.getMessage());
    }
}
