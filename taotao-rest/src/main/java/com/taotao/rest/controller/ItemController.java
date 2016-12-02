package com.taotao.rest.controller;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.pojo.SearchItem;
import com.taotao.rest.service.ContentService;
import com.taotao.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	static Logger log = LoggerFactory.getLogger(ItemController.class.getName());

	@Autowired
	private ItemService itemService;

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ContentService contentService;

	/*
	 * 更新solr
	 */
	@RequestMapping("/updatesolr/{itemId}")
	@ResponseBody
	public TaotaoResult updateSolrById(@PathVariable Long itemId) {

		log.debug("更新一下solr：" + itemId);

		try {
			TbItem tbItem = itemService.getItemById(itemId);
			SearchItem item = new SearchItem();
			item.setId(tbItem.getId().toString());
			item.setTitle(tbItem.getTitle());
			item.setSell_point(tbItem.getSellPoint());
			item.setPrice(tbItem.getPrice());
			item.setImage(tbItem.getImage());
			// 描述
			TbItemDesc tid = itemService.getItemDescById(itemId);
			item.setItem_desc(tid.getItemDesc());
			// cat
			TbItemCat tic = itemService.getTbItemCatByid(tbItem.getCid());
			item.setCategory_name(tic.getName());

			// 更新
			// 创建文档对象
			SolrInputDocument document = new SolrInputDocument();
			// 添加域
			document.addField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			document.addField("item_desc", item.getItem_desc());
			// 写入索引库
			solrServer.add(document);
			// 提交
			solrServer.commit();

			// 更新redis
			contentService.syncContentItem(itemId);
			contentService.syncContentItemDesc(itemId);
			contentService.syncContentItemCat(tbItem.getCid());

			return TaotaoResult.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 查询商品基本信息
	 * <p>
	 * Title: getItemById
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/base/{itemId}")
	@ResponseBody
	public TaotaoResult getItemById(@PathVariable Long itemId) {
		try {
			TbItem item = itemService.getItemById(itemId);
			return TaotaoResult.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDescById(@PathVariable Long itemId) {
		try {
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			return TaotaoResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParamById(@PathVariable Long itemId) {
		try {
			TbItemParamItem itemParamItem = itemService.getItemParamById(itemId);
			return TaotaoResult.ok(itemParamItem);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
