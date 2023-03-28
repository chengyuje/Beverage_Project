package com.training.vo;

import org.apache.struts.action.ActionForm;

public class ShoppingCartVO extends ActionForm{
	
	private String goodsID;
	private Integer buyQuantity;

	public ShoppingCartVO() {
	}

	public ShoppingCartVO(String goodsID, Integer buyQuantity) {
		this.goodsID = goodsID;
		this.buyQuantity = buyQuantity;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public Integer getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Integer buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

}
