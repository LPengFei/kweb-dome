<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../taglib.jsp" %> 


<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx }/kconf/modellink" method="post">
		<input type="hidden" name="pageNumber" value="${pageNumber}">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="mid" value="${mid}">
		<div class="bjui-searchBar ess-searchBar">
			<div><label>名称：</label><input type="text" value="${label }" name="label" size="10">&nbsp;</div>
			<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>
			<button type="button"  data-url="${ctx }/kconf/modellink/edit?mid=${mid}" data-icon="plus" data-toggle="navtab" style="background: #14CAB4; color: white; float: right;" data-id="user-add" data-title="新增模型链接">新增</button>
		</div>
	</form>
</div>
 
 <div class="bjui-pageContent tableContent white ess-pageContent" id="fields_div">
 	<form data-toggle="validate" method="post">
		<table id="modellinks-list-table" class="table table-bordered table-hover table-striped table-top" >
			  <thead>
	                <tr data-idname="r.mid">
	                    <th class="center" width="2%">序号</th>
						<th class="center" width="8%">名称</th>
						<th width="17%">地址</th>
						<th width="10%">样式</th>
						<th width="40%">其他</th>
						<th width="3%">位置</th>
						<th width="2%">顺序</th>
						<th width="8%">备注</th>
						<th class="center" width="10%">操作</th>
	                </tr>
	            </thead>
	            <tbody>
	            	<c:forEach items="${page.list }" var="r" varStatus="s">
	                <tr data-id="${r.id }">
	                    <td class="center">${s.index + 1}</td>
	                    <td class="center">${r.label }</td>
	                    <td>${r.link }</td>
	                    <td>${r.style }</td>
	                    <td>${r.other }</td>
	                    <td>${r.position }</td>
						<td>${r.ord}</td>
						<td>${r.remark}</td>
						<td  style="padding:0px 5px;" class="center">
							<a href="${ctx }/kconf/modellink/delete?id=${r.id}"data-toggle="doajax"  style="color:red;" data-confirm-msg="确定要删除该行信息吗？">删除</a>
							<a href="${ctx }/kconf/modellink/edit?id=${r.id}&mid=${r.mid }" style="color:green;" class="edit" data-toggle="navtab" data-id="app-edit" data-title="${r.field_name }编辑">编辑</a>
							<a href="${ctx}/kconf/linkauthority?kid=${r.id}&mid=${r.mid}" style="color:green;" class="edit" data-toggle="navtab" data-id="authority-edit" data-title="${r.field_name }权限控制">权限控制</a>
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
