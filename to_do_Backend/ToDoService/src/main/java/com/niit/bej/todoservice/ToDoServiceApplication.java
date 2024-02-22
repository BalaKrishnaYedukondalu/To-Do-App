package com.niit.bej.todoservice;

import com.niit.bej.todoservice.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient

public class ToDoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoServiceApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> registerFilterBean() {
        FilterRegistrationBean<JwtFilter> jwtFilterBean = new FilterRegistrationBean<>();
        jwtFilterBean.setFilter(new JwtFilter());
        jwtFilterBean.addUrlPatterns("/toDo/user/*");
        return jwtFilterBean;
    }
}
