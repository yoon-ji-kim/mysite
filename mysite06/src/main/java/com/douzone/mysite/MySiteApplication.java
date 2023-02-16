package com.douzone.mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.event.ApplicationContextEventListener;

@SpringBootApplication
public class MySiteApplication {
	
	//Application Context Event Listener
	@Bean
	public ApplicationContextEventListener applicationContextEventListener() {
		return new ApplicationContextEventListener();
	}
	@Controller
	public class TestController {
		@ResponseBody
		@RequestMapping("/test")
		public String test() {
			return "Test!";
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(MySiteApplication.class, args);
	}
}
