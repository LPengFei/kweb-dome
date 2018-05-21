<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/menu/child_index" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
			<c:set var="queryCount" value="0"></c:set>
			<c:forEach items="${fields }" var="f">
				<c:set var="listset" value="${f.list_view }"></c:set>
				<c:set var="fset" value="${f.form_view }"></c:set>
				<c:if test="${!empty(f.list_search_op)}">
					<c:set var="queryCount" value="${queryCount + 1 }"></c:set>
					<div><label>${f.field_alias }：</label>
					<input type="text" value="${requestScope[f.field_name] }" name="${f.field_name }"
						data-ds="${ctx}${f.form_data_source }"
						data-toggle="${f.form_view_type }"
						data-chk-style="${f.form_multi_select }"
						data-live-search='${f.form_is_search }'
					>&nbsp;</div>
				</c:if>
			</c:forEach>
			<c:if test="${queryCount gt 0 }">
				<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
			</c:if>
			<button type="button"  data-url="${ctx}/kconf/menu/edit?pid=${pmenuid}&type=child" data-icon="plus" data-toggle="navtab" data-id="menu-child-edit" data-title="新增菜单" style="background: #14CAB4; color: white; float: right;"  >新增</button>
		</div>
	</form>
</div><div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th class="center">序号</th>
				<th>菜单名称</th>
				<th>菜单地址</th>
				<th class="center">排序</th>
				<th class="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
					<td class="center">${s.index + 1 }</td>
					<td>${r.mname }</td>
					<td>${r.murl }</td>
					<td class="center">${r.ord }</td>
					<td class="center">
						<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/menu/delete?id=${r.menuid}">删除</a>  
						&nbsp;&nbsp;
 						<a href="${ctx}/kconf/menu/edit?id=${r.menuid}&type=child"  style="color:green;"  data-toggle="navtab" data-id="menu-child-edit" data-title="编辑菜单" >修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="bjui-pageFooter">
	<div class="pages">
		<span>每页&nbsp;</span>
		<div class="selectPagesize">
			<select data-toggle="selectpicker" data-toggle-change="changepagesize">
				<option value="30">30</option>
				<option value="60">60</option>
				<option value="120">120</option>
				<option value="150">150</option>
			</select>
		</div>
		<span>&nbsp;条，共 ${page.totalRow } 条</span>
	</div>
	<div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>
<script type="text/javascript">
initForm();
</script>
