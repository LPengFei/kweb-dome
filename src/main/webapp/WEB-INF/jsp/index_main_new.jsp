<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>
<style>
    .chart-base-container .chart {
        display: block;
    }

</style>
<div class="bjui-pageContent fixedtableScroller" style=" padding-top: 20px; overflow: auto;">
    <div class="float-button"><a href="${ctx}/index/main" data-toggle="navtab" data-id="view-main">视图1</a></div>
    <div class="row nomarginLR">
        <div class="col-md-9 nopadding">
            <div class="account-box border-radius padding12 white">
                <div class="head-pic"><img src="${ctx }/images/icon_head_pic.png"></div>
                <div class="info-box" style="float: none;">
                    <div class="base-info">
                        <!-- 当前有用户登录     -->
                        <h3>${loginUser.uname}，欢迎您！</h3>
                        <ul>
                            <li><label>所在部门：</label><span>${_login_dept_key.dname }</span></li>
                            <li><label>职位：</label><span>${rname}</span></li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="work-info leader">
                        <span class="work-info-label">安全提醒（2016年12月29日）：</span>
                        <ul>
                            <li><img src="${ctx}/images/ic_zyfx.png">
                                <label>作业风险<span>（五级及以上）</span><a href="#">3</a>个</label>
                            </li>
                            <li><img src="${ctx}/images/ic_dwfx.png">
                                <label>电网风险<span>（预控措施未落实）</span><a href="#">3</a>个</label>
                            </li>
                            <li><img src="${ctx}/images/ic_cftz.png">
                                <label>重复跳闸<span>（三次以上）</span><a href="#">1</a>个</label></li>
                        </ul>
                        <div class="clearfix"></div>
                        <div class="work-info-item-wrap" style="visibility: hidden;"><a class="work-info-item"
                                href="#">待审核计划数：8个</a><a class="work-info-item" href="#">待审核违章：3个</a><a
                                class="work-info-item" href="#">待审核风险预警：2个</a></div>
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
                                <a href="#">10</a>
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

    <!-- 统计分析 -->
    <ul class="nav nav-tabs tjfx" role="tablist" id="myTab">
        <li role="presentation" class="active"><a href="#zyjh" role="tab" data-toggle="tab">作业计划</a></li>
        <li role="presentation"><a href="#czlfx" role="tab" data-toggle="tab">承载力分析</a></li>
        <li role="presentation"><a href="#fxyjj" role="tab" data-toggle="tab">风险预警</a></li>
        <li role="presentation"><a href="#dgdw" role="tab" data-toggle="tab">到岗到位</a></li>
        <li role="presentation"><a href="#wzqk" role="tab" data-toggle="tab">违章情况</a></li>
        <li role="presentation"><a href="#tzzs" role="tab" data-toggle="tab">跳闸走势</a></li>
    </ul>

    <div class="tab-content mains">
        <div role="tabpanel" class="tab-pane active" id="zyjh">
            <!-- 作业计划 -->
            <div class="row section nomarginLR" style="margin-right: 15px;">
                <!--tile-body-->
                <div class="tile-body">
                    <div class="col-md-12">
                        <div class="chart-base-container" data-url="${ctx}/js/2-1-7.json">
                            <div class="chart"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="czlfx">
            <!-- 承载力分析 -->
            <div class="row section nomarginLR" style="margin-right: 15px;">
                <!--tile-body-->
                <div class="tile-body">
                    <div class="col-md-12">
                        <div class="chart-base-container" data-url="${ctx}/js/2-1-7.json">
                            <div class="chart"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="fxyjj">
            <!-- 风险预警 -->
            <div class="row section nomarginLR" style="margin-right: 15px;">
                <!--tile-body-->
                <div class="tile-body">
                    <div class="col-md-8 nopadding">
                        <div class="chart-base-container" data-url="${ctx }/js/2-1-7.json" style="margin-left: 0px;">
                            <div class="chart"></div>
                        </div>
                    </div>
                    <div class="col-md-1 nopadding text-center">
                        <span class="middle-line"
                                style="display: inline-block; height: 500px; border-left: 1px dashed #DBDFDE; z-index: 10;"></span>
                    </div>
                    <div class="col-md-3 nopadding">
                        <div class="chart-base-container" data-url="${ctx }/js/2-1-8.json">
                            <div class="chart"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="dgdw">
            <!-- 到岗到位 -->
            <div class="row section nomarginLR" style="margin-right: 15px;">
                <!--tile-body-->
                <div class="tile-body">
                    <div class="col-md-12">
                        <div class="chart-base-container" data-url="${ctx }/js/2-1-3.json">
                            <div class="chart"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="wzqk">
            <!-- 违章情况 -->
            <div class="row section nomarginLR" style="margin-right: 15px;">
                <!--tile-body-->
                <div class="tile-body">
                    <div class="col-md-12">
                        <div class="chart-base-container" data-url="${ctx }/js/2-1-4.json">
                            <div class="chart" style="height: 480px;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="tzzs">
            <!-- 跳闸走势 -->
            <div class="row section nomarginLR" style="margin-right: 15px;">
                <!--tile-body-->
                <div class="tile-body">
                    <div class="col-md-12">
                        <div class="chart-base-container" data-url="${ctx }/js/2-1-5.json">
                            <div class="chart"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${ctx }/js/ess.js" type="text/javascript"></script>
<script src="${ctx }/js/Highcharts-zh.js" type="text/javascript"></script>
<script type="text/javascript">
    var index_options = {
        //承载力分析
        czlfx_option: function () {
            return {
                title: {
                    show: true,
                    x: 'center',
                    textAlign: "center",
                    text: '各区县本周作业任务承载力分析',
                    textStyle: {
                        color: "#6A6D70",
                        fontWeight: "normal",
                    }
                },
                toolbox: {
                    show: false
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    },
                    formatter: function (params) {
                        return params[0].name + '<br/>'
                            + params[0].seriesName + ' : ' + params[0].value + '<br/>'
                            + params[1].seriesName + ' : ' + (params[1].value + params[0].value);
                    }
                },
                // 网格
                grid: {
                    show: true,
                    backgroundColor: 'rgba(0,0,0,0)',
                    borderWidth: 1,
                    borderColor: '#F0F0F0',
                    x: 0,
                    y: 40,
                },
                legend: {
                    show: true,
                    x: 'right',
                    y: 'top',
                    right: 'left',
                    align: 'left',
                    orient: 'vertical',
                    textStyle: {
                        color: "#929292",
                        fontWeight: "bolder",
                    },
                    selectedMode: false,
                    data: ['核定能力', '超载任务']
                },
                calculable: true,
                xAxis: [{
                    type: 'category',
                    data: ["夹江", "峨眉山", "井研", "沙湾", "五通桥", "变电运维室", "运维检修部", "输电运检室", "变电检修室", "配电运检室"],
                    axisLabel: {
                        textStyle: {
                            color: '#929292',
                            fontSize: 16,
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            width: 1,
                            color: "#F0F0F0"
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            width: 1,
                            color: "#F0F0F0"
                        }
                    },
                    axisTick: {
                        lineStyle: {
                            width: 1,
                            color: "#F0F0F0"
                        }
                    }
                }],
                yAxis: [
                    {
                        type: 'value',
                        boundaryGap: [0, 0.1],
                        axisLabel: {
                            textStyle: {
                                color: '#929292',
                                fontSize: 16,
                            }
                        },
                        axisLine: {
                            lineStyle: {
                                width: 1,
                                color: "#F0F0F0"
                            }
                        },
                        splitLine: {
                            lineStyle: {
                                width: 1,
                                color: "#F0F0F0"
                            }
                        },
                        axisTick: {
                            lineStyle: {
                                width: 1,
                                color: "#F0F0F0"
                            }
                        }
                    }
                ],
                series: [
                    {
                        name: '核定能力',
                        type: 'bar',
                        stack: 'sum',
                        barCategoryGap: '50%',
                        itemStyle: {
                            normal: {
                                color: '#15CAB5',
                                barBorderColor: '#15CAB5',
                                barBorderWidth: 6,
                                barBorderRadius: 0,
                                label: {
                                    show: true, position: 'insideTop'
                                }
                            }
                        },
                        data: [12, 18, 15, 12, 18, 13, 14, 16, 12, 17],
                    },
                    {
                        name: '超载任务',
                        type: 'bar',
                        stack: 'sum',
                        itemStyle: {
                            normal: {
                                color: '#fff',
                                barBorderColor: 'tomato',
                                barBorderWidth: 6,
                                barBorderRadius: 0,
                                label: {
                                    show: true,
                                    position: 'top',
//                                    formatter: function (params) {
//                                        for (var i = 0, l = option0.xAxis[0].data.length; i < l; i++) {
//                                            if (option0.xAxis[0].data[i] == params.name) {
//                                                return option0.series[0].data[i] + params.value;
//                                            }
//                                        }
//                                    },
                                    textStyle: {
                                        color: 'tomato'
                                    }
                                }
                            }
                        },
                        data: [3, 0, 0, 0, 2, 0, 0, 0, 0, 0],
                    }
                ],
            }
        },
        //风险预警柱状图
        fxyj_option1: function () {
            return {
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
                    floating: false,
                    y: 30,
                },
                subtitle: {
                    text: '电网风险预警分析（本周）',
                    align: "center",
                    verticalAlign: "top",
                    style: {
                        color: '#6A6D70',
                        fontSize: '20px',
                        fontWeight: 'bold'
                    },
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
        },
        //风险预警环形图
        fxyj_option2: function () {
            return {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                toolbox: {
                    show: false,
                },
                xAxis: {
                    axisLabel: {
                        show: false,
                    },
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    }
                },
                series: [
                    {
                        name: '预警占比',
                        type: 'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data: [
                            {value: 1, name: '一级'},
                            {value: 2, name: '二级'},
                            {value: 4, name: '三级'},
                            {value: 3, name: '四级'},
                            {value: 5, name: '五级'},
                            {value: 2, name: '六级'},
                            {value: 4, name: '七级'},
                            {value: 5, name: '八级'}
                        ],
                    }
                ],
                color: ["#F96A6A", "#FF9146", "#FFC100", "#EBD3AD", "#B185F9", "#6ABBFF", "#7ADFC3", "#BDEB74"],
            }
        },
        //到岗到位
        dgdw_option: function () {
            return {
                title: {
                    show: true,
                    x: 'center',
                    textAlign: "center",
                    text: '各区县本周到岗到位统计',
                    textStyle: {
                        color: "#6A6D70",
                        fontWeight: "normal",
                    }
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    show: true,
                    x: 'right',
                    y: 'middle',
                    right: 'left',
                    align: 'left',
                    orient: 'vertical',
                    textStyle: {
                        color: "#929292",
                        fontWeight: "bolder",
                    },
                    selectedMode: true,
                    data: ['领导（正）', '领导（副）']
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {show: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                grid: {
                    left: 20,
                    right: 150,
                },
                calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        data: ["夹江", "峨眉山", "井研", "沙湾", "五通桥", "变电运维室", "运维检修部", "输电运检室", "变电检修室", "配电运检室"],
                        axisLabel: {
                            textStyle: {
                                color: "#929292",
                                fontSize: 14,
                            },
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisTick: {
                            show: false,
                        }
                    }
                ],
                series: [
                    {
                        name: '领导（正）',
                        type: 'bar',
                        data: [1, 2, 0, 2, 1, 1, 3, 1, 1, 2],
                        barWidth: 30,
                        markPoint: {
                            data: [
                                {name: '最高', value: 3, xAxis: "运维检修部", yAxis: 3, symbolSize: 50},
                                {name: '最低', value: 0, xAxis: "井研", yAxis: 0}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        name: '领导（副）',
                        type: 'bar',
                        barWidth: 30,
                        data: [3, 1, 3, 2, 2, 3, 3, 2, 4, 3],
                        markPoint: {
                            data: [
                                {name: '最高', value: 4, xAxis: "变电检修室", yAxis: 4, symbolSize: 50},
                                {name: '最低', value: 1, xAxis: "峨眉山", yAxis: 1}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            }
        },
        //违章情况
        wzqk_option: function () {
            return {
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
                    floating: false,
                    y: 30,
                },
                subtitle: {
                    text: '违章情况统计（本周）',
                    align: "center",
                    verticalAlign: "top",
                    style: {
                        color: '#6A6D70',
                        fontSize: '20px',
                        fontWeight: 'bold'
                    },
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
                colors: ["#FF9147", "#FFC000"],
            }
        },
        //跳闸走势
        tzzs_option: function () {
            return {
                chart: {
                    type: "area",
                    backgroundColor: "#fff"
                },
                title: {
                    floating: false,
                    y: 30,
                },
                subtitle: {
                    text: '跳闸次数统计',
                    align: "center",
                    verticalAlign: "top",
                    style: {
                        color: '#6A6D70',
                        fontSize: '20px',
                        fontWeight: 'bold'
                    },
                    y: 10,
                },
                xAxis: {
                    categories: ["2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月", "1月"],
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
                    enabled: false,
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
        },
        //作业计划
        zyjh_option: function () {
            return {
                tooltip: {
                    trigger: 'axis'
                },
                toolbox: {
                    show: true,
                    y: 'top',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {show: true},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                legend: {
                    data: ['计划', '新增停电', '新增非停电', '总数走势']
                },
                xAxis: [
                    {
                        type: 'category',
                        splitLine: {show: false},
                        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        position: 'right',
                        axisTick: {
                            show: false,
                        },
                    }
                ],
                series: [
                    {
                        name: '计划',
                        type: 'bar',
                        data: [10, 8, 11, 13, 12, 17, 13]
                    }, {
                        name: '新增非停电',
                        type: 'bar',
                        stack: '计划',
                        itemStyle: {
                            normal: {
                                color: "#5AB1EF",
                            },
                        },
                        data: [2, 5, 9, 3, 7, 4, 3]
                    }, {
                        name: '新增停电',
                        type: 'bar',
                        stack: '计划',
                        itemStyle: {
                            normal: {
                                color: "#FFB981",
                            },
                        },
                        data: [2, 2, 1, 2, 1, 1, 2]
                    }, {
                        name: '总数走势',
                        type: 'line',
                        itemStyle: {
                            normal: {
                                color: "#D97B81",
                            },
                        },
                        data: [14, 15, 21, 18, 20, 22, 18]
                    }, {
                        name: '供电所',
                        type: 'pie',
                        animation: true,
                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },
                        center: [150, 130],
                        radius: [0, 50],
                        itemStyle: {
                            normal: {
                                labelLine: {
                                    length: 20
                                }
                            }
                        }, data: [
                            {value: 14, name: '夹江'},
                            {value: 15, name: '峨眉山'},
                            {value: 21, name: '井研'},
                            {value: 18, name: '沙湾'},
                            {value: 20, name: '五通桥'},
                            {value: 22, name: '变电运维室'},
                            {value: 18, name: '运维检修部'},
                            {value: 22, name: '输电运检室'},
                            {value: 24, name: '变电检修室'},
                            {value: 20, name: '配电运检室'}
                        ]
                    }
                ]
            }
        }
    }
    $(function () {
        $(".bjui-pageContent").height($(window).height() - 120);
        $(window).resize(function () {
            $(".bjui-pageContent").height($(window).height() - 120);
        });
        var $container = $(".chart-base-container", $.CurrentNavtab);
        loadEchartDiv($($container[0]), index_options.zyjh_option());

        $('#myTab a[href="#zyjh"]').on('shown.bs.tab', function (e) {
            loadEchartDiv($($container[0]), index_options.zyjh_option());
        })

        $('#myTab a[href="#czlfx"]').on('shown.bs.tab', function (e) {
            loadEchartDiv($($container[1]), index_options.czlfx_option());
        })

        $('#myTab a[href="#fxyjj"]').on('shown.bs.tab', function (e) {
            loadHightDiv($($container[2]), index_options.fxyj_option1());
            loadEchartDiv($($container[3]), index_options.fxyj_option2());
        })

        $('#myTab a[href="#dgdw"]').on('shown.bs.tab', function (e) {
            loadEchartDiv($($container[4]), index_options.dgdw_option())
        })

        $('#myTab a[href="#wzqk"]').on('shown.bs.tab', function (e) {
            loadHightDiv($($container[5]), index_options.wzqk_option());
        })

        $('#myTab a[href="#tzzs"]').on('shown.bs.tab', function (e) {
            loadHightDiv($($container[6]), index_options.tzzs_option());
        })
    })
</script>