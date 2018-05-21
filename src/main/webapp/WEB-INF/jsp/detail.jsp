<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>
<div class="bjui-pageContent tableContent  white ess-pageContent"
        style="background-color: #eceff2;padding-top: 30px;padding-bottom: 30px;overflow: auto;">
    <div class="ess-wrap-auto">
        <div class="ess-title">月计划信息<a>查看详情>></a></div>
        <div class="detail-l">
            <span>计划编号</span>
            <p>主Y1701001</p>
        </div>

        <div class="detail-l">
            <span>报送单位</span>
            <p>配电运检室</p>
        </div>

        <div class="detail-l">
            <span>停电设备及停电范围</span>
            <p>主Y1701001</p>
        </div>

        <div class="detail-l">
            <span>检修内容</span>
            <p>100KV刀闸检修</p>
        </div>
    </div>

    <div class="ess-wrap-auto">
        <div class="ess-title">周计划信息<a>查看详情>></a></div>
        <div class="detail-l dashed">
            <span>报送部门</span>
            <p>配电运检室</p>
        </div>

        <div class="detail-l dashed">
            <span>任务名称</span>
            <p>10KV关牟线25A#杆开关后段</p>
        </div>

        <div class="detail-l dashed">
            <span>工作内容</span>
            <p>10KV关牟线25A#杆开关后段</p>
        </div>

        <div class="detail-l dashed">
            <span>工作类型</span>
            <p>农网作业</p>
        </div>

        <div class="detail-l">
            <span>计划开工时间</span>
            <p>2017-01-02 8:30:00</p>
        </div>

        <div class="detail-l">
            <span>计划收工时间</span>
            <p>2017-01-02 8:30:00</p>
        </div>

        <div class="detail-l">
            <span>工作负责人及联系方式</span>
            <p>张三 13981175265</p>
        </div>

        <div class="detail-l">
            <span>电网风险评级</span>
            <p>五级</p>
        </div>
    </div>

    <div class="ess-wrap-auto">
        <div class="ess-title">风险信息</div>
        <div class="detail-l dashed">
            <span>单号</span>
            <p><a>第20160906号</a></p>
        </div>

        <div class="detail-l dashed">
            <span>等级</span>
            <p>五级</p>
        </div>

        <div class="detail-l dashed">
            <span>时段</span>
            <p>2016-10-24到2013-10-31</p>
        </div>

        <div class="detail-l dashed">
            <span>风险分析</span>
            <p>静云站220KV单母运行，故障后将导致静云站全站失压</p>
        </div>

        <div class="detail-l" style="width: 100%;">
            <span>主送部门</span>
            <div class="detail-fx">
                <div class="detail-fx-active"><i></i>调度中心</div>
                <div><i></i>安监部</div>
                <div class="detail-fx-active"><i></i>运检部</div>
                <div><i></i>营销部</div>
            </div>
        </div>
    </div>


    <div class="ess-wrap-auto" style="padding-bottom: 70px">
        <div class="ess-title">作业准备</div>
        <div style="position: relative;padding-top: 50px">
            <div class="detail-yuan-info">
                <p class="green-active-f">2016-09-01</p>
                <span class="green-active-b"></span>
                <p class="green-active-f" style="margin-top: 10px">现场勘察<br>（肖鹏、李晓锋）</p>
            </div>

            <div class="detail-yuan-info" style="left: 20%">
                <p class="green-active-f">2016-09-02</p>
                <span class="green-active-b"></span>
                <p class="green-active-f" style="margin-top: 10px">风险评估<br>（肖鹏、李晓锋）</p>
            </div>

            <div class="detail-yuan-info" style="left: 40%">
                <p class="blue-active-f">2016-09-04</p>
                <span class="blue-active-b"></span>
                <p class="blue-active-f" style="margin-top: 10px">承载力分析<br>（肖鹏、李晓锋）</p>
            </div>

            <div class="detail-yuan-info" style="left: 60%">
                <p>&nbsp;</p>
                <span></span>
                <p style="margin-top: 10px">"三措"编制</p>
            </div>

            <div class="detail-yuan-info" style="left: 80%">
                <p>&nbsp;</p>
                <span></span>
                <p style="margin-top: 10px">"两票"填写</p>
            </div>

            <div class="detail-yuan-info" style="left: 95%">
                <p>&nbsp;</p>
                <span></span>
                <p style="margin-top: 10px">班前会</p>
            </div>
            <div class="detail-work-line"></div>
        </div>
    </div>
    <div class="ess-wrap-auto">
        <div class="ess-title">作业实施</div>
        <div class="ess-task">
            <ul>
                <li class="task-finish">
                    <div class="ess-task-img"><img src="${ctx}/images/ic-task-img.png"></div>
                    <div class="ess-task-status">倒闸操作</div>
                </li>
                <li class="task-finish">
                    <div class="ess-task-img"><img src="${ctx}/images/ic-task-img.png"></div>
                    <div class="ess-task-status">安全布置措置</div>
                </li>
                <li class="task-finish">
                    <div class="ess-task-img"><img src="${ctx}/images/ic-task-img.png"></div>
                    <div class="ess-task-status">许可开工</div>
                </li>
                <li class="task-finish">
                    <div class="ess-task-img"><img src="${ctx}/images/ic-task-img.png"></div>
                    <div class="ess-task-status">安全交底</div>
                </li>
                <li class="task-unfinish">
                    <div class="ess-task-img"></div>
                    <div class="ess-task-status">现存作业</div>
                </li>
                <li class="task-unfinish">
                    <div class="ess-task-img"></div>
                    <div class="ess-task-status">作业监护</div>
                </li>
                <li class="task-unfinish">
                    <div class="ess-task-img"></div>
                    <div class="ess-task-status">当岗到位</div>
                </li>
                <li class="task-finish">
                    <div class="ess-task-img"><img src="${ctx}/images/ic-task-img.png"></div>
                    <div class="ess-task-status">验收级工作总结</div>
                </li>
                <li class="task-unfinish">
                    <div class="ess-task-img"></div>
                    <div class="ess-task-status">班后会</div>
                </li>
            </ul>
            <span class="clearfix"></span>
        </div>
    </div>
    <div class="row" style="margin: 0px 4px;">
        <div class="col-md-9 nopadding">
            <div class="ess-wrap-auto">
                <div class="ess-title" style="position: relative;">现场监督<span class="ess-title-button"
                        data-container="body" data-toggle="popover" data-placement="top">违</span></div>
                <div class="ess-supervise">
                    <div class="col-md-3">
                        <h5>监督人员</h5>
                        <div class="person-name">张三</div>
                    </div>
                    <div class="col-md-3">
                        <h5>到岗人员</h5>
                        <div class="person-name">李四</div>
                    </div>
                    <div class="col-md-3">
                        <h5>全景照</h5>
                        <div class="supervise-img"><img src="${ctx}/images/ic_sup_img.png"><img
                                src="${ctx}/images/ic_sup_img.png"></div>
                    </div>
                    <div class="col-md-3">
                        <h5>工作票</h5>
                        <div class="supervise-img"><img src="${ctx}/images/ic_sup_img.png"><img
                                src="${ctx}/images/ic_sup_img.png"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
        <div class="col-md-3" style="padding-right: 12px;">
            <div class="ess-wrap-auto">
                <div class="ess-title">评价卡<a data-toggle="modal" data-target="#myModal">查看评价详情>></a></div>
                <div class="row ess-card">
                    <div class="col-md-6 text-center">
                        <div class="ess-card-bg">
                            <h4>A级</h4>
                            <p>评分等级</p>
                        </div>
                    </div>
                    <div class="col-md-6 text-center">
                        <div class="ess-card-bg">
                            <h4>94分</h4>
                            <p>得分</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 变更日志 -->
    <div class="change-log-window">
        <span class="switch-button"><img class="off" src="${ctx}/images/ic_log-right-arrow.png"><img class="on"
                src="${ctx}/images/ic-log-left-arrow.png"></span>
        <div class="log-main">
            <div class="log-title">变更日志</div>
            <div class="log-content">
                <div class="log-change-item">
                    <div class="log-change-item-col fl">
                        <label>类型：</label><span>上报</span>
                    </div>
                    <div class="log-change-item-col">
                        <label>人员：</label><span>张三</span>
                    </div>
                    <div class="log-change-item-col">
                        <label>时间：</label><span>2017-01-02 09:30</span>
                    </div>
                    <div class="log-change-item-col">
                        <label>内容：</label><span>审核通过</span>
                    </div>
                </div>
                <div class="log-change-item">
                    <div class="log-change-item-col fl">
                        <label>类型：</label><span>审核</span>
                    </div>
                    <div class="log-change-item-col">
                        <label>人员：</label><span>肖鹏</span>
                    </div>
                    <div class="log-change-item-col">
                        <label>时间：</label><span>2017-01-02 09:30</span>
                    </div>
                    <div class="log-change-item-col">
                        <label>内容：</label><span>审核通过</span>
                    </div>
                </div>
                <div class="log-change-item">
                    <div class="log-change-item-col fl">
                        <label>类型：</label><span>上报</span>
                    </div>
                    <div class="log-change-item-col">
                        <label>人员：</label><span>张三</span>
                    </div>
                    <div class="log-change-item-col">
                        <label>时间：</label><span>2017-01-02 09:30</span>
                    </div>
                    <div class="log-change-item-col">
                        <label>内容：</label><span>审核通过</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade card-modal" id="myModal" style="margin-top: 80px;">
    <div class="modal-dialog" style="width: 800px; ">
        <div class="modal-content" style="border: none;">
            <div class="modal-header" style="border-radius: 4px 4px 0px 0px; border: none;">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title text-center">评价卡</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="type-wrap">
                            <span class="question">问</span>
                            图省事不从专业爬梯上下
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="type-wrap">
                            <span class="illegal" data-trigger="click" data-container="body" data-toggle="popover"
                                    data-placement="auto" data-html="true">违</span>
                            外来队伍人员未参加本次作业班前会
                        </div>
                    </div>
                </div>
                <!-- 扣分项 -->
                <div class="panel-group kfx-collapse" id="accordion" role="tablist" aria-multiselectable="true"
                        style="margin-top: 30px;">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <span class="plan-title-arrow actived" data-toggle="collapse" data-parent="#accordion"
                                        href="#collapseOne" aria-expanded="true" aria-controls="collapseOne"></span>
                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                                        aria-expanded="true" aria-controls="collapseOne">
                                    是否对现场作业进行勘察
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel"
                                aria-labelledby="headingOne">
                            <div class="panel-body">
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                                <a href="#">查勘内容不全或不一致一处扣2分</a>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
                                    aria-expanded="false" aria-controls="collapseTwo">
                                <span class="plan-title-arrow"></span>
                                <a class="collapsed">
                                    工作负责人和工作班成员
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel"
                                aria-labelledby="headingTwo">
                            <div class="panel-body">
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title">
                                <span class="plan-title-arrow" data-toggle="collapse" data-parent="#accordion"
                                        href="#collapseThree" aria-expanded="false"
                                        aria-controls="collapseThree"></span>
                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion"
                                        href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                    临时用工
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                                aria-labelledby="headingThree">
                            <div class="panel-body">
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFour">
                            <h4 class="panel-title">
                                <span class="plan-title-arrow" data-toggle="collapse" data-parent="#accordion"
                                        href="#collapseFour" aria-expanded="false" aria-controls="collapseThree"></span>
                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion"
                                        href="#collapseFour" aria-expanded="false" aria-controls="collapseThree">
                                    现存到岗到位情况
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel"
                                aria-labelledby="headingThree">
                            <div class="panel-body">
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFive">
                            <h4 class="panel-title">
                                <span class="plan-title-arrow" data-toggle="collapse" data-parent="#accordion"
                                        href="#collapseFive" aria-expanded="false" aria-controls="collapseThree"></span>
                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion"
                                        href="#collapseFive" aria-expanded="false" aria-controls="collapseThree">
                                    工作票制度执行
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel"
                                aria-labelledby="headingThree">
                            <div class="panel-body">
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                                <a href="#">未组织进行勘察扣10分（无勘察报告）</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 签字 -->
                <div class="row sign">
                    <div class="col-md-6">
                        <h5>监督负责人签字</h5><br/>
                        <img class="sign-img" src="${ctx}/images/ic_single_img.png">
                    </div>
                    <div class="col-md-6">
                        <h5>被监督负责人签字</h5><br/>
                        <img class="sign-img" src="${ctx}/images/ic_single_img.png">
                    </div>
                </div>
            </div>

        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 违章详情 -->
<div id="illegal-modal-window" style="display:none;">
    <div class="illegal-details-window">
        <div class="illegal-detail">
            <div class="illegal-detail-title">违章详情</div>
            <div class="illegal-detail-main">
                <ul>
                    <li class="illegal-item">
                        <span class="illegal-item-label">违章事由：</span><span
                            class="illegal-item-content">外来队伍人员未参加本次作业班前会</span>
                    </li>
                    <li class="illegal-item">
                        <span class="illegal-item-label">违章类别：</span><span class="illegal-item-content">行为违章</span>
                    </li>
                    <li class="illegal-item">
                        <span class="illegal-item-label">违章性质：</span><span class="illegal-item-content">严重违章</span>
                    </li>
                    <%--<li class="illegal-item">
                        <span class="illegal-item-label">违章层面：</span><span class="illegal-item-content">管理层</span>
                    </li>--%>
                    <li class="illegal-item">
                        <span class="illegal-item-label">处罚：</span><span class="illegal-item-content">扣6分</span>
                    </li>
                    <li class="illegal-item">
                        <span class="illegal-item-label">问题原因：</span><span class="illegal-item-content">（主观）学习不力</span>
                    </li>
                    <li class="illegal-item">
                        <span class="illegal-item-label">违章人员：</span><span class="illegal-item-content">周春来</span>
                    </li>
                    <li class="illegal-item" style="width:50%">
                        <span class="illegal-item-label">违章照相：</span><span class="illegal-item-content"><img
                            src="${ctx}/images/ic_illegal_img.png"><img src="${ctx}/images/ic_illegal_img.png"></span>
                    </li>
                    <li class="illegal-item" style="width:50%">
                        <span class="illegal-item-label">违章摄像：</span><span class="illegal-item-content"><img
                            src="${ctx}/images/ic_video_thumb.png"><img src="${ctx}/images/ic_video_thumb.png"></span>
                    </li>
                </ul>
                <div class="clearfix"></div>
            </div>
        </div>
        <div class="illegal-detail" style="border-bottom: none; padding-top: 28px;">
            <div class="illegal-detail-title">整改详情</div>
            <div class="illegal-detail-main">
                <ul>
                    <li class="illegal-item">
                        <span class="illegal-item-label">整改情况：</span><span class="illegal-item-content">完成整改</span>
                    </li>
                    <li class="illegal-item" style="width: 80%;">
                        <span class="illegal-item-label">整改备注：</span><span class="illegal-item-content">无</span>
                    </li>
                    <li class="illegal-item" style="width:50%">
                        <span class="illegal-item-label">整改照相：</span><span class="illegal-item-content"><img
                            src="${ctx}/images/ic_illegal_img.png"><img src="${ctx}/images/ic_illegal_img.png"></span>
                    </li>
                    <li class="illegal-item" style="width:50%">
                        <span class="illegal-item-label">整改摄像：</span><span class="illegal-item-content"><img
                            src="${ctx}/images/ic_video_thumb.png"><img src="${ctx}/images/ic_video_thumb.png"></span>
                    </li>
                </ul>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        //违章详情
        $("[data-toggle='popover']").popover({
            html: true,
            content: $("#illegal-modal-window").html()
        });

        $(".plan-title-arrow").click(function () {
            if ($(this).hasClass("actived")) {
                $(this).removeClass("actived");
            } else {
                $(this).addClass("actived");
                $(this).parents(".panel").siblings().find(".plan-title-arrow").removeClass("actived");
            }
        })
        $(".collapsed").click(function () {
            if ($(this).prev().hasClass("actived")) {
                $(this).prev().removeClass("actived");
            } else {
                $(this).prev().addClass("actived");
                $(this).parents(".panel").siblings().find(".plan-title-arrow").removeClass("actived");
            }
        })
        //变更日志
        $(".switch-button .off").click(function () {
            $(this).hide().siblings().show();
            $(".change-log-window").animate({"right":"-254px"},300);
        })
        $(".switch-button .on").click(function () {
            $(this).hide().siblings().show();
            $(".change-log-window").animate({"right":"0px"},500)
        })
        //查看大图
//            $("#auto-loop").lightGallery({
//                loop:true,
//                auto:true,
//                pause:4000
//            });

    });
</script>