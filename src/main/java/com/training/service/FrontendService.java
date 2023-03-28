package com.training.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.training.dao.FrontendDao;
import com.training.model.Goods;

public class FrontendService {

	private static FrontendService frontendService = new FrontendService();

	private FrontendService() {}

	private FrontendDao frontendDao = FrontendDao.getFrontendDao_Instance();

	public static FrontendService getFrontendService_Instance() {
		return frontendService;
	}
	
	public List<Goods> queryAllGoods() {
		return frontendDao.queryAllGoods();
	}
	
	public Goods queryID(String id) {
		return frontendDao.searchGoodsID(id);
	}
	
	public List<Goods> searchGoods(String searchKeyword) {
		return frontendDao.searchGoods(searchKeyword);
	}

	public List<Goods> searchGoods(String searchKeyword, int start, int end) {
		return frontendDao.searchGoods(searchKeyword, start, end);
	}

	public Set<Goods> eachPageGoods(int start, int end) {
		return frontendDao.eachPageGoods(start, end);
	}

	public boolean createBeverageOrder(Map<Goods, Integer> orderGoods_CheckAndRenew, String customerID) {
		return frontendDao.createBeverageOrder(orderGoods_CheckAndRenew, customerID);
	}

	public boolean updateBeverageGoods(Set<Goods> updateGoods) {
		return frontendDao.updateBeverageGoods(updateGoods);
	}
	
	public Map<Goods, Integer> renewShoppingCart(Map<Goods, Integer> shoppingCartGoods) {
		return frontendDao.renewGoodsInfo(shoppingCartGoods);
	}

	public Map<Goods, Integer> orderRenew(Map<Goods, Integer> shoppingCartGoods) {
		Map<Goods, Integer> renewOrder = new LinkedHashMap<>();
		Iterator<Goods> cartGoodsIterator = shoppingCartGoods.keySet().iterator();
		while (cartGoodsIterator.hasNext()) {
			Goods cartGoods = cartGoodsIterator.next();
			Integer cartGoodsPrice = cartGoods.getGoodsPrice();
			Integer buyQuantity = shoppingCartGoods.get(cartGoods);
			Goods databaseGoodsInfo = frontendDao.searchGoodsID(cartGoods.getGoodsID().toString());
			if (databaseGoodsInfo.getGoodsQuantity() > 0) {
				if (!cartGoodsPrice.equals(databaseGoodsInfo.getGoodsPrice()))
					cartGoods.setGoodsPrice(databaseGoodsInfo.getGoodsPrice());
				if (buyQuantity > databaseGoodsInfo.getGoodsQuantity())
					buyQuantity = databaseGoodsInfo.getGoodsQuantity();
				cartGoods.setGoodsQuantity(databaseGoodsInfo.getGoodsQuantity() - buyQuantity);
				renewOrder.put(cartGoods, buyQuantity);
			}
		}
		return renewOrder;
	}
	
	public int getBuyAmount(Map<Goods, Integer> customerOrder) {
		int buyAmount = 0;
		Set<Goods> set = customerOrder.keySet();
		Iterator<Goods> iterator = set.iterator();
		while (iterator.hasNext()) {
			Goods goods = iterator.next();
			buyAmount += goods.getGoodsPrice() * customerOrder.get(goods);
		}
		return buyAmount;
	}
	
	public List<Integer> getPageCount(List<Goods> queryGoods) {
		List<Integer> pageCount = new ArrayList<>();
		int i = 1;
		int goodsCount = (int) queryGoods.stream().count();
		int pageUpperLimit = goodsCount / 6;
		while (pageCount.size() < pageUpperLimit) pageCount.add(i++);
		if ((goodsCount % 6) != 0) pageCount.add(i);
		return pageCount;
	}

}
