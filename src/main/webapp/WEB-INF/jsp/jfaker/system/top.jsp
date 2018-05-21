<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
<title>Top</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link href="${ctx}/snaker/styles/css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="header">
  <%--<div class="logo"><img src="${ctx }/styles/images/logo.png" alt="JFaker"/></div>--%>
    <ul class="qj">
      <li style="width: 100px;"><span>${_login_user_key.uname }</span></li>
      <li class="top_aqtc" ><a href="javascript:void(0)" onclick="window.parent.location.href='${ctx }/logout'">安全退出<b></b></a></li>
    </ul>
</div>
<div id="admin-nav-wrap">
  <div class="admin-nav">
    <ul>
      <%--<li><a href="http://www.snakerflow.com/" target="_blank">Snaker</a></li>--%>
      <%--<li><a href="http://www.jfinal.com/" target="_blank">JFinal</a></li>--%>
      <%--<li><a href="http://formdesign.leipi.org/" target="_blank">Leipi</a></li>--%>
    </ul>
  </div>
</div>

</body>
</html>