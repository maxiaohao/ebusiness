package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

/**
 * 商品查询Service
 * <p>
 * Title: ItemServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.taotao.com
 * </p>
 * 
 * @author VincentDING
 * @date 2016年11月11日下午4:28:55
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	static Logger log = LoggerFactory.getLogger(ItemServiceImpl.class.getName());

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemParamMapper itemParamMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItemById(Long itemId) {
		// TbItem item = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		// 创建查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		// 判断list中是否为空
		TbItem item = null;
		if (list != null && list.size() > 0) {
			item = list.get(0);
		}
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {

		// 分页处理
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();

		/*
		 * 这个id的产品有完整的信息,测试用
		 * Criteria criteria = example.createCriteria();
		 * criteria.andIdEqualTo(Long.valueOf("101434521126763"));
		 */
		List<TbItem> list = itemMapper.selectByExample(example);
		// 取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		// 返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);

		return result;

	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc) {
		// 生成商品ID
		long itemId = IDUtils.genItemId();
		// 补全TbItem属性
		item.setId(itemId);
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		// 创建时间和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入商品表
		itemMapper.insert(item);
		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述数据
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
		// 生成商品ID
		long itemId = IDUtils.genItemId();
		// 补全TbItem属性
		item.setId(itemId);
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		// 创建时间和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入商品表
		itemMapper.insert(item);
		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述数据
		itemDescMapper.insert(itemDesc);

		// 商品规格
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		itemParamItemMapper.insert(itemParamItem);

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItem(Long ids) {
		log.debug("參數是：" + ids);
		itemDescMapper.deleteByPrimaryKey(ids);
		itemMapper.deleteByPrimaryKey(ids);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult getTbItemDescByItemId(Long itemId) {
		log.debug("參數是：" + itemId);
		return TaotaoResult.ok(itemDescMapper.selectByPrimaryKey(itemId));
	}

	@Override
	public TaotaoResult getTbItemParamItemByItemId(Long itemId) {
		log.debug("getTbItemParamItemByItemId參數是：" + itemId);
		TbItemParamItem tpi = itemParamItemMapper.selectByItemId(itemId);
		log.debug("测试是否取到数据:" + tpi.getParamData());
		return TaotaoResult.ok(tpi);
	}

	@Override
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams, String itemParamId) {

		// 目前数据库里面的数据，有好多产品没有规格参数等，为了防止出错，判断一下再修改

		// 1、更新item
		itemMapper.updateByPrimaryKeySelective(item);
		// 2、更新desc
		TbItemDesc id = new TbItemDesc();
		id.setItemId(item.getId());
		id.setItemDesc(desc);
		itemDescMapper.updateByPrimaryKeySelective(id);
		// 3、更新param
		if (itemParamId != null && !"".equals(itemParamId.trim())) {
			TbItemParamItem ipi = new TbItemParamItem();
			ipi.setId(Long.valueOf(itemParamId));
			ipi.setParamData(itemParams);
			itemParamItemMapper.updateByPrimaryKeySelective(ipi);
		}

		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult getItemParamList(int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		// 取分页信息
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		// 返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);

		log.debug("总数据是：" + pageInfo.getTotal());

		return result;
	}

}
