<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/department" method="post">
        <input type="hidden" name="pageNumber" value="${query.pageNumber }"/>
        <input type="hidden" name="pageSize" value="${query.pageSize }"/>
        <input type="hidden" name="orderField" value="${query.orderField }"/>
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
            <%@include file="../common/common_list_query.jsp" %>

            <a class="btn btn-green" data-url="${ctx}/kconf/department/edit${subRequestStr}" data-toggle="navtab" data-id="${modelName }-edit" data-title="新增${modelTitle }" style="background: #14CAB4; color: white; float: right;">新增</a>
        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent  white ess-pageContent">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th style="background: #DBF1ED; color: #03B9A0;text-align: center" width="40">序号</th>
            <%@include file="../common/common_list_thead.jsp" %>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${page.list }" var="r" varStatus="s">
            <tr>
                <td style="text-align: center">${s.count}</td>
                <c:forEach items="${fields }" var="f">
                    <c:if test="${f.list_view =='true' }">
                        <kval:val model="${r }" field="${f }"></kval:val>
                    </c:if>
                </c:forEach>

                <td>
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
            </select>
        </div>
        <span>&nbsp;条，共 ${page.totalRow } 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>
