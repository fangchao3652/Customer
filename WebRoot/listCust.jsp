<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  		function checkAll(allC){
  			var otherCs = document.getElementsByName("delId");
  			for(var i=0;i<otherCs.length;i++){
  				otherCs[i].checked = allC.checked;
  			}
  		}
  	</script>
  </head>
  <body style="text-align: center;">
  <form action="${pageContext.request.contextPath }/servlet/FindCustByCondServlet" method="POST">
  		客户姓名:<input type="text" name="name" value="${param.name }"/>
  		客户性别:
  				<input type="radio" name="gender" value="男"
  						<c:if test="${param.gender=='男' }">checked='checked'</c:if>
  					/>男
  					<input type="radio" name="gender" value="女"
  						<c:if test="${param.gender=='女' }">checked='checked'</c:if>
  					/>女
  					<input type="radio" name="gender" value=""/>无
  		客户类型:
	  			<select name="type">
					<option value=""
						<c:if test="${param.type=='' }">
							 selected="selected"
						</c:if>
					>不选择</option>
					<option value="钻石客户"
						<c:if test="${param.type=='钻石客户' }">
							 selected="selected"
						</c:if>
					>钻石客户</option>
					<option value="白金客户"
						<c:if test="${param.type=='白金客户' }">
							 selected="selected"
						</c:if>
					>白金客户</option>
					<option value="黄金客户"
						<c:if test="${param.type=='黄金客户' }">
							 selected="selected"
						</c:if>
					>黄金客户</option>
					<option value="白银客户"
						<c:if test="${param.type=='白银客户' }">
							 selected="selected"
						</c:if>
					>白银客户</option>
					<option value="青铜客户"
						<c:if test="${param.type=='青铜客户' }">
							 selected="selected"
						</c:if>
					>青铜客户</option>
					<option value="黑铁客户"
						<c:if test="${param.type=='黑铁客户' }">
							 selected="selected"
						</c:if>
					>黑铁客户</option>
					<option value="没牌客户"
						<c:if test="${param.type=='没牌客户' }">
							 selected="selected"
						</c:if>
					>没牌客户</option>
				</select>
		<input type="submit" value="条件查询客户"/>
  	</form>
  	<form action="${pageContext.request.contextPath}/servlet/BatchDelServlet" method="POST">
  	<table border="1" width="100%">
  		<tr>
  			<th><input type="checkbox" onclick="checkAll(this)"/>全选</th>
  			<th>客户姓名</th>
  			<th>客户性别</th>
  			<th>出生日期</th>
  			<th>手机号码</th>
  			<th>电子邮箱</th>
  			<th>客户爱好</th>
  			<th>客户类型</th>
  			<th>描述信息</th>
  			<th>修改</th>
  			<th>删除</th>
  		</tr>
  		<c:forEach items="${requestScope.list}" var="cust">
	  		<tr>
	  			<td><input type="checkbox" name="delId" value="${cust.id }" /></td>
	  			<td><c:out value="${cust.name }"/></td>
	  			<td><c:out value="${cust.gender }"/></td>
	  			<td><c:out value="${cust.birthday }"/></td>
	  			<td><c:out value="${cust.cellphone }"/></td>
	  			<td><c:out value="${cust.email }"/></td>
	  			<td><c:out value="${cust.preference }"/></td>
	  			<td><c:out value="${cust.type }"/></td>
	  			<td><c:out value="${cust.description }"/></td>
	  			<td><a href="${pageContext.request.contextPath }/servlet/CustInfoServlet?id=${cust.id }">修改</a></td>
	  			<td><a href="${pageContext.request.contextPath }/servlet/DelCustServlet?id=${cust.id }">删除</a></td>
	  		</tr>
  		</c:forEach>
  	</table>
  	<input type="submit" value="批量删除"/>
  	</form>
  </body>
</html>
