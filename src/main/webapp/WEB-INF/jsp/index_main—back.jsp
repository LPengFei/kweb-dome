<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ include file="taglib.jsp" %> 
<style>
	.chart-base-container .chart{
		display: inline-block;
		width: 49%;
	}

</style>
<div class="bjui-pageContent fixedtableScroller" style="padding-left: 20px; padding-top: 20px; overflow: auto;">
	<div class="row nomarginLR">
		<div class="col-md-9 nopadding">
			<div class="account-box border-radius padding12 white">
				<div class="head-pic"><img src="${ctx }/images/icon_head_pic.png"></div>
				<div class="info-box">

					<div class="base-info">
						<!-- 当前有用户登录     -->
						   <h3> ${_login_user_key.uname }，欢迎您！</h3>
						
						<ul>
							<li><label>所在部门：</label><span>${_login_dept_key.dname }</span></li>
							<li><label>角色：</label><span>${role.rname}</span></li>
						</ul>
						<div class="clearfix"></div>
					</div>
					<div class="work-info">
						<div class="account-security">
							<label>账户安全：</label>
							<div class="progress">
								<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 40%;">
									<span class="sr-only">40% </span>
								</div>
							</div>
						</div>
						<ul>
							<li><i class="fa fa-unlock-alt ess-blue"></i>
								<a href="${ctx}/kconf/user/changePwd" data-toggle="dialog">修改密码</a>
							</li>
							<li><i class="fa fa-clock-o ess-orange"></i><label>最近登录:</label> <span>2016-08-19 15:12:25</span></li>
							<li><i class="ess-green" style="display: inline-block; font-style: normal; font-family: '微软雅黑';">IP</i><label>IP:</label> <span>${ip }</span></li>
						</ul>
						<div class="clearfix"></div>
					</div>

				</div>
				<!-- 待审计划 -->
				<div class="pending-plan" style="visibility: hidden">
					<label>待审计划:</label><strong>3条</strong>
					<a href="#" class="ess-btn deep-blue">计划审核</a>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<!--系统通知-->
		<div class="col-md-3">
			<div class="system-notice border-radius padding12 white">
				<h4>系统信息</h4>
				<ul>
					<li> Java的运行环境版本：${sysinfo.java_version }  </li>
					<li> 操作系统信息：${sysinfo.os_name }(版本号：${sysinfo.os_version}) / ${sysinfo.os_arch } </li>
					<li> 操作系统时间： <fmt:formatDate value="${sysinfo.os_date }" pattern="yyyy-MM-dd HH:mm:ss"/> </li>
					<li> 服务器IP地址：${sysinfo.os_ip }  / ${sysinfo.os_mac } </li>
					<%-- <li> 服务器信息：CPU ${sysinfo.os_cpus }  </li> --%>
					<li> 使用内存信息：${sysinfo.totalMemory }(可用内存) /  ${sysinfo.freeMemory }(剩余可用内存) </li>
					<li> 物理内存信息：${sysinfo.totalMemorySize}(总物理内存) / ${sysinfo.freePhysicalMemorySize }(剩余物理内存) </li>
				</ul>
			</div>
		</div>
	</div>
	<!-- 功能模块 -->
	<%--<div class="row nomarginLR" style="margin-top:15px;margin-bottom:15px;">
		<div class="col-md-2 nopadding">
			<a class="fun-box" href="#"><i class="fa fa-upload upload"></i><span>计划上报</span></a>
		</div>
		<div class="col-md-2">
			<a class="fun-box" href="#"><i class="fa fa-newspaper-o examine"></i><span>计划审核</span></a>
		</div>
		<div class="col-md-2">
			<a class="fun-box" href="#"><i class="fa fa-user-plus meet"></i><span>开平衡会</span></a>
		</div>
		<div class="col-md-2">
			<a class="fun-box" href="#"><i class="fa fa-file-text-o report"></i><span>巡查简报</span></a>
		</div>
		<div class="col-md-2">
			<a class="fun-box" href="#"><i class="fa fa-hdd-o archives"></i><span>违章档案</span></a>
		</div>
		<div class="col-md-2">
			<a class="fun-box" href="#"><i class="fa fa-comment-o question"></i><span>问题反馈</span></a>
		</div>
	</div>--%>

	<!-- 本周计划统计 -->
	<div class="row section nomarginLR" style="margin-right: 15px; margin-top: 10px;">
		<!--tile-header-->
		<div class="tile-header" style="visibility: hidden;">
			<span>本周计划情况</span>
		</div>
		<!--tile-body-->
		<div class="tile-body">
			<div class="col-md-12">
				<div class="chart-base-container" data-url2="${ctx }/js/2-1-1.json">
					<%--<div class="chart" data-url="${ctx}/app/project/chart?fname=project_type"></div>--%>
					<%--<div class="chart" data-url="${ctx}/app/project/chart?fname=tally_label"></div>--%>
					<div class="chart" id="mainChart1" data-url="${ctx}/kconf/chart/chartData?id=index1"></div>
					<div class="chart" id="mainChart2" data-url="${ctx}/kconf/chart/chartData?id=index2"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${ctx }/js/ess.js" type="text/javascript"></script>
<script type="text/javascript">
//	$(function() {
		$(".bjui-pageContent").height($(window).height() - 160);
		$(window).resize(function() {
			$(".bjui-pageContent").height($(window).height() - 160);
		});

		drawMainChart();

//		loadHightDiv($($(".chart-base-container")[0]), option);
//	})

</script>