package com.douzone.mysite.controller;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.auth.TokenProvider;


@RestController
public class AuthController {
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder managerBuilder;
	
}
