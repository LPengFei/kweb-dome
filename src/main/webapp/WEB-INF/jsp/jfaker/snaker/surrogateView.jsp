<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>委托授权管理</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="td_table_1">
						<span>流程名称：</span>
					</td>
					<td class="td_table_2" colspan="3">
						${surrogate.processName }&nbsp;
					</td>
				</tr>
 				<tr>
					<td class="td_table_1">
						<span>授权人：</span>
					</td>
					<td class="td_table_2">
						${surrogate.operator }&nbsp;
					</td>
					<td class="td_table_1">
						<span>代理人：</span>
					</td>
					<td class="td_table_2">
						${surrogate.surrogate }&nbsp;
					</td>
				</tr>
 				<tr>
					<td class="td_table_1">
						<span>开始时间：</span>
					</td>
					<td class="td_table_2">
						${surrogate.sdate }&nbsp;
					</td>
					<td class="td_table_1">
						<span>结束时间：</span>
					</td>
					<td class="td_table_2">
						${surrogate.edate }&nbsp;
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
		</form>
	</body>
</html>
