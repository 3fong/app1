package com.zzlh.auth_client.common.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description 认证拦截
 * @author liulei
 * @date 2018年7月13日 下午2:42:57
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
    	log.info("请求路径：{}", request.getRequestURI());
    	Cookie[] cookies = request.getCookies();
    	if(cookies!=null && cookies.length>0) {
    		for (Cookie cookie : cookies) {
    			if("JSESSIONID".equals(cookie.getName())) {
    				HttpSession session = request.getSession(false);
    				if(session != null && session.getId() == cookie.getValue()) {
    					return true;
    				}
    			}
    		}
    	}
    	response.sendRedirect("login");
    	return false;
    }
    
}