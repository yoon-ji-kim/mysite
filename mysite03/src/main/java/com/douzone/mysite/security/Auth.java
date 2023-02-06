package com.douzone.mysite.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Auth {
	//default method
//	public String value() default "USER";
	public String role() default "USER"; // 생략시 @Auth(role="") user로 하겠다
	public boolean test() default false;
}
