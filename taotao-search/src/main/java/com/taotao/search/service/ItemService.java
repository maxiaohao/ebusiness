package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemService {
	public TaotaoResult importItems() throws Exception ;
	public String getItemSize();
}