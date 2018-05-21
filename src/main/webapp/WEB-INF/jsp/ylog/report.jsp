<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>

<div class="row section nomarginLR" style="margin-right: 15px;margin-top: 20px;">
    <!--tile-header-->
    <div class="tile-header">
        <span>耗时分析</span>
    </div>
    <!--tile-body-->
    <div class="tile-body">
        <div class="col-md-12">
            <div>
                <label>
                    <span style="margin-right:10px;"> 开始时间</span>
                </label>
                <c:set var="sTime" value="${start_time }"/>
                <fmt:formatDate value="${sTime }" pattern="yyyy-MM-dd" var="sTime"/>

                <span class="wrap_bjui_btn_box" style="position: relative; display: inline-block;">
                        <input
                                type="text" value="${sTime}" id="j_input_start_time_130list" name="start_time" size="12"
                                data-rule="" data-ds="" data-toggle="datepicker" data-chk-style="false" data-form-style=""
                                data-live-search="false"
                                data-url="?chk_style=false&amp;lookup_param=start_time&amp;lookup_modelId=130list"
                                data-group="" data-width="" data-height="" data-title="" data-select-empty-text="---请选择---"
                                readonly="" data-pattern="yyyy-MM-dd" class="form-control"
                                style="padding-right: 15px; width: 120px;" title="">
                        <a class="bjui-lookup" href="javascript:;"data-toggle="datepickerbtn" style="height: 22px; line-height: 22px;">
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
                                data-rule="" data-ds="" data-toggle="datepicker" data-chk-style="false" data-form-style=""
                                data-live-search="false"
                                data-url="?chk_style=false&amp;lookup_param=end_time&amp;lookup_modelId=130list"
                                data-group="" data-width="" data-height="" data-title="" data-select-empty-text="---请选择---"
                                readonly="" data-pattern="yyyy-MM-dd" class="form-control"
                                style="padding-right: 15px; width: 120px;" title="">
                        <a class="bjui-lookup"
                           href="javascript:;"
                           data-toggle="datepickerbtn"
                           style="height: 22px; line-height: 22px;"><i
                                class="fa fa-calendar"></i></a></span>&nbsp;

                <button type="button" class="red-button search_auto"  data-icon="search" style="margin-top: 4px">筛选</button>
                <button type="button" class="red-button search_week"   style="margin-top: 4px">本周</button>
                <button type="button" class="red-button search_month"   style="...">本月</button>
                <button type="button" class="red-button search_year"   style="...">本年</button>
            </div>
            <div class="chart-base-container" data-url=""  style="margin-left: -24px; height: 400px;width: 100%;">
                <div class="chart" style="height: 400px;width: 100%"></div>
            </div>
        </div>
    </div>
</div>


<script src="${ctx }/js/ess.js" type="text/javascript"></script>

<script >
    var type="auto";
    var start_time=$("#j_input_start_time_130list").val();
    var end_time=$("#j_input_end_time_130list").val();
    $(function () {
        searchData(type,start_time,end_time);
        $(".search_auto").click(function () {
            start_time=$("#j_input_start_time_130list").val();
            end_time=$("#j_input_end_time_130list").val();
            type="auto";
            searchData(type,start_time,end_time);
        });

        $(".search_week").click(function () {
            type="week";
            searchData(type,start_time,end_time);
        });
        $(".search_month").click(function () {
            type="month";
            searchData(type,start_time,end_time);
        });
        $(".search_year").click(function () {
            type="year";
            searchData(type,start_time,end_time);
        });

    });

    function searchData(type,start_time,end_time) {
        var Hchar_result=null;
        $.getJSON("${ctx }/app/ylog/ylogReport?type="+type+"&start_time="+start_time+"&end_time="+end_time, function (result) {
            Hchar_result= result;
            $("#j_input_start_time_130list").attr("value",Hchar_result.start_time);
            $("#j_input_end_time_130list").attr("value",Hchar_result.end_time);
            $('.chart-base-container').highcharts({
                chart: {
                    type: 'column',
                    backgroundColor: 'white'
                },
                title: {
                    text: '统计分析'
                },
                xAxis: {
                    categories: Hchar_result.projectName
                },
                series: Hchar_result.series,
                yAxis: {
                    min: 0,
                    title: {
                        text: '耗时(H)'
                    },
                    stackLabels: {
                        enabled: true,
                        style: {
                            fontWeight: 'bold',
                            color: 'gray'
                        }
                    }
                },
                legend: {
                    align: 'right',
                    x: -30,
                    verticalAlign: 'top',
                    y: 25,
                    floating: true,

                    borderColor: '#fff',
                    borderWidth: 1,
                    shadow: false
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.x + '</b><br/>' +
                            this.series.name + ': ' + this.y + '<br/>' +
                            '耗时(H): ' + this.point.stackTotal;
                    }
                },
                plotOptions: {
                    column: {
                        stacking: 'normal',
                        dataLabels: {
                            enabled: true,
                            color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                            style: {
                                textShadow: '0 0 3px white'
                            }
                        }
                    }
                }
            });
        });
    }

</script>