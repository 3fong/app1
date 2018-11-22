package com.zzlh.auth_client.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zzlh.auth_client.common.constants.CommonConstants;
import com.zzlh.auth_client.domain.ResponseEntity;
import com.zzlh.auth_client.domain.User;
import com.zzlh.auth_client.util.HttpUtil;

/**
 * @Description 认证控制器
 * @author liulei
 * @date 2018年10月22日 下午2:24:25
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	/**
	 * @Description 获取Ticket服务访问证书
	 * @param user 登录用户
	 * @param response 请求响应体
	 * @throws IOException
	 */
    @RequestMapping(path = "/getticket",method = {RequestMethod.POST,RequestMethod.GET})
    public void auth(User user,HttpServletResponse response) throws IOException {
    	response.sendRedirect(CommonConstants.REDIRECTHOST+"/getticket?service="+CommonConstants.FRONTHOST+"&username="+user.getUsername()+"&password="+user.getPassword());
    }
    
    /**
     * @Description 登录
     * @param ticket 服务访问证书
     * @param request 请求体
     * @param response 响应体
     * @throws Exception
     */
    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public ResponseEntity<Object> ticket(@RequestParam String ticket,HttpServletRequest request,HttpServletResponse response) throws Exception {
    	ResponseEntity<Object> responseEntity = HttpUtil.doGet(CommonConstants.REDIRECTHOST+"/verifyst?service="+CommonConstants.HOST+"&ticket="+ticket);
    	if("200".equals(responseEntity.getStatus())) {
    		HttpSession session = request.getSession();
    		session.setMaxInactiveInterval(1800);
    		session.setAttribute("username", responseEntity.getData());
    		Cookie cookie = new Cookie("JSESSIONID",session.getId());
    		cookie.setMaxAge(1800);
    		response.addCookie(cookie);
    	}
    	return responseEntity;
    }
    
    /**
     * @Description 登出
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public void logout(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
		session.invalidate();
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
    	response.sendRedirect(CommonConstants.REDIRECTHOST+"/close?service="+CommonConstants.HOST);
    }
    
}
