package com.training.formbean;

import java.math.BigDecimal;

public class UpdateGoodsForm {

	private BigDecimal goodsID;
	private Integer goodsPrice;
	private Integer goodsQuantity;
	private String goodsStatus;

	public UpdateGoodsForm() {}

	public UpdateGoodsForm(BigDecimal goodsID, Integer goodsPrice, Integer goodsQuantity, String goodsStatus) {
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

	@Override
	public String toString() {
		return "UpdateGoodsForm [goodsID=" + goodsID + ", goodsPrice=" + goodsPrice + ", goodsQuantity=" + goodsQuantity
				+ ", goodsStatus=" + goodsStatus + "]";
	}

}
