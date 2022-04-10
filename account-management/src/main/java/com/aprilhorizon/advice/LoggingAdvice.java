package com.aprilhorizon.advice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

    Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut(value="execution(* com.aprilhorizon.accountmanagement.AccountService.*(..) )")
    public void myPointcut() {

    }

    @Around("myPointcut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();

        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] args = pjp.getArgs();
        LoggedMethod loggedMethod = new LoggedMethod(className, methodName);

        Object object = pjp.proceed();
        String response = mapper.writeValueAsString(object);
        
        loggedMethod.setResponse(response);

        log.info("{}, \"arguments:\", {}", mapper.writeValueAsString(loggedMethod), args);
    
        return object;
    }
}
