package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1. handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			//DefaultServletHandler가 처리하는 경우
			// 정적자원, /assets/**
			 return true;
		}
		//Handler Method인 경우
		//핸들러 정보를 담고 있는 type : Handler Method로 캐스팅
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		//3. Handler Method의 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. Handler Method에 @Auth가 안되어 있으면 Type(Class)에 붙어 있는 지 확인
		if(auth == null) {
			//getBeanType()
			//This method returns the type of the handler for this handler method. 
			//핸들러 메서드에 대한 핸들러 유형을 반환
			//현재 찾은 핸들러 메서드에 Auth(커스텀 어노테이션)이 있는지
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
		}
		//5. type이나 method에 @Auth가 없는 경우(인증 필요 없는 경우) 
		if(auth == null ) {
			return true;
		}
		//6. @Auth가 annotation이 붙어 있기 때문에 (Authenfication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser= (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			//인증이 안됨
			response.sendRedirect(request.getContextPath() + "/user/login");
			//응답 후 끝냄
			return false;
		}
		//7. 권한(Authorization) 체크 ("ADMIN"인지, "USER"인지)를 위해 @Auth의 role 가져오기
		String role = auth.role();
		String authUserRole = authUser.getRole();
			//로직만들기
		if(!role.equals("ADMIN") || !authUserRole.equals("ADMIN")) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		//8. 인증확인
		return true;
	}

}
