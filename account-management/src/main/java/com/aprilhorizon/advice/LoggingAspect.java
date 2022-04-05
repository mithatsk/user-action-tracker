package com.aprilhorizon.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut(value="execution(* com.aprilhorizon.accountmanagement.AccountService.*(..) )")
    public void accountManagementPointcut() {}

    @Around("accountManagementPointcut()")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        LogResultTuple resultTuple = createMethodLog(joinPoint);
        LoggedMethod methodLog = resultTuple.getMethodLog();
        Object response = resultTuple.getResponse();

        log(methodLog);

        return response;
    }

    private LogResultTuple<LoggedMethod, Object> createMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();
        String methodName = method.getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object[] methodArgs = joinPoint.getArgs();
        String[] methodParams = codeSignature.getParameterNames();

        LoggedMethod methodLog = new LoggedMethod(className, methodName);
        methodLog.setArguments(mapArguments(methodParams, methodArgs));

        Instant start = Instant.now();
        Object responseObject = joinPoint.proceed();
        Instant end = Instant.now();
        String executionTime = String.format("%s", ChronoUnit.MILLIS.between(start, end));
        methodLog.setExecutionTime(executionTime);
        methodLog.setExecutionTimeUnit(ChronoUnit.MILLIS);

        ObjectMapper mapper = new ObjectMapper();
        // Check if method has a response
        if (Objects.nonNull(responseObject)) {
            String result = mapper.writeValueAsString(responseObject);
            LoggedMethodResponse response = new LoggedMethodResponse(result, responseObject.getClass().getName());
            methodLog.setResponse(response);
        }


        return new LogResultTuple<>(methodLog, responseObject);
    }

    void log(LoggedMethod object) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(object).replaceAll("\\\\", "");
        logger.info(message);
    }

    ArrayList<LoggedArgument> mapArguments(String[] params, Object[] args) {
        ArrayList<LoggedArgument> arguments = new ArrayList<>();
        // Check if number of parameters and arguments exists and equal to each other.
        if (Objects.nonNull(params) && Objects.nonNull(args) && params.length == args.length) {
            for (int index = 0; index < params.length; index++) {
                LoggedArgument argument = new LoggedArgument(params[index], args[index].toString(), args[index].getClass().getName());
                arguments.add(argument);
            }
        }
        return arguments;
    }
}