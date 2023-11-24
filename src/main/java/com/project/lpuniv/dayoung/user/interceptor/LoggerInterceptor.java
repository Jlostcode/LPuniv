package com.project.lpuniv.dayoung.user.interceptor;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoggerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (ObjectUtils.isEmpty(authInfo)) {

            response.sendRedirect("/login");//수업 연결 후에 연결하기.
            return false;
        } else {
            session.setMaxInactiveInterval(24*60*60);
            return true;
        }
    }


//    private boolean isAllowedpath(HttpServletRequest request){
//        String requestURI = request.getRequestURI();
//        String[] allowedPatterns = {"/login"};
//        for (String pattern : allowedPatterns){
//            if(requestURI.equals(pattern)){
//                System.out.println("받아온 URI"+requestURI);
//                return true;
//            }
//        }
//        return false;
//    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
