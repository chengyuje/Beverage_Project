<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<link type="text/css" rel="stylesheet" href="css/bootstrap.css"/>
<link type="text/css" rel="stylesheet" href="css/BeverageCss.css"/>
<link type="text/css" rel="stylesheet" href="css/signin.css"/>
<script type="text/javascript" src="js/jquery.min.js" ></script>
<script type="text/javascript" src="js/popper.min.js" ></script>
<script type="text/javascript" src="js/bootstrap.min.js" ></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#loginModal').modal('show');
		
		$("#readMe").bind("change",function(){
			
		});
		
	});
</script>
<title>Beverage Member Login</title>
</head>
<body class="BML_image" link="#0033FF" alink="#0033FF" vlink="#0033FF">
	<form class="form-signin" action="LoginDispatchAction.do" method="post">
	  <input type="hidden" name="action" value="login">
	  <input type="hidden" name="pageNo" value="1">
      <p class="h3 mb-3 font-weight-bold text-center">Beverage Account Log In</p>
      <label for="inputUserName" class="sr-only" 
      	<c:if test="${not empty requestScope.userName}"> value="${requestScope.userName}"</c:if>
      >
      USERNAME(ID_CARD_NO)</label>
      <input type="text" class="form-control" id="inputUserName" name="userName"  placeholder="USERNAME(ID_CARD_NO)" required autofocus>
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password" required>
      <div class="font-weight-bold">
        <p>
          <input type="checkbox" id="readMe"  value="remember-me" style="color:#00CCFF;"> Remember me
	      <c:url value="/MemberDispatchAction.do" var="update">
	     	<c:param name="action" value="memberSelectedView"/>
	      </c:url>
	      <a href="${update}" style="text-decoration:none; color:blue; float:right;">Forgot Password?</a>
        </p>
      </div>
      <button type="submit" class="btn btn-lg btn-primary btn-block" data-toggle="modal">Sign in</button>
      <br/>	
	  <c:if test="${not empty requestScope.loginMsg}">
		<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="loginModalLabel">MESSAGE</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <font color="red">${requestScope.loginMsg}</font>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
	  </c:if>
      <p>
      	<c:url value="/MemberDispatchAction.do" var="create">
	      <c:param name="action" value="memberCreateView"/>
	    </c:url>
		<a class="btn btn-lg btn-success btn-block" href="${create}" role="button" aria-expanded="false" aria-controls="collapseExample">
		     Don't have an account?&nbsp;Sign Up
		</a>
	  </p> 
    </form>
</body>
</html>


