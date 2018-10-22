package com.zzlh.auth_client.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zzlh.auth_client.domain.User;
import com.zzlh.auth_client.util.HttpUtil;

/**
 * @Description TODO
 * @author liulei
 * @date 2018年10月22日 下午2:24:25
 */
@RestController
@RequestMapping("/cas")
public class CasController {
	
    @RequestMapping(path = "/auth",method = RequestMethod.POST)
    public void auth(User user,HttpServletResponse response) throws IOException {
    	response.sendRedirect("http://localhost:8090/auth/login?"+"service=http://localhost:8070&username="+user.getUsername()+"&password="+user.getPassword());
    }
    
    @RequestMapping(path = "/ticket",method = RequestMethod.GET)
    public void ticket(@RequestParam String ticket,HttpServletRequest request,HttpServletResponse response) throws Exception {
    	Map<String, String> params = new HashMap<>();
    	params.put("service", "http://localhost:8070");
    	params.put("ticket", ticket);
    	HttpResponse doGet = HttpUtil.doGet("http://localhost:8090", "/auth/ticket", null, params);
    	
    	if(doGet.getStatusLine().getStatusCode()==200) {
    		HttpEntity entity = doGet.getEntity();
    		HttpSession session = request.getSession();
    		session.setMaxInactiveInterval(1800);
    		session.setAttribute("username", null);
    		Cookie cookie = new Cookie("JSESSIONID",session.getId());
    		cookie.setMaxAge(1800);
    		response.addCookie(cookie);
    	}
    	request.getRequestDispatcher("/").forward(request, response);
    }
    
}
