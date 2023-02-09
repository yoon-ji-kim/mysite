package com.douzone.mysite.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

//Web Application Context에 생성되게 spring-servlet.xml에 설정
//요청이 들어와서 Dispatcher Servlet이 init에서 생성이 되면서 실행
//SiteVo 만들기
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
		//local 변수로 받아오기
		SiteService service= applicationContext.getBean(SiteService.class);
		SiteVo site = service.getSite();
		//site 빈에 등록 시키기    
	}
}
