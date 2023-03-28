package com.training.vo;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

public class GoodsListVO extends ActionForm {
	private BigDecimal goodsID;
	private String goodsName;
	private Integer priceStart;
	private Integer priceEnd;
	private Integer priceQuantity;
	private String status;
	private String priceSort;
	
	public GoodsListVO() {}
	
	public GoodsListVO(BigDecimal goodsID, String goodsName, Integer priceStart, Integer priceEnd, String priceSort,
			Integer priceQuantity, String status) {
		this.goodsID = goodsID;
		this.goodsName = goodsName;
		this.priceStart = priceStart;
		this.priceEnd = priceEnd;
		this.priceSort = priceSort;
		this.priceQuantity = priceQuantity;
		this.status = status;
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
	public Integer getPriceStart() {
		return priceStart;
	}
	public void setPriceStart(Integer priceStart) {
		this.priceStart = priceStart;
	}
	public Integer getPriceEnd() {
		return priceEnd;
	}
	public void setPriceEnd(Integer priceEnd) {
		this.priceEnd = priceEnd;
	}
	public String getPriceSort() {
		return priceSort;
	}
	public void setPriceSort(String priceSort) {
		this.priceSort = priceSort;
	}
	public Integer getPriceQuantity() {
		return priceQuantity;
	}
	public void setPriceQuantity(Integer priceQuantity) {
		this.priceQuantity = priceQuantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "GoodsListVO [goodsID=" + goodsID + ", goodsName=" + goodsName + ", priceStart=" + priceStart
				+ ", priceEnd=" + priceEnd + ", priceQuantity=" + priceQuantity + ", status=" + status + ", priceSort="
				+ priceSort + "]";
	}
	
}
