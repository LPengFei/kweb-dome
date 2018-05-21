<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
    <div class="col-md-12" style="margin-top: 20px;">
        <div>
            <label>
                <span style="margin-right:10px;"> 开始时间</span>
            </label>
            <c:set var="sTime" value="${start_time }"/>
            <fmt:formatDate value="${sTime }" pattern="yyyy-MM-dd" var="sTime"/>

            <span class="wrap_bjui_btn_box" style="position: relative; display: inline-block;">
                        <input
                                type="text" value="${sTime}" id="j_input_start_time_130list" name="start_time" size="12"
                                data-rule="" data-ds="" data-toggle="datepicker" data-chk-style="false"
                                data-form-style=""
                                data-live-search="false"
                                data-url="?chk_style=false&amp;lookup_param=start_time&amp;lookup_modelId=130list"
                                data-group="" data-width="" data-height="" data-title=""
                                data-select-empty-text="---请选择---"
                                readonly="" data-pattern="yyyy-MM-dd" class="form-control"
                                style="padding-right: 15px; width: 120px;" title="">
                        <a class="bjui-lookup" href="javascript:;" data-toggle="datepickerbtn"
                           style="height: 22px; line-height: 22px;">
                            <i class="fa fa-calendar"></i>
                        </a>
                    </span>&nbsp;
            <label>
                <span style="margin-right:10px;"> 结束时间</span>
            </label>
            <c:set var="eTime" value="${end_time }"/>
            <fmt:formatDate value="${eTime }" pattern="yyyy-MM-dd" var="eTime"/>
            <span class="wrap_bjui_btn_box" style="position: relative; display: inline-block;">
                        <input
                                type="text" value="${eTime}" id="j_input_end_time_130list" name="start_time" size="12"
                                data-rule="" data-ds="" data-toggle="datepicker" data-chk-style="false"
                                data-form-style=""
                                data-live-search="false"
                                data-url="?chk_style=false&amp;lookup_param=end_time&amp;lookup_modelId=130list"
                                data-group="" data-width="" data-height="" data-title=""
                                data-select-empty-text="---请选择---"
                                readonly="" data-pattern="yyyy-MM-dd" class="form-control"
                                style="padding-right: 15px; width: 120px;" title="">
                        <a class="bjui-lookup"
                           href="javascript:;"
                           data-toggle="datepickerbtn"
                           style="height: 22px; line-height: 22px;"><i
                                class="fa fa-calendar"></i></a></span>&nbsp;
            <button type="button" class="red-button search_auto" data-icon="search" style="margin-top: 4px">筛选</button>
            <button type="button" class="red-button search_week" style="margin-top: 4px">本周</button>
            <button type="button" class="red-button search_month" style="...">本月</button>
            <button type="button" class="red-button search_year" style="...">本年</button>
        </div>

        <div id="container" style="height: 80%;margin-top: 20px;"></div>
<script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        title: {
            text: '各类问题耗时统计',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: 335, name: '直接访问'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1548, name: '搜索引擎'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
