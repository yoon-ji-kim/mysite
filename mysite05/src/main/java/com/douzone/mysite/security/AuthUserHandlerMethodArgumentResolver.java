package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
		if(!supportsParameter(parameter)) {
			//지원 못해준다면 unresolved return: 세팅하지 않음
			//다음 argument resolve가 해석
			return WebArgumentResolver.UNRESOLVED;
		}
		//지원함, 해당되는 객체 return
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session= request.getSession();
		
		return session.getAttribute("authUser");
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//parameter  정보
		AuthUser authUser =parameter.getParameterAnnotation(AuthUser.class);
		//@AuthUser가 붙어 있지 않으면
		if(authUser == null) {
			return false;
		}
		//파라미터 타입이 UserVo가 아니라면
		if(!parameter.getParameterType().equals(UserVo.class)) {
			return false;
		}
		return true;
	}

}
