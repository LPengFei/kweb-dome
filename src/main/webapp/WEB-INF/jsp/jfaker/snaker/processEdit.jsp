<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>流程定义</title>

		<link rel="stylesheet" href="${ctx}/snaker/styles/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="${ctx}/snaker/styles/wbox/wbox/wbox.css" />
		<script src="${ctx}/snaker/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/snaker/styles/wbox/wbox.js"></script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/snaker/process/doFileDeploy" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${process.id }">
		<table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					流程定义
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">
						<span>流程名称：</span>
					</td>
					<td class="td_table_2" colspan="3">
						&nbsp;${process.name }
					</td>
				</tr>
				<tr>
					<td class="td_table_1">
						<span>显示名称：</span>
					</td>
					<td class="td_table_2" colspan="3">
						&nbsp;${process.displayName }
					</td>
				</tr>
				<tr>
					<td class="td_table_1">
						<span>状态：</span>
					</td>
					<td class="td_table_2" colspan="3">
						&nbsp;${process.state == 1 ? '启用' : '禁用' }
					</td>
				</tr>
				<tr>
					<td class="td_table_1">
						<span>上传流程定义文件：</span>
					</td>
					<td class="td_table_2" colspan="3">
						<input type="file" class="input_file" id="snakerFile" name="snakerFile"/>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">
						<span>流程定义：</span>
					</td>
					<td class="td_table_2" colspan="3">
						${content }
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="submit" class="button_70px" name="submit" value="提交">
						&nbsp;&nbsp;
						<input type="button" class="button_70px" name="reback" value="返回"
							onclick="history.back()">
					</td>
				</tr>
			</table>
			
		</form>
	</body>
</html>
