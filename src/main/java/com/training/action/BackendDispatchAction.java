package com.training.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.training.formbean.CreateGoodsForm;
import com.training.formbean.GoodsListForm;
import com.training.formbean.UpdateGoodsForm;
import com.training.model.Goods;
import com.training.model.GoodsOrder;
import com.training.service.BackendService;
import com.training.vo.CreateGoodsVO;
import com.training.vo.GoodsListVO;
import com.training.vo.UpdateGoodsVO;

import net.sf.json.JSONObject;

@MultipartConfig
public class BackendDispatchAction extends DispatchAction {

	private BackendService backendService = BackendService.getBackendService_Instance();

	public ActionForward queryGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Goods> queryAllGoods = backendService.queryAllGoods();
		return mapping.findForward("queryGoodsView");
	}

//	public ActionForward updateGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		UpdateGoodsVO goodsVo = (UpdateGoodsVO) form;
//		UpdateGoodsForm goods = new UpdateGoodsForm();
//		BeanUtils.copyProperties(goods, goodsVo);
//		boolean updateResult = backendService.updateGoods(goods);
//		HttpSession session = request.getSession();
//		String message = updateResult ? "商品維護作業，成功！商品編號：" + goods.getGoodsID() : "商品維護作業，失敗！商品編號：" + goods.getGoodsID();
//		session.setAttribute("updateMsg", message);
//		session.setAttribute("modifyGoodsID", goods.getGoodsID().toString());
//		return mapping.findForward("updateGoods");
//	}

	// for Ajax
	public ActionForward updateGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UpdateGoodsVO goodsVo = (UpdateGoodsVO) form;
		UpdateGoodsForm goods = new UpdateGoodsForm();
		BeanUtils.copyProperties(goods, goodsVo);
		boolean updateResult = backendService.updateGoods(goods);
		String message = updateResult ? "商品維護作業，成功！商品編號：" + goods.getGoodsID() : "商品維護作業，失敗！商品編號：" + goods.getGoodsID();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/text");
		PrintWriter out = response.getWriter();
		out.print(message);
		out.flush();
		out.close();
		return null;
	}

	public ActionForward addGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionServlet actionServlet = getServlet();
		String imagePath = actionServlet.getInitParameter("GoodsImgPath");
		String severImagePath = actionServlet.getServletContext().getRealPath(imagePath);
		CreateGoodsVO createGoodsVo = (CreateGoodsVO) form;
		FormFile formFile = createGoodsVo.getGoodsImage();
		String fileName = formFile.getFileName();
		Path newGoodsImagePath = Paths.get(severImagePath).resolve(fileName);
		InputStream inputStream = formFile.getInputStream();
		Files.copy(inputStream, newGoodsImagePath, StandardCopyOption.REPLACE_EXISTING);
		inputStream.close();

		CreateGoodsForm newGoods = new CreateGoodsForm();
		BeanUtils.copyProperties(newGoods, createGoodsVo);
		int goodsID = backendService.createGoods(newGoods);
		HttpSession session = request.getSession();
		session.setAttribute("createMsg", "商品新增成功！商品編號：" + goodsID);
		return mapping.findForward("addGoods");
	}

	public ActionForward querySalesReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String queryStartDate = request.getParameter("queryStartDate");
		String queryEndDate = request.getParameter("queryEndDate");
		Set<GoodsOrder> querySalesReport = backendService.querySalesReport(queryStartDate, queryEndDate);
		request.setAttribute("querySalesReport", querySalesReport);
		return mapping.findForward("querySalesReportView");
	}

	public ActionForward queryGoodsWithCondition(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("urlValue", request.getServletPath());
		session.setAttribute("paramValue", "queryGoodsWithCondition");

		String pageNo = request.getParameter("pageNo");
		int end = Integer.parseInt(pageNo) * 6;
		int start = end - 5;
		GoodsListVO goodsListVo = (GoodsListVO) form;
		request.setAttribute("goodsListVo", goodsListVo);
		GoodsListForm goodsListForm = new GoodsListForm();
		BeanUtils.copyProperties(goodsListForm, goodsListVo);
		List<Goods> queryGoodsList = backendService.queryGoodsWithCondition(goodsListForm, start, end);
		List<Goods> goodsList = backendService.queryAllWithCondition(goodsListForm);
		List<Integer> conditionPageCount = backendService.getPageCount(goodsList);

		int conditionLastPage = conditionPageCount.get(conditionPageCount.size() - 1);
		session.setAttribute("lastPage", conditionLastPage);

		session.setAttribute("pageCount", conditionPageCount);

		if (queryGoodsList.size() != 0) {
			request.setAttribute("queryGoodsList", queryGoodsList);
		} else {
			request.setAttribute("queryGoodsListMsg", "搜尋條件查無資料");
		}
		return mapping.findForward("queryGoodsWithConditionView");
	}

	public ActionForward eachPagesGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Goods> queryAllGoods = backendService.queryAllGoods();
		HttpSession session = request.getSession();
		session.setAttribute("urlValue", request.getServletPath());
		session.setAttribute("paramValue", "eachPagesGoods");

		List<Integer> pageCount = backendService.getPageCount(queryAllGoods);
		int lastPage = pageCount.get(pageCount.size() - 1);
		session.setAttribute("lastPage", lastPage);
		session.setAttribute("pageCount", pageCount);

		String pageNo = request.getParameter("pageNo");
		int end = Integer.parseInt(pageNo) * 6;
		int start = end - 5;

		Set<Goods> pageGoods = backendService.eachPageGoods(start, end);
		if (pageGoods.size() != 0) {
			request.setAttribute("queryGoodsList", pageGoods);
		} else {
			request.setAttribute("queryGoodsListMsg", "搜尋條件查無資料");
		}
		return mapping.findForward("queryGoodsView");
	}

	public ActionForward goodsSelectedView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Goods> queryAllGoods = backendService.queryAllGoods();
		request.setAttribute("queryAllGoods", queryAllGoods);

		String goodsSelectedID = request.getParameter("goodsID");
		goodsSelectedID = (goodsSelectedID != null) ? goodsSelectedID
				: (String) request.getSession().getAttribute("modifyGoodsID");
		if (goodsSelectedID != null) {
			Goods goods = backendService.queryGoodsID(goodsSelectedID);
			request.setAttribute("modifyGoods", goods);
		}
		return mapping.findForward("updateGoodsView");
	}

	// for Ajax
	public ActionForward getGoodsInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String goodsID = request.getParameter("id");
		Goods goods = backendService.queryGoodsID(goodsID);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(JSONObject.fromObject(goods));
		out.flush();
		out.close();
		return null;
	}

	public ActionForward toVMBackendGoodsListHtml(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("queryGoodsView");
	}

	public ActionForward toVMBackendGoodsReplenishmentHtml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("updateGoodsView");
	}

	public ActionForward toVMBackendGoodsCreateHtml(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("addGoodsView");
	}

	public ActionForward toVMBackendGoodsSaleReportHtml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("querySalesReportView");
	}
}
