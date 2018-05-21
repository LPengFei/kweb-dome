<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>权限管理</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="${ctx}/snaker/styles/wbox/wbox/wbox.css" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/snaker/styles/wbox/wbox.js"></script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/security/authority/update" method="post">
			<input type="hidden" name="authority.id" id="id" value="${authority.id }"/>
		<table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					权限管理
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0" style="margin-top: 0px">
				<c:if test="${not empty nameMsg }">
				<tr>
					<td class="td_table_1">
						<span>错误信息：</span>
					</td>
					<td class="td_table_2" colspan="3">
						<font color="red">${nameMsg }</font>
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_table_1">
						<span>权限名称：</span>
					</td>
					<td class="td_table_2" colspan="3">
						<input type="text" class="input_240" id="name" name="authority.name"
							value="${authority.name }" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">
						<span>权限描述：</span>
					</td>
					<td class="td_table_2" colspan="3">
						<input type="text" class="input_240" id="description" name="authority.description"
							value="${authority.description }" />
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
			
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align=center width=10% class="td_list_1" nowrap>
						<input type="checkbox" title="全选" id="selectAll">
					</td>
					<td align=center width=45% class="td_list_1" nowrap>
						<a href="javascript:sort('name','asc')">资源名称</a>
					</td>
					<td align=center width=45% class="td_list_1" nowrap>
						<a href="javascript:sort('source','asc')">资源</a>
					</td>
				</tr>
				<c:forEach items="${resources}" var="resource">
					<tr>
						<td class="td_list_2" align=center nowrap>
							<label class="checkbox">
								<input type="checkbox" name="orderIndexs" value="${resource.id}" ${resource.selected== 1 ? 'checked' : '' }>
							</label>
						</td>
						<td class="td_list_2" align=left nowrap>
							${resource.name}&nbsp;
						</td>
						<td class="td_list_2" align=left nowrap>
							${resource.source}&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</body>
	
	<script type="text/javascript">
	$("#selectAll",$.CurrentNavtab).click(function(){
		var status = $(this).attr("checked");
		if(status) {
			$("input[name='orderIndexs']",$.CurrentNavtab).attr("checked",true);
		} else {
			$("input[name='orderIndexs']",$.CurrentNavtab).attr("checked",false);
		}
	    
	});
	</script>
</html>