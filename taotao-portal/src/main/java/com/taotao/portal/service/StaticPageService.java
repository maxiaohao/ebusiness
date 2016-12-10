package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;

public interface StaticPageService {
	public TaotaoResult genItemHtml(Long itemId) throws Exception;
}
