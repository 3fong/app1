package com.zzlh.auth_client.domain;

import lombok.Data;
import sun.net.spi.nameservice.dns.DNSNameService;

/**
 * @Description 用户实体
 * @author liulei
 * @date 2018年10月21日 下午6:00:53
 */

public class User {
	// 用户名
	private String username;
	// 密码
	private String password;
	// 请求类型 1:ca登录 其他:用户登录
	private String type;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public static void main(String[] args) {
		System.out.println(User.class.getClassLoader());
		System.out.println(DNSNameService.class.getClassLoader());
		System.out.println(String.class.getClassLoader());
	}
}
