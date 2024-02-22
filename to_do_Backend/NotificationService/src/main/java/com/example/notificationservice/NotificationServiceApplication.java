package com.example.notificationservice;

import com.example.notificationservice.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<JwtFilter> registerFilterBean() {
		FilterRegistrationBean<JwtFilter> jwtFilterBean = new FilterRegistrationBean<>();
		jwtFilterBean.setFilter(new JwtFilter());
		jwtFilterBean.addUrlPatterns("/notificationController/*");
		return jwtFilterBean;
	}
}
