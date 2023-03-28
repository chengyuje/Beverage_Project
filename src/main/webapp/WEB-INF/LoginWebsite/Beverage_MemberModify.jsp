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
<title>Beverage Member Modify</title>
<script type="text/javascript">
	function memberSelected(){
    	document.memberSelectedForm.action.value = "memberSelectedView";
    	document.memberSelectedForm.submit();
	}
	$(document).ready(function(){
		$(updateModal).modal('show');
	});
</script>
</head>
<body class="BML_image" link="#0033FF" alink="#0033FF" vlink="#0033FF">
	<form name="memberSelectedForm" class="form-signin" action="MemberDispatchAction.do" method="post">
	  <input type="hidden" name="action" value="updateBeverageMember">
      <p class="h3 mb-3 font-weight-bold text-center">Member Update</p>
      <select class="custom-select" name="userName" onchange="memberSelected();">
				<option value="">---請選擇---</option>
				<c:forEach items="${requestScope.queryAllMember}" var="member">
					<option value="${member.userName}" <c:if test="${member.userName eq modifyMember.userName}">selected</c:if>>
						${member.userName}
					</option>
				</c:forEach>
	  </select>
      <label for="inputCustomerName" class="sr-only">CUSTOME NAME</label>
      <input type="text" class="form-control" id="inputCustomerName" name="customerName" value="${requestScope.modifyMember.customerName}" placeholder="CUSTOME NAME" required autofocus>
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password" required>
      <button type="submit" class="btn btn-lg btn-primary btn-block">Update</button>
      <br/>
      <p>
		<a class="btn btn-lg btn-success btn-block"  href="BeverageMemberLogin.jsp" role="button" aria-expanded="false" aria-controls="collapseExample">
		     Have an account?&nbsp;Sign In
		</a>
	  </p>
    </form>
    <c:if test="${not empty sessionScope.updateMemberMsg}">
		<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="updateModalLabel">MESSAGE</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <font color="blue">${sessionScope.updateMemberMsg}</font>
		        <% session.removeAttribute("updateMemberMsg"); %>
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