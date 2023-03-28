package com.training.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GoodsOrder {

	private BigDecimal orderID;
	private Date orderDate;
	private String customerName;
	private String goodsName;
	private Integer goodsBuyPrice;
	private Integer buyQuantity;
	private Integer buyAmount;

	public GoodsOrder() {
	}

	public GoodsOrder(BigDecimal orderID, Date orderDate, String customerName, String goodsName, Integer goodsBuyPrice,
			Integer buyQuantity, Integer buyAmount) {
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.customerName = customerName;
		this.goodsName = goodsName;
		this.goodsBuyPrice = goodsBuyPrice;
		this.buyQuantity = buyQuantity;
		this.buyAmount = buyAmount;
	}

	public BigDecimal getOrderID() {
		return orderID;
	}

	public void setOrderID(BigDecimal orderID) {
		this.orderID = orderID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsBuyPrice() {
		return goodsBuyPrice;
	}

	public void setGoodsBuyPrice(Integer goodsBuyPrice) {
		this.goodsBuyPrice = goodsBuyPrice;
	}

	public Integer getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Integer buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Integer getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(Integer buyAmount) {
		this.buyAmount = buyAmount;
	}

	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.TAIWAN);
		return sdf.format(date);
	}

	

}
