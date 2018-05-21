<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/27
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>


<c:if test="${!empty(files)}">
    <c:choose>
        <c:when test="${type eq 'pictures'}">
            <a href="${ctx}/${appid}/attachment/view?type=pictures&files=${files}" data-toggle="navtab" data-id="attachment-view-pic" data-title="查看附件">
                <img src="${ctx}/images/ic_attachment.png" style="height: 26px;width: 26px">
            </a>
        </c:when>
        <c:when test="${type eq 'video'}">
            <c:set var="fileList" value="${fn:split(files, ',')}"/>
            <c:forEach items="${fileList}" var="file">
                <a href="${ctx}/${appid}/attachment/download?file=video/${file}">
                    下载
                </a>
            </c:forEach>
        </c:when>
    </c:choose>
</c:if>