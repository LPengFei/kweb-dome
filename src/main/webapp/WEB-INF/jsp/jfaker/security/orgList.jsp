<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>部门管理</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/js/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/security/org?lookup=${lookup }" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNumber}"/>
		<table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					部门管理
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">
					<span>部门名称：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="name" value="${name}"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="left">
				<c:choose>
					<c:when test="${empty lookup}">
					<shiro:hasPermission name="ORGEDIT">
					<input type='button' onclick="addNew('${ctx}/security/org/add')" class='button_70px' value='新建'/>
					</shiro:hasPermission>
					</c:when>
					<c:otherwise>
					<input type='button' onclick="javascript:bringback('','')" class='button_70px' value='重置'/>
					</c:otherwise>
				</c:choose>
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align=center width=45% class="td_list_1" nowrap>
					部门名称
				</td>
				<td align=center width=45% class="td_list_1" nowrap>
					上级部门名称
				</td>
				<td align=center width=10% class="td_list_1" nowrap>
					操作
				</td>				
			</tr>
			<c:forEach items="${page.list}" var="org">
				<tr>
					<td class="td_list_2" align=left nowrap>
						${org.name}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						${org.parentName}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
				    <c:choose>
				    <c:when test="${empty lookup}">
				    <shiro:hasPermission name="ORGDELETE">
						<a href="${ctx}/security/org/delete/${org.id }" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="ORGEDIT">
						<a href="${ctx}/security/org/edit/${org.id }" class="btnEdit" title="编辑">编辑</a>
					</shiro:hasPermission>
						<a href="${ctx}/security/org/view/${org.id }" class="btnView" title="查看">查看</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0)" class="btnSelect" title="选择" onclick="bringback('${org.id}','${org.name }')">选择</a>
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNumber}" totalPages="${page.totalPage }" totalRecords="${page.totalRow }" lookup="${lookup }"/>
		</table>
	</form>
	</body>
</html>
