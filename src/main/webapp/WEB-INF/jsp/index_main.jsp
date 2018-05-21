<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>
<style>
    .chart-base-container .chart {
        display: block;
    }

    .nav_menu .col-md-2 a:hover {
        color: #fff
    }

    .nav_menu .col-md-2 a:lang(examine):hover {
        background: #00a2fa;
    }

    .nav_menu .col-md-2 a:lang(upload):hover {
        background: #ff5f5a;
    }

    .nav_menu .col-md-2 a:lang(meet):hover {
        background: #5f9fd7;
    }

    .nav_menu .col-md-2 a:lang(report):hover {
        background: #00b2d5;
    }

    .nav_menu .col-md-2 a:lang(archives):hover {
        background: #00cac0;
    }

    .nav_menu .col-md-2 a:lang(question):hover {
        background: #7168d3;
    }

</style>
<div class="bjui-pageContent fixedtableScroller" style=" padding-top: 20px; overflow: auto;">
    <div class="float-button" style="display:none;"><a href="${ctx}/index/employeeView?rname=${role.rname}"
                                                       data-toggle="navtab" data-id="view2-index"
                                                       data-title="管理员视图">视图2</a></div>
    <div class="row nomarginLR">
        <!--系统通知-->
        <div class="col-md-12 nopadding" style="padding-right: 15px">
            <div class="account-box border-radius padding12 white">
                <div class="head-pic">
                    <c:if test="${empty(loginUser.headpic) }">
                        <img src="${ctx }/images/icon_head_pic.png"/>
                    </c:if>
                    <c:if test="${not empty(loginUser.headpic) }">
                        <img src="${ctx }/kconf/file/downfile?type=hr&filename=${loginUser.headpic}"/>
                    </c:if>
                </div>
                <div class="info-box" style="float: none;">
                    <div class="base-info">
                        <!-- 当前有用户登录     -->
                        <h3 style="width:405px"> ${loginUser.uname}，欢迎您！</h3>
                        <ul>
                            <li><label>所在部门：</label><span>${_login_dept_key.dname }</span></li>
                            <%--<li><label>角色：</label><span>${role.rname}</span></li>--%>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="work-info">
                        <ul>
                            <li><img src="${ctx}/images/ic_grzx.png">
                                <a href="javascript:;" style="text-decoration:none">个人中心</a>
                            </li>
                            <li><img src="${ctx}/images/ic_password.png">
                                <a href="${ctx}/kconf/user/changePwd" data-toggle="dialog" style="text-decoration:none">密码修改</a>
                            </li>
                            <li><img src="${ctx}/images/ic_denglu.png"><label>最近登录:</label>
                                <span><fmt:formatDate value="${lastLoginInfo.lwhen}" type="both" dateStyle="medium"
                                                      timeStyle="medium"/></span></li>
                            <li><img src="${ctx}/images/ic_IP.png"><label>IP:</label>
                                <span>${lastLoginInfo.lwhere }</span></li>
                        </ul>
                        <div class="clearfix"></div>

                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
        <%--<div class="col-md-4" style="padding-left: 0">
            <div class="system-notice border-radius padding12 white">
                <div class="system-notice-wrap">
                    <h4>消息中心</h4>
                    <div class="message_center">
                        <c:forEach items="${messageCenters}" var="msgCenter">
                            <div class="fl" style="width: 25%; padding: 0px;">
                                <h4><a href="${ctx}${msgCenter.lvalue}" data-title="${msgCenter.remark}" data-toggle="navtab" data-id="${msgCenter.lkey}">${msgCenter.count}</a></h4>
                                <p>${msgCenter.meg_remark}</p>
                            </div>

                        </c:forEach>
                        <span class="clearfix"></span>
                    </div>
                </div>
                    <c:set var="end_time" value="${report_end_time}"/>
                    <fmt:parseDate value="${end_time }"  pattern="yyyy-MM-dd HH:mm" var="end_time"/>

                    <c:set var="start_time" value="${report_start_time}"/>
                    <fmt:parseDate value="${start_time }"  pattern="yyyy-MM-dd HH:mm" var="start_time"/>
                <div class="remind">
                    <c:if test="${not empty start_time && not empty end_time}">
                        <img src="${ctx}/images/ic_sbtx.png">计划上报时间:<fmt:formatDate value="${start_time}" pattern="MM月dd日 HH:mm"/>-<fmt:formatDate value="${end_time}" pattern="MM月dd日 HH:mm"/>
                    </c:if>
                    <c:if test="${ empty start_time ||  empty end_time}">
                        <img src="${ctx}/images/ic_sbtx.png">计划上报提醒
                    </c:if>
                </div>
            </div>
        </div>--%>
        <%--<div class="col-md-4" style="padding-left: 0">
            <div class="system-notice border-radius padding12 white">
                <div class="system-notice-wrap">
                    <h4>消息中心</h4>
                    <div class="message_center">

                            <div class="fl" style=" padding: 0px;height: 100px;color: red;font-size: 32px;">
                                &lt;%&ndash;<h4><a href="#" data-title="title" data-toggle="navtab" data-id="">事项</a></h4>
                                <p>内容</p>&ndash;%&gt;

                                <span style="line-height: 100px;">您有<a href="#" data-title="title" data-toggle="navtab" data-id=""> 10 </a>条待办事项需要立即处理！</span>
                            </div>
                        <span class="clearfix"></span>
                    </div>
                </div>
            </div>
        </div>--%>
    </div>
    <!-- 功能模块 -->
    <%--<div class="row nomarginLR fun_box_wrap nav_menu">

        <c:forEach items="${sys_home}" var="home" varStatus="s">
           <div class="col-md-2 <c:if test="${s.count==1}">nopadding</c:if>">
            <a class="fun-box" lang="${home.pid}" style="text-decoration:none"  data-id="${home.remark}" data-toggle="navtab"  href="${ctx}${home.lvalue}"><span class="icon_img ${home.pid}"></span><span class="item_name">${home.lkey}</span></a>
          </div>
        </c:forEach>

    </div>--%>

    <!-- 统计分析 -->
    <div class="row section nomarginLR" style="margin-right: 15px;margin-top: 20px;">
        <!--tile-header-->
        <div class="tile-header">
            <span>各类问题耗时占比图</span>
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
                                type="text" value="${sTime}" id="j_input_start_time" name="start_time" size="12"
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
                                type="text" value="${eTime}" id="j_input_end_time" name="start_time" size="12"
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
                    <button type="button" class="red-button search_auto1" data-icon="search" style="margin-top: 4px">
                        筛选
                    </button>
                    <button type="button" class="red-button search_week1" style="margin-top: 4px">本周</button>
                    <button type="button" class="red-button search_month1" style="...">本月</button>
                    <button type="button" class="red-button search_year1" style="...">本年</button>
                </div>

                <div id="container" style="height: 80%;margin-top: 20px;">

                </div>
                <div id="no_data" style="height: 80%;margin-top: 20px; display: none">
                    <p class="no_data" style="height: 186px;
        line-height: 186px;
        text-align: center;
        color: rgb(198,198,198);
        font-size: 25px;
        margin-bottom: 0">未查询到相关数据</p>
                </div>


            </div>
        </div>
    </div>
    <script src="${ctx }/js/ess.js" type="text/javascript"></script>
    <script type="text/javascript">
        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);
        var app = {};
        var type = "auto";
        var start_time = $("#j_input_start_time").val();
        var end_time = $("#j_input_end_time").val();
        option = null;
        $(function () {
            searchData(type, start_time, end_time);
            $(".search_auto1",$.CurrentNavtab).click(function () {
                start_time = $("#j_input_start_time").val();
                end_time = $("#j_input_end_time").val();
                type = "auto";
                searchData(type, start_time, end_time);
            });

            $(".search_week1").click(function () {
                type = "week";
                searchData(type, start_time, end_time);
            });
            $(".search_month1").click(function () {
                type = "month";
                searchData(type, start_time, end_time);
            });
            $(".search_year1").click(function () {
                type = "year";
                searchData(type, start_time, end_time);
            });

        });


        function searchData(type, start_time, end_time) {
            $.getJSON("${ctx }/app/ylog/ylogReportPie?type=" + type + "&start_time=" + start_time + "&end_time=" + end_time, function (result) {
                $("#j_input_start_time").attr("value", result.start_time);
                $("#j_input_end_time").attr("value", result.end_time);

                if (result.status == 'false') {
                    $("#container").hide();
                    $("#no_data").show();
                } else {
                    $("#container").show();
                    $("#no_data").hide();
                }
                option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    series: [
                        {
                            name: '耗时(H)',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: result.series,
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
            });
        }


    </script>
