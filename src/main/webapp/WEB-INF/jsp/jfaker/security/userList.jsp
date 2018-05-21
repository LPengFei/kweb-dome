<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>账号管理</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/js/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/security/user" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNumber}"/>
		<table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					账号管理
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">
					<span>账号：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="user.username" value="${user.username}"/>
				</td>
				<td class="td_table_1">
					<span>姓名：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="user.fullname" value="${user.fullname}"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="left">
				<shiro:hasPermission name="USEREDIT">
					<input type='button' onclick="addNew('${ctx}/security/user/add')" class='button_70px' value='新建'/>
				</shiro:hasPermission>
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align=center width=20% class="td_list_1" nowrap>
					账号
				</td>
				<td align=center width=20% class="td_list_1" nowrap>
					姓名
				</td>
				<td align=center width=30% class="td_list_1" nowrap>
					是否可用
				</td>
				<td align=center width=20% class="td_list_1" nowrap>
					部门
				</td>
				<td align=center width=10% class="td_list_1" nowrap>
					操作
				</td>				
			</tr>
			<c:forEach items="${page.list}" var="user">
				<tr>
					<td class="td_list_2" align=left nowrap>
						${user.username}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						${user.fullname}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						<frame:select name="enabled" type="select" configName="yesNo" value="${user.enabled}" displayType="1"/>
						&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
						${user.orgName}&nbsp;
					</td>
					<td class="td_list_2" align=left nowrap>
					<shiro:hasPermission name="USERDELETE">
						<a href="${ctx}/security/user/delete/${user.id }" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="USEREDIT">
						<a href="${ctx}/security/user/edit/${user.id }" class="btnEdit" title="编辑">编辑</a>
					</shiro:hasPermission>
						<a href="${ctx}/security/user/view/${user.id }" class="btnView" title="查看">查看</a>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNumber}" totalPages="${page.totalPage }" totalRecords="${page.totalRow }"/>
		</table>
	</form>
	</body>
</html>