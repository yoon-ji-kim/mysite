package com.douzone.mysite.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.douzone.mysite.dto.TokenInfo;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		PrintWriter writer = response.getWriter();
        TokenInfo res = TokenInfo.builder()
                    .status(500)
                    .message("JwtAuthenticationEntryPoint Error").build();
        try{
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writer.write(res.toString());
        }catch(NullPointerException e){
            LOGGER.error("응답 메시지 작성 에러", e);
        }finally{
            if(writer != null) {
                writer.flush();
                writer.close();
            }
        }
        response.getWriter().write(res.toString());
	}

}
