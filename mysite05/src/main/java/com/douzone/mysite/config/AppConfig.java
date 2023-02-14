package com.douzone.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.mysite.config.app.DBConfig;
import com.douzone.mysite.config.app.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy // autoproxy 활성화 : <aop:aspectj-autoproxy /> 대체
@ComponentScan({"com.douzone.mysite.service","com.douzone.mysite.repository","com.douzone.mysite.exception", "com.douzone.mysite.aspect"}) // <context:annotation-config /> 대체
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {
//root 설정, application context

}
