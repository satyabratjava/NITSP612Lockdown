package com.nt.aspect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("cachingAspect")
@Aspect

public class CachingAspect {
	private Map<String,Object> cacheMap=new HashMap();
	
	@Around("execution(float com.nt.service.ECommerceStore.calcBillAmount(..)) ")
	@Order(2)
	public  Object caching (ProceedingJoinPoint pjp)throws Throwable{
		String key=null;
		Object retVal=null;
		key=pjp.getSignature()+Arrays.toString(pjp.getArgs());
		if(!cacheMap.containsKey(key)) {
			retVal=pjp.proceed();
			cacheMap.put(key, retVal);
			System.out.println("from target");
		}
		else {
			retVal=cacheMap.get(key);
			System.out.println("from cache");
		}
		return retVal;
	}//method
}//class
