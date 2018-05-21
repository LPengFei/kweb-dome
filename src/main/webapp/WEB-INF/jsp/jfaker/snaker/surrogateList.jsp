<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>委托授权管理</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/js/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/snaker/surrogate" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
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
					<input type="text" class="input_240" name="name" value="${name }"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<input type='button' onclick="addNew('${ctx}/snaker/surrogate/add')" class='button_70px' value='新建'/>
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align=center width=20% class="td_list_1" nowrap>
					流程名称
				</td>
				<td align=center width=20% class="td_list_1" nowrap>
					授权人
				</td>
				<td align=center width=20% class="td_list_1" nowrap>
					代理人
				</td>
				<td align=center width=20% class="td_list_1" nowrap>
					开始时间
				</td>
				<td align=center width=20% class="td_list_1" nowrap>
					结束时间
				</td>
				<td align=center width=10% class="td_list_1" nowrap>
					操作
				</td>				
			</tr>
			<c:forEach items="${page.result}" var="surrogate">
				<tr>
					<td class="td_list_2" align=left nowrap>
						${surrogate.processName}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						${surrogate.operator}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						${surrogate.surrogate}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						${surrogate.sdate}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						${surrogate.edate}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						<a href="${ctx}/snaker/surrogate/delete/${surrogate.id }" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
						<a href="${ctx}/snaker/surrogate/edit/${surrogate.id }" class="btnEdit" title="编辑">编辑</a>
						<a href="${ctx}/snaker/surrogate/view/${surrogate.id }" class="btnView" title="查看">查看</a>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }"/>
		</table>
	</form>
	</body>
</html>