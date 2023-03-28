package com.training.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.training.dao.BackendDao;
import com.training.formbean.CreateGoodsForm;
import com.training.formbean.GoodsListForm;
import com.training.formbean.UpdateGoodsForm;
import com.training.model.Goods;
import com.training.model.GoodsOrder;

public class BackendService {

	private static BackendService backendService = new BackendService();

	private BackendService() {
	}

	private BackendDao backendDao = BackendDao.getBackendDao_Instance();

	public static BackendService getBackendService_Instance() {
		return backendService;
	}

	public List<Goods> queryAllGoods() {
		return backendDao.queryAllGoods();
	}
	
	public Goods queryGoodsID(String goodsSelectedID) {
		return backendDao.queryGoodsID(goodsSelectedID);
	}

	public boolean updateGoods(UpdateGoodsForm goods) {
		return backendDao.updateGoods(goods);
	}

	public int createGoods(CreateGoodsForm goods) {
		return backendDao.createGoods(goods);
	}

	public Set<GoodsOrder> querySalesReport(String queryStartDate, String queryEndDate) {
		return backendDao.querySalesReport(queryStartDate, queryEndDate);
	}
	
	public List<Goods> queryAllWithCondition(GoodsListForm goodsListForm) {
		Map<String, Object> fieldsMap = goodsListForm.getFieldsMap();
		return backendDao.queryAllWithCondition(fieldsMap);
	}
	
	public List<Goods> queryGoodsWithCondition(GoodsListForm goodsListForm,int start,int end) {
		Map<String, Object> fieldsMap = goodsListForm.getFieldsMap();
		return backendDao.queryGoodsWithCondition(fieldsMap,start,end);
	}

	public Set<Goods> eachPageGoods(int start, int end) {
		return backendDao.eachPageGoods(start, end);
	}

	public List<Integer> getPageCount(List<Goods> queryGoods) {
		List<Integer> pageCount = new ArrayList<>();
		int i = 1;
		int goodsCount = (int) queryGoods.stream().count();
		int pageUpperLimit = goodsCount / 6;
		while (pageCount.size() < pageUpperLimit) pageCount.add(i++); 
		if ((goodsCount % 6) != 0 ) pageCount.add(i);
		return pageCount;
	}
	
}
