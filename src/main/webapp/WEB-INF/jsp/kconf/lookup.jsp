<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/lookup" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
            <div>
			<label>键：</label><input type="text" value="${query.k }" name="k" size="10">&nbsp;
			<!-- <label>类型：</label> --><input type="hidden" value="${query.type }" name="type" size="10">&nbsp;
            </div>
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button> &nbsp;
			<button type="button"  data-url="${ctx}/kconf/lookup/edit?ltid=${query.type }" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="lookup-create" data-title="新增lookup">新增</button>
		</div>
	</form>
</div><div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed">
		<thead>
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>所属类型</th>
				<th>键(数据库存储)</th>
				<th style="width: 60%">值(显示)</th>
				<th>父类ID</th>
				<th>顺序</th>
				<th>是否默认</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
					<td>${s.count}</td>
					<td>${r.lkid }</td>
					<td>${r.ltid }</td>
					<td>${r.lkey }</td>
					<td style="width: 60%">${r.lvalue }</td>
					<th>${r.pid }</th>
					<th>${r.sort }</th>
					<th>${r.is_check }</th>
					<td>${r.remark }</td>
					<td>
						<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/lookup/delete?id=${r.lkid}">删除</a>  &nbsp;&nbsp;
 						<a href="${ctx}/kconf/lookup/edit?id=${r.lkid}"  style="color:green;"  data-toggle="navtab" data-id="lookup-edit" data-title="编辑用户" >修改</a>
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
