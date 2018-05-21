<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %>
<div class="bjui-pageHeader">
	<c:set var="modelTitle" value="${model.mname }"></c:set>

	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/${appid }/${modelName}" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<c:forEach items="${hiddenFields}" var="item">
			<input type="hidden" name="${item.key}" value="${item.value }">
		</c:forEach>
		<c:forEach items="${fields}" var="item">
			<c:if test="${item.form_view_type eq 'hidden' and not empty item.list_search_op}">
				<input type="hidden" name="record.${item.field_name}" value="${record[item.field_name] }">
			</c:if>
		</c:forEach>

		<div class="bjui-searchBar ess-searchBar">
			<%--动态生成查询条件 start --%>
			<%-- <%@include file="../common/common_list_query.jsp" %> --%>
			<c:set var="queryCount" value="0"></c:set>

			<c:forEach items="${searchFields }" var="f">
				<c:if test="${!empty(f.list_search_op) and item.form_view_type ne 'hidden' }">
					<c:set var="queryCount" value="${queryCount + 1 }"></c:set>
			
					<div><label>${f.field_alias }：</label>
						<c:if test="${not empty f.list_format and fn:containsIgnoreCase(f.type, 'date')}">
							<fmt:formatDate value="${fvalue }" pattern="${f.list_format }" var="fvalue" />
						</c:if>
						<input type="text" 
							value="${requestScope[f.field_name] }" name="${f.field_name }"
								data-width="${f.list_search_width  }"
								size="32"
								data-ds="${ctx }${f.form_data_source }"
			                   data-toggle="${f.form_view_type }"
			                   data-chk-style="${f.chk-style }"
			                   data-live-search='${f.form_is_search }'
							   data-select-empty-text="-全部-"
							   <c:if test="${not empty f.list_format }">data-pattern="${f.list_format }"</c:if>
						>&nbsp;
					</div>
				</c:if>
			</c:forEach>
			<c:if test="${queryCount gt 0 }">
				<button type="submit" class="red-button"  data-icon="search">筛选</button>
			</c:if>




			<%--动态生成查询条件 end --%>
			<%--
			<c:forEach items="${links }" var="link">
				<c:if test="${link.position eq 'nav'}">
					<a  ${link.other} href="${link.link}" style=" float: right; margin:auto 5px;${link.style}">${link.label}</a>
				</c:if>
			</c:forEach>
			--%>
	<a class="btn "  data-url="${ctx}/${appid }/${modelName}/edit${subRequestStr}"  data-toggle="navtab" data-id="${modelName }-edit" data-title="新增${modelTitle }" style="background: #14CAB4; color: white; float: right;margin-right:20px;"  >新增</a>

    <div class="pull-right">
     <c:if test="${!empty(exports) or !empty(imports) }">
     <div class="btn-group" style="margin-top:-2px;">
        <button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
        <ul class="dropdown-menu right" role="menu">
            <c:forEach items="${exports }" var="e">
                <li><a href="${ctx}/${appid }/${modelName}/export?export=true&xlsid=${e.ieid}" class="export" data-toggle="dialog" data-confirm-msg="确定要${e.iename }吗？" class="blue">${e.iename }-导出</a></li>
            </c:forEach>
            <c:if test="${!empty(imports) }">
            <li class="divider"></li>
               <c:forEach items="${imports }" var="e">
                <li><a href="${ctx}/${appid }/${modelName}/importxls?xlsid=${e.ieid}"  data-toggle="dialog" data-width="500" data-height="200" data-id="dialog-normal" class="green">${e.iename }-导入</a></li>
            </c:forEach>
            </c:if>
        </ul>
    </div>
    </c:if>
</div>

</div>
</form>
</div>
<div class="bjui-pageContent tableContent white ess-pageContent">
<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
<thead>
<tr>
    <th style="background: #DBF1ED; color: #03B9A0;text-align: center" width="40">序号</th>
    <%@include file="../common/common_list_thead.jsp" %>
    <th width="150px" style="background: #DBF1ED; color: #03B9A0;">操作</th>
</tr>
</thead>
<tbody>
<c:forEach items="${page.list }" var="r" varStatus="s">
    <tr>
        <td style="text-align: center">${s.count}</td>
        <c:forEach items="${fields }" var="f">
            <c:if test="${f.list_view =='true' }">
            	<c:if test="${f.field_name!='filename'}">
                	<kval:val model="${r }" field="${f }"></kval:val>
                </c:if>
                <c:if test="${f.field_name=='filename' && not empty(r.filename) && !(fn:contains(r.filename,'document'))}">
                	<td style="width: 80px; text-align: center;"><a target="_blank" href="${ctx }/kconf/file/downfile?type=document&filename=${r.filename}">下载</a></td>
                </c:if>
                <c:if test="${f.field_name=='filename' && not empty(r.filename) && fn:contains(r.filename,'document')}">
                	<td style="width: 80px; text-align: center;"><a target="_blank" href="${synchronizingServerPath}${r.filename}">下载</a></td>
                </c:if>
                <c:if test="${f.field_name=='filename' && empty(r.filename)}">
                	<td style="width: 80px; text-align: center;"><%-- <a href="${ctx }/kconf/file/downfile?filename=${r.filename}">下载</a> --%></td>
                </c:if>
            </c:if>
        </c:forEach>

        <td width="120px">
            <c:forEach items="${links }" var="link">
                <c:if test="${link.position eq 'list'}">
                 <klink:link model="${r }" link="${link }"/> &nbsp;
                </c:if>
            </c:forEach>
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
    <option value="500">500</option>
    <option value="1000">1000</option>
    <option value="2000">2000</option>
</select>
</div>
<span>&nbsp;条，共 ${page.totalRow } 条</span>
</div>
<div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>

<c:choose>
<c:when test="${not empty jsFile}">
<script src="${ctx }${jsFile}"></script>
</c:when>
<c:otherwise>
<script type="text/javascript">
initForm();
</script>
</c:otherwise>
</c:choose>
