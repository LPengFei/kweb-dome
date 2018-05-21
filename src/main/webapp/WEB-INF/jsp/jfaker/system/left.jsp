<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Left</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link href="${ctx}/snaker/styles/css/index.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
</head>

<body>
<div class="sidebar">
  <%--<frame:menu />--%>
	  <dl>
		  <dt id="sidebar_goods_manage" class=""><i class="pngFix"></i>流程管理</dt>
		  <dd style="display: block;">
			  <ul>
				  <li ><a href="${ctx}/snaker/task/active" target="mainFrame">待办任务</a></li>
				  <li class=""><a href="${ctx}/snaker/flow/order" target="mainFrame">流程实例</a></li>
				  <li class=""><a href="${ctx}/snaker/task/history" target="mainFrame">历史任务</a></li>
				  <li class=""><a href="${ctx}/snaker/process/list" target="mainFrame">流程定义</a></li>
				  <li class=""><a href="${ctx}/snaker/surrogate/list" target="mainFrame">委托授权</a></li>
			  </ul>
		  </dd>
	  </dl>

</div>
<script type="text/javascript">
    $("li").click(function() {
    	removeCSS();
        $(this).addClass("current").siblings().removeClass("current");
    }).hover(function() {
        $(this).addClass("lihover");
    },
    function() {
        $(this).removeClass("lihover");
    })
    function removeCSS(){
    	$("li").each(function(){
			$(this).removeClass("current").siblings().removeClass("current");
		});
    }
</script>
<script type="text/javascript">
	$(document).ready(function (){
//		$("dd").each(function(){
//			$(this).css("display","none");
//		});
		
		$("dt").click(function(){
			var ele = $(this).parent().children("dd");
			if(ele.css("display") == "none"){
				$(this).attr("class","");
				ele.css("display","block");
			}else if(ele.css("display") == "block"){
				$(this).attr("class","hou");
				ele.css("display","none");
			}
		});
	});
	function Show_left() {
	if (document.getElementById("oaleft").style.display == "none") {
		document.getElementById("oaleft").style.display = "block";
		document.getElementById("leftimg").style.background = 'url(../images/switch_left.gif)';
	} else {
		document.getElementById("oaleft").style.display = "none";
		document.getElementById("leftimg").style.background = 'url(../images/switch_right.gif)';
	}
}
</script>
</body>
</html>