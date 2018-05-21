<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>国网乐山电力作业计划报送平台</title>
    <link rel="stylesheet" href="${ctx}/ls/css/index.css">
    <script src="${ctx }/BJUI/js/jquery-1.7.2.min.js"></script>
</head>
<body>
<div class="platform_bg">
    <form action="${ctx}/login" method="post" id="loginess">
        <input type="hidden" name="username" value="${username}">
        <input type="hidden" name="password" value="${password}">
        <input type="hidden" name="tab_id">
    </form>
    <div class="platform_content">
        <div class="platform_head">
            <div style="flex:1">
                <img src="${ctx}/ls/image/login.png" alt="">
                <p class="platform_title">国网乐山供电公司作业计划报送平台</p>
                <div class="platform_classify">
                    <a href="${ctx}/tolscc?action=${lscc_server_login}/login">地县一体化调控平台</a>
                    <a href="${ctx}/${login_or_index}">安全风险管控平台</a>

                </div>
            </div>
            <!--<img src="${ctx}/ls/image/index_phone.png" alt="">-->
        </div>
        <div class="platform_function">
            <div class="platform_item">
                <a href="javascript:opreation('month_plan_draft_box-list','lscc');">
                    <img src="${ctx}/ls/image/icon_monthReport.png" alt="">
                    <p>月计划报送</p>
                </a>
            </div>
            <div class="platform_item">
                <a href="javascript:opreation('week_report_plan-list','');">
                    <img src="${ctx}/ls/image/icon_weekReport.png" alt="">
                    <p>周计划报送</p>
                </a>
            </div>

            <div class="platform_item">
                <a  href="javascript:opreation('temp_report_plan-list','');">
                    <img src="${ctx}/ls/image/icon_shortReport.png" alt="">
                    <p>临时计划报送</p>
                </a>
            </div>
            <div class="platform_item">
                <a   href="javascript:opreation('qx_report_plan-list','');">
                    <img src="${ctx}/ls/image/icon_urgencyReport.png" alt="">
                    <p>抢修计划报送</p>
                </a>
            </div>
            <div class="platform_item">
                <a href="javascript:opreation('month_plan_back-list','lscc');">
                    <img src="${ctx}/ls/image/icon_retreatMonth.png" alt="">
                    <p>被退回月计划</p>
                </a>
            </div>
            <div class="platform_item">
                <a   href="javascript:opreation('week_reject_plan-list','');">
                    <img src="${ctx}/ls/image/icon_retreatWeek.png" alt="">
                    <p>被退回周计划</p>
                </a>
            </div>
        </div>
        <p class="platform_copyright">Copyright 2017 四川嘉能佳电力集团有限责任公司 All Rights Reserved.    未经授权,严禁转载 蜀ICP备05028077号<br/>服务电话：028-85172418
        </p>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    var serverUrl = '${serverUrl}'
    var lscc_server_login = '${lscc_server_login}/login';  // 地县一体化调控平台登录地址
    var tolscc_url = serverUrl + '/tolscc?action=' + lscc_server_login;
    var login_or_index = '${login_or_index}';

    function loginess() {
        $('input[name=tab_id]').val('');
        $("#loginess").submit();
    }


    function opreation(param,type){
        if (type == 'lscc') {
            location.href = tolscc_url + "?tab_id=" + param;
        } else {
            location.href = serverUrl + "/" + login_or_index + "?tab_id=" + param;
        }

//        $('input[name=tab_id]').val(param);
//        if(type == 'lscc'){
//            $("#loginess").attr("action",lscc_server_login);
//        }
//        $("#loginess").submit();
    }

    // 切换到地县一体化调控平台
//    function loginlscc() {
//        $("#loginess").attr("action",lscc_server_login);
//        $('input[name=tab_id]').val('');
//        $("#loginess").submit();
//    }

</script>