<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>
<style>
    .chart-base-container .chart {
        display: block;
    }

</style>
<div class="bjui-pageContent fixedtableScroller"
        style="padding-left: 20px; padding-top: 20px; padding-right: 30px; overflow: auto;">
    <div class="float-button"><a href="#">工作人员视图</a></div>
    <div class="row nomarginLR">
        <div class="col-md-9 nopadding">
            <div class="account-box border-radius padding12 white">
                <div class="head-pic"><img src="${ctx }/images/icon_head_pic.png"></div>
                <div class="info-box" style="float: none;">
                    <div class="base-info">
                        <!-- 当前有用户登录     -->
                        <h3> 黄总，欢迎您！</h3>
                        <ul>
                            <li><label>所在部门：</label><span>${_login_dept_key.dname }</span></li>
                            <li><label>职位：</label><span>${_login_dept_key.rname}</span></li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="work-info">
                        <ul>
                            <li><img src="../../images/ic_grzx.png">
                                <a href="${ctx}/kconf/user/changePwd" data-toggle="dialog">个人中心</a>
                            </li>
                            <li><img src="../../images/ic_password.png">
                                <a href="${ctx}/kconf/user/changePwd" data-toggle="dialog">密码修改</a>
                            </li>
                            <li><img src="../../images/ic_denglu.png"><label>最近登录:</label>
                                <span>2016-08-19 15:12:25</span></li>
                            <li><img src="../../images/ic_IP.png"><label>IP:</label>
                                <span>${ip }</span></li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
        <!--系统通知-->
        <div class="col-md-3">
            <div class="system-notice border-radius padding12 white">
                <div class="system-notice-wrap">
                    <div class="sytem-notice-header"><span class="sytem-notice-header-name">安全简报</span><span
                            class="sytem-notice-header-time">2017-1-2/8第一周<i class="fa fa-angle-down"></i></span></div>
                    <div class="message_center">
                        <ul>
                            <li>
                                <a href="#">23</a>
                                <p>作业分析</p>
                            </li>
                            <li>
                                <a href="#">5</a>
                                <p>到岗到位</p>
                            </li>
                            <li>
                                <a href="#">0</a>
                                <p>违章情况</p>
                            </li>
                            <li class="noborder">
                                <a href="#">0</a>
                                <p>事件统计</p>
                            </li>
                            <li class="noborder">
                                <a href="#">1</a>
                                <p>风险预警</p>
                            </li>
                        </ul>
                        <span class="clearfix"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 承载力分析 -->
    <div class="row section nomarginLR marginT18" style="margin-right: 15px;">
        <!--tile-header-->
        <div class="tile-header">
            <span>承载力分析</span>
        </div>
        <!--tile-body-->
        <div class="tile-body">
            <div class="col-md-12">
                <div class="chart-base-container" data-url="${ctx }/js/2-1-2.json">
                    <div class="chart"></div>
                    <div class="statistics-time"><span>各单位</span></div>
                </div>
            </div>
        </div>
    </div>
    <!-- 风险预警 -->
    <div class="row section nomarginLR marginT18" style="margin-right: 15px;">
        <!--tile-header-->
        <div class="tile-header">
            <span>风险预警</span>
        </div>
        <!--tile-body-->
        <div class="tile-body">
            <div class="col-md-8 nopadding">
                <div class="chart-base-container" data-url="${ctx }/js/2-1-7.json" style="margin-left: 0px;">
                    <div class="chart"></div>
                    <div class="statistics-time"><span>预警级别</span></div>
                </div>
            </div>
            <div class="col-md-1 nopadding text-center">
                <span style="display: inline-block; height: 400px; border-left: 1px dashed #DBDFDE; z-index: 10;"></span>
            </div>
            <div class="col-md-3 nopadding">
                <div class="chart-base-container" data-url="${ctx }/js/2-1-6.json">
                    <div class="chart" id="fxyj" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <!-- 到岗到位 -->
    <div class="row section nomarginLR marginT18" style="margin-right: 15px;">
        <!--tile-header-->
        <div class="tile-header">
            <span>到岗到位</span>
        </div>
        <!--tile-body-->
        <div class="tile-body">
            <div class="col-md-12">
                <div class="chart-base-container" data-url="${ctx }/js/2-1-3.json">
                    <div class="chart"></div>
                    <div class="statistics-time"><span>各单位</span></div>
                </div>
            </div>
        </div>
    </div>
    <!-- 违章情况 -->
    <div class="row section nomarginLR marginT18" style="margin-right: 15px;">
        <!--tile-header-->
        <div class="tile-header">
            <span>违章情况</span>
        </div>
        <!--tile-body-->
        <div class="tile-body">
            <div class="col-md-12">
                <div class="chart-base-container" data-url="${ctx }/js/2-1-4.json">
                    <div class="chart"></div>
                    <div class="statistics-time"><span>各单位</span></div>
                </div>
            </div>
        </div>
    </div>
    <!-- 跳闸走势 -->
    <div class="row section nomarginLR marginT18" style="margin-right: 15px;">
        <!--tile-header-->
        <div class="tile-header">
            <span>跳闸走势</span>
        </div>
        <!--tile-body-->
        <div class="tile-body">
            <div class="col-md-12">
                <div class="chart-base-container" data-url="${ctx }/js/2-1-5.json" style="height: 400px;">
                    <div class="chart"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${ctx }/js/ess.js" type="text/javascript"></script>
<script src="${ctx }/js/Highcharts-zh.js" type="text/javascript"></script>
<script type="text/javascript">
    var option0 = {
        drilldown: {
            animation: {
                easing: 'linear'
            }
        },
        chart: {
            type: "area",
            backgroundColor: "#fff"
        },
        tooltip: {
            shared: true,
            crosshairs: {
                width: 2,
                color: 'red',
            }
        },
        xAxis: {
            categories: ["夹江", "峨眉山", "井研", "沙湾", "五通桥", "变电运维室", "运维检修部", "输电运检室", "变电检修室", "配电运检室"],
            tickWidth: 1,
            gridLineWidth: 1,
            gridLineColor: '#F0F0F0',
            lineColor: "#F0F0F0",
            tickWidth: 1,
            tickColor: "#F0F0F0",
            labels: {
                style: {
                    color: '#929292',
                    fontSize: "14px",
                    fontWeight: 600
                }
            }
        },
        yAxis: {
            lineWidth: 1,
            title: {
                align: 'high',
                offset: -10,
                text: '数量（个）',
                rotation: 0,
                y: -20
            },
            gridLineColor: "#F0F0F0",
            stackLabels: {
                enabled: false,
            },
            lineColor: "#F0F0F0"
        },
        legend: {
            color: "#fff",
            enabled: true,
            shadow: false,
            align: 'right',
            verticalAlign: 'top',
            y: 30,
            layout: "vertical",
            itemMarginBottom: 20,
            symbolWidth: 12,
            itemStyle: {
                lineHeight: "100px",
                color: "#929292"
            },
            itemHoverStyle: {
                color: '#929292'
            }
        },
        title: {
            text: "Chart sadf ",
            y: 10,
        },
        plotOptions: {
            area: {
                fillOpacity: 0.2
            }
        },
        colors: ["#019CDF", "#FF9147"],
    }
    var option1 = {
        xAxis: {
            categories: ["8级", "7级", "6级", "5级", "4级", "3级", "2级", "1级"],
            lineColor: "#F0F0F0",
            tickWidth: 1,
            tickColor: "#F0F0F0",
            labels: {
                style: {
                    color: '#929292',
                    fontSize: "14px",
                    fontWeight: 600
                }
            }
        },
        yAxis: {
            lineWidth: 1,
            title: {
                align: 'high',
                offset: -10,
                text: '数量（个）',
                rotation: 0,
                y: -20
            },
            gridLineColor: "#F0F0F0",
            stackLabels: {
                enabled: false,
            },
            lineColor: "#F0F0F0"
        },
        title: {
            text: "Chart sadf ",
            y: 10,
        },
        legend: {
            color: "#929292",
            enabled: true,
            shadow: false,
            align: 'right',
            verticalAlign: 'top',
            y: 30,
            layout: "vertical",
            itemMarginBottom: 20,
            symbolWidth: 12,
            itemStyle: {
                lineHeight: "100px",
                color: "#929292"
            },
            itemHoverStyle: {
                color: '#929292'
            }
        },
        tooltip: {
            style: {
                lineHeight: "20px"
            }
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true, // dataLabels设为true
                    align: "center",
                    verticalAlign: "top",
                    style: {
                        color: '#ffffff',
                    }
                }
            }
        },
        colors: ["#15CAB5"],
    }

    var option2 = {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/><br/>{b} : {c} ({d}%)"
        },
        toolbox: {
            show : true,
        },
        series : [
            {
                name:'预警占比',
                type:'pie',
                radius : ['50%', '70%'],
                itemStyle : {
                    normal : {
                        label : {
                            show : false
                        },
                        labelLine : {
                            show : false
                        }
                    },
                    emphasis : {
                        label : {
                            show : true,
                            formatter: '{b}\n  {d}%',
                            position : 'center',
                            textStyle : {
                                fontSize : '30',
                                fontWeight : 'bold'
                            }
                        }
                    }
                },
                data:[
                    {value:1, name:'一级'},
                    {value:2, name:'二级'},
                    {value:4, name:'三级'},
                    {value:3, name:'四级'},
                    {value:5, name:'五级'},
                    {value:2, name:'六级'},
                    {value:4, name:'七级'},
                    {value:5, name:'八级'}
                ]
            }
        ],



//        chart: {
//            type: "pie",
//            backgroundColor: "#fff",
//            plotBackgroundColor: null,
//            plotBorderWidth: null,
//            plotShadow: false
//        },
//        plotOptions: {
//            pie: {
//                allowPointSelect: true,
//                dataLabels: {
//                    enabled: false,
//                },
//                showInLegend: true
//            },
//        },
//        tooltip: {
//            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>',
//            style: {
//                lineHeight: "20px"
//            }
//        },
//        legend: {
//            color: "#fff",
//            enabled: true,
//            shadow: false,
//            align: 'right',
//            verticalAlign: 'top',
//            y: 30,
//            layout: "vertical",
//            itemMarginBottom: 20,
//            symbolWidth: 12,
//            itemStyle: {
//                lineHeight: "100px",
//                color: "#929292"
//            },
//            itemHoverStyle: {
//                color: '#929292'
//            }
//        },
        color: ["#F96A6A", "#FF9146", "#FFC100", "#EBD3AD", "#B185F9", "#6ABBFF", "#7ADFC3", "#BDEB74"],
    }
    var myChart = echarts.init(document.getElementById("fxyj"));
    myChart.setOption(option2);
    var option3 = {
        xAxis: {
            categories: ["夹江", "峨眉山", "井研", "沙湾", "五通桥", "变电运维室", "运维检修部", "输电运检室", "变电检修室", "配电运检室"],
            lineColor: "#F0F0F0",
            tickWidth: 1,
            tickColor: "#F0F0F0",
            labels: {
                style: {
                    color: '#929292',
                    fontSize: "14px",
                    fontWeight: 600
                }
            }
        },
        yAxis: {
            lineWidth: 1,
            title: {
                align: 'high',
                offset: -10,
                text: '次数（个）',
                rotation: 0,
                y: -20
            },
            gridLineColor: "#F0F0F0",
            stackLabels: {
                enabled: false,
            },
            lineColor: "#F0F0F0"
        },
        legend: {
            color: "#fff",
            enabled: true,
            shadow: false,
            align: 'right',
            verticalAlign: 'top',
            y: 30,
            layout: "vertical",
            itemMarginBottom: 20,
            symbolWidth: 12,
            itemStyle: {
                lineHeight: "100px",
                color: "#929292"
            },
            itemHoverStyle: {
                color: '#929292'
            }
        },
        title: {
            text: "Chart sadf ",
            y: 10,
        },
        tooltip: {
            style: {
                lineHeight: "20px"
            }
        },
        plotOptions: {
            column: {
                dataLabels: {
                    enabled: true, // dataLabels设为true
                    verticalAlign: "top",
                    style: {
                        color: '#ffffff',
                    }
                }
            }
        },
        colors: ["#019CDF"],
    }
    var option4 = {
        xAxis: {
            categories: ["夹江", "峨眉山", "井研", "沙湾", "五通桥", "变电运维室", "运维检修部", "输电运检室", "变电检修室", "配电运检室"],
            lineColor: "#F0F0F0",
            tickWidth: 1,
            tickColor: "#F0F0F0",
            labels: {
                style: {
                    color: '#929292',
                    fontSize: "14px",
                    fontWeight: 600
                }
            }
        },
        yAxis: {
            lineWidth: 1,
            title: {
                align: 'high',
                offset: -10,
                text: '违章个数（个）',
                rotation: 0,
                y: -20
            },
            gridLineColor: "#F0F0F0",
            stackLabels: {
                enabled: false,
            },
            lineColor: "#F0F0F0"
        },
        title: {
            text: "Chart sadf ",
            y: 10,
        },
        legend: {
            color: "#929292",
            enabled: true,
            shadow: false,
            align: 'right',
            verticalAlign: 'top',
            y: 30,
            layout: "vertical",
            itemMarginBottom: 20,
            symbolWidth: 12,
            itemStyle: {
                lineHeight: "100px",
                color: "#929292"
            },
            itemHoverStyle: {
                color: '#929292'
            }
        },
        tooltip: {
            shape: "square",
            useHTML: true,
            shared: true,
            followPointer: true,
            headerFormat: '<div style="display: block; width: 100px; height: 26px; line-height: 26px;">{point.key}</div><table>',
            pointFormat: '<tr><td style="padding: 4px 6px; color: {series.color}">{series.name}: </td>' +
            '<td style="color: white; text-align: right"><b>{point.y}</b></td></tr>',
            footerFormat: '</table>',
            style: {
                lineHeight: "20px"
            }
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true, // dataLabels设为true
                    verticalAlign: "middle",
                    style: {
                        color: '#ffffff',
                    }
                }
            }
        },
        colors: ["#F96A6A", "#FF9147", "#FFC000"],
    }
    var option5 = {
        chart: {
            type: "area",
            backgroundColor: "#fff"
        },
        xAxis: {
            categories: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
            lineColor: "#F0F0F0",
            tickWidth: 1,
            tickColor: "#F0F0F0",
            labels: {
                style: {
                    color: '#929292',
                    fontSize: "14px",
                    fontWeight: 600
                }
            }
        },
        yAxis: {
            lineWidth: 1,
            title: {
                align: 'high',
                offset: -10,
                text: '次数（个）',
                rotation: 0,
                y: -20
            },
            gridLineColor: "#F0F0F0",
            stackLabels: {
                enabled: false,
            },
            lineColor: "#F0F0F0"
        },
        legend: {
            color: "#929292",
            enabled: true,
            shadow: false,
            align: 'right',
            verticalAlign: 'top',
            y: 30,
            layout: "vertical",
            itemMarginBottom: 20,
            symbolWidth: 12,
            itemStyle: {
                lineHeight: "100px",
                color: "#929292"
            },
            itemHoverStyle: {
                color: '#929292'
            }
        },
        title: {
            text: "Chart sadf ",
            y: 10,
        },
        tooltip: {
            style: {
                lineHeight: "20px"
            },
            series: {
                animation: {
                    animationLimit: 10,
                    duration: 2000,
                    easing: 'easeOutBounce'
                }
            }
        },
        plotOptions: {
            area: {
                fillOpacity: 0.1
            }
        },
        colors: ["#019CDF", "#FF9147", "#4DD098", "#FFC000"],

    }

    var option6 = {
        colors: ["#16CDBA"]
    }
    var option7 = {
        colors: ["#16CDBA"]
    }

    $(function () {
        $(".bjui-pageContent").height($(window).height() - 120);
        $(window).resize(function () {
            $(".bjui-pageContent").height($(window).height() - 120);
        });
        // drawMainChart();
        loadHightDiv($($(".chart-base-container")[0]), option0);
        loadHightDiv($($(".chart-base-container")[1]), option1);
        //loadHightDiv($($(".chart-base-container")[2]), option2);

        loadHightDiv($($(".chart-base-container")[3]), option3);
        loadHightDiv($($(".chart-base-container")[4]), option4);
        loadHightDiv($($(".chart-base-container")[5]), option5);
    })
    /**
     * 处理统计图动态显示的函数
     */
    var i = 6;
    function animtionChart() {
        switch (i) {
            case 5:
                console.log(5)
                $($(".chart-base-container")[5]).attr("url", "${ctx }/js/2-1-4.json");
                loadHightDiv($($(".chart-base-container")[5]), option5);
                break;
            case 6:
                console.log(6)
                $($(".chart-base-container")[5]).attr("url", "${ctx }/js/2-1-3.json");
                loadHightDiv($($(".chart-base-container")[5]), option6);
                break;
            case 7:
                console.log(7)
                loadHightDiv($($(".chart-base-container")[5]), option7);
                break;
        }
        if (i > 7) {
            i = 4;
            console.log("当前position:" + i);
        }
        ++i;
        console.log("当前position:------------->" + i);
    }
    // setInterval("animtionChart()", 3000);
</script>