<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
	.table > thead > tr > th > .fixedtableCol, .table > tbody > tr > td > .fixedtableCol{white-space:normal}
</style>
<%--动态生成列表table head --%>
<c:forEach items="${fields }" var="f">
	<c:if test="${f.list_view == 'true' }">

		<th title="${f.field_alias }" style="background: #DBF1ED; color: #03B9A0; text-align:${f.list_align}" width="${f.list_width }"
				<c:if test="${f.list_order eq '1' }"> data-order-field="${f.field_name }" </c:if> >${f.field_alias }</th>
	</c:if>
</c:forEach>
