<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../taglib.jsp" %> 

<style>

#kfield-list-table td.yes button{
	color: green
}
#kfield-list-table td.no button{
	color: red
}

/* 表格输入框，selectpicker居中显示  */
#kfield-list-table td.center input, #kfield-list-table td span{
	text-align: center;
}


</style>
 
 <div class="bjui-pageContent tableContent white ess-pageContent" id="fields_div">
 	<form data-toggle="validate" method="post">
		<table id="kfield-list-table" class="table table-bordered table-hover table-striped table-top" >
			  <thead>
	                <tr>
	                    <th class="center">序号</th>
						<th>名称</th>
						<th>别名</th>
						<th>类型</th>
						<th class="center">列表显示</th>
						<th class="center">显示顺序</th>
						<th class="center">表单输入</th>
						<th class="center">表单排序</th>
						<th class="center">必填</th>
						<th>配置</th>
	                </tr>
	            </thead>
	            <tbody>
	            	<c:forEach items="${rolefields }" var="r" varStatus="s">
	                <tr>
	                    <td class="center">${s.index + 1}</td>
	                    <td>${r.field_name }</td>
	                    <td>${r.field_alias }</td>
	                    <td>${r.type }</td>
	                   
						<td style="text-align: center;">
							<c:if test="${r.list_view=='true' }">
								<a data-toggle="doajax"   href="${ctx }/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=list_view"><span style="color:green;">&radic;</span></a>
							</c:if>
							<c:if test="${r.list_view=='false' }">
							 	<a data-toggle="doajax"   href="${ctx }/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=list_view"><span style="color:red;">&times;</span></a>
							</c:if>
						</td>
						<td class="center">
							<div style="width: 100px;margin: auto;">
								<div style="float: left;width: 10px;margin-left: 20;">
									<a href="${ctx}/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=list_sort&val=${r.list_sort }&type=plus" data-toggle="doajax"><span style="color:green;font-weight:bold;">&plus;</span></a> 
								</div>
		                   	 	<div style="float: left;width: 50px;text-align: center">
		                   	 		<span style="width: 20px;">${r.list_sort }</span>
	                   	 		</div>
								<div style="float: left;width: 10px;">
									<a href="${ctx}/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=list_sort&val=${r.list_sort }&type=minus" data-toggle="doajax"><span style="color:red;font-weight: 700;">&minus;</span></a>
								</div>
							</div>
						</td>
						<td style="text-align: center;">
							<c:if test="${r.form_view=='true' }">
								<a data-toggle="doajax"   href="${ctx }/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=form_view"><span style="color:green;">&radic;</span></a>
							</c:if>
							<c:if test="${r.form_view=='false' }">
							 	<a data-toggle="doajax"   href="${ctx }/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=form_view"><span style="color:red;">&times;</span></a>
							</c:if>
						</td>
						
						<td class="center">
							<div style="width: 100px;margin: auto;">
								<div style="float: left;width: 10px;margin-left: 20;">
									<a href="${ctx}/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=form_sort&val=${r.form_sort }&type=plus" data-toggle="doajax"><span style="color:green;font-weight:bold;">&plus;</span></a> 
								</div>
		                   	 	<div style="float: left;width: 50px;text-align: center">
		                   	 		<span style="width: 20px;">${r.form_sort }</span>
	                   	 		</div>
								<div style="float: left;width: 10px;">
									<a href="${ctx}/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=form_sort&val=${r.form_sort }&type=minus" data-toggle="doajax"><span style="color:red;font-weight: 700;">&minus;</span></a>
								</div>
							</div>
						</td>
						 <td style="text-align: center;">
							<c:if test="${r.form_required=='true' }">
								<a data-toggle="doajax"   href="${ctx }/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=form_required"><span style="color:green;">&radic;</span></a>
							</c:if>
							<c:if test="${r.form_required=='false' }">
							 	<a data-toggle="doajax"   href="${ctx }/kconf/field/fieldChange?id=${r.mfid}&updatecolumn=form_required"><span style="color:red;">&times;</span></a>
							</c:if>
						</td>
						<td  style="padding:0px 5px;">
							<a href="${ctx }/kconf/field/delete?mfid=${r.mfid}" class="row-del" style="color:red;" data-confirm-msg="确定要删除该行信息吗？">删除</a>
							<a href="${ctx }/kconf/field/edit?mfid=${r.mfid}" style="color:green;" class="edit" data-toggle="navtab" data-id="app-edit" data-title="${r.field_name }编辑">编辑</a>
					    </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div> 