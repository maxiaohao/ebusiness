package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;



public interface ItemService {
	public TbItem getItemById(Long itemId) ;
	public TbItemDesc getItemDescById(Long itemId);
	public TbItemParamItem getItemParamById(Long itemId);	
	public TbItemCat getTbItemCatByid(Long cid);

}
