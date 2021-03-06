<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>字典管理</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="td_table_1">
						<span>字典名称：</span>
					</td>
					<td class="td_table_2" colspan="3">
						${dict.name }&nbsp;
					</td>
				</tr>
				<tr>
					<td class="td_table_1">
						<span>显示名称：</span>
					</td>
					<td class="td_table_2" colspan="3">
						${dict.cnName }&nbsp;
					</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align=center width=15% class="td_list_1" nowrap>
						序号
					</td>
					<td align=center width=25% class="td_list_1" nowrap>
						编号
					</td>
					<td align=center width=60% class="td_list_1" nowrap>
						显示名称
					</td>
				</tr>

				<c:forEach var="item" items="${dictItems}" varStatus="s">
					<tr>
						<td class="td_list_2" align=left nowrap>
							${item.orderby }&nbsp;
						</td>
						<td class="td_list_2" align=left nowrap>
							${item.code }&nbsp;
						</td>					
						<td class="td_list_2" align=left nowrap>
							${item.name }&nbsp;
						</td>
					</tr>
				</c:forEach>
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
		</form>
	</body>
</html>
