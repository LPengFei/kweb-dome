<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>资源管理</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/js/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/security/resource" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNumber}"/>
		<table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					资源管理
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">
					<span>资源名称：</span>
				</td>
				<td class="td_table_2" colspan="3">
					<input type="text" class="input_240" name="name" value="${name}"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
				<shiro:hasPermission name="RESOURCEEDIT">
					<input type='button' onclick="addNew('${ctx}/security/resource/add')" class='button_70px' value='新建'/>
				</shiro:hasPermission>
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align=center width=10% class="td_list_1" nowrap>
					资源名称
				</td>
				<td align=center width=60% class="td_list_1" nowrap>
					资源值
				</td>
				<td align=center width=15% class="td_list_1" nowrap>
					所属菜单
				</td>
				<td align=center width=15% class="td_list_1" nowrap>
					操作
				</td>				
			</tr>
			<c:forEach items="${page.list}" var="resource">
				<tr>
					<td class="td_list_2" align=left nowrap>
						${resource.name}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						${resource.source}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						${resource.menuName}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
					<shiro:hasPermission name="ORGDELETE">
						<a href="${ctx}/security/resource/delete/${resource.id }" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="RESOURCEDELETE">
						<a href="${ctx}/security/resource/edit/${resource.id }" class="btnEdit" title="编辑">编辑</a>
					</shiro:hasPermission>
						<a href="${ctx}/security/resource/view/${resource.id }" class="btnView" title="查看">查看</a>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNumber}" totalPages="${page.totalPage }" totalRecords="${page.totalRow }"/>
		</table>
	</form>
	</body>
</html>
