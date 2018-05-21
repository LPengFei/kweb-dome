<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>流程状态</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" href="${ctx}/snaker/styles/css/snaker.css" type="text/css" media="all" />
		<script src="${ctx}/snaker/styles/js/raphael-min.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/js/jquery-ui-1.8.4.custom/js/jquery.min.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/js/jquery-ui-1.8.4.custom/js/jquery-ui.min.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/js/snaker/snaker.designer.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/js/snaker/snaker.model.js" type="text/javascript"></script>
		<script src="${ctx}/snaker/styles/js/snaker/snaker.editors.js" type="text/javascript"></script>

<script type="text/javascript">
    function addTaskActor(taskName) {
        var url = '${ctx}/snaker/task/actor/add?orderId=${orderId}&taskName=' + taskName;
        var returnValue = window.open(url,window,'dialogWidth:1000px;dialogHeight:600px');
        if(returnValue) {
            $('#currentActorDIV').append(',' + returnValue);
        }
    }
	function display(process, state) {
		/** view*/
		$('#snakerflow').snakerflow($.extend(true,{
			basePath : "${ctx}/snaker/styles/js/snaker/",
            ctxPath : "${ctx}",
            orderId : "${orderId}",
			restore : eval("(" + process + ")"),
			editable : false
			},eval("(" + state + ")")
		));
	}
</script>
</head>
	<body>
		<table class="properties_all" align="center" border="1" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<div id="snakerflow" style="border: 1px solid #d2dde2; margin-top:10px; margin-left:10px; margin-bottom:10px; width:98%;">
			</div>
		</table>
		<script type="text/javascript">
		$.ajax({
				type:'GET',
				url:"${ctx}/snaker/process/json",
				data:"processId=${processId}&orderId=${orderId}",
				async: false,
				globle:false,
				error: function(){
					alert('数据处理错误！');
					return false;
				},
				success: function(data){
					display(data.process, data.state);
				}
			});
		</script>
	</body>
</html>
