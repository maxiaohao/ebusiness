package com.taotao.search.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.common.BaseTest;

public class TestItemService extends BaseTest{
	@Autowired(required=true)
	ItemService itemService;
	
	@Test
	public void testImportItems(){
		/*
		try {
			itemService.importItems();
			System.out.println("全部商品导入成功!");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		*/
		
	}
	
}
