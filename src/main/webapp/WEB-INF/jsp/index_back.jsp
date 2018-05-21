<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ include file="taglib.jsp" %> 

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><kprop:prop key="title"/> </title>
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

<!-- 自定义ess.css -->
<link rel="stylesheet" href="${ctx }/BJUI/themes/css/ess.css" />

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
<script src="${ctx }/BJUI/plugins/highcharts/themes/gray.js"></script>
<!-- ECharts -->
<script src="${ctx }/BJUI/plugins/echarts/echarts-back.js"></script>
<!-- other plugins -->
<script src="${ctx }/BJUI/plugins/other/jquery.autosize.js"></script>
<link href="${ctx }/BJUI/plugins/uploadify/css/uploadify.css" rel="stylesheet">
<script src="${ctx }/BJUI/plugins/uploadify/scripts/jquery.uploadify.min.js"></script>
<script src="${ctx }/BJUI/plugins/download/jquery.fileDownload.js"></script>

<!-- 自定义统计图chart.js -->
<script src="${ctx }/js/chart.js" type="text/javascript"></script>
 
<script src="${ctx }/BJUI/form.js" type="text/javascript"></script>

<!-- init -->
<script type="text/javascript">

var SERVER_CTX_KESS="";

$(function() {
    BJUI.init({
        JSPATH       : '${ctx }/BJUI/',         //[可选]框架路径
        PLUGINPATH   : '${ctx }/BJUI/plugins/', //[可选]插件路径
        loginInfo    : {url:'login_timeout.html', title:'登录', width:400, height:200}, // 会话超时后弹出登录对话框
        statusCode   : {ok:200, error:300, timeout:301}, //[可选]
        ajaxTimeout  : 50000, //[可选]全局Ajax请求超时时间(毫秒)
        pageInfo     : {total:'total', pageCurrent:'pageNumber', pageSize:'pageSize', orderField:'orderField', orderDirection:'orderDirection'}, //[可选]分页参数
        alertMsg     : {displayPosition:'topcenter', displayMode:'slide', alertTimeout:2000}, //[可选]信息提示的显示位置，显隐方式，及[info/correct]方式时自动关闭延时(毫秒)
        keys         : {statusCode:'statusCode', message:'message'}, //[可选]
        ui           : {
                         windowWidth      : 0,    //框架可视宽度，0=100%宽，> 600为则居中显示
                         showSlidebar     : true, //[可选]左侧导航栏锁定/隐藏
                         clientPaging     : true, //[可选]是否在客户端响应分页及排序参数
                         overwriteHomeTab : false //[可选]当打开一个未定义id的navtab时，是否可以覆盖主navtab(我的主页)
                       },
        debug        : true,    // [可选]调试模式 [true|false，默认false]
        theme        : 'sky' // 若有Cookie['bjui_theme'],优先选择Cookie['bjui_theme']。皮肤[五种皮肤:default, orange, purple, blue, red, green]
    })
    
    // main - menu
    $('#bjui-accordionmenu')
        .collapse()
        .on('hidden.bs.collapse', function(e) {
            $(this).find('> .panel > .panel-heading').each(function() {
                var $heading = $(this), $a = $heading.find('> h4 > a')
                
                if ($a.hasClass('collapsed')) $heading.removeClass('active')
            })
        })
        .on('shown.bs.collapse', function (e) {
            $(this).find('> .panel > .panel-heading').each(function() {
                var $heading = $(this), $a = $heading.find('> h4 > a')
                
                if (!$a.hasClass('collapsed')) $heading.addClass('active')
            })
        })
    
    $(document).on('click', 'ul.menu-items > li > a', function(e) {
        var $a = $(this), $li = $a.parent(), options = $a.data('options').toObj()
        var onClose = function() {
            $li.removeClass('active')
        }
        var onSwitch = function() {
            $('#bjui-accordionmenu').find('ul.menu-items > li').removeClass('switch')
            $li.addClass('switch')
        }
        
        $li.addClass('active')
        if (options) {
            options.url      = $a.attr('href')
            options.onClose  = onClose
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

    //页面宽度小于800，隐藏侧边栏
    setTimeout(function(){
        if ($(window).width() < 1300){
            $('#bjui-sidebar ul li:first a').click();
            $('#bjui-sidebar .lock').click();
        }
    }, 100);

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
        $(event.target).dialog({id:treeNode.tabid, url:treeNode.url, title:treeNode.name})
    else
        $(event.target).navtab({id:treeNode.tabid, url:treeNode.url, title:treeNode.name, fresh:treeNode.fresh, external:treeNode.external})
}
</script>
<style type="text/css"> 
.center{text-align:center;}	
.table > thead > tr > th{
	height:30px;
}

.fixedtableScroller::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.1);
	background-color: #F5F5F5;
	border-radius: 10px;
}

.fixedtableScroller::-webkit-scrollbar
{
	width: 5px;
	background-color: #F5F5F5;
}

.fixedtableScroller::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	background-color: #FFF;
	background-image: -webkit-linear-gradient(top, #e4f5fc 0%, #bfe8f9 50%, #9fd8ef 51%, #2ab0ed 100%);
}

.bjui-pageHeader{
	padding:0px 5px 0px 0px;
}	
.bjui-pageFooter{
	border-left:1px solid #ccc;
}	
.bjui-tablefixed .table{
table-layout:fixed;
}
.bjui-pageContent{
	padding: 10px 0px;
}
#bjui-accordionmenu{padding-top: 0px;}


</style>
</head>
<body>
    <!--[if lte IE 7]>
        <div id="errorie"><div>您还在使用老掉牙的IE，正常使用系统前请升级您的浏览器到 IE8以上版本 <a target="_blank" href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-8-worldwide-languages">点击升级</a>&nbsp;&nbsp;强烈建议您更改换浏览器：<a href="http://down.tech.sina.com.cn/content/40975.html" target="_blank">谷歌 Chrome</a></div></div>
    <![endif]-->
    <div id="bjui-window">
    <header id="bjui-header">
        <div class="bjui-navbar-header">
             <div class="ess-line"><kprop:prop key="title"/></div>
            <a class="bjui-navbar-logo" href="#"><img src="${ctx }/images/logo.png"></a>
        </div>
        <div id="bjui-hnav">
            <button type="button" class="btn-default bjui-hnav-more-left" title="导航菜单左移"><i class="fa fa-angle-double-left"></i></button>
            <div id="bjui-hnav-navbar-box">
                <ul id="bjui-hnav-navbar">
                   	<c:forEach items="${parentMenus }" var="pmenu" varStatus="pstatus" >
                   	<c:set var="active" value=""></c:set>
                   	<c:if test="${pstatus.index eq 0 }"><c:set var="active" value="active"></c:set></c:if>

                    <li class="${active }">
                    	<a class="ic_home" href="javascript:;" data-toggle="slidebar">${pmenu.mname }</a>

                    	<div class="items hide" data-noinit="true">
                    		<ul class="menu-items" data-faicon="edit">
                    		<c:if test="${empty(pmenu.menus) }">
                    			<p style="color: #99A1AC">请配置k_menu表中的菜单数据</p>
                    		</c:if>
	                    	<c:forEach items="${pmenu.menus }" var="menu">
	                    		<li><a href="${ctx }${menu.murl}" data-options="{id:'${menu.menuid }-list', faicon:'fa fa-circle'}">${menu.mname} </a></li>
	                    	</c:forEach>
                    		</ul>
                    	</div>
                    </li>
                    </c:forEach>
                    <li><a class="ic_exit" href="${ctx }/logout" ><i class=""></i>退出</a></li>
                </ul>
            </div>
            <button type="button" class="btn-default bjui-hnav-more-right" title="导航菜单右移"><i class="fa fa-angle-double-right"></i></button>
        </div>
    </header>
    <div id="bjui-container">
        <div id="bjui-leftside">
            <div id="bjui-sidebar-s">
                <div class="collapse"></div>
            </div>
            <div id="bjui-sidebar">
                <div class="toggleCollapse" style="">
                    <%--<h2><i class="fa fa-bars"></i> 导航栏 <i class="fa fa-bars"></i></h2>--%>
                    <a href="javascript:;" class="lock"><i class="fa fa-lock"></i></a>
                </div>
                <div class="panel-group panel-main" data-toggle="accordion" id="bjui-accordionmenu" data-heightbox="#bjui-sidebar" data-offsety="26">
                </div>
            </div>
        </div>
        <div id="bjui-navtab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent">
                    <ul class="navtab-tab nav nav-tabs ">
                        <li data-url="${ctx }/index/main"><a href="javascript:;"><span><i class="fa fa-home"></i> #maintab#</span></a></li>
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
                    <div class="bjui-pageContent ">
                        Loading...
                    </div>
                </div>
            </div>
        </div>
    </div>
   
    </div>
</body>
</html>