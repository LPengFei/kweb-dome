<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>
<body>
<div class="login_bg">
    <form action="${action}" method="post">
        <c:forEach items="${hiddens}" var="item">
            <input type="hidden" name="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form>
</div>
</body>
<script>

    document.forms[0].submit();
    document.forms[0].remove();

</script>
</html>
