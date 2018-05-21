<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp" %>
<style>
    .form_search{min-height:30px;height: auto;background: white;}
    .bjui-pageHeader{background: #fff;}
</style>

<div class="bjui-pageHeader">
    <c:set var="modelTitle" value="${model.mname }"></c:set>

    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx }/kconf/linkauthority/deptminus" method="post">
        <input type="hidden" name="pageNumber" value="${pageNumber }" />
        <input type="hidden" name="pageSize" value="${pageSize }" />
        <input type="hidden" name="orderField" value="${orderField }" />
        <input type="hidden" name="orderDirection" value="${orderDirection}">
        <input type="hidden" name="mid" value="${mid}">
        <input type="hidden" name="kid" value="${kid}">
        <input type="hidden" name="dept_id" value="${dept_id}">
        <c:forEach items="${hiddenFields}" var="item">
            <input type="hidden" name="${item.key}" value="${item.value }">
        </c:forEach>
        <c:forEach items="${fields}" var="item">
            <c:if test="${item.form_view_type eq 'hidden' and not empty item.list_search_op}">
                <input type="hidden" name="record.${item.field_name}" value="${record[item.field_name] }">
            </c:if>
        </c:forEach>

        <div class="bjui-searchBar form_search">

                <label>单位类型：</label>
                <input type="text" name="type" id="j_input_type" size="12" data-select-empty-text="---请选择---"
                       data-width="120" value="${type}"  placeholder="请输入单位" data-ds="${ctx }/kconf/lookup/json?typeid=department_type"
                       data-toggle="selectpicker" data-chk-style="false" data-live-search='false'/>

            <label>归口部门：</label>
            <input type="text" name="relevant" id="j_input_relevant" size="12" data-select-empty-text="---请选择---"
                   data-width="120" value="${relevant}"  placeholder="请输入单位" data-ds="${ctx }/kconf/lookup/json?typeid=relevant"
                   data-toggle="selectpicker" data-chk-style="false" data-live-search='false'/>

            <label>主管单位：</label>
            <input type="text" name="is_zg_dept" id="j_input_is_zg_dept" size="12" data-select-empty-text="---请选择---"
                   data-width="120" value="${is_zg_dept}"  placeholder="请输入单位" data-ds="${ctx }/kconf/lookup/json?typeid=yes_no"
                   data-toggle="selectpicker" data-chk-style="false" data-live-search='false'/>

            <button type="submit" class="ess-red ess_btn" style="margin-left: 5px">查询</button>
            <a href="javascript:lotAuthorityDept();" style="text-decoration: none;background: #14CAB4; text-align: center;
            color: white; padding:5px; line-height: 1.29758; font-size: 14px; height:30px; float: right; border: none;
            margin-right:30px;" data-id="dialog-normal" id="lotAdd">批量添加权限</a>

        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent white ess-pageContent">
    <table data-toggle="tablefixed">
        <thead>
        <tr>
            <th style="background: #DBF1ED; color: #03B9A0;" width="30">
                <div style="text-align: center"><input type="checkbox" name="dept_checkAll"></div>
            </th>
            <th style="background: #DBF1ED; color: #03B9A0;text-align: center" width="40">序号</th>
            <th style="background: #DBF1ED; color: #03B9A0;">上级部门</th>
            <th style="background: #DBF1ED; color: #03B9A0;">基层单位</th>
            <th style="background: #DBF1ED; color: #03B9A0;">部门名称</th>
            <th width="120px" style="background: #DBF1ED; color: #03B9A0;text-align: center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list }" var="r" varStatus="s">
            <tr>
                <td align="center"><input type="checkbox" value="${r.id}"
                                          name="dept_checkAll"></td>
                <td style="text-align: center">${s.count}</td>
                <td>${r.dept_pid}</td>
                <td>${r.dept_jcdwid}</td>
                <td>${r.dname}</td>

                <td align="center">
                    <a href="${ctx}/kconf/linkauthority/save?ids=${r.id}&tabid=authority-edit&mid=${mid}&kid=${kid}" style="color:green;" data-toggle="doajax" class="edit"  data-id="authority-add" data-title="添加权限">添加权限</a>
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
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="40">40</option>
                <option value="80">80</option>
                <option value="100">100</option>
                <option value="150">150</option>
                <option value="250">250</option>
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
    </c:otherwise>
</c:choose>
<script type="text/javascript">
    initForm();
    $.CurrentNavtab.on('bjui.afterInitUI', function () {
        //全选或取消
        $("input[name='dept_checkAll']:eq(0)", $.CurrentNavtab).on('click', function () {
            var check = $(this).get(0).checked;
            var $node = $('input[name=dept_checkAll]:not(0)', $.CurrentNavtab);
            if (check == true) {
                $node.attr('checked', 'true');
            } else if (check == false) {
                $node.removeAttr('checked');
            }
        });


    });

    function lotAuthorityDept(){
        var ids = [];
        var $node = $('input[name=dept_checkAll]', $.CurrentNavtab).not(':eq(0)');
        $.each($node, function (k, v) {
            if ($(this).attr('checked') == 'checked') {
                ids.push($(this).attr('value'));
            }
        });

        $.ajax({
            type: "post",
            url:"${ctx }/kconf/linkauthority/save?ids=" + ids+"&kid=${kid}&mid=${mid}",
            success: function (data) {
               $(this).navtab('reload');
            }
        });

    }
</script>
