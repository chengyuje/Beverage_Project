<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:url value="/" var="WEB_PATH"/>
<c:url value="/js" var="JS_PATH"/>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link type="text/css" rel="stylesheet" href="css/BeverageCss.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/popper.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script src="${JS_PATH}/jquery-3.6.3.min.js"></script>
<script type="text/javascript">
	function ajaxFunction(goodsIDParam){
		$.ajax({
			url:'${WEB_PATH}BackendDispatchAction.do?action=getGoodsInfo',
			type:'GET',
			data:goodsIDParam,
			dataType:'JSON',
			success:function(goodsInfo){
						$("#goodsPrice").val(goodsInfo.goodsPrice);
						$("#goodsQuantityDiv").text('商品庫存量：'+goodsInfo.goodsQuantity+'瓶');
						$("#goodsQuantity").val(0);
						$("#goodsStatus").val(goodsInfo.goodsStatus);
					},
			error:function(error){
					alert("Ajax Error!");
				  }
		});
	}
	$(document).ready(function(){
		$("#goodsID").bind("change",function(){
			var goodsID=$("#goodsID option:selected").val();
			var goodsIDParam={id:goodsID};
			if(goodsID!=""){
				ajaxFunction(goodsIDParam);
			}else{
				$("#goodsPrice").val('');
				$("#goodsQuantityDiv").text('商品庫存量：瓶');
				$("#goodsQuantity").val(0);
				$("#goodsStatus").val('');
			}
		});
		
		$("#updateButton").bind("click",function(){
			var updateFormData=$('#updateForm').serialize();
			$.ajax({
				url:'${WEB_PATH}BackendDispatchAction.do',
				type:'POST',
				data:updateFormData,
				dataType:'text',
				success:function(message){
							$("#updateMsg").empty();
							$("#updateMsg").append(message);
							var goodsID=$("#goodsID option:selected").val();
							var goodsIDParam={id:goodsID};
							ajaxFunction(goodsIDParam);
						},
				error:function(error){
						alert("Ajax Error!");
					  }
				
			});
		});
	});
</script>
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
	<h2 style="margin-left: 25px;">Goods Replenishment</h2>
	<div style="margin-left: 30px;">
	<p id="updateMsg" style="color: blue;"></p>
	<form id="updateForm">
		<input type="hidden" name="action" value="updateGoods">
		<p>
			飲料名稱：
			<select id="goodsID" name="goodsID">
				<option value="">---請選擇---</option>
				<c:forEach items="${requestScope.queryAllGoods}" var="goods">
					<option value="${goods.goodsID}" <c:if test="${goods.goodsID eq modifyGoods.goodsID}">selected</c:if>>
						${goods.goodsName}
					</option>
				</c:forEach>
			</select>
		</p>		
		<p>
			更改價格： 
			<input id="goodsPrice" type="number" name="goodsPrice" min="0"  
				value="${requestScope.modifyGoods.goodsPrice}" style="width: 170px; font-size: 14px;"
			/>
		</p>
		<p>
			<div id="goodsQuantityDiv" >商品庫存量：
				<c:if test="${not empty requestScope.modifyGoods}">
					${requestScope.modifyGoods.goodsQuantity}
				</c:if>
				瓶
			</div>
		</p>
		<p>
			補貨數量：
			<input id="goodsQuantity" type="number" name="goodsQuantity" value="0" min="0" max="1000"  style="width: 170px; font-size: 14px;"/>
		</p>
		<p>
			商品狀態：
			<select id="goodsStatus" name="goodsStatus" style="width: 170px; font-size: 20px;">
				<option value="">---請選擇---</option>
				<option value="1" <c:if test="${requestScope.modifyGoods.goodsStatus eq '1'}">selected</c:if>>上架</option>
				<option value="0" <c:if test="${requestScope.modifyGoods.goodsStatus eq '0'}">selected</c:if>>下架</option>
			</select>
		</p>		
		<p><input class="btn btn-warning" id="updateButton" type="button" value="送出" ></p>
	</form>
	</div>
</body>
</html>