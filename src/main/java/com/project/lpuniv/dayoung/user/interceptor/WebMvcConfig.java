package com.project.lpuniv.dayoung.user.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    LoggerInterceptor loggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns( "/login","/main","/css/**", "/js/**", "//code.jquery.com/jquery-3.6.0.min.js");
//                .excludePathPatterns("/dayoung/logIn","/")
//                .excludePathPatterns("/css/**")
//                .excludePathPatterns("/js/**")
//                .excludePathPatterns("//code.jquery.com/jquery-3.6.0.min.js");
    }
}
