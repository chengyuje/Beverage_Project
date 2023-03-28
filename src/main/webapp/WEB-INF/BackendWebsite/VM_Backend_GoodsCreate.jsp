<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<h2 style="margin-left: 25px;">Create Goods</h2>
	<div style="margin-left: 30px;">
	<form action="CreateGoods.do?action=addGoods" enctype="multipart/form-data" method="post">
		<c:if test="${not empty sessionScope.createMsg}">
			<p><font color="blue">${sessionScope.createMsg}</font></p>
			<% session.removeAttribute("createMsg");%>
		</c:if>
		<p>
			飲料名稱：
			<input type="text" name="goodsName" style="width: 170px; font-size: 14px;"/>
		</p>
		<p>
			設定價格： 
			<input type="number" name="goodsPrice" value="0" min="0" style="width: 170px; font-size: 14px;"/>
		</p>
		<p>
			初始數量：
			<input type="number" name="goodsQuantity" value="0" min="0" style="width: 170px; font-size: 14px;"/>
		</p>
		<p>
			商品圖片：
			<input type="file" name="goodsImage"/>
		</p>
		<p>
			商品狀態：
			<select name="goodsStatus" style="width: 170px; font-size: 20px;">
				<option value="1">上架</option>
				<option value="0">下架</option>				
			</select>
		</p>
		<p><input class="btn btn-warning" type="submit" value="送出"></p>
	</form>
	</div>
</body>
</html>