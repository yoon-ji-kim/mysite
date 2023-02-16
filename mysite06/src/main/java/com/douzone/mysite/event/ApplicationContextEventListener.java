package com.douzone.mysite.event;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

//Web Application Context에 생성되게 spring-servlet.xml에 설정
//요청이 들어와서 Dispatcher Servlet이 init에서 생성이 되면서 실행
//SiteVo 만들기
@Component
public class ApplicationContextEventListener {
	//주입받기
	@Autowired
	private ApplicationContext applicationContext;
				//이벤트 클래스
	@EventListener({ContextRefreshedEvent.class })
	public void handleApplicationContextRefreshedEvent() {
		System.out.println(((Object)applicationContext).getClass());
		System.out.println(System.identityHashCode(applicationContext));
		System.out.println("--- ContextRefreshedEvent() Received---: "+ applicationContext);
		
		InternalResourceViewResolver viewResolver = applicationContext.getBean(InternalResourceViewResolver.class);
		viewResolver.setExposeContextBeansAsAttributes(true);
		viewResolver.setExposedContextBeanNames("site");
		
		//local 변수로 받아오기
		SiteService service= applicationContext.getBean(SiteService.class);
		SiteVo site = service.getSite();
		//site 빈에 등록 시키기  빈 관리하는 Factory를 통해서 등록
		AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry)factory;
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		//SiteVo 객체의 이름,값 넣기
		propertyValues.add("title", site.getTitle());
		propertyValues.add("profile", site.getProfile());
		propertyValues.add("welcome", site.getWelcome());
		propertyValues.add("description", site.getDescription());
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(SiteVo.class);
		beanDefinition.setPropertyValues(propertyValues);
		
		//Bean정의 이름
		registry.registerBeanDefinition("site", beanDefinition);
	}
}
