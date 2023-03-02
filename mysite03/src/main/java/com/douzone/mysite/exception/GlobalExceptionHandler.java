package com.douzone.mysite.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.mysite.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
	//어느 exception에 작동하는지
	//@ExceptionHandler(UserRepositoryException.class)//개별적으로 처리
	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request, HttpServletResponse response,Exception e) throws IOException, ServletException {
		//1. 로깅(Logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());
		
		//2. 요청 구분
		//	json 요청인 경우: request header의 Accept에 application/json(O)
		//	html 요청인 경우: request header의 Accept에 application/json(X)
		String accept = request.getHeader("accept");	//accept 가져오기
		if(accept.matches(".*application/json.*")) {
			//3. json 응답
			JsonResult jsonResult =JsonResult.fail(errors.toString());
			
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			//body
//			response.getWriter()
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();
		} else {
			//3. 사과 페이지(4. 정상종료)
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
		
	}
}
