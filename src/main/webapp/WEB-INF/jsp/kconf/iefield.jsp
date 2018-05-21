<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/iexport/fields" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
            <div>
			<label>字段名称：</label><input type="text" value="${query.name }" name="name" size="10">&nbsp;
			<label>字段别名：</label><input type="text" value="${query.alias }" name="alias" size="10">&nbsp;
            </div>
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button> &nbsp;
<%--  			<a href="${ctx}/kconf/iexport/fieldEdit?ieid=${query.ieid}" data-toggle="navtab" data-id="confiefield-create" data-title="新增" class="btn btn-green" data-icon="plus">添加</a>&nbsp; --%>
			<button type="button" data-url="${ctx}/kconf/iexport/fieldEdit?ieid=${query.ieid}" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="confiefield-create" data-title="新增字段配置">新增</button>
			<button data-toggle="doajax"  href="${ctx }/kconf/iexport/reset?tabid=iefield-list&ieid=${query.ieid}" data-confirm-msg="您确定要重置导入字段？重置后导入字段将全部列为不显示" data-icon="reset" style="background: #14CAB4; color: white; float: right;" data-id="user-reset" data-title="一键重置">一键重置</button>
			<c:if test="${iexport.ietype eq '导入' }">
				<button data-toggle="doajax"  href="${ctx }/kconf/iexport/resetform?mid=${iexport.modelid}&ieid=${query.ieid}&tabid=iefield-list" data-confirm-msg="通过对应模型得表单顺序生成导入功能？注意：数据获取和附加数据选项无法自动生成，请自行编辑" data-icon="reset" style="background: #14CAB4; color: white; float: right;" data-id="user-reset" data-title="一键重置">通过模型表单生成</button>
			</c:if>
			<c:if test="${iexport.ietype eq '导出' }">
				<button href="${ctx}/kconf/iexport/toexchange?ieid=${query.ieid}&tabid=iefield-list" data-toggle="dialog"  data-options="{id:'copyData', width:500,height:200,title:'字段覆盖',mask:true,resizable:false,maxable:false,minable:false}" style="background: #14CAB4; color: white; float: right;">覆盖</button>&nbsp;
			</c:if>
			<%--<div class="pull-right">
				<div class="btn-group" style="margin-top:-2px;">
					<button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
					<ul class="dropdown-menu right" role="menu">
						<li><a href="${ctx}/kconf/iefield/export?export=true&xlsid=352&ieid=${query.ieid}" class="export" data-toggle="dialog" data-confirm-msg="确定要导出相应模板吗？" class="blue">导出配置-导出</a></li>
						<li class="divider"></li>
						<li><a href="${ctx}/kconf/iefield/importxls?xlsid=351"  data-toggle="dialog" data-width="500" data-height="200" data-id="dialog-normal" class="green">导出配置-导入</a></li>
					</ul>
				</div>
			</div>--%>
		</div>
	</form>
</div>

<c:if test="${iexport.ietype eq '导出' }">
<div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead> <tr>
				<th >字段编号</th>
				<th >字段名称</th>
				<th >别名</th>
				<th >宽度</th>
				<th >类型</th>
				<th >格式化</th>
				<th style="text-align: center;">是否导出</th>
				<th >顺序</th>
				<th >备注</th>
				<th >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr data-id="${r.iefid }">
					<td>${r.iefid }</td>
					<td>${r.field_name }</td>
					<td>${r.field_alias }</td>
					<td>${r.width }</td>
					<td>${r.type=='1'?'字符串':'数字' }</td>
					<td>${r.format }</td>
					<td style="text-align: center;">
						<c:if test="${r.enabled=='0' }">
							<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=enabled"><span style="color:green;">&radic;</span></a>
						</c:if>
						<c:if test="${r.enabled=='1' }">
						 	<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=enabled"><span style="color:red;">&times;</span></a>
						</c:if>
					</td>
					
					<td class="center">
						<div style="width: 100px;margin: auto;">
							<div style="float: left;width: 10px;margin-left: 20px;">
								<a href="${ctx}/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=sort&val=${r.sort }&type=plus" data-toggle="doajax"><span style="color:green;font-weight:bold;">&plus;</span></a> 
<%-- 								<a href="${ctx}/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=sort&val=${r.sort }&type=plus" data-toggle="doajax" style="color:green;"><i class="fa fa-angle-double-up fa-lg" aria-hidden="true"></i></a> --%>
							</div>
	                   	 	<div style="float: left;width: 50px;text-align: center">
	                   	 		<span style="width: 20px;">${r.sort }</span>
                   	 		</div>
							<div style="float: left;width: 10px;">
								<a href="${ctx}/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=sort&val=${r.sort }&type=minus" data-toggle="doajax"><span style="color:red;font-weight: 700;">&minus;</span></a>
<%-- 								<a href="${ctx}/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=sort&val=${r.sort }&type=minus" data-toggle="doajax" style="color:green;"><i class="fa fa-angle-double-down fa-lg" aria-hidden="true"></i></a> --%>
							</div>
						</div>
					</td>
					
					<td>${r.remark }</td>
					<td>
						<!-- 
						<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}">删除</a>  &nbsp;&nbsp;
 						 -->
 						<a href="${ctx}/kconf/iexport/fieldEdit?id=${r.iefid}"  style="color:green;"  data-toggle="navtab" data-id="iefield-edit" data-title="编辑${r.field_name }" >修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:if>


<c:if test="${iexport.ietype eq '导入' }">
<div class="bjui-pageContent tableContent  white ess-pageContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th class="center">字段序号</th>
				<th >字段名称</th>
				<th >别名</th>
				<th class="center">是否导入</th>
				<th class="center">必包含列？</th>
				<th class="center">内容为空？</th>
				<th class="center">字段顺序</th>
				<th >备注</th>
				<th class="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr data-id="${r.iefid }">
					<td class="center">${r.iefid }</td>
					<td>${r.field_name }</td>
					<td>${r.field_alias }</td>
					
					<td style="text-align: center;">
						<c:if test="${r.enabled=='0' }">
<%-- 							<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=enabled"><span style="color:green"><i class="fa fa-check fa-lg" aria-hidden="true"></i></span></a> --%>
							<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=enabled"><span style="color:green;">&radic;</span></a>
						</c:if>
						<c:if test="${r.enabled=='1' }">
						 	<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=enabled"><span style="color:red;">&times;</span></a>
<%-- 						 	<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=enabled"><span style="color:red"><i class="fa fa-times fa-lg" aria-hidden="true"></i></span></a> --%>
						</c:if>
					</td>
					
					
					
					<td class="center">
						<c:if test="${r.required=='1' }">
							<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=required"><span style="color:green;">&radic;</span></a>
<%-- 							<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=required"><span style="color:green"><i class="fa fa-check fa-lg" aria-hidden="true"></i></span></a> --%>
						</c:if>
						<c:if test="${r.required=='0' }">
						 	<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=required"><span style="color:red;">&times;</span></a>
<%-- 						 	<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=required"><span style="color:red"><i class="fa fa-times fa-lg" aria-hidden="true"></i></span></a> --%>
						</c:if>
					</td>
					<td style="text-align: center;">
						<c:if test="${r.allow_blank=='0' }">
							<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=allow_blank"><span style="color:green;">&radic;</span></a>
<%-- 							<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=allow_blank"><span style="color:green"><i class="fa fa-check fa-lg" aria-hidden="true"></i></span></a> --%>
						</c:if>
						<c:if test="${r.allow_blank=='1' }">
						 	<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=allow_blank"><span style="color:red;">&times;</span></a>
<%-- 						 	<a data-toggle="doajax"   href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=allow_blank"><span style="color:red"><i class="fa fa-times fa-lg" aria-hidden="true"></i></span></a> --%>
						</c:if>
					</td>
				
					<td class="center">
						<div style="width: 100px;margin: auto;">
							<div style="float: left;width: 10px;margin-left: 20px;">
								<a href="${ctx}/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=sort&val=${r.sort }&type=plus" data-toggle="doajax"><span style="color:green;font-weight:bold;">&plus;</span></a> 
<%-- 								<a href="${ctx}/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=sort&val=${r.sort }&type=plus" data-toggle="doajax" style="color:green;"><i class="fa fa-angle-double-up fa-lg" aria-hidden="true"></i></a> --%>
							</div>
	                   	 	<div style="float: left;width: 50px;text-align: center">
	                   	 		<span style="width: 20px;">${r.sort }</span>
                   	 		</div>
							<div style="float: left;width: 10px;">
								<a href="${ctx}/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=sort&val=${r.sort }&type=minus" data-toggle="doajax"><span style="color:red;font-weight: 700;">&minus;</span></a>
<%-- 								<a href="${ctx}/kconf/iexport/fieldDelete?id=${r.iefid}&updatecolumn=sort&val=${r.sort }&type=minus" data-toggle="doajax" style="color:green;"><i class="fa fa-angle-double-down fa-lg" aria-hidden="true"></i></a> --%>
							</div>
						</div>
					</td>
					
					<td>${r.remark }</td>
					<td class="center">
					<!-- 
						<a style="color:red;"  data-toggle="doajax"  data-confirm-msg="确定要删除该行信息吗？" href="${ctx }/kconf/iexport/fieldDelete?id=${r.iefid}">删除</a>  &nbsp;&nbsp;
 					-->
 						<a href="${ctx}/kconf/iexport/fieldEdit?id=${r.iefid}"  style="color:green;"  data-toggle="navtab" data-id="iefield-edit" data-title="编辑${r.field_name }" >修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:if>

<div class="bjui-pageFooter">
	<div class="pages">
		<span>每页&nbsp;</span>
		<div class="selectPagesize">
			<select data-toggle="selectpicker" data-toggle-change="changepagesize">
				<option value="1000">1000</option>
			</select>
		</div>
		<span>&nbsp;条，共 ${page.totalRow } 条</span>
	</div>
	<div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>
