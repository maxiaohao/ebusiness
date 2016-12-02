package com.taotao.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	static Logger log = LoggerFactory.getLogger(ItemController.class.getName());

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_UPDATESOLR_URL}")
	private String REST_UPDATESOLR_URL;

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	private TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		return item;
	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemParamList(page, rows);
		log.debug("查询结果，数据：" + result.getTotal());
		return result;
	}

	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
		log.debug("新增产品参数1：" + item.getTitle());
		log.debug("新增产品参数2：" + desc);
		log.debug("新增产品参数3：" + itemParams);
		TaotaoResult result = itemService.createItem(item, desc, itemParams);
		//更新solr
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_UPDATESOLR_URL + item.getId());
		log.debug("更新solr结果:" + json);
		return result;
	}

	@RequestMapping(value = "/rest/item/delete", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteItem(HttpServletRequest request) {
		Long ids = Long.valueOf(request.getParameter("ids"));
		TaotaoResult result = itemService.deleteItem(ids);
		//更新solr
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_UPDATESOLR_URL + ids);
		log.debug("更新solr结果:" + json);
		return result;
	}

	@RequestMapping(value = "/rest/item/query/item/desc/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult queryDesc(@PathVariable Long id) {
		return itemService.getTbItemDescByItemId(id);
	}

	@RequestMapping(value = "/rest/item/param/item/query/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult queryItemParam(@PathVariable Long id) {
		return itemService.getTbItemParamItemByItemId(id);
	}

	@RequestMapping(value = "/rest/item/update", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams, String itemParamId) {
		log.debug("item=" + item.getImage());
		log.debug("itemParams=" + itemParams);
		log.debug("desc=" + desc);
		log.debug("itemParamId=" + itemParamId);

		TaotaoResult tr = itemService.updateItem(item, desc, itemParams, itemParamId);

		//更新solr
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_UPDATESOLR_URL + item.getId());
		log.debug("更新solr结果:" + json);

		return tr;
	}
}
