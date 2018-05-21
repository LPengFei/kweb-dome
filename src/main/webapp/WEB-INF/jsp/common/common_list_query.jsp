<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--动态生成查询条件  --%>
<c:set var="queryCount" value="0"></c:set>
<c:forEach items="${searchFields }" var="f">
	<c:if test="${!empty(f.list_search_op) and f.form_view_type ne 'hidden' }">
		<c:set var="queryCount" value="${queryCount + 1 }"></c:set>
		<div >
			<label>
				<c:if test="${not empty(f.list_search_label)}">
					<span style="margin-right:10px;"> ${f.list_search_label}</span>
				</c:if>
				<c:if test="${empty(f.list_search_label)}">
					${f.field_alias }：
				</c:if></label>
			<input type="text" value="${requestScope[f.field_name] }" id="j_input_${f.field_name }_${f.mid}list" name="${f.field_name }"  size="12"
				   data-rule="${validate_type }"
				   data-ds="${ctx }${f.form_data_source }"
				   data-toggle="${f.form_view_type }"
				   data-chk-style="${f.form_chk_style }"
				   data-form-style="${f.form_style }"
				   data-live-search='${f.form_live_search }'
				   data-url='${ctx }${f.data_url }?chk_style=false&lookup_param=${f.field_name }&lookup_modelId=${f.mid}list'
				   data-group="${f.data_group }"
				   data-width="${f.list_search_width}"
				   data-height="${f.data_height eq ""?'400':f.data_height}"
				   data-title="${f.data_title }"
				   data-select-empty-text="---请选择---"
				   <c:if test="${f.form_readonly eq 'readonly' }">readonly</c:if>
				   <c:if test="${f.form_chk_stype }">multiple="multiple"</c:if>
				   <c:if test="${not empty f.list_search_dateformat }">data-pattern="${f.list_search_dateformat }"</c:if>
			/>&nbsp;
		</div>
	</c:if>
</c:forEach>


<c:if test="${queryCount gt 0 }">
	<button type="submit" class="red-button"  data-icon="search" style="margin-top: 4px">筛选</button>
</c:if>
<script>

</script>
