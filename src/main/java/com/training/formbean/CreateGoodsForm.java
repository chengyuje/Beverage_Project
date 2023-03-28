package com.training.formbean;

import org.apache.struts.upload.FormFile;

public class CreateGoodsForm {
	private String goodsName;
	private Integer goodsPrice;
	private Integer goodsQuantity;
	private FormFile goodsImage;
	private String goodsStatus;

	public CreateGoodsForm() {
	}

	public CreateGoodsForm(String goodsName, Integer goodsPrice, Integer goodsQuantity, FormFile goodsImage,
			String goodsStatus) {
		this.goodsName = goodsName;
		this.goodsPrice = goodsPrice;
		this.goodsQuantity = goodsQuantity;
		this.goodsImage = goodsImage;
		this.goodsStatus = goodsStatus;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public FormFile getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(FormFile goodsImage) {
		this.goodsImage = goodsImage;
	}
	
	

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	@Override
	public String toString() {
		return "CreateGoodsForm [goodsName=" + goodsName + ", goodsPrice=" + goodsPrice + ", goodsQuantity="
				+ goodsQuantity + ", goodsImage=" + goodsImage + "]";
	}

}
