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
                .excludePathPatterns( "/login","/main","/dayoung/css/**", "/dayoung/js/**","/css/**", "/js/**", "//code.jquery.com/jquery-3.6.0.min.js","https://uicdn.toast.com/tui.code-snippet/latest/tui-code-snippet.min.js","https://uicdn.toast.com/tui.grid/latest/tui-grid.js","https://uicdn.toast.com/tui.grid/latest/tui-grid.css");
//                .excludePathPatterns("/dayoung/logIn","/")
//                .excludePathPatterns("/css/**")
//                .excludePathPatterns("/js/**")

    }

}
