package com.training.vo;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

public class UpdateGoodsVO extends ActionForm {

	private BigDecimal goodsID;
	private Integer goodsPrice;
	private Integer goodsQuantity;
	private String goodsStatus;

	public UpdateGoodsVO() {}

	public UpdateGoodsVO(BigDecimal goodsID, Integer goodsPrice, Integer goodsQuantity, String goodsStatus) {
		this.goodsID = goodsID;
		this.goodsPrice = goodsPrice;
		this.goodsQuantity = goodsQuantity;
		this.goodsStatus = goodsStatus;
	}

	public BigDecimal getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(BigDecimal goodsID) {
		this.goodsID = goodsID;
	}

	public Integer getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(Integer goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

}
