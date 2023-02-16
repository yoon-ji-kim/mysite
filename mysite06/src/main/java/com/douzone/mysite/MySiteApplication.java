package com.douzone.mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MySiteApplication {
	
	//Application Context Event Listener -> Component
//	@Bean
//	public ApplicationContextEventListener applicationContextEventListener() {
//		return new ApplicationContextEventListener();
//	}

	//View Resolver  --> EventListener로
//	@Bean
//	public ViewResolver viewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setViewClass(JstlView.class);
//		viewResolver.setPrefix("/WEB-INF/views/");
//		viewResolver.setSuffix(".jsp");
//		viewResolver.setExposeContextBeansAsAttributes(true);
//		viewResolver.setExposedContextBeanNames("site");
//		return viewResolver;
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(MySiteApplication.class, args);
	}
}
