package com.taotao.portal.service;

import com.taotao.pojo.TbItem;

public interface ItemService {
	public TbItem getItemById(Long itemId);
	public String getItemDescById(Long itemId);
	public String getItemParamById(Long itemId);
}
