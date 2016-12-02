package com.taotao.service;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService{
	public TbItem getItemById(Long itemId);
	public EasyUIDataGridResult getItemList(int page,int rows);
	public EasyUIDataGridResult getItemParamList(int page,int rows);
	TaotaoResult createItem(TbItem item, String desc);
	TaotaoResult createItem(TbItem item, String desc,String itemParams);

	//删除一个商品
	TaotaoResult deleteItem(Long ids);	
	TaotaoResult getTbItemDescByItemId(Long itemId);
	TaotaoResult getTbItemParamItemByItemId(Long itemId);
	//更新一个商品
	TaotaoResult updateItem(TbItem item, String desc,String itemParams,String itemParamId);
}