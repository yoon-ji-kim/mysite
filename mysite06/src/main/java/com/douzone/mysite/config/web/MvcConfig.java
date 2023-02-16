package com.douzone.mysite.config.web;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc //WebMvcConfigurer 사용시 EnableWebMvc 적어주기
public class MvcConfig implements WebMvcConfigurer{
	//View Resolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setExposeContextBeansAsAttributes(true);
		viewResolver.setExposedContextBeanNames("site");
		return viewResolver;
	}
	//Message-converters
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
//		List<MediaType> list = new ArrayList<>();
//		list.add(new MediaType("text", "html", Charset.forName("utf-8")));
//		messageConverter.setSupportedMediaTypes(list);
		messageConverter.setSupportedMediaTypes(
				Arrays.asList(
						new MediaType("text", "html", Charset.forName("utf-8"))
						)
				);
		return messageConverter;
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
																						// 들여쓰기	dateFormat
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder().indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-mm-dd"));
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(builder.build());
		messageConverter.setSupportedMediaTypes(
				Arrays.asList(
						new MediaType("application", "json", Charset.forName("utf-8"))
				)
		);
		return messageConverter;
	}
	//Message-converters override
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}

	//URL Resource Mapping
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/assets/**")
			.addResourceLocations("classpath:/assets/");
	}
	
	//Default Servlet Handler
//	@Override
//	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		configurer.enable();
//	}
	
}
