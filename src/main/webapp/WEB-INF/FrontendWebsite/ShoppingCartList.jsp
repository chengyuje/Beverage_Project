<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript">
	function quantityChange(goodsID,index){
			var id=goodsID;
			var quantity=$('select[name="buyQuantity"] option:selected')[index].value;
			var param={ goodsID:id, buyQuantity:quantity };
			$.ajax({
				url:'${WEB_PATH}ShoppingCartDispatchAction.do?action=quantityChange',
				type:'GET',
				data:param,
				dataType:'JSON',
				success:function(object){
							$($('div[id="amount"]')[index]).text(object.amount);
							if($('#totalAmount').val() != object.totalAmount){
								$('#totalAmount').text('購買總金額：NT$'+object.totalAmount);
							}
						},
				error:function(error){
						alert("Ajax Error!");
					  }
			});
	}
</script>
<title>Insert title here</title>
</head>
<body>
	<table width="780" align="center" border="1" cellpadding="3px">
		<tr align="center">
			<th>商品圖片</th>
			<th>商品名稱</th>
			<th>商品價格</th>
			<th>購買數量</th>
			<th>總金額</th>
			<th>刪除商品</th>
		</tr>
		<c:forEach items="${sessionScope.shoppingCartGoods}" var="shoppingCartGoods" varStatus="status">
			<input type="hidden" id="id" name="goodsID" value="${shoppingCartGoods.key.goodsID}">
			<c:set var="amount" scope="page" value="${shoppingCartGoods.key.goodsPrice*shoppingCartGoods.value}" />
			<c:set var="minQuantity" scope="page" value="1" />
			<c:set var="maxQuantity" scope="page" value="${shoppingCartGoods.key.goodsQuantity}" />
			<tr align="center">
				<td><img border="0" src="DrinksImage/${shoppingCartGoods.key.goodsImageName}" width="75" height="75" /></td>
				<td>${shoppingCartGoods.key.goodsName}</td>
				<td>${shoppingCartGoods.key.goodsPrice}</td>
				<td>
					<select id="select" name="buyQuantity" onChange="quantityChange(${shoppingCartGoods.key.goodsID},${status.index})">
						<c:forEach var="quantity" begin="${pageScope.minQuantity}" end="${pageScope.maxQuantity}">
							<option value="${quantity}" <c:if test="${shoppingCartGoods.value eq quantity}">selected</c:if>>${quantity}</option>
						</c:forEach>
					</select>&nbsp;
					<font color="red">(庫存 ${shoppingCartGoods.key.goodsQuantity} 罐)</font>			
				</td>
				<td><div id="amount">${pageScope.amount}</div></td>
				<td><button type="submit" class="btn btn-outline-danger"
						onClick="deleteInCart(${shoppingCartGoods.key.goodsID})">刪除</button></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>