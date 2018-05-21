<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/27
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<style>
    .bigPic{width:100%; height:auto;}
    .bigPic li{ width: 100%;height: auto;}
    .bigPic li img{width: auto; max-width: 100%;}
</style>
<div class="bjui-pageContent">
    <ul class="bigPic">
        <c:forEach items="${pathList}" var="path">
            <li>
                <img src="${ctx}/${path}">
            </li>
        </c:forEach>
    </ul>
</div>