package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

public class PortalItem extends TbItem{
	public String[] getImages(){
		if(this.getImage()!=null && this.getImage().equals("")){
			return this.getImage().split(",");
		}
		return null;
	}
}
