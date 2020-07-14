package com.stj.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

public class ExceptionAspect {

	private static final int DEFAULT_MAX_RETRIES = 3;
	private int maxRetries;
	private int order;

	public ExceptionAspect() {
		maxRetries = DEFAULT_MAX_RETRIES;
		order = 1;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
		int numAttempts = 0;
		do {
			numAttempts++;
			try {
				return pjp.proceed();
			} catch (Exception ex) {
				Thread.sleep(3000L);
				Exception exception = ex;
				System.out.println((new StringBuilder("Aspect caught exception: ")).append(ex.getMessage()).toString());
				if (numAttempts > maxRetries) {
					throw exception;
				}
			}
		} while (true);
	}

}
