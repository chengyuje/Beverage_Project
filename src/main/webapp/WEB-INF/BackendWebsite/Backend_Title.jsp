<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:url value="/BackendDispatchAction.do" var="queryGoods">
		<c:param name="action" value="eachPagesGoods"/>
		<c:param name="pageNo" value="1"/>
	</c:url>
	<c:url value="/BackendDispatchAction.do" var="replenishment">
		<c:param name="action" value="goodsSelectedView"/>
	</c:url>
	
	<c:url value="/CreateGoods.do" var="create">
		<c:param name="action" value="toVMBackendGoodsCreateHtml"/>
	</c:url>
	
	<c:url value="/BackendDispatchAction.do" var="report">
		<c:param name="action" value="toVMBackendGoodsSaleReportHtml"/>
	</c:url>

	<h2 style="margin-left:25px">Vending Machine Backend Service</h2>
	<table  border="1" style="margin-left:30px">
		<tr>
			<td width="270" height="50" align="center" bgcolor="#E0E0E0">
			  <a href="${queryGoods}" style="font-size:150%; text-decoration:none;">
			   <b>Goods List</b>
			  </a>
			</td>
			<td width="270" height="50" align="center" bgcolor="#F0F0F0">
			  <a href="${replenishment}" style="font-size:150%; text-decoration:none;">
			    <b>Goods Replenishment</b>
			  </a>
			</td>
			<td width="270" height="50" align="center" bgcolor="#E0E0E0">
			  <a href="${create}" style="font-size:150%; text-decoration:none;">
			    <b>Create Goods</b>
			  </a>
			</td>
			<td width="270" height="50" align="center" bgcolor="#F0F0F0">
			  <a href="${report}" style="font-size:150%; text-decoration:none;">
			    <b>Sales Report</b>
			  </a>
			</td>
		</tr>
	</table>

</body>
</html>