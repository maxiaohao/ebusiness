package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

@Controller
public class PageController {
	@RequestMapping(value="/page/login")	
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping(value="/page/register")	
	public String showRegister() {
		return "register";
	}
}
