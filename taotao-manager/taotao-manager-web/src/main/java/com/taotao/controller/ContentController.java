package com.taotao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	static Logger log = LoggerFactory.getLogger(ContentController.class.getName());

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteContent(HttpServletRequest request) {
		Long ids = Long.valueOf(request.getParameter("ids"));
		TaotaoResult result = contentService.deleteContent(ids);
		return result;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content) {
		TaotaoResult result = contentService.updateContent(content);
		return result;
	}
	
	@RequestMapping("/query/list")
	@ResponseBody
	public List<TbContent> getContentList(@RequestParam(value="categoryId", defaultValue="0") Long categoryId) {
		List<TbContent> result = contentService.getContentList(categoryId);
		log.debug("查询结果，数据："+result.size());
		return result;
	}
}

