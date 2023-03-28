package com.training.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.dao.FrontendDao;
import com.training.model.Goods;
import com.training.model.ShoppingCartGoods;
import com.training.model.ShoppingCartGoodsInfo;
import com.training.service.FrontendService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@MultipartConfig
public class ShoppingCartDispatchAction extends DispatchAction {

	private FrontendDao frontEndDao = FrontendDao.getFrontendDao_Instance();
	private FrontendService frontendService = FrontendService.getFrontendService_Instance();
	
	public ActionForward addCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject object=new JSONObject();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		String goodsID = request.getParameter("goodsID");
		Integer buyQuantity = Integer.valueOf(request.getParameter("buyQuantity"));
		Goods goodsInDB = frontEndDao.searchGoodsID(goodsID);
		String message=null;
		if(buyQuantity.equals(0)) message="購買數量 0 請重新輸入！";
		if(goodsInDB.getGoodsQuantity().equals(0) && message==null) message="庫存不足，請購買其他商品！";
		if(buyQuantity>goodsInDB.getGoodsQuantity() && message==null) message="購買數量已超過上限，請重新輸入！";
		if(message!=null) {
			object.put("message", message);
			out.println(JSONObject.fromObject(object));
			out.flush();
			out.close();
			return null;
		}
		
		Map<Goods, Integer> shoppingCartGoods = null;
		if (session.getAttribute("shoppingCartGoods") == null) {
			session.removeAttribute("shoppingCartGoods");
			shoppingCartGoods = new LinkedHashMap<>();
			shoppingCartGoods.put(goodsInDB, buyQuantity);
			session.setAttribute("shoppingCartGoods", shoppingCartGoods);
		} else {
			shoppingCartGoods = (Map<Goods, Integer>) session.getAttribute("shoppingCartGoods");
			if (shoppingCartGoods.containsKey(goodsInDB)) {
				Goods goodsInCart = null;
				for (Goods goods : shoppingCartGoods.keySet()) {
					if (goods.getGoodsID().equals(goodsInDB.getGoodsID())) {
						goodsInCart = goods; 
						if (!goodsInDB.getGoodsPrice().equals(goodsInCart.getGoodsPrice())) {
							goodsInCart.setGoodsPrice(goodsInDB.getGoodsPrice());
						}
						break;
					}
				}
				Integer renewBuyQuantity = buyQuantity + shoppingCartGoods.get(goodsInCart);
				if(renewBuyQuantity>goodsInDB.getGoodsQuantity()) {
					message="購買數量已超過上限，請確認購買數量!";
					object.put("message", message);
					out.println(JSONObject.fromObject(object));
					out.flush();
					out.close();
					return null;
				}
				shoppingCartGoods.put(goodsInCart, renewBuyQuantity);
				session.setAttribute("shoppingCartGoods", shoppingCartGoods);
			} else {
				shoppingCartGoods.put(goodsInDB, buyQuantity);
				session.setAttribute("shoppingCartGoods", shoppingCartGoods);
			}
		}
		
		object.put("goodsName", goodsInDB.getGoodsName());
		object.put("buyQuantity", buyQuantity);
		object.put("goodsPrice", goodsInDB.getGoodsPrice());
		out.println(JSONObject.fromObject(object));
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward queryCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Map<Goods, Integer> shoppingCartGoods = (Map<Goods, Integer>) session.getAttribute("shoppingCartGoods");
		if (shoppingCartGoods == null) { return null; }
		Map<Goods, Integer> renewShoppingCartGoods = frontendService.renewShoppingCart(shoppingCartGoods);
		session.setAttribute("shoppingCartGoods", renewShoppingCartGoods);
		Set<Goods> set = renewShoppingCartGoods.keySet();
		Set<ShoppingCartGoods> cartGoodsSet = new LinkedHashSet<>();
		int totalAmount = 0;
		for (Goods goods : set) {
			int goodsPrice = goods.getGoodsPrice();
			int buyQuantity = renewShoppingCartGoods.get(goods);
			totalAmount += goodsPrice * buyQuantity;
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(totalAmount);
		out.flush();
		out.close();
		return null;
	}
	
	
	public ActionForward deleteInCart(ActionMapping mapping, ActionForm from, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		Map<Goods,Integer> shoppingCartGoods=(Map<Goods,Integer>)session.getAttribute("shoppingCartGoods");
		Goods goods=new Goods();
		goods.setGoodsID(new BigDecimal(request.getParameter("goodsID")));
		shoppingCartGoods.remove(goods);
		if(shoppingCartGoods.size()==0) shoppingCartGoods.clear();
		int totalAmount=0;
		for(Goods cartGoods:shoppingCartGoods.keySet()) {
			int goodsPrice=cartGoods.getGoodsPrice();
			int quantity=shoppingCartGoods.get(cartGoods);
			totalAmount+=goodsPrice*quantity;
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/text");
		PrintWriter out = response.getWriter();
		out.println(totalAmount);
		out.flush();
		out.close();
		return null;
	}

	public ActionForward clearCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("shoppingCartGoods");
		Thread.sleep(10);
		return null;
	}
	
	public ActionForward cartListView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("cartList");
	}
	
	public ActionForward quantityChange(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String goodsID=request.getParameter("goodsID");
		String buyQuantity=request.getParameter("buyQuantity");
		HttpSession session = request.getSession();
		Map<Goods, Integer> shoppingCartGoods = (Map<Goods, Integer>) session.getAttribute("shoppingCartGoods");
		Goods goods=frontendService.queryID(goodsID);
		shoppingCartGoods.put(goods, Integer.valueOf(buyQuantity));
		int amount=goods.getGoodsPrice()*Integer.parseInt(buyQuantity);
		int totalAmount=0;
		for(Goods cartGoods:shoppingCartGoods.keySet()) {
			int goodsPrice=cartGoods.getGoodsPrice();
			int quantity=shoppingCartGoods.get(cartGoods);
			totalAmount+=goodsPrice*quantity;
		}
		JSONObject object=new JSONObject();
		object.put("amount",amount);
		object.put("totalAmount", totalAmount);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(JSONObject.fromObject(object));
		out.flush();
		out.close();
		return null;
	}
}
