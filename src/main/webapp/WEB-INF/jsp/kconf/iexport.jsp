<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/iexport" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
            <div>
			<label>配置名称：</label><input type="text" value="${query.iename }" name="iename" size="10">&nbsp;
			<label>导出类型：</label><input type="text" value="${query.ietype }" name="ietype" size="10">&nbsp;
            </div>
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button> &nbsp;
			<button type="button"  data-url="${ctx}/kconf/iexport/edit" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="confiexport-create" data-title="新增Excel配置">新增</button>
			<%--<div class="pull-right">
				<div class="btn-group" style="margin-top:-2px;">
					<button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
					<ul class="dropdown-menu right" role="menu">
						<li><a href="${ctx}/kconf/iexport/export?export=true&xlsid=354" class="export" data-toggle="dialog" data-confirm-msg="确定要导出相应模板吗？" class="blue">导出配置-导出</a></li>
						<li class="divider"></li>
						<li><a href="${ctx}/kconf/iexport/importxls?xlsid=353"  data-toggle="dialog" data-width="500" data-height="200" data-id="dialog-normal" class="green">导出配置-导入</a></li>

					</ul>
				</div>
			</div>--%>
		</div>
	</form>
</div><div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th>配置ID</th>
				<th>配置名称</th>
				<th>类型</th>
				<th>导出表</th>
				<th>对应模型(Modelid)</th>
				<th>当前状态</th>
				<th>导出Excel标题</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr data-id="r.ieid">
					<td>${r.ieid }</td>
					<td>${r.iename }</td>
					<td>${r.ietype }</td>
					<td>${r.ietable}</td>
					<td>${r.mname}(${r.modelid })</td>
					
					<td>
						<c:if test="${r.enabled eq '0' }">正常</c:if>
						<c:if test="${r.enabled eq '1' }"><span style="color:red;">已被禁用</span></c:if>
					</td>
					<td>${r.title}</td>
					<td>
						<c:if test="${r.enabled eq '0' }">
							<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要禁用本记录吗？" href="${ctx }/kconf/iexport/delete?id=${r.ieid}&enabled=1">禁用</a>  &nbsp;&nbsp;
						</c:if>
						<c:if test="${r.enabled eq '1' }">
							<a style="color:green;"  data-toggle="doajax"  data-confirm-msg="确定要启用本记录吗？" href="${ctx }/kconf/iexport/delete?id=${r.ieid}&enabled=0">启用</a>  &nbsp;&nbsp;
						</c:if>
 						<a href="${ctx}/kconf/iexport/edit?id=${r.ieid}"  style="color:green;"  data-toggle="navtab" data-id="iexport-edit" data-title="编辑" >修改</a>  &nbsp;&nbsp;
						<a href="${ctx}/kconf/iexport/fields?ieid=${r.ieid}" data-toggle="navtab" data-id="iefield-list" data-title="${r.iename }${r.ietype }字段配置"  >字段配置</a>
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
<script>
	initForm();
</script>