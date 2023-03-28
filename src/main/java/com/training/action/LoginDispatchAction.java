package com.training.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.dao.BeverageMemberDao;
import com.training.model.BeverageMember;
import com.training.service.BackendService;

public class LoginDispatchAction extends DispatchAction {

	private BeverageMemberDao beverageMemberDao = BeverageMemberDao.getBeverageMemberDao_Instance();
	private BackendService backendService = BackendService.getBackendService_Instance();

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		BeverageMember beverageMember = new BeverageMember(null, userName, password);	
		beverageMember = beverageMemberDao.queryBeverageMember(beverageMember);
		if (beverageMember != null) {
			session.setAttribute("beverageMember", beverageMember);
			return mapping.findForward("frontendView");
		} else {
			BeverageMember member = beverageMemberDao.queryBeverageMember(userName);
			if(member != null) {
				request.setAttribute("userName", userName);
				request.setAttribute("loginMsg", "密碼輸入錯誤，請重新輸入！");
				return mapping.findForward("loginView");
			}else {
				request.setAttribute("loginMsg", "無此帳號，請註冊！");
				return mapping.findForward("loginView");
			}
			
		}
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("beverageMember");
		session.removeAttribute("shoppingCartGoods");
		return mapping.findForward("loginView");
	}
	
}
