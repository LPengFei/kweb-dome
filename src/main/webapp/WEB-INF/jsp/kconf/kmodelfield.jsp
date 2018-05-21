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

<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx }/kconf/field/<c:if test="${sort_type eq 'list' }">list</c:if><c:if test="${sort_type eq 'form' }">form</c:if>" method="post">
		<input type="hidden" name="pageNumber" value="${pageNumber}">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="orderField" value="${orderField}">
		<input type="hidden" name="orderDirection" value="${orderDirection}">
		<input type="hidden" name="mid" value="${mid}">
		<div class="bjui-searchBar ess-searchBar">
			<div><label>字段名称：</label><input type="text" value="${fieldname }" name="fieldname" size="10">&nbsp;</div>
			<div><label>字段别名：</label><input type="text" value="${fieldalias }" name="fieldalias" size="10">&nbsp; </div>
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>

			<button type="button"  data-url="${ctx }/kconf/field/edit?mid=${mid}" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="user-add" data-title="新增模型">新增</button>
			<button data-toggle="doajax"  href="${ctx }/kconf/field/reset?mid=${mid}&sort_type=${sort_type}" data-icon="reset" style="background: #14CAB4; color: white; float: right;" data-id="user-reset" data-title="一键重置">一键重置</button>
			<%--<div class="pull-right">
			<div class="btn-group" style="margin-top:-2px;">
				<button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
				<ul class="dropdown-menu right" role="menu">
						<li><a href="${ctx}/kconf/field/export?export=true&xlsid=350" class="export" data-toggle="dialog" data-confirm-msg="确定要导出相应模板吗？" class="blue">模板-导出</a></li>
						<li class="divider"></li>
					<li><a href="${ctx}/kconf/field/importxls?xlsid=349"  data-toggle="dialog" data-width="500" data-height="200" data-id="dialog-normal" class="green">模板-导入</a></li>

				</ul>
			</div>
			</div>--%>
		</div>
	</form>
</div>
 
 <div class="bjui-pageContent tableContent white ess-pageContent" id="fields_div">
 	<form data-toggle="validate" method="post">
		<table id="kfield-list-table" class="table table-bordered table-hover table-striped table-top" >
			  <thead>
	                <tr data-idname="f.mfid">
	                    <th class="center">序号</th>
						<th data-order-field="field_name">名称</th>
						<th data-order-field="field_alias">别名</th>
						<th data-order-field="type">类型</th>
						<c:if test="${sort_type eq 'list' }">
						<th class="center" data-order-field="list_view">列表显示</th>
						<th class="center" data-order-field="list_sort">显示顺序</th>
						</c:if>
						<c:if test="${sort_type eq 'form' }">
						<th class="center"  data-order-field="is_lock">加锁</th>
						<th class="center"  data-order-field="form_view">表单输入</th>
						<th class="center"  data-order-field="form_sort">表单排序</th>
						<th class="center"  data-order-field="form_required">必填</th>
						<th class="center" >表单换行</th>
						</c:if>
						<th>配置</th>
	                </tr>
	            </thead>
	            <tbody>
	            	<c:forEach items="${page.list }" var="r" varStatus="s">
	                <tr data-id="${r.mfid }">
	                    <td class="center">${s.index + 1}</td>
	                    <td>${r.field_name }</td>
	                    <td>${r.field_alias }</td>
	                    <td>${r.type }</td>
	                   	<c:if test="${sort_type eq 'list' }">
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
								<div style="float: left;width: 10px;margin-left: 20px;">
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
						</c:if>
						<c:if test="${sort_type eq 'form' }">
							<c:if test="${r.is_lock eq '1'}">
							<td style="text-align: center;background-color:red">
								 <span style="color: #fff">是</span>
							</td>
							</c:if>
							<c:if test="${r.is_lock != '1'}">
								<td style="text-align: center;">
									<span>否</span>
								</td>
							</c:if>

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
								<div style="float: left;width: 10px;margin-left: 20px;">
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
						<td style="text-align: center;">
							<c:choose>
								<c:when test="${fn:contains(r.form_style, 'clear:left') }">
									<a data-toggle="doajax"   href="${ctx }/kconf/field/formNewLine?id=${r.mfid}&newline=false"><span style="color:green;">&radic;</span></a>
								</c:when>
								<c:otherwise>
									<a data-toggle="doajax"   href="${ctx }/kconf/field/formNewLine?id=${r.mfid}&newline=true"><span style="color:red;">&times;</span></a>
								</c:otherwise>
							</c:choose>
						</td>
						</c:if>
						<td  style="padding:0px 5px;">
							<a href="${ctx }/kconf/field/delete?mfid=${r.mfid}" class="row-del" style="color:red;" data-toggle="doajax" data-confirm-msg="确定要删除该行信息吗？">删除</a>
							<a href="${ctx }/kconf/field/edit?mfid=${r.mfid}" style="color:green;" class="edit" data-toggle="navtab" data-id="app-edit" data-title="${r.field_name }编辑">编辑</a>
					    </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>
<div class="bjui-pageFooter white ess-pageContent ">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="20">20</option>
                <option value="30">30</option>
                <option value="60">60</option>
                <option value="120">120</option>
                <option value="150">150</option>
            </select>
        </div>
        <span>&nbsp;条，共 ${page.totalRow } 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15">
    </div>
</div> 

<script type="text/javascript">
	function list_sort_update(that,id){
		$.ajax({
			type: "post",
			url: "${ctx }/kconf/field/list_sort_update?name="+$(that).attr("name")+"&value="+$(that).val()+"&mfid="+id,
			success: function(data){
				$(this).navtab({id:'fields-list', url:'${ctx }/kconf/field?mid='+data.mid,title:data.mname+'配置字段' , fresh:true});
			}
		});
	    return true
	}
	
	
	$.CurrentNavtab.on(BJUI.eventType.afterInitUI, function(e){
		//页面初始化之后，重新绑定 编辑按钮 点击事件
		$(e.target).find(".edit").click(function(){
			$(this).attr("data-toggle", "navtab");
		});
	});
	
	//双击编辑行，隐藏原编辑按钮
	$('#kfield-list-table tbody tr', $.CurrentNavtab).dblclick(function(){
//		$(this).find('td:last [data-toggle="doedit"]').show().nextAll().hide();
		$(this).find('td:last .edit').click();
	});
	
	//单击完成后，提交修改，显示原来的编辑按钮
	$("[data-toggle=doedit]", $.CurrentNavtab).on("click",function(){
		$(this).hide().nextAll().show();
		//刷新页面
		setTimeout(function() { currentNavtab("#pagerForm").submit() }, 100);
	});
	
	currentNavtab("table#kfield-list-table td[data-val]").each(function(){
		if($(this).data("val") == '1'){
			$(this).addClass("yes");
		}else{
			$(this).addClass("no");
		}
	});
	
</script>