<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>角色管理</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="td_table_1">
						<span>角色名称：</span>
					</td>
					<td class="td_table_2" colspan="3">
						${role.name }&nbsp;
					</td>
				</tr>
				<tr>
					<td class="td_table_1">
						<span>角色描述：</span>
					</td>
					<td class="td_table_2" colspan="3">
						${role.description }&nbsp;
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="button" class="button_70px" name="reback" value="返回"
							onclick="history.back()">
					</td>
				</tr>
			</table>
			
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align=center width=100% class="td_list_1" nowrap>
						<a href="javascript:sort('name','asc')">权限名称</a>
					</td>
				</tr>
				<c:forEach items="${authorities}" var="authority">
					<tr>
						<td class="td_list_2" align=left nowrap>
							${authority.name}&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</body>
</html>
