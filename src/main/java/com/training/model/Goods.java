package com.training.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Goods {

	private BigDecimal goodsID;
	private String goodsName;
	private String goodsDescription;
	private Integer goodsPrice;
	private Integer goodsQuantity;
	private String goodsImageName;
	private String goodsStatus;

	public Goods() {}

	public Goods(BigDecimal goodsID, String goodsName, String goodsDescription, int goodsPrice, int goodsQuantity,
			String goodsImageName, String goodsStatus) {
		this.goodsID = goodsID;
		this.goodsName = goodsName;
		this.goodsDescription = goodsDescription;
		this.goodsPrice = goodsPrice;
		this.goodsQuantity = goodsQuantity;
		this.goodsImageName = goodsImageName;
		this.goodsStatus = goodsStatus;
	}


	@Override
	public int hashCode() {
		return Objects.hash(goodsID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Goods other = (Goods) obj;
		return Objects.equals(goodsID, other.goodsID);
	}

	public BigDecimal getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(BigDecimal goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDescription() {
		return goodsDescription;
	}

	public void setGoodsDescription(String goodsDescription) {
		this.goodsDescription = goodsDescription;
	}

	public Integer getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(int goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public String getGoodsImageName() {
		return goodsImageName;
	}

	public void setGoodsImageName(String goodsImageName) {
		this.goodsImageName = goodsImageName;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	@Override
	public String toString() {
		return "Goods [goodsID=" + goodsID + ", goodsName=" + goodsName + ", goodsDescription=" + goodsDescription
				+ ", goodsPrice=" + goodsPrice + ", goodsQuantity=" + goodsQuantity + ", goodsImageName="
				+ goodsImageName + ", goodsStatus=" + goodsStatus + "]";
	}

}
