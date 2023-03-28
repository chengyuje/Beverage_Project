package com.training.formbean;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class GoodsListForm {

	private BigDecimal goodsID;
	private String goodsName;
	private Integer priceStart;
	private Integer priceEnd;
	private Integer priceQuantity;
	private String status;
	private String priceSort;

	public GoodsListForm() {
	}

	public GoodsListForm(BigDecimal goodsID, String goodsName, Integer priceStart, Integer priceEnd, String priceSort,
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

	public Map<String, Object> getFieldsMap() {
		Map<String, Object> fieldsMap = new LinkedHashMap<>();
		if (goodsID != null)
			fieldsMap.put("goodsID", goodsID);
		if (goodsName != "")
			fieldsMap.put("goodsName", goodsName);
		if (priceStart != null)
			fieldsMap.put("priceStart", priceStart);
		if (priceEnd != null)
			fieldsMap.put("priceEnd", priceEnd);
		if (priceQuantity != null)
			fieldsMap.put("priceQuantity", priceQuantity);
		if (status != "")
			fieldsMap.put("status", status);
		if (priceSort != "")
			fieldsMap.put("priceSort", priceSort);
		return fieldsMap;
	}

	@Override
	public String toString() {
		return "GoodsListForm [goodsID=" + goodsID + ", goodsName=" + goodsName + ", priceStart=" + priceStart
				+ ", priceEnd=" + priceEnd + ", priceSort=" + priceSort + ", priceQuantity=" + priceQuantity
				+ ", status=" + status + "]";
	}

}
