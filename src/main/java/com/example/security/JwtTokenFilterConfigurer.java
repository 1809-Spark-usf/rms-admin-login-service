package com.example.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private JwtTokenProvider jwtTokenFilterConfigurer;

	public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenFilterConfigurer) {
		this.jwtTokenFilterConfigurer = jwtTokenFilterConfigurer;
	}
	
}
