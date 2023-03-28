<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link type="text/css" rel="stylesheet" href="css/bootstrap.css"/>
<link type="text/css" rel="stylesheet" href="css/BeverageCss.css"/>
<link type="text/css" rel="stylesheet" href="css/signin.css"/>
<script type="text/javascript" src="js/jquery.min.js" ></script>
<script type="text/javascript" src="js/popper.min.js" ></script>
<script type="text/javascript" src="js/bootstrap.min.js" ></script>
<title>Beverage Member Create</title>
<script type="text/javascript">
	$(document).ready(function(){
		$(createModal).modal('show');
	});
</script>
</head>
<body class="BML_image" link="#0033FF" alink="#0033FF" vlink="#0033FF">
	<form class="form-signin" action="MemberDispatchAction.do" method="post">
	  <input type="hidden" name="action" value="createBeverageMember"/>
      <p class="h3 mb-3 font-weight-bold text-center">Member Sign Up</p>
      <label for="inputCustomerName" class="sr-only">CUSTOME NAME</label>
      <input type="text" class="form-control" id="inputCustomerName" name="customerName" placeholder="CUSTOMER NAME" required autofocus>
      <label for="inputUserName" class="sr-only">USERNAME(ID_CARD_NO)</label>
      <input type="text" class="form-control" id="inputUserName" name="userName"  placeholder="USERNAME(ID_CARD_NO)" required autofocus>
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password" required>
      <button type="submit" class="btn btn-lg btn-primary btn-block">Sign Up</button>
      <br/>
      <p>
		<a class="btn btn-lg btn-success btn-block" href="BeverageMemberLogin.jsp" role="button" aria-expanded="false" aria-controls="collapseExample">
		     Have an account?&nbsp;Sign In
		</a>
	  </p>
    </form>
    <c:if test="${not empty sessionScope.createMemberMsg}">
		<div class="modal fade" id="createModal" tabindex="-1" role="dialog" aria-labelledby="createModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="createModalLabel">MESSAGE</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <font color="blue">${sessionScope.createMemberMsg}</font>
		        <% session.removeAttribute("createMemberMsg"); %>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
	  </c:if>
</body>
</html>