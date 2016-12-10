package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.sso.service.LoginService;

@Controller
public class LoginController {

	static Logger log = LoggerFactory.getLogger(LoginController.class.getName());

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = loginService.login(username, password, request, response);
		return result;
	}

	@RequestMapping("/user/page/login")
	public String showRegister(String redirect, Model model) {
		// 把url参数传递到jsp
		model.addAttribute("redirect", redirect);
		return "login";
	}

	@RequestMapping("/user/showLogin")
	public String showRegister() {
		return "login";
	}

	@RequestMapping(value = "/user/logoutOld/{token}", method = RequestMethod.GET)
	public TaotaoResult logout(@PathVariable String token, String callback, HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = loginService.logout(token, request, response);

		log.debug("callback是否爲空：" + callback);
		log.debug("callback是否爲空：" + result.getStatus());
		return result;
/*		if (StringUtils.isBlank(callback)) {
			return result;
		}
		// 支持jsonp调用
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
		mappingJacksonValue.setJsonpFunction(callback);

		return mappingJacksonValue;*/
	}
	
	@RequestMapping(value = "/user/logoutBack/{token}", method = RequestMethod.GET)
	public TaotaoResult logoutTest(@PathVariable String token, HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = loginService.logout(token, request, response);

	//	log.debug("callback是否爲空：" + callback);
		log.debug("callback是否爲空：" + result.getStatus());
		return result;
/*		if (StringUtils.isBlank(callback)) {
			return result;
		}
		// 支持jsonp调用
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
		mappingJacksonValue.setJsonpFunction(callback);

		return mappingJacksonValue;*/
	}
	
	@RequestMapping(value="/user/logout/{token}", method=RequestMethod.GET)
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result =  loginService.logout(token,null,null);
		if (StringUtils.isBlank(callback)) {
			return result;
		}
		//支持jsonp调用
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
		mappingJacksonValue.setJsonpFunction(callback);
		
		return mappingJacksonValue;
	}
}
