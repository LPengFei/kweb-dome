<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>委托授权管理</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/snaker/surrogate/save" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					委托授权管理
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">
						<span>流程名称：</span>
					</td>
					<td class="td_table_2" colspan="3">
						<select class="input_select" id="processName" name="surrogate.processName">
							<option value='' selected>------请选择------</option>
							<c:forEach items="${processNames}" var="name">
								<option value='${name }' ${surrogate.processName == name ? 'selected' : '' }>${name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">
						<span>授权人：</span>
					</td>
					<td class="td_table_2">
						<input type="text" class="input_240" id="operator" name="surrogate.operator" />
					</td>
					<td class="td_table_1">
						<span>代理人：</span>
					</td>
					<td class="td_table_2">
						<input type="text" class="input_240" id="surrogate" name="surrogate.surrogate" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">
						<span>开始时间：</span>
					</td>
					<td class="td_table_2">
						<input type="text" class="input_240" id="sdate" name="surrogate.sdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly/>
					</td>
					<td class="td_table_1">
						<span>结束时间：</span>
					</td>
					<td class="td_table_2">
						<input type="text" class="input_240" id="edate" name="surrogate.edate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly/>
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="submit" class="button_70px" name="submit" value="提交">
						&nbsp;&nbsp;
						<input type="button" class="button_70px" name="reback" value="返回"
							onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>