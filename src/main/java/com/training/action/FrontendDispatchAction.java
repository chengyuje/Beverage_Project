package com.training.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.model.BeverageMember;
import com.training.model.Goods;
import com.training.model.GoodsReceipt;
import com.training.service.FrontendService;

public class FrontendDispatchAction extends DispatchAction {

	private FrontendService frontendService = FrontendService.getFrontendService_Instance();

	public ActionForward searchGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String searchKeyword = request.getParameter("searchKeyword");
		if(searchKeyword == null || searchKeyword == "") return mapping.findForward("frontendView");
		HttpSession session = request.getSession();
		session.setAttribute("paramValue", "searchGoods");
		
		String pageNo = request.getParameter("pageNo");
		int end = Integer.parseInt(pageNo) * 6;
		int start = end - 5;
		request.setAttribute("searchKeyword", searchKeyword);
		List<Goods> queryGoodsList = frontendService.searchGoods(searchKeyword, start, end);
		if(!searchKeyword.equals(session.getAttribute("searchKeyword"))) {
			List<Goods> searchList = frontendService.searchGoods(searchKeyword);
			List<Integer> pageCount = frontendService.getPageCount(searchList);
			int lastPage = pageCount.get(pageCount.size() - 1);
			session.setAttribute("searchKeyword", searchKeyword);
			session.setAttribute("lastPage", lastPage);
			session.setAttribute("pageCount", pageCount);
		}
			
		if (queryGoodsList.size() != 0) {
			session.setAttribute("queryGoodsList", queryGoodsList);
		} else {
			request.setAttribute("queryGoodsListMsg", "搜尋條件查無資料");
		}

		return mapping.findForward("frontendView");
	}

	public ActionForward eachPageGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("paramValue", "eachPageGoods");
		List<Goods> queryAllGoods = frontendService.queryAllGoods();
		List<Integer> pageCount = frontendService.getPageCount(queryAllGoods);
		int lastPage = pageCount.get(pageCount.size() - 1);
		session.setAttribute("lastPage", lastPage);
		session.setAttribute("pageCount", pageCount);
		String pageNo = request.getParameter("pageNo");
		int end = Integer.parseInt(pageNo) * 6;
		int start = end - 5;
		Set<Goods> pageGoods = frontendService.eachPageGoods(start, end);
		if (pageGoods.size() != 0) {
			session.setAttribute("queryGoodsList", pageGoods);
		} else {
			request.setAttribute("queryGoodsListMsg", "搜尋條件查無資料");
		}
		return mapping.findForward("frontendView");
	}

	public ActionForward buyGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int inputMoney = Integer.parseInt(request.getParameter("inputMoney"));

		HttpSession session = request.getSession();
		BeverageMember beverageMember = (BeverageMember) session.getAttribute("beverageMember");
		String customerID = beverageMember.getUserName();

		Map<Goods, Integer> shoppingCartGoods = (Map<Goods, Integer>) session.getAttribute("shoppingCartGoods");
		if (shoppingCartGoods == null) return mapping.findForward("buyGoods");

		Map<Goods, Integer> customerOrder = frontendService.orderRenew(shoppingCartGoods);
		int buyAmount = frontendService.getBuyAmount(customerOrder);
		if (customerOrder.size() != 0 && inputMoney >= buyAmount) {
			boolean updateResult = false;
			boolean createResult = frontendService.createBeverageOrder(customerOrder, customerID);
			if (createResult) {
				Set<Goods> updateGoods = customerOrder.keySet();
				updateResult = frontendService.updateBeverageGoods(updateGoods);
				if (updateResult) session.removeAttribute("shoppingCartGoods");
			}
			GoodsReceipt goodsReceipt = new GoodsReceipt(inputMoney, buyAmount, customerOrder);
			session.setAttribute("orderGoods", customerOrder);
			session.setAttribute("goodsReceipt", goodsReceipt);
		} else {
			if (inputMoney < buyAmount) session.setAttribute("buyGoodsMsg", "輸入金額不足！");
		}
		return mapping.findForward("buyGoods");
	}

}
