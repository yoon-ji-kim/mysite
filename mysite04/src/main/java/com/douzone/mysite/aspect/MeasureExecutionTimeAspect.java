package com.douzone.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MeasureExecutionTimeAspect {
					//리턴타입, 패키지(com.douzone.mysite.repository)의 모든 메소드(모든 파라미터)
	@Around("execution(* *..*.repository.*.*(..)) || execution(* *..*.service.*.*(..)) || execution(* *..*.controller.*.*(..))")
	public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable {
		//before 
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object result = pjp.proceed();
		
		//after
		sw.stop();
		Long totaltime = sw.getTotalTimeMillis();
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className +"." +methodName;
		System.out.println("[Execution Time]["+taskName+ "]"+ totaltime + "milis");
		return result;
		//서비스 시간 : 리포지토리 + 서비스 : 서비스 - 리포지토리
	}
}
