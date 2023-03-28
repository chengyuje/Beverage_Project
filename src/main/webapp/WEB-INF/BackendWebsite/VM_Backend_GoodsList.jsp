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
<body class="VMB_image" link="#0033FF" alink="#0033FF" vlink="#0033FF" style="background-color:#FAFAD2;" >
	<c:url value="/LoginDispatchAction.do" var="logout">
		<c:param name="action" value="logout"/>
	</c:url>
	<c:url value="/FrontendDispatchAction.do" var="homePage">
		<c:param name="action" value="eachPageGoods"/>
		<c:param name="pageNo" value="1"/>
	</c:url>
	<font style="font-size:140%; margin-left: 5px; font-weight: bold; color: #F75000;">${sessionScope.beverageMember.customerName} 先生/小姐</font>
	<font style="font-size:140%; margin-left: 5px; font-weight: bold;">您好!</font>
	<a href="${logout}" style="text-decoration: none; font-size: 140%; float: right; margin-right: 5px;">
	  <b>Log Out&nbsp;&nbsp;</b>
	</a>
	<a href="${homePage}" style="text-decoration: none; font-size: 140%; float: right; margin-right: 15px;">
	  <b>Home Page</b>
	</a>
	<br/>
	<hr size="5px" color="black"/>
	<%@ include file="Backend_Title.jsp" %>
	<br />
	<hr size="3px" color="black" />
	<h2 style="margin-left:25px">Search Goods With Condition</h2>
	<c:if test="${not empty queryGoodsListMsg}">
		&emsp;&emsp;<font size="4" color="red"><b>${queryGoodsListMsg}</b></font>
	</c:if>
	<div style="display: block; width: 600px; height: 250px; margin-left: 30px;">
		<table align="center" valign="top" width="900"  cellpadding="10px" border="0">
			<tr>
			  <td>
			  <c:url value="/SearchGoodsWithCondition.do" var="search">
			  	<c:param name="action" value="queryGoodsWithCondition"/>
			  	<c:param name="pageNo" value="1"/>
			  </c:url>
			  <form action="${search}" method="post">
			    <table class="table table-striped table-dark" border="1">
					<tr>
						<td align="left" valign="middle"><font size="4">Goods ID：</font><br />
							<input type="text" name="goodsID" style="width: 170px; font-size: 12px;" 
								<c:if test="${not empty requestScope.goodsListVo.goodsID}">
									value="${requestScope.goodsListVo.goodsID}"
								</c:if>
							/>
						</td>
						<td align="left" valign="middle"><font size="4">Goods Name：</font><br />
							<input type="text" name="goodsName" style="width: 170px; font-size: 12px;"  
								<c:if test="${not empty requestScope.goodsListVo.goodsName}">
									value="${requestScope.goodsListVo.goodsName}"
								</c:if>
							 />
						</td>
						<td align="left" valign="middle"><font size="4">Goods Quantity(Lower)：</font><br />
							 <input type="text" name="priceQuantity" style="width: 170px; font-size: 12px;" 
							 	<c:if test="${not empty requestScope.goodsListVo.priceQuantity}">
									value="${requestScope.goodsListVo.priceQuantity}"
								</c:if>
							 />
						</td>
						<td align="left" valign="middle"><font size="4">Goods Status：</font><br /> 
							<select name="status" style="width: 170px; font-size: 20px;">
								<option value="(0,1)">ALL</option>
								<option value="1">上架</option>
								<option value="0">下架</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" valign="middle"><font size="4">Price Start：</font><br />
							<input type="text" name="priceStart" style="width: 170px; font-size: 12px;" 
								<c:if test="${not empty requestScope.goodsListVo.priceStart}">
									value="${requestScope.goodsListVo.priceStart}"
								</c:if>
							/>
						</td>
						<td align="left" valign="middle"><font size="4">Price End：</font><br />
							<input type="text" name="priceEnd" style="width: 170px; font-size: 12px;"
								<c:if test="${not empty requestScope.goodsListVo.priceEnd}">
									value="${requestScope.goodsListVo.priceEnd}"
								</c:if> 
							/>
						</td>
						<td align="left" valign="middle"><font size="4">Sort：</font><br />
							<select name="priceSort" style="width: 170px; font-size: 20px;">
								<option value="">無</option>
								<option value="ASC">由低到高</option>
								<option value="DESC">由高到低</option>
							</select>
						</td>
						<td class="align-middle text-left">
							<input type="submit" class="btn btn-outline-light" value="Search" style="width:170px;" />
						</td>
					</tr>
				</table>
				</form>
			  </td>
			</tr>
			<tr>
			  <td>
				<table  height="450" class="table table-striped table-light" border="0">
				   <thead class="thead-dark">
					    <tr class="text-center">
					      <th class="border border-dark" scope="col" ><font size="4">Goods ID</font></th>
					      <th class="border border-dark" scope="col" ><font size="4">Goods Name</font></th>
					      <th class="border border-dark" scope="col" ><font size="4">Goods Price</font></th>
					      <th class="border border-dark" scope="col" ><font size="4">Goods Quantity</font></th>
					      <th class="border border-dark" scope="col" ><font size="4">Goods Status</font></th>
					    </tr>
				    </thead>
					<c:if test="${not empty requestScope.queryGoodsList}">
						<c:forEach items="${requestScope.queryGoodsList}" var="goods" varStatus="status">
							<tr class="text-center">
								<td class="border border-dark" valign="middle" width="80"><font size="3">${goods.goodsID}</font></td>
								<td class="border border-dark" valign="middle" width="130"><font size="3">${goods.goodsName}</font></td>
								<td class="border border-dark" valign="middle" width="80"><font size="3">${goods.goodsPrice}</font></td>
								<td class="border border-dark" valign="middle" width="80"><font size="3">${goods.goodsQuantity}</font></td>
								<td class="border border-dark" valign="middle" width="80">
									<c:choose>
										<c:when test="${goods.goodsStatus eq '1'}">
											<font size="3" color="blue">上架</font>
										</c:when>
										<c:otherwise>
											<font size="3" color="red">下架</font>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
						<tr class="border border-dark">
							<td colspan="5">
							<ul class="pagination float-right">
								<c:url value="${sessionScope.urlValue}" var="previousPage">
									<c:param name="action" value="${sessionScope.paramValue}"/>
									<c:param name="pageNo" value="${param.pageNo-1}"/>				
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
										<c:set var="startPage" scope="session" value="${param.pageNo}"/>
									</c:when>
									<c:when test="${param.pageNo%3 == '0'}">
										<c:set var="startPage" scope="session" value="${param.pageNo-2}"/>
									</c:when>
								</c:choose>
								
								<c:forEach items="${sessionScope.pageCount}" begin="${startPage-1}" end="${startPage+1}" var="eachPage" varStatus="status">
									<c:url value="${sessionScope.urlValue}" var="page">
										<c:param name="action" value="${sessionScope.paramValue}"/>
										<c:param name="pageNo" value="${eachPage}"/>
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
							
								<c:url value="${sessionScope.urlValue}" var="nextPage">
									<c:param name="action" value="${sessionScope.paramValue}"/>
									<c:param name="pageNo" value="${param.pageNo+1}" />
								</c:url>
								<li
									<c:choose>
										<c:when test="${param.pageNo ne sessionScope.lastPage}">
											class="page-item"
										</c:when>
										<c:otherwise>
											class="page-item disabled"
										</c:otherwise>
									</c:choose>
								>
									<a class="page-link" href="${nextPage}">Next</a>
								</li>
							</ul>
						</td>
					</tr>
					</c:if>
				</table>	
			  </td>
			</tr>
		</table>
	</div>
</body>
</html>