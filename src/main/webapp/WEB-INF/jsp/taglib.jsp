<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ taglib uri="/kvalue" prefix="kval" %>
<%@ taglib uri="/kprop" prefix="kprop" %>
<%@ taglib uri="/klink" prefix="klink" %>
<%@ taglib uri="/klookup" prefix="klookup" %>


<%@ taglib prefix="frame" uri="/frame" %>

<c:set var="appid" value="${empty(appid)?'app':appid }"></c:set>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctx_kess" value="${pageContext.request.contextPath}/${appid }"/>
<c:set var="loginUser" value="${_login_user_key}"/>

<%--  js页面的ctx常量  --%>
<script type="text/javascript">
    var SERVER_CTX = "${ctx}";
    var SERVER_CTX_KESS="${ctx_kess}";
</script>