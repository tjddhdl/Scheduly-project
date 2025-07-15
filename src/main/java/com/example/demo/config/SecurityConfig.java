package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.ApiCheckFilter;
import com.example.demo.security.ApiLoginFilter;
import com.example.demo.security.UserDetailsServiceImpl;
import com.example.demo.user.UserService;
import com.example.demo.user.UserServiceImpl;

@Configuration
public class SecurityConfig {

	@Bean
	ApiCheckFilter apiCheckFilter() {
		return new ApiCheckFilter(customUserDetailsService());
	}
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
	
	@Bean
	UserDetailsService customUserDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.formLogin().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable();
		
		http.authorizeHttpRequests()
		.requestMatchers("/register","/login").permitAll()
		.requestMatchers("/jsonmodify").hasAnyRole("free","pay")
		.requestMatchers("jsonstatus").hasAnyRole("free","pay")
		.requestMatchers("/addJson").hasAnyRole("free","pay")
		.requestMatchers("/removeJson").hasAnyRole("free","pay")
		.requestMatchers("/moveJson").hasAnyRole("free","pay")
		.anyRequest().authenticated();
		// 주소 적어넣어야됨
		
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(customUserDetailsService())
				.passwordEncoder(passwordEncoder());
		
		AuthenticationManager manager = builder.build();
		http.authenticationManager(manager);
		
		ApiLoginFilter loginFilter = new ApiLoginFilter("/login", userService());
		loginFilter.setAuthenticationManager(manager);
		
		http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
}
