<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link type="text/css" rel="stylesheet" href="css/BeverageCss.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/popper.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<title>販賣機-後臺</title>
</head>
<body class="VMB_image" link="#0033FF" alink="#0033FF" vlink="#0033FF" style="background-color:#FAFAD2;">
	<c:url value="/LoginDispatchAction.do" var="logout">
		<c:param name="action" value="logout"/>
	</c:url>
	<c:url value="/FrontendDispatchAction.do" var="homePage">
		<c:param name="action" value="eachPageGoods"/>
		<c:param name="pageNo" value="1"/>
	</c:url>
	<font style="font-size:140%;margin-left:5px;font-weight:bold;color:#F75000;">${sessionScope.beverageMember.customerName} 先生/小姐</font>
	<font style="font-size:140%;margin-left:5px;font-weight:bold;">您好!</font>
	<a href="${logout}" style="text-decoration:none;font-size:130%;float:right;margin-right:5px;">
	  <b>Log Out&nbsp;&nbsp;</b>
	</a>
	<a href="${homePage}" style="text-decoration:none;font-size:130%; float:right; margin-right:15px;">
	  <b>Home Page</b>
	</a>
	<br/>
	<hr size="3px" color="black"/>
	<%@include file="Backend_Title.jsp" %>
	<br />
	<hr size="3px" color="black" />
	<h2 style="margin-left: 25px;">Sales Report</h2>
	<div style="margin-left: 30px;">
	<form action="BackendDispatchAction.do" method="get">
		<input type="hidden" name="action" value="querySalesReport"/>
		起 &nbsp; <input type="date" name="queryStartDate" style="height:25px;width:150px;font-size:16px;text-align:center;"/>
		&nbsp;
		迄 &nbsp; <input type="date" name="queryEndDate" style="height:25px;width:150px;font-size:16px;text-align:center;"/>	
		<input class="btn btn-warning" type="submit" value="查詢" style="margin-left:25px;"/>
	</form>
	<br/>
	<c:if test="${not empty requestScope.querySalesReport}">
		<div style="width:1000px;">
			<table class="table table-striped table-light" width="1000" border="0">
				<thead class="thead-dark">
				    <tr class="text-center">
				      <th class="border border-dark" scope="col"><font size="4">Order ID</font></th>
				      <th class="border border-dark" scope="col"><font size="4">Customer Name</font></th>
				      <th class="border border-dark" scope="col"><font size="4">Order Date</font></th>
				      <th class="border border-dark" scope="col"><font size="4">Goods Name</font></th>
				      <th class="border border-dark" scope="col"><font size="4">Goods Price</font></th>
				      <th class="border border-dark" scope="col"><font size="4">Quantity</font></th>
				      <th class="border border-dark" scope="col"><font size="4">Total Price</font></th>
				    </tr>
			    </thead>
				<tbody>
					<c:forEach items="${requestScope.querySalesReport}" var="eachReport" varStatus="status">
						<tr class="text-center">
							<td class="border border-dark">${eachReport.orderID}</td>
							<td class="border border-dark">${eachReport.customerName}</td>
							<td class="border border-dark"><fmt:formatDate value="${eachReport.orderDate}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
							<td class="border border-dark">${eachReport.goodsName}</td>
							<td class="border border-dark">${eachReport.goodsBuyPrice}</td> 
							<td class="border border-dark">${eachReport.buyQuantity}</td>
							<td class="border border-dark">NT$ ${eachReport.buyAmount}</td>	
						</tr>	
					</c:forEach>
				</tbody>
			</table>
		</div>
		
	
	</c:if>
	
	</div>
</body>
</html>