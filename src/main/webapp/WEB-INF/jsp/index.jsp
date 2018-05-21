<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>

<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title><klookup:kv key="title" type="system_settings"/></title>

    <link rel="icon" type="image/x-icon" href="${ctx}/images/kingstone(32x32).ico" />

    <!-- bootstrap - css -->
    <link href="${ctx }/BJUI/themes/css/bootstrap.css" rel="stylesheet">
    <!-- core - css -->
    <link href="${ctx }/BJUI/themes/css/style.css" rel="stylesheet">
    <link href="${ctx }/BJUI/themes/blue/core.css" id="bjui-link-theme" rel="stylesheet">
    <!-- plug - css -->
    <link href="${ctx }/BJUI/plugins/kindeditor_4.1.10/themes/default/default.css" rel="stylesheet">
    <link href="${ctx }/BJUI/plugins/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
    <link href="${ctx }/BJUI/plugins/niceValidator/jquery.validator.css" rel="stylesheet">
    <link href="${ctx }/BJUI/plugins/bootstrapSelect/bootstrap-select.css" rel="stylesheet">
    <link href="${ctx }/BJUI/themes/css/FA/css/font-awesome.min.css" rel="stylesheet">
    <%--<link href="${ctx }/snaker/styles/fancybox/source/jquery.fancybox.css">--%>
    <%--<link href="${ctx }/snaker/styles/fancybox/source/helpers/jquery.fancybox-buttons.css">--%>
    <link rel="stylesheet" href="${ctx }/js/lightBox/source/jquery.fancybox.css">
    <link rel="stylesheet" href="${ctx }/js/lightBox/source/helpers/jquery.fancybox-buttons.css">
    <link rel="stylesheet" href="${ctx }/js/swiper/swiper.min.css">

	<link rel="stylesheet" href="${ctx }/BJUI/themes/css/homepage.css" />

    <!-- 自定义字体图片css -->
    <link rel="stylesheet" href="${ctx}/BJUI/themes/css/ksi-icon-font.css">
    <!-- 自定义ess.css -->
    <link rel="stylesheet" href="${ctx }/BJUI/themes/css/ess.css"/>
    <link rel="stylesheet" href="${ctx }/BJUI/themes/css/essv4.0.css"/>
    <!--[if lte IE 7]>
    <link href="${ctx }/BJUI/themes/css/ie7.css" rel="stylesheet">
    <![endif]-->
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lte IE 9]>
    <script src="${ctx }/BJUI/other/html5shiv.min.js"></script>
    <script src="${ctx }/BJUI/other/respond.min.js"></script>
    <![endif]-->
    <!-- jquery -->
    <script src="${ctx }/BJUI/js/jquery-1.7.2.min.js"></script>
    <script src="${ctx }/BJUI/js/jquery.cookie.js"></script>
    <!--[if lte IE 9]>
    <script src="${ctx }/BJUI/other/jquery.iframe-transport.js"></script>
    <![endif]-->
    <!-- BJUI.all 分模块压缩版 -->
    <%-- <script src="${ctx }/BJUI/js/bjui-all.js"></script> --%>
    <!-- 以下是B-JUI的分模块未压缩版，建议开发调试阶段使用下面的版本 -->
    <link rel="stylesheet" href="${ctx }/BJUI/jquery.fine-uploader/fine-uploader-gallery.min.css">
    <script src="${ctx }/BJUI/jquery.fine-uploader/jquery.fine-uploader.min.js" type="text/javascript"></script>
    <script src="${ctx }/BJUI/js/bjui-core.js"></script>
    <script src="${ctx }/BJUI/js/bjui-regional.zh-CN.js"></script>
    <script src="${ctx }/BJUI/js/bjui-frag.js"></script>
    <script src="${ctx }/BJUI/js/bjui-extends.js"></script>
    <script src="${ctx }/BJUI/js/bjui-basedrag.js"></script>
    <script src="${ctx }/BJUI/js/bjui-slidebar.js"></script>
    <script src="${ctx }/BJUI/js/bjui-contextmenu.js"></script>
    <script src="${ctx }/BJUI/js/bjui-navtab.js"></script>
    <script src="${ctx }/BJUI/js/bjui-dialog.js"></script>
    <script src="${ctx }/BJUI/js/bjui-taskbar.js"></script>
    <script src="${ctx }/BJUI/js/bjui-ajax.js"></script>
    <script src="${ctx }/BJUI/js/bjui-alertmsg.js"></script>
    <script src="${ctx }/BJUI/js/bjui-pagination.js"></script>
    <script src="${ctx }/BJUI/js/bjui-util.date.js"></script>
    <script src="${ctx }/BJUI/js/bjui-datepicker.js"></script>
    <script src="${ctx }/BJUI/js/bjui-ajaxtab.js"></script>
    <script src="${ctx }/BJUI/js/bjui-datagrid.js"></script>
    <script src="${ctx }/BJUI/js/bjui-tablefixed.js"></script>
    <script src="${ctx }/BJUI/js/bjui-tabledit.js"></script>
    <script src="${ctx }/BJUI/js/bjui-spinner.js"></script>
    <script src="${ctx }/BJUI/js/bjui-lookup.js"></script>
    <script src="${ctx }/BJUI/js/bjui-tags.js"></script>
    <script src="${ctx }/BJUI/js/bjui-upload.js"></script>
    <script src="${ctx }/BJUI/js/bjui-theme.js"></script>
    <script src="${ctx }/BJUI/js/bjui-initui.js"></script>
    <script src="${ctx }/BJUI/js/bjui-plugins.js"></script>

    <!-- plugins -->
    <!-- swfupload for uploadify && kindeditor -->
    <script src="${ctx }/BJUI/plugins/swfupload/swfupload.js"></script>
    <!-- kindeditor -->
    <script src="${ctx }/BJUI/plugins/kindeditor_4.1.10/kindeditor-all.min.js"></script>
    <script src="${ctx }/BJUI/plugins/kindeditor_4.1.10/lang/zh_CN.js"></script>
    <!-- colorpicker -->
    <script src="${ctx }/BJUI/plugins/colorpicker/js/bootstrap-colorpicker.min.js"></script>
    <!-- ztree -->
    <script src="${ctx }/BJUI/plugins/ztree/jquery.ztree.all-3.5.js"></script>
    <!-- nice validate -->
    <script src="${ctx }/BJUI/plugins/niceValidator/jquery.validator.js"></script>
    <script src="${ctx }/BJUI/plugins/niceValidator/jquery.validator.themes.js"></script>
    <!-- bootstrap plugins -->
    <script src="${ctx }/BJUI/plugins/bootstrap.min.js"></script>
    <script src="${ctx }/BJUI/plugins/bootstrapSelect/bootstrap-select.min.js"></script>
    <script src="${ctx }/BJUI/plugins/bootstrapSelect/defaults-zh_CN.min.js"></script>
    <!-- icheck -->
    <script src="${ctx }/BJUI/plugins/icheck/icheck.min.js"></script>
    <!-- dragsort -->
    <script src="${ctx }/BJUI/plugins/dragsort/jquery.dragsort-0.5.1.min.js"></script>
    <!-- HighCharts -->
    <script src="${ctx }/BJUI/plugins/highcharts/highcharts.js"></script>
    <script src="${ctx }/BJUI/plugins/highcharts/highcharts-3d.js"></script>
    <script src="${ctx }/BJUI/plugins/highcharts/modules/drilldown.js"></script>
    <script src="${ctx }/BJUI/plugins/highcharts/themes/gray.js"></script>
    <!-- ECharts -->
    <script src="${ctx }/BJUI/plugins/echarts/echarts.js"></script>
    <script src="${ctx }/BJUI/plugins/echarts/theme/macarons.js"></script>
    <!-- other plugins -->
    <script src="${ctx }/BJUI/plugins/other/jquery.autosize.js"></script>
    <link href="${ctx }/BJUI/plugins/uploadify/css/uploadify.css" rel="stylesheet">
    <script src="${ctx }/BJUI/plugins/uploadify/scripts/jquery.uploadify.min.js"></script>
    <script src="${ctx }/BJUI/plugins/download/jquery.fileDownload.js"></script>
     <script src="${ctx }/js/homepage.js"></script>


    <%--fancybox 大图查看工具--%>
    <%--<script src="${ctx }/snaker/styles/fancybox/source/jquery.fancybox.js"></script>--%>
    <%--<script src="${ctx }/snaker/styles/fancybox/source/helpers/jquery.fancybox-buttons.js"></script>--%>
    <script src="${ctx }/js/lightBox/source/jquery.fancybox.js"></script>
    <script src="${ctx }/js/lightBox/source/helpers/jquery.fancybox-buttons.js"></script>
    <script src="${ctx }/js/swiper/swiper.min.js"></script>

    <%--自定义jq拓展--%>
    <script src="${ctx }/js/jq_extend.js"></script>

    <!-- 自定义统计图chart.js -->
    <script src="${ctx }/js/chart.js" type="text/javascript"></script>
    <script src="${ctx}/js/mychart.js" type="text/javascript"></script>

    <script src="${ctx }/BJUI/form.js" type="text/javascript"></script>
    <%--时间选择器--%>
    <script src="${ctx }/js/moment.js"></script>
    <!-- init -->
    <script type="text/javascript">
        var resizeStop = true;
        var SERVER_CTX_KESS = "";

        $(function () {
            BJUI.init({
                JSPATH: '${ctx }/BJUI/',         //[可选]框架路径
                PLUGINPATH: '${ctx }/BJUI/plugins/', //[可选]插件路径
                loginInfo: {url: 'login_timeout.html', title: '登录', width: 400, height: 200}, // 会话超时后弹出登录对话框
                statusCode: {ok: 200, error: 300, timeout: 301}, //[可选]
                ajaxTimeout: 50000, //[可选]全局Ajax请求超时时间(毫秒)
                pageInfo: {
                    total: 'total',
                    pageCurrent: 'pageNumber',
                    pageSize: 'pageSize',
                    orderField: 'orderField',
                    orderDirection: 'orderDirection'
                }, //[可选]分页参数
                alertMsg: {displayPosition: 'topcenter', displayMode: 'slide', alertTimeout: 2000}, //[可选]信息提示的显示位置，显隐方式，及[info/correct]方式时自动关闭延时(毫秒)
                keys: {statusCode: 'statusCode', message: 'message'}, //[可选]
                ui: {
                    windowWidth: 0,    //框架可视宽度，0=100%宽，> 600为则居中显示
                    showSlidebar: true, //[可选]左侧导航栏锁定/隐藏
                    clientPaging: true, //[可选]是否在客户端响应分页及排序参数
                    overwriteHomeTab: false //[可选]当打开一个未定义id的navtab时，是否可以覆盖主navtab(我的主页)
                },
                debug: true,    // [可选]调试模式 [true|false，默认false]
                theme: 'sky' // 若有Cookie['bjui_theme'],优先选择Cookie['bjui_theme']。皮肤[五种皮肤:default, orange, purple, blue, red, green]
            })

            // main - menu
            $('#bjui-accordionmenu')
                .collapse()
                .on('hidden.bs.collapse', function (e) {
                    $(this).find('> .panel > .panel-heading').each(function () {
                        var $heading = $(this), $a = $heading.find('> h4 > a')

                        if ($a.hasClass('collapsed')) $heading.removeClass('active')
                    })
                })
                .on('shown.bs.collapse', function (e) {
                    $(this).find('> .panel > .panel-heading').each(function () {
                        var $heading = $(this), $a = $heading.find('> h4 > a')

                        if (!$a.hasClass('collapsed')) $heading.addClass('active')
                    })
                })

            $(document).on('click', 'ul.menu-items > li > a', function (e) {
                var $a = $(this), $li = $a.parent(), options = $a.data('options').toObj()
                var onClose = function () {
                    $li.removeClass('active')
                }
                var onSwitch = function () {
                    $('#bjui-accordionmenu').find('ul.menu-items > li').removeClass('switch')
                    $li.addClass('switch')
                }

                $li.addClass('active')
                if (options) {
                    options.url = $a.attr('href')
                    options.onClose = onClose
                    options.onSwitch = onSwitch
                    if (!options.title) options.title = $a.text()

                    if (!options.target)
                        $a.navtab(options)
                    else
                        $a.dialog(options)
                }

                e.preventDefault()
            })

            //时钟
//    var today = new Date(), time = today.getTime()
//    $('#bjui-date').html(today.formatDate('yyyy/MM/dd'))
//    setInterval(function() {
//        today = new Date(today.setSeconds(today.getSeconds() + 1))
//        $('#bjui-clock').html(today.formatDate('HH:mm:ss'))
//    }, 1000)


        })

        //菜单-事件
        function MainMenuClick(event, treeId, treeNode) {
            event.preventDefault()

            if (treeNode.isParent) {
                var zTree = $.fn.zTree.getZTreeObj(treeId)

                zTree.expandNode(treeNode, !treeNode.open, false, true, true)
                return
            }

            if (treeNode.target && treeNode.target == 'dialog')
                $(event.target).dialog({id: treeNode.tabid, url: treeNode.url, title: treeNode.name})
            else
                $(event.target).navtab({
                    id: treeNode.tabid,
                    url: treeNode.url,
                    title: treeNode.name,
                    fresh: treeNode.fresh,
                    external: treeNode.external
                })
        }
    </script>
    <style type="text/css">
        .center {
            text-align: center;
        }

        .table > thead > tr > th {
            height: 30px;
        }

        .fixedtableScroller::-webkit-scrollbar-track {
            -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
            background-color: #F5F5F5;
            border-radius: 10px;
        }

        .fixedtableScroller::-webkit-scrollbar {
            width: 5px;
            background-color: #F5F5F5;
        }

        .fixedtableScroller::-webkit-scrollbar-thumb {
            border-radius: 10px;
            background-color: #FFF;
            background-image: -webkit-linear-gradient(top, #e4f5fc 0%, #bfe8f9 50%, #9fd8ef 51%, #2ab0ed 100%);
        }

        .bjui-pageHeader {
            padding-right: 0px;
            padding-left: 0px;
            margin: 0px 20px 0px 0px;
            border-bottom:none;
        }

        .bjui-pageFooter {
            border-left: 1px solid #ccc;
        }

        .bjui-tablefixed .table {
            table-layout: fixed;
        }

        .bjui-pageContent {
            padding: 10px 0px;
        }

        #bjui-accordionmenu {
            position: relative;
            padding-top: 0px;
            z-index: 2;
        }
        .ess-modal-main-menu-wrap{height: 100%; overflow-y: auto;}
        .ess-modal-main-menu{ display: inline-block; padding-left: 100px; padding-top:50px; }
        .ess-main-menu{width:100%; padding-bottom: 100px; margin:0 auto;}
        .ess-menu-box{ float:left; display:inline-block; width:220px; margin-right:120px; }
        .ess-menu-box-wrap{ float:left; }
        .ess-menu-box.nofloat{display:block; float:none; }
        .ess-menu-box.nofloat .menu-box-title{padding-top:50px;}
        .sub-menu-contain{ float:left;}
        .sub-menu-contain.full .sub-menu-box{ width:210px; }
        .menu-box-title{ padding-bottom:10px; padding-left:10px; color: white; font-size: 16px;}
        .sub-menu-box{ width:100px; padding:20px 0px 6px 0px; margin:10px 0px 0px 10px; text-align:center; background:#98A2A1; }
        .sub-menu-icon{ width: 30px; height: 30px; margin-bottom:20px; }
        .sub-menu-icon.done{visibility: hidden;}
        .sub-menu-name{ display:block; text-align:left; padding-left:10px; font-size:14px; color:white; }
        .ess-modal-footer{position:absolute; bottom:0px; width:100%; padding:16px 0px; border: none; text-align:center;}
        .sub-menu-box.zyjh-bg{background: #2192FF;}
        .sub-menu-box.xckc-bg{background: #FF8042;}
        .sub-menu-box.xxts-bg{background: #39B54A;}
        .sub-menu-box.fxpg-bg{background: #76B2EC;}
        .sub-menu-box.bqh-bg{background: #FF72D9;}
        .sub-menu-box.zyry-bg{background:#39B54A}
        .sub-menu-box.aqgqj-bg{background:#FFAA30}
        .sub-menu-box.sgjj-bg{background:#5C8FC1}
        .sub-menu-box.wbdw-bg{background:#2192FF}
        .sub-menu-box.dgdw-bg{background:#B656CE}
        .sub-menu-box.bhh-bg{background:#FF8042}
        .sub-menu-box.xcjd-bg{background:#806FE7}
        .sub-menu-box.spjd-bg{background:#76B2EC}
        .sub-menu-box.zdxcbk-bg{background:#39B54A}
        .sub-menu-box.zhdt-bg{background:#B656CE}
        .sub-menu-box.tjbb-bg{background:#2192FF}
        .sub-menu-box.lhpj-bg{background:#F9BB14}
        .sub-menu-box.aqxx-bg{background:#FF8042}
        .sub-menu-box.dlzst-bg{background:#76B2EC}
        .sub-menu-box.agtk-bg{background:#39B54A}
        .sub-menu-box.dwfxyj-bg{background:#76B2EC}
        .sub-menu-box.zyfxyj-bg{background:#806FE7}
        .sub-menu-box.yjzhxt-bg{background:#39B54A}
        @media (max-width:1366px){
            .ess-menu-box{ margin-right:26px; }
            .ess-modal-main-menu{padding-left: 50px;}
        }

        .icon_img{
            width: 30px;
        }

        .ess-searchBar >div{
            height: 30px;
        }
        .exit_icon{
            display: flex;
            align-items: center;
            background: #0fc0ab;
            color: #fff;
            height: 100%;
            padding: 0 15px;
        }
        .exit_icon img{
            border-radius: 50%;
            width: 40px;
            height: 40px;
            margin-right: 12px;
        }
        .exit_icon a{
            color: #e7de6c;
            margin-left: 4px;
        }
        #bjui-hnav #bjui-hnav-navbar > li:hover .exit_icon a{
            line-height: 1;
            padding: 0;
            font-size: 12px;
            color: #d81045a3;
            text-decoration: underline;
        }

        .fancy_arr {
            cursor: pointer;
        }
    </style>
</head>
<body>
<!--[if lte IE 7]>
<div id="errorie">
    <div>您还在使用老掉牙的IE，正常使用系统前请升级您的浏览器到 IE8以上版本 <a target="_blank"
            href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-8-worldwide-languages">点击升级</a>&nbsp;&nbsp;强烈建议您更改换浏览器：<a
            href="http://down.tech.sina.com.cn/content/40975.html" target="_blank">谷歌 Chrome</a></div>
</div>
<![endif]-->
<div id="bjui-window">
    <header id="bjui-header" style="z-index: 1">
        <div class="bjui-navbar-header">
            <div class="ess-line" id="system-title" data-toggle="modal" data-target=".ess-wrap-model" style="cursor: pointer;position: relative;z-index: 10">
                    <%-- <kprop:prop key="title"/> --%>
                    <c:if test="${not empty(tab_id) && not empty(dk_rename)}">
                         ${dk_rename}
                    </c:if>
                        <c:if test="${empty(tab_id) || empty(dk_rename)}">

                            <klookup:kv key="title" type="system_settings" defaultValue="金信石运维管理系统"/>
                        </c:if>
            </div>
            
            <a class="bjui-navbar-logo" href="#" style="display:<klookup:kv key="logo_display" type="system_settings" defaultValue="none"/>   "> <img src="${ctx }/images/logo.png"></a>
             
        </div>
        <div id="bjui-hnav">
            <button type="button" class="btn-default bjui-hnav-more-left" title="导航菜单左移">
                <i class="fa fa-angle-double-left"></i>
            </button>
            <div id="bjui-hnav-navbar-box">
                <ul id="bjui-hnav-navbar">
                    <c:forEach items="${menus }" var="module" varStatus="pstatus">
                        <c:set var="active" value=""></c:set>
                        <c:if test="${pstatus.first }"><c:set var="active" value="active"></c:set></c:if>
                        <c:if test="${module.menuid ne 'lscc'}">
                            <li class="${active }">
                                <c:if test="${empty(module.murl) }">
                                    <a class="${module.icon}" href="javascript:;" data-toggle="slidebar">${module.mname }</a>
                                </c:if>
                                <c:if test="${!empty(module.murl) }">
                                    <a class="${module.icon}" href="${module.murl}" target="_blank">${module.mname }</a>
                                </c:if>
                                <div class="items hide" data-noinit="true">
                                    <c:forEach items="${module.parents}" var="parent" varStatus="parentStatus">
                                        <c:if test="${!empty(parent.children) }">
                                        <ul class="menu-items" data-faicon="edit" data-tit="${parent.mname}">
                                            <%--<p style="color: #99A1AC">请配置k_menu表中的菜单数据</p>--%>
                                            <c:forEach items="${parent.children }" var="sub">
                                                <c:if test="${sub.murl.contains('http')}">

                                                    <c:if test="${sub.murl.contains('{jcdwid}')}">
                                                        <li>
                                                            <a href="${sub.murl.replaceAll('\\{jcdwid\\}', jcdwid)}" data-options="{id:'${sub.menuid }-list', faicon:'fa fa-circle',external:true}">${sub.mname} </a>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${!sub.murl.contains('{jcdwid}')}">
                                                        <li>
                                                            <a href="${sub.murl}" data-options="{id:'${sub.menuid }-list', faicon:'fa fa-circle',external:true}">${sub.mname} </a>
                                                        </li>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${!sub.murl.contains('http')}">
                                                    <li>
                                                        <a href="${ctx }${sub.murl}" data-options="{id:'${sub.menuid }-list', faicon:'fa fa-circle'}">${sub.mname} </a>
                                                    </li>
                                                </c:if>

                                            </c:forEach>
                                        </ul>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </li>
                        </c:if>
                        <c:if test="${module.menuid eq 'lscc'}">
                            <li><a class="${module.icon} ic_lscc_a" href="${ctx }/"><i class=""></i>${module.mname }</a></li>
                        </c:if>
                    </c:forEach>
                    <li style="width: auto">
                        <%--<a class="ic_exit" href="${ctx }/logout"><i class=""></i>退出</a>--%>
                        <div class="exit_icon">
                                <c:if test="${empty(loginUser.headpic) }">
                                    <img src="${ctx }/images/icon_head_pic.png" />
                                </c:if>
                                <c:if test="${not empty(loginUser.headpic) }">
                                    <img src="${ctx }/kconf/file/downfile?type=hr&filename=${loginUser.headpic}" />
                                </c:if>
                            <div>
                                <p>${_login_dept_key.dname }</p>
                                <div>${loginUser.uname}<a href="${ctx }/logout">[退出]</a> </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <button type="button" class="btn-default bjui-hnav-more-right" title="导航菜单右移">
                <i class="fa fa-angle-double-right"></i>
            </button>
        </div>
        <form action="${lscc_server_login}" method="post" id="loginlscc" style="margin-bottom: 0">
            <input type="hidden" name="username" value="${loginUser.uaccount}">
            <input type="hidden" name="password" value="${loginUser.upwd}">
            <input type="hidden" name="tab_id">
        </form>
    </header>
    <div id="bjui-container">

        <div id="bjui-leftside">

            <div id="bjui-sidebar-s">
                <div class="collapse"></div>
            </div>

            <div id="bjui-sidebar">
                <div class="toggleCollapse"><a href="javascript:;" class="lock"><i class="fa fa-lock"></i></a></div>
                <div class="panel-group panel-main" data-toggle="accordion" id="bjui-accordionmenu">
                </div>
                <!--技术支持 姓名和电话-->
                <div class="hot-line">
                    <p><klookup:kv key="support_people" type="system_settings"/></p>
                    <span><klookup:kv key="support_phone" type="system_settings"/></span>
                </div>
            </div>

        </div>

        <div id="bjui-navtab" class="tabsPage">
            <div class="tabsPageHeader" >
                <div class="tabsPageHeaderContent" style="left: 15px;">
                    <ul class="navtab-tab nav nav-tabs ">
                        <li data-url="${ctx }/index/main"><a href="javascript:;"><span><i class="fa fa-home"></i> #maintab#</span></a>
                        </li>
                    </ul>
                </div>
                <div class="tabsLeft"><i class="fa fa-angle-double-left"></i></div>
                <div class="tabsRight"><i class="fa fa-angle-double-right"></i></div>
                <div class="tabsMore"><i class="fa fa-angle-double-down"></i></div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">#maintab#</a></li>
            </ul>
            <div class="navtab-panel tabsPageContent">
                <div class="navtabPage unitBox" style="background:#ECEFF2;">
                    <div class="bjui-pageContent">
                        Loading...
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade ess-wrap-model" tabindex="-1" role="dialog" id="system-menu-model"
            aria-labelledby="myLargeModalLabel" style="top: 90px; overflow: hidden;">
        <div class="modal-dialog modal-lg" style="width: auto; margin-top:0px;">
            <div class="modal-content wrap-contain" style="border-radius: 0px; box-shadow: none; border: none;  background-color: rgba(0, 0, 0, 0.8)">
                <div class="modal-body" style="padding: 0px;">
                    <div class="ess-modal-main-menu-wrap">
                    <div class="ess-modal-main-menu">
                        <div class="ess-main-menu">
                            <div class="ess-menu-box-wrap">
                                <!-- 作业计划 -->
                                <div class="ess-menu-box">
                                    <div class="menu-box-title">作业计划</div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box zyjh-bg">
                                            <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_zyjh.png">
                                            <span class="sub-menu-name">作业计划</span>
                                        </div>
                                    </div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box">
                                            <img class="sub-menu-icon done" src="">
                                            <span class="sub-menu-name">对接D5000</span>
                                        </div>
                                    </div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box">
                                            <img class="sub-menu-icon done" src="">
                                            <span class="sub-menu-name">对接OMS</span>
                                        </div>
                                    </div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box xxts-bg">
                                            <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_dxts.png">
                                            <span class="sub-menu-name">短信推送</span>
                                        </div>
                                    </div>
                                </div>
                                <div style="clear:both;"></div>
                                <!-- 作业准备 -->
                                <div class="ess-menu-box nofloat">
                                    <div class="menu-box-title">作业准备</div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box xckc-bg">
                                            <img class="sub-menu-icon" style="width: 40px;" src="${ctx}/images/ic_menu_item_xckc.png">
                                            <span class="sub-menu-name">现场勘察</span>
                                        </div>
                                    </div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box fxpg-bg">
                                            <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_fxpg.png">
                                            <span class="sub-menu-name">风险评估</span>
                                        </div>
                                    </div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box">
                                            <img class="sub-menu-icon done" src="">
                                            <span class="sub-menu-name">承载力分析</span>
                                        </div>
                                    </div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box">
                                            <img class="sub-menu-icon done" src="">
                                            <span class="sub-menu-name">*三措*管理</span>
                                        </div>
                                    </div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box">
                                            <img class="sub-menu-icon done" src="">
                                            <span class="sub-menu-name">*两票*管理</span>
                                        </div>
                                    </div>
                                    <div class="sub-menu-contain">
                                        <div class="sub-menu-box bqh-bg">
                                            <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_bqh.png">
                                            <span class="sub-menu-name">班前会</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- 作业实施 -->
                            <div class="ess-menu-box">
                                <div class="menu-box-title">作业实施</div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box">
                                        <img class="sub-menu-icon done" src="">
                                        <span class="sub-menu-name">倒闸操作</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box">
                                        <img class="sub-menu-icon done" src="">
                                        <span class="sub-menu-name">安全措施布置</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box">
                                        <img class="sub-menu-icon done" src="">
                                        <span class="sub-menu-name">许可开工</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box">
                                        <img class="sub-menu-icon done" src="">
                                        <span class="sub-menu-name">安全交底</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box zyry-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_zyry.png">
                                        <span class="sub-menu-name">作业人员</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box aqgqj-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_aqgqj.png">
                                        <span class="sub-menu-name">安全工器具</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box sgjj-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_sgjj.png">
                                        <span class="sub-menu-name">施工机具</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box wbdw-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_wbdw.png">
                                        <span class="sub-menu-name">外包队伍</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box">
                                        <img class="sub-menu-icon done" src="">
                                        <span class="sub-menu-name">作业监护</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box dgdw-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_dgdw.png">
                                        <span class="sub-menu-name">到岗到位</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box">
                                        <img class="sub-menu-icon done" src="">
                                        <span class="sub-menu-name" style="letter-spacing:-2px;">验收及工作终结</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box bhh-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_bhh.png">
                                        <span class="sub-menu-name">班后会</span>
                                    </div>
                                </div>
                            </div>

                            <!-- 监督考核 -->
                            <div class="ess-menu-box">
                                <div class="menu-box-title">监督考核</div>
                                <div class="sub-menu-contain full">
                                    <div class="sub-menu-box xcjd-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_xcjd.png">
                                        <span class="sub-menu-name">现场监督</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box spjd-bg">
                                        <img class="sub-menu-icon" style="width: 40px;" src="${ctx}/images/ic_menu_item_spjd.png">
                                        <span class="sub-menu-name">视频监督</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box zdxcbk-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_zdxcbk.png">
                                        <span class="sub-menu-name">重点现场布控</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain full">
                                    <div class="sub-menu-box zhdt-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_zhdt.png">
                                        <span class="sub-menu-name">指挥大厅</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box tjbb-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_tjbb.png">
                                        <span class="sub-menu-name">统计报表</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box lhpj-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_lhpj.png">
                                        <span class="sub-menu-name">量化评价</span>
                                    </div>
                                </div>
                            </div>

                            <!-- 安全教育培训 -->
                            <div class="ess-menu-box">
                                <div class="menu-box-title">安全教育培训</div>
                                <div class="sub-menu-contain full">
                                    <div class="sub-menu-box aqxx-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_aqxx.png">
                                        <span class="sub-menu-name">安全学习</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box dlzst-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_dlzst.png">
                                        <span class="sub-menu-name">电力知识通</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box">
                                        <img class="sub-menu-icon done" src="">
                                        <span class="sub-menu-name">安全规程库</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box agtk-bg">
                                        <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_agtk.png">
                                        <span class="sub-menu-name">安规调考</span>
                                    </div>
                                </div>
                                <div class="sub-menu-contain">
                                    <div class="sub-menu-box">
                                        <img class="sub-menu-icon done" src="">
                                        <span class="sub-menu-name">危险源辨识库</span>
                                    </div>
                                </div>
                            </div>
                            <div class="ess-menu-box-wrap">
                                <!-- 风险预警管理 -->
                                <div class="ess-menu-box">
                                    <div class="menu-box-title">风险预警管理</div>
                                    <div class="sub-menu-contain full">
                                        <div class="sub-menu-box dwfxyj-bg">
                                            <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_dwfxyj.png">
                                            <span class="sub-menu-name">电网风险预警</span>
                                        </div>
                                    </div>
                                    <div class="sub-menu-contain full">
                                        <div class="sub-menu-box zyfxyj-bg">
                                            <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_zyfxyj.png">
                                            <span class="sub-menu-name">作业风险预警</span>
                                        </div>
                                    </div>
                                </div>
                                <div style="clear:both;"></div>
                                <!-- 应急管理 -->
                                <div class="ess-menu-box nofloat">
                                    <div class="menu-box-title">应急管理</div>
                                    <div class="sub-menu-contain full">
                                        <div class="sub-menu-box yjzhxt-bg">
                                            <img class="sub-menu-icon" src="${ctx}/images/ic_menu_item_yjzhxt.png">
                                            <span class="sub-menu-name">应急指挥</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div style="clear:both;"></div>

                        </div>
                    </div>
                    <div style="clear: both;"></div>
                    <div class="modal-footer ess-modal-footer"><img id="sys-menu" src="${ctx }/images/icon_up.png" data-dismiss="modal" alt="" style="width: 20px;cursor: pointer"></div>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
//    if(sessionStorage.getItem('isFirst') == null || sessionStorage.getItem('isFirst') == undefined){
//        window.onload = function () {
//            $("#system-title").click();
//        }
//    }
//    //只有第一次加载三秒自动消失系统菜单
//    setTimeout(function () {
//        $("#sys-menu").click();
//        sessionStorage.setItem('isFirst', 'no');
//    }, 3000);

    $(function () {
        //默认显示系统菜单
        window.onresize = function () {
            var $width = parseInt($(document).width());
            if ($width < 1550) {
                $('.wrap-contain').css({'width': $("body").width()+ "px"})
            }
        };
       $(window).trigger('resize');

        $('#system-menu-model').on('shown.bs.modal', function (e) {

        });

        // $(window).resize(function () {
        //     setTimeout(function () {
        //         var $node = $('.tableS_init');
        //         var height = $node.height();
        //         var wrapH = $node.closest('.wrap_tables ').height();
        //         $node.height(wrapH - 175)
        //     }, 200)
        // });

        //页面宽度小于800，隐藏侧边栏
        setTimeout(function () {
            if ($(window).width() < 1300) {
                $('#bjui-sidebar ul li:first a').click();
                $('#bjui-sidebar .lock').click();
            }

        }, 100);

        tab_id = '${tab_id}';

        $('.navtabPage:first').on(BJUI.eventType.afterInitUI, function (e) {
            if (tab_id) {
                setTimeout(function () {
                    $('.menu-items li a').each(function () {
                        var options = $(this).data("options");
                        if (!options) return;
                        if (options.indexOf(tab_id) > '-1') {
                            $(this).parents(".panel-collapse").prev('.panel-heading').find('a').click();
                            $(this).click();
                            tab_id = null;
                        }
                    });
                })
            }
        });

        // 切换到安全风险管控平台
        $(".ic_lscc_a").on("click", function(){
            $("#loginlscc").submit();
        });

    })
</script>
</html>