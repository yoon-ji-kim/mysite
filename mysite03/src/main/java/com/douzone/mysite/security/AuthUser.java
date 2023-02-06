package com.douzone.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  //어떤 타입에 들어갈 건지, 실행시: RUNTIME
@Target(ElementType.PARAMETER) //파라미터
public @interface AuthUser {

}
