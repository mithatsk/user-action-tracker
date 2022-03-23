package org.springframework.samples.petclinic.service;

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

    @Pointcut("execution(* org.springframework.samples.petclinic.owner.*.*(..)) || execution(* org.springframework.samples.petclinic.vet.*.*(..))")
	public void myPointcut() {

	}

	@Around("myPointcut()")
	public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();

		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().toString();
		Object[] array = pjp.getArgs();

		LoggedMethod loggedMethod = new LoggedMethod(className, methodName);

		Object object = pjp.proceed();
		String response = mapper.writeValueAsString(object);
		loggedMethod.setResponse(mapper.writeValueAsString(response));

		log.info("{}, \"arguments\": {}", mapper.writeValueAsString(loggedMethod), arguments);

		return object;
	}
}
