package com.training.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.dao.BeverageMemberDao;
import com.training.model.BeverageMember;
import com.training.vo.MemberVO;

public class MemberDispatchAction extends DispatchAction {

	private BeverageMemberDao beverageMemberDao = BeverageMemberDao.getBeverageMemberDao_Instance();

	public ActionForward queryAllBeverageMember(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List<BeverageMember> list = beverageMemberDao.queryAllBeverageMember();
		request.setAttribute("memberList", list);
		return mapping.findForward("memberListView");
	}

	public ActionForward createBeverageMember(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MemberVO memberVo = (MemberVO) form;
		BeverageMember beverageMember = new BeverageMember();
		BeanUtils.copyProperties(beverageMember, memberVo);
		String printInfo = beverageMemberDao.createBeverageMember(beverageMember) ? "新增成功，請重新登入！" : "新增失敗，帳號已存在！";
		HttpSession session = request.getSession();
		session.setAttribute("createMemberMsg", printInfo);
		return mapping.findForward("toMemberCreate");
	}

	public ActionForward updateBeverageMember(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MemberVO memberVo = (MemberVO) form;
		BeverageMember beverageMember = new BeverageMember();
		BeanUtils.copyProperties(beverageMember, memberVo);
		int updateRecord = beverageMemberDao.updateBeverageMember(beverageMember);
		HttpSession session = request.getSession();
		if (updateRecord > 0) {
			session.setAttribute("updateMemberMsg", "修改成功！");
			session.setAttribute("modifyMemberUserName", beverageMember.getUserName());
		}else {
			session.setAttribute("updteMemberMsg", "修改失敗！");
		}
		return mapping.findForward("toMemberModify");
	}

	public ActionForward deleteBeverageMember(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userName = request.getParameter("userName");
		int deleteRecord = beverageMemberDao.deleteBeverageMember(userName);
		HttpSession session = request.getSession();
		if (deleteRecord > 0) {
			session.setAttribute("deleteMemberMsg", "刪除成功！");
		}else {
			session.setAttribute("deleteMemberMsg", "刪除失敗！");
		}
		return mapping.findForward("toMemberList");
	}
	
	public ActionForward memberSelectedView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<BeverageMember> queryAllMember = beverageMemberDao.queryAllBeverageMember();
		request.setAttribute("queryAllMember", queryAllMember);
		String userNameSelected=request.getParameter("userName");
		String password=request.getParameter("password");
		
		userNameSelected=(userNameSelected!=null)? userNameSelected:(String)request.getSession().getAttribute("modifyMemberUserName");
		if(userNameSelected!=null) {
			BeverageMember member=beverageMemberDao.queryBeverageMember(userNameSelected);
			request.setAttribute("modifyMember", member);
		}
		return mapping.findForward("memberModifyView");
	}

	public ActionForward memberListView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("memberListView");
	}

	public ActionForward memberCreateView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("memberCreateView");
	}

	public ActionForward memberModifyView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("memberModifyView");
	}

}
