package com.training.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoodsReceipt {

	private Integer inputMoney;
	private Integer buyAmount;
	private Integer change;
	private List<Goods> goodsList;

	public GoodsReceipt() {
	}

	public GoodsReceipt(Integer inputMoney, Integer buyAmount, Map<Goods, Integer> customerOrder) {
		this.inputMoney = inputMoney;
		this.buyAmount = buyAmount;
		setChange(inputMoney,buyAmount);
		setGoodsList(customerOrder);
	}

	public GoodsReceipt(Integer inputMoney, Integer buyAmount, Integer change, List<Goods> receipt) {
		this.inputMoney = inputMoney;
		this.buyAmount = buyAmount;
		this.change = change;
		this.goodsList = receipt;
	}

	public Integer getInputMoney() {
		return inputMoney;
	}

	public void setInputMoney(Integer inputMoney) {
		this.inputMoney = inputMoney;
	}

	public Integer getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(Integer buyAmount) {
		this.buyAmount = buyAmount;
	}

	public Integer getChange() {
		return change;
	}

	public void setChange(int inputMoney, int buyAmount) {
		this.change = inputMoney - buyAmount;
	}

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}

	public void setGoodsList(Map<Goods, Integer> customerOrder) {
		goodsList = new ArrayList<>();
		Set<Goods> set = customerOrder.keySet();
		for (Goods goods : set) {
			goodsList.add(goods);
		}
	}

}
