<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../taglib.jsp" %> 

<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx }/kconf/model" method="post">
		<input type="hidden" name="pageNumber" value="${pageNumber}">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="orderField" value="${orderField}">
		<input type="hidden" name="orderDirection" value="${orderDirection}">
		<div class="bjui-searchBar ess-searchBar">
			<div><label>模型名称：</label><input type="text" value="${mname }" name="mname" size="10">&nbsp;</div>
			<div><label>对应数据表：</label><input type="text" value="${mtable }" name="mtable" size="10">&nbsp; </div>
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
			<button type="button"  data-url="${ctx}/kconf/model/edit" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="role-create" data-title="新增模型">新增</button>
			<button data-toggle="doajax"  href="${ctx }/kconf/model/clearBusiness" data-confirm-msg="请先备份数据库，再清除业务表？" data-icon="clear" style="background: #14CAB4; color: white; float: right;display: none" data-id="user-clear" data-title="清除业务表">清除业务表</button>
			<%--<div class="pull-right">
				<div class="btn-group" style="margin-top:-2px;">
					<button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
					<ul class="dropdown-menu right" role="menu">
						<li><a href="${ctx}/kconf/model/export?export=true&xlsid=360" class="export" data-toggle="dialog" data-confirm-msg="确定要导出相应模板吗？" class="blue">模型-导出</a></li>
						<li class="divider"></li>
						<li><a href="${ctx}/kconf/model/importxls?xlsid=359"  data-toggle="dialog" data-width="500" data-height="200" data-id="dialog-normal" class="green">模型-导入</a></li>

					</ul>
				</div>
			</div>--%>
		</div>
	</form>
</div>
 
 <div class="bjui-pageContent tableContent white ess-pageContent">
	<table data-toggle="tablefixed"   >
		 <thead>
			<tr>
			    <th width="50px;" class="center">ModelID</th>
			    <th>模型名称</th>
			    <th>对应数据表</th>
				<th>对应菜单</th>
				<th>备注</th>
			    <th>配置</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr>
				    <td width="50px;" class="center">${r.mid }</td>
					<td>${r.mname }</td>
					<td>${r.mtable }</td>
					<td>${r.menu_name}</td>
					<td>${r.remark}</td>
					<td  style="padding:0px 10px;">
						<a href="${ctx }/kconf/model/delete?mid=${r.mid}" style="color:red;"  data-toggle="doajax" data-confirm-msg="确定要删除该行信息吗？">删除</a>&nbsp;
						<a href="${ctx}/kconf/model/edit?mid=${r.mid}" style="color:green;"  data-toggle="navtab" data-id="model-edit-${r.mid}" data-title="${r.mname }编辑"  >编辑</a> &nbsp;
						<a href="${ctx}/kconf/model/copy/${r.mid}" data-toggle="doajax" data-confirm-msg="复制模型配置及字段配置">复制</a>&nbsp;
						<a href="${ctx}/kconf/model/toexchange?mid=${r.mid}&tabid=model-list" data-toggle="dialog"  data-options="{id:'copyData', width:500,height:200,title:'字段覆盖',mask:true,resizable:false,maxable:false,minable:false}">覆盖</a>&nbsp;
						<a href="${ctx}/kconf/field/list?mid=${r.mid}" data-toggle="navtab" data-id="fields-list-${r. mid}" data-title="${r.mname}_列表字段配置">列表字段配置</a>
						<a href="${ctx}/kconf/field/form?mid=${r.mid}" data-toggle="navtab" data-id="fields-form-${r.mid}" data-title="${r.mname}_表单字段配置">表单字段配置</a>
						<a href="${ctx}/kconf/field/role?mid=${r.mid}" data-toggle="navtab" data-id="fields-view-${r.mid}" data-title="${r.mname}_角色显示配置">角色显示配置</a>
						<a href="${ctx}/kconf/modellink?mid=${r.mid}" data-toggle="navtab" data-id="modellinks-list-${r.mid}" data-title="${r.mname}_列表模型链接">模型链接</a>

				    </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
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