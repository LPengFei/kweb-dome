<%@ page import="java.util.Date" %>
<%@ page import="com.cnksi.kcore.utils.DateUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<%
    Date startWeek = DateUtil.getWeekStartDate();
    Date endWeek = DateUtil.getWeekEndDate();
    Date startMonth = DateUtil.getMonthStartDate();
    Date endMonth = DateUtil.getMonthEndDate();
    Date startYear = DateUtil.getYearStart(DateUtil.getCurrentYear());
    Date endYear = DateUtil.getYearEnd(DateUtil.getCurrentYear());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    request.setAttribute("startWeek", sdf.format(startWeek));
    request.setAttribute("endWeek", sdf.format(endWeek));
    request.setAttribute("startMonth", sdf.format(startMonth));
    request.setAttribute("endMonth", sdf.format(endMonth));
    request.setAttribute("startYear", sdf.format(startYear));
    request.setAttribute("endYear", sdf.format(endYear));

    if (request.getAttribute("dateSelect") == null) {
        request.setAttribute("dateSelect", "month");
    }
    if (request.getAttribute("start_time") == null) {
        request.setAttribute("start_time", sdf.format(startMonth));
    }
    if (request.getAttribute("end_time") == null) {
        request.setAttribute("end_time", sdf.format(endMonth));
    }
%>
<style>
    .select_bar label, .select_bar div {
        position: relative;
        top: -2px;
    }

    .export-button {
        height: auto;
        background: #15c9b4;
        color: white;
        border: 1px solid #15c9b4;
        display: inline-block;
        padding: 6px 14px;
        text-decoration: none;
        border-radius: 4px;
        cursor: pointer;
    }
    .export-button:hover {
        color: white;
        text-decoration: none;
        background: #38a598;
        border: 1px solid #38a598;
    }


    .type_date{
        padding-top: 9px;
        display: inline-block;
        margin-top: 11px;
        margin-left: 85px;
    }
    .type_date a{
        padding: 7px 14px;
        border: 1px solid #15cab5;
        border-radius: 3px;
        color: #15cab5;
        font-size: 14px;
        margin-right: 5px;
        text-decoration: none;
    }
    .type_date a.current{
        color: #ffffff;
        background-color: #15cab5;
    }
</style>

<script type="text/javascript">
    $(function () {
        initForm();
    });
</script>

<div class="bjui-pageHeader">
    <c:set var="modelTitle" value="${model.mname }"></c:set>
    <form id="pagerForm" name="dept_credit_rank" data-toggle="ajaxsearch" action="${ctx}/hr/deptcreditrank" method="post">
        <input type="hidden" name="pageNumber" value="${query.pageNumber }"/>
        <input type="hidden" name="pageSize" value="${query.pageSize }"/>
        <input type="hidden" name="orderField" value="${query.orderField }"/>
        <input type="hidden" name="orderDirection" value="${query.orderDirection}">
        <input type="hidden" name="dateSelect" value="${dateSelect}">

        <c:forEach items="${hiddenFields}" var="item">
            <input type="hidden" name="${item.key}" value="${item.value }">
        </c:forEach>
        <c:forEach items="${fields}" var="item">
            <c:if test="${item.form_view_type eq 'hidden' and not empty item.list_search_op}">
                <input type="hidden" name="record.${item.field_name}" value="${record[item.field_name] }">
            </c:if>
        </c:forEach>

        <div class="bjui-searchBar ess-searchBar tableContent white ess-pageContent">

            <%--  开始时间  结束时间--%>
            <div><label>开始时间：</label>
                <input type="text" value="${start_time}" data-rule="required" readonly="" name="start_time" size="12" data-toggle="datepicker">
            </div>
            <div><label>结束时间：</label>
                <input type="text" value="${end_time}" data-rule="required" readonly="" name="end_time" size="12" data-toggle="datepicker">
            </div>

            <button type="submit" class="red-button" data-icon="search">查询</button>
            <a href="javascript:;" style="display: none" id="excel_name"></a>
            <a class="export-button"  onclick="excel('dpet_credit_rank_table')" class="export" data-toggle="dialog" data-confirm-msg="确定要导出${e.iename }吗？">导出</a>

            <div class="type_date" style="float: right">
                <a name="week_btn" onclick="week_opera()" <c:if test="${dateSelect == 'week'}">class="current"</c:if> href="javascript:;">本周</a>
                <a name="month_btn" onclick="month_opera()" <c:if test="${dateSelect == 'month'}">class="current"</c:if> href="javascript:;">本月</a>
                <a name="year_btn" onclick="year_opera()" <c:if test="${dateSelect == 'year'}">class="current"</c:if> href="javascript:;">本年</a>
            </div>
            <span class="clearfix"></span>
        </div>
    </form>
</div>
<div class="tableContent white ess-pageContent" style="padding:0px 0px 20px; border:0px solid #E0E2E5;margin-bottom: 20px;overflow: hidden">
    <h2 style="font-size: 24px;font-weight: 400;text-align: center;margin: 20px 0px;padding: 0;color: #f96a6a;">单位资信排名</h2>
    <table class="table table-bordered"  style="width: 98%;margin: 0 auto;text-align: center">
        <thead>
        <tr>
            <th style="background: #DBF1ED; color: #03B9A0;text-align: center" width="40">序号</th>
            <th style="background: #DBF1ED; color: #03B9A0;width: 150px;text-align: center;font-weight: 400">单位</th>
            <th style="background: #DBF1ED; color: #03B9A0;width: 150px;text-align: center;font-weight: 400">到岗到位率</th>
            <th style="background: #DBF1ED; color: #03B9A0;width: 150px;text-align: center;font-weight: 400">作业监督检查率</th>
            <th style="background: #DBF1ED; color: #03B9A0;width: 150px;text-align: center;font-weight: 400">作业现场平均违章率</th>
            <th style="background: #DBF1ED; color: #03B9A0;width: 150px;text-align: center;font-weight: 400">问题整改完成率</th>
            <th style="background: #DBF1ED; color: #03B9A0;width: 150px;text-align: center;font-weight: 400">得分</th>
            <th style="background: #DBF1ED; color: #03B9A0;width: 150px;text-align: center;font-weight: 400">名次</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list }" var="r" varStatus="s">
            <tr>
                <td style="text-align: center;width: 10px;">${s.count}</td>
                <td style="width: 150px;">${r.dname}</td>
                <td style="width: 150px;">${r.dgdw_rate}</td>
                <td style="width: 150px;">${r.jd_rate}</td>
                <td style="width: 150px;">${r.illegal_avg_rate}</td>
                <td style="width: 150px;">${r.illegal_zg_rate}</td>
                <td style="width: 150px;">${r.score}</td>
                <td style="width: 150px;">${s.index + 1}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table table-bordered" id="dpet_credit_rank_table" style="width: 98%;margin: 0 auto;text-align: center;display: none">
        <thead>
        <tr><th colspan="8"> <h2 style="font-size: 24px;font-weight: 400;text-align: center;margin: 20px 0px;padding: 0;">单位资信排名</h2></th></tr>
        <tr>
            <th style="text-align: center" width="40">序号</th>
            <th style="width: 150px;text-align: center;font-weight: 400">单位</th>
            <th style="width: 150px;text-align: center;font-weight: 400">到岗到位率</th>
            <th style="width: 150px;text-align: center;font-weight: 400">作业监督检查率</th>
            <th style="width: 150px;text-align: center;font-weight: 400">作业现场平均违章率</th>
            <th style="width: 150px;text-align: center;font-weight: 400">问题整改完成率</th>
            <th style="width: 150px;text-align: center;font-weight: 400">得分</th>
            <th style="width: 150px;text-align: center;font-weight: 400">名次</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list }" var="r" varStatus="s">
            <tr>
                <td style="text-align: center;width: 10px;">${s.count}</td>
                <td style="width: 150px;">${r.dname}</td>
                <td style="width: 150px;">${r.dgdw_rate}</td>
                <td style="width: 150px;">${r.jd_rate}</td>
                <td style="width: 150px;">${r.illegal_avg_rate}</td>
                <td style="width: 150px;">${r.illegal_zg_rate}</td>
                <td style="width: 150px;">${r.score}</td>
                <td style="width: 150px;">${s.index + 1}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%--<div class="bjui-pageFooter">--%>
    <%--<div class="pages">--%>
        <%--<span>每页&nbsp;</span>--%>
        <%--<div class="selectPagesize">--%>
            <%--<select data-toggle="selectpicker" data-toggle-change="changepagesize">--%>
                <%--<option value="30">30</option>--%>
                <%--<option value="60">60</option>--%>
                <%--<option value="120">120</option>--%>
                <%--<option value="150">150</option>--%>
            <%--</select>--%>
        <%--</div>--%>
        <%--<span>&nbsp;条，共 ${page.totalRow } 条</span>--%>
    <%--</div>--%>
    <%--<div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>--%>
<%--</div>--%>
<script type="text/javascript">
    $.CurrentNavtab.on('bjui.afterInitUI', function () {

   });

    function week_opera() {
        var start_time = "${startWeek}";
        var end_time = "${endWeek}";
        $("#pagerForm input[name='start_time']").val(start_time);
        $("#pagerForm input[name='end_time']").val(end_time);

        $("#pagerForm .type_date a").removeClass("current");
        $("#pagerForm a[name='week_btn']").addClass("current");
        $("#pagerForm input[name='dateSelect']").val("week");
        $("#pagerForm[name='dept_credit_rank']", $.CurrentNavtab).submit();

    }
    function month_opera() {
        var start_time = "${startMonth}";
        var end_time = "${endMonth}";
        $("#pagerForm input[name='start_time']").val(start_time);
        $("#pagerForm input[name='end_time']").val(end_time);

        $("#pagerForm .type_date a").removeClass("current");
        $("#pagerForm a[name='month_btn']").addClass("current");
        $("#pagerForm input[name='dateSelect']").val("month");
        $("#pagerForm[name='dept_credit_rank']", $.CurrentNavtab).submit();
    }
    function year_opera() {
        var start_time = "${startYear}";
        var end_time = "${endYear}";
        $("#pagerForm input[name='start_time']").val(start_time);
        $("#pagerForm input[name='end_time']").val(end_time);

        $("#pagerForm .type_date a").removeClass("current");
        $("#pagerForm a[name='year_btn']").addClass("current");
        $("#pagerForm input[name='dateSelect']").val("year");
        $("#pagerForm[name='dept_credit_rank']", $.CurrentNavtab).submit();
    }
    function excel(tableid) {
        resizeStop = false;
        if(getExplorer()=='ie')
        {
            var curTbl = document.getElementById(tableid);
            var oXL = new ActiveXObject("Excel.Application");
            var oWB = oXL.Workbooks.Add();
            var xlsheet = oWB.Worksheets(1);
            var sel = document.body.createTextRange();
            sel.moveToElementText(curTbl);
            sel.select();
            sel.execCommand("Copy");
            xlsheet.Paste();
            oXL.Visible = true;

            try {
                var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
            } catch (e) {
                print("Nested catch caught " + e);
            } finally {
                oWB.SaveAs(fname);
                oWB.Close(savechanges = false);
                oXL.Quit();
                oXL = null;
                idTmr = window.setInterval("Cleanup();", 1);
            }

        }
        else
        {
            tableToExcel(tableid)
        }
    }
    function  getExplorer() {
        var explorer = window.navigator.userAgent ;
        //ie
        if (explorer.indexOf("MSIE") >= 0) {
            return 'ie';
        }
        //firefox
        else if (explorer.indexOf("Firefox") >= 0) {
            return 'Firefox';
        }
        //Chrome
        else if(explorer.indexOf("Chrome") >= 0){
            return 'Chrome';
        }
        //Opera
        else if(explorer.indexOf("Opera") >= 0){
            return 'Opera';
        }
        //Safari
        else if(explorer.indexOf("Safari") >= 0){
            return 'Safari';
        }
    }
    function Cleanup() {
        window.clearInterval(idTmr);
        CollectGarbage();
    }
    var tableToExcel = (function() {
        var uri = 'data:application/vnd.ms-excel;base64,',
            template = '<html><head><meta charset="UTF-8"></head><body><table border="1">{table}</table></body></html>',
            base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
            format = function(s, c) {
                return s.replace(/{(\w+)}/g,
                    function(m, p) { return c[p]; }) }
        return function(table, name) {
            if (!table.nodeType) table = document.getElementById(table)
            var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
            document.getElementById('excel_name').href = uri + base64(format(template, ctx))
            document.getElementById('excel_name').download='单位资信排名(${start_time}至${end_time}).xls'
            document.getElementById('excel_name').click()
        }
    })()
</script>