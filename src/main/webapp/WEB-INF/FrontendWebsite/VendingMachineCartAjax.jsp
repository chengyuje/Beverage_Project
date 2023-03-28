<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<c:url value="/" var="WEB_PATH" />
<c:url value="/js" var="JS_PATH" />
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link type="text/css" rel="stylesheet" href="css/BeverageCss.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/popper.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<title>販賣機</title>
<%-- <script src="${JS_PATH}/jquery-3.6.3.min.js"></script> --%>
<script type="text/javascript">
		var url='${WEB_PATH}ShoppingCartDispatchAction.do?action=cartListView';
		function addCartGoods(goodsID, buyQuantityIdx){
			var buyQuantity = document.getElementsByName("buyQuantity")[buyQuantityIdx].value;
			$.ajax({
				url:'${WEB_PATH}ShoppingCartDispatchAction.do?action=addCartGoods',
				type:'POST',
				data:{goodsID,buyQuantity},
				dataType:'JSON',
				success:function(object){
							if(object.message != null){
								alert(object.message);
							}else{
								alert(
										'已將商品加入購物車！'+
										'\n\n商品名稱：'+object.goodsName+
										'\n購買數量：'+object.buyQuantity+'罐'+
										'\n購買單價：NT$'+object.goodsPrice+'/罐'
//		 	 							JSON.stringify(cartGoods,null,3)
									);	
							}
						},
				error:function(error){
						alert("Ajax Error!");
					  }
			});
		}
		
		function queryCartGoods(){
			$.ajax({
				url:'${WEB_PATH}ShoppingCartDispatchAction.do?action=queryCartGoods',
				type:'POST',
				dataType:'JSON',
				success:function(totalAmount){
							if(totalAmount != 0){
// 								var url='${WEB_PATH}ShoppingCartDispatchAction.do?action=cartListView';
								$('#cartList .modal-body').load(url,function(){
									$('#cartList').modal('show');
								});
								$('#totalAmount').text('購買總金額：NT$'+totalAmount);	
							}else{
								$('#close').click();
								$('#totalAmount').empty();
								clearCartGoods();
							}
						},
				error:function(error){
					  	alert("您的購物車是空的！");
					  }
			});
		}
		
		function clearCartGoods(){
			$.ajax({
				url:'${WEB_PATH}ShoppingCartDispatchAction.do?action=clearCartGoods',
				type:'POST',
				success:function(){
							alert("購物車已清空！");
						},
				error:function(error){
					  	alert("Ajax Error！");
					  }
			});
		}
		
		function deleteInCart(goodsID){
			$.ajax({
				url:'${WEB_PATH}ShoppingCartDispatchAction.do?action=deleteInCart',
				type:'POST',
				data:{goodsID},
				dattaType:'text',
				success:function(totalAmount){
							if($('#totalAmount').val() != totalAmount && totalAmount>0){
								$('#totalAmount').text('購買總金額：NT$'+totalAmount);
								$('#cartList .modal-body').load(url,function(){});
							}else {
								$('#close').click();
								clearCartGoods();
							}
						},
				error:function(){
						alert("Ajax Error!");
					  }
			});
		}
		
	</script>
</head>
<body class="VMC_image" align="center" link="#0033FF" alink="#0033FF" vlink="#0033FF">
	<c:url value="/FrontendDispatchAction.do" var="homePage">
		<c:param name="action" value="eachPageGoods" />
		<c:param name="pageNo" value="1" />
	</c:url> 
	<nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4 fixed-top">
	  <a class="navbar-brand" href="${homePage}">Beverage</a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	  <div class="collapse navbar-collapse" id="navbarCollapse">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item active">
	        <button class="btn btn-danger" onclick="clearCartGoods()">清空購物車</button>&nbsp;&nbsp;
	      </li>
	      <li class="nav-item">
	        <button class="btn btn-primary" onclick="queryCartGoods()">購物車商品列表</button>
	      </li>
	    </ul>
	    <form class="form-inline mt-2 mt-md-0" action="FrontendDispatchAction.do" method="get">
			<input type="hidden" name="action" value="searchGoods" /> 
			<input type="hidden" name="pageNo" value="1" />
			 <input class="form-control mr-sm-2" type="text" name="searchKeyword" size="16" placeholder="Search" aria-label="Search"
				<c:if test="${not empty requestScope.searchKeyword}">
			      value="${requestScope.searchKeyword}"
			    </c:if> />
			 <button class="btn btn-outline-warning my-2 my-sm-0" type="submit">Search</button>
		</form>
	  </div>
	</nav>

	<table width="1300" height="400" align="center" border="0" cellpadding="5px" style="margin-top:5rem;">
		<tr>
			<td width="400" height="200" align="center">
				<img border="0" src="DrinksImage/coffee.jpg" width="200" height="200"><br/><br/>
				<h2>${sessionScope.beverageMember.customerName}，歡迎光臨！</h2> 
				  <c:url value="/BackendDispatchAction.do" var="queryGoods">
					<c:param name="action" value="eachPagesGoods" />
					<c:param name="pageNo" value="1" />
				  </c:url> 
				  <a href="${homePage}" style="text-decoration: none;"><b>首頁</b></a>&nbsp;&nbsp; 
<!-- 				  <a href="MemberDispatchAction.do?action=toBeverageMemberListHtml" style="text-decoration:none;" >會員管理</a>&nbsp; &nbsp; -->
				  <a href="${queryGoods}" style="text-decoration: none;">後臺頁面</a>&nbsp;&nbsp; 
				  <a href="LoginDispatchAction.do?action=logout" style="text-decoration: none;">登出</a> <br />
				<br />
				<form action="FrontendDispatchAction.do" method="post">
					<input type="hidden" name="action" value="buyGoods" /> 
					<input type="hidden" name="pageNo" value="1" />
					 <font face="微軟正黑體" size="4"> <b>投入:</b> 
						<input type="number" name="inputMoney" min="0" max="100000" size="5" value="0" style="font-size: 14px;"> <b>元</b>
					    <b><input class="VMC_submitButton" type="submit" value="送出"></b>
					    <br /><br />
					</font>
				</form>
				<div style="border-width: 3px; border-style: dashed; border-color: #FFAC55; padding: 5px; width: 300px;">
					<p style="color: blue;">~~~~~~~ 消費明細 ~~~~~~~</p>
					<c:choose>
						<c:when test="${not empty sessionScope.buyGoodsMsg}">
							<p style="color: red;">${sessionScope.buyGoodsMsg}</p>
							<% session.removeAttribute("buyGoodsMsg"); %>
						</c:when>
						<c:when test="${not empty sessionScope.goodsReceipt}">
							<p style="margin: 10px;">投入金額：${sessionScope.goodsReceipt.inputMoney}</p>
							<p style="margin: 10px;">購買金額：${sessionScope.goodsReceipt.buyAmount}</p>
							<p style="margin: 10px;">找零金額：${sessionScope.goodsReceipt.change}</p>
							<c:forEach items="${sessionScope.goodsReceipt.goodsList}" var="goods" varStatus="status">
								<p style="margin: 10px;">
									<font color="blue">${goods.goodsName}</font>&nbsp;${goods.goodsPrice}&nbsp;*&nbsp;
									<c:forEach items="${sessionScope.orderGoods}" var="quantity" begin="${status.index}" end="${status.index}">
										${quantity.value}
									</c:forEach>
								</p>
							</c:forEach>
							<%
								session.removeAttribute("goodsReceipt");
								session.removeAttribute("orderGoods");
							%>
						</c:when>
					</c:choose>
				</div>
			</td>
			<td width="800">
				<table height="800" border="2" style="border-collapse: collapse" bgcolor="white">
					<c:forEach items="${sessionScope.queryGoodsList}" var="goods" varStatus="status">
						<c:if test="${status.index eq 0 or status.index eq 3}">
							<tr>
						</c:if>
							  <td align="center" width="300">
								  <font face="微軟正黑體" size="5">${goods.goodsName}</font> <br /> 
								  <font face="微軟正黑體" size="4" style="color: gray;"> ${goods.goodsPrice} 元/罐 </font> <br /> 
								  <img border="0" src="DrinksImage/${goods.goodsImageName}" width="150" height="150"> <br />
							      <font face="微軟正黑體" size="3">
									  <input type="hidden" name="goodsID" value="${goods.goodsID}">
									  購買&nbsp;<input type="number" name="buyQuantity" min="0" max="${goods.goodsQuantity}" size="5" value="0" style="font-size: 14px;">罐<br /><br />
									  <button class="VMC_addCartButton" onclick="addCartGoods(${goods.goodsID},${status.index})">加入購物車</button><br /><br />
									  <p style="color: red;">(庫存 ${goods.goodsQuantity} 罐)</p>
								  </font>
							  </td>
						<c:if test="${status.index eq 2 or status.index eq 5}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<ul class="pagination float-right">
					<c:url value="/FrontendDispatchAction.do" var="previousPage">
						<c:param name="action" value="${sessionScope.paramValue}" />
						<c:if test="${param.action eq 'searchGoods'}">
							<c:param name="searchKeyword" value="${requestScope.searchKeyword}" />
						</c:if>
						<c:param name="pageNo" value="${param.pageNo-1}" />
					</c:url>
					<li
						<c:choose>
							<c:when test="${param.pageNo ne '1'}">
								class="page-item"
							</c:when>
							<c:otherwise>class="page-item disabled"</c:otherwise>
						</c:choose>
					>
						<a class="page-link" href="${previousPage}">Previous</a>
					</li>
					
					<c:choose>
						<c:when test="${param.pageNo%3 == '1'}">
							<c:set var="startPage" scope="session" value="${param.pageNo}" />
						</c:when>
						<c:when test="${param.pageNo%3 == '0'}">
							<c:set var="startPage" scope="session" value="${param.pageNo-2}" />
						</c:when>
					</c:choose>
					
					<c:forEach items="${sessionScope.pageCount}" begin="${startPage-1}" end="${startPage+1}" var="eachPage" varStatus="status">
						<c:url value="/FrontendDispatchAction.do" var="page">
							<c:param name="action" value="${sessionScope.paramValue}" />
							<c:if test="${param.action eq 'searchGoods'}">
								<c:param name="searchKeyword" value="${requestScope.searchKeyword}" />
							</c:if>
							<c:param name="pageNo" value="${eachPage}" />
						</c:url>
						<li class="page-item">
							<a class="page-link" href="${page}"
								<c:if test="${eachPage eq param.pageNo}">
									style="color:red;"
								</c:if> >
								${eachPage} 
							</a>
						</li>
					</c:forEach>
					
					<c:url value="/FrontendDispatchAction.do" var="nextPage">
						<c:param name="action" value="${sessionScope.paramValue}" />
							<c:if test="${param.action eq 'searchGoods'}">
								<c:param name="searchKeyword" value="${requestScope.searchKeyword}" />
							</c:if>
						<c:param name="pageNo" value="${param.pageNo+1}" />
					</c:url>
				
					<li
						<c:choose>
							<c:when test="${param.pageNo ne sessionScope.lastPage}">
								class="page-item"
							</c:when>
							<c:otherwise>class="page-item disabled"</c:otherwise>
						</c:choose>
					>
						<a class="page-link" href="${nextPage}">Next</a>
					</li>
						
				</ul>
			</td>
		</tr>
	</table>
	
	<!-- Modal -->
	<div class="modal fade" id="cartList" tabindex="-1" aria-labelledby="cartListLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="cartListLabel">購物車</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body align-self-center"></div>
	      <div class="d-flex justify-content-end font-weight-normal" id="totalAmount" style="padding: 0.3rem; color:#FF0088;"></div>
	      <div class="modal-footer">
	        <button id="close" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>