<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %> 
	<div class="bjui-pageContent">
	<div class="ess-form">
		<form action="${ctx}/kconf/lookup/save"  data-toggle="validate" data-alertmsg="false">
			<input type="hidden" name="record.lkid" value="${record.lkid }">
			<ul>
	            <li class="input_selectpicker">
	                <label>所属类型</label>
	                <input type="text" name="record.ltid" id="j_input_ltid" value="${record.ltid }" readonly="readonly">
	            </li> 
	            <li class="input_text">
	                <label>键(存数据库)</label>
	                <input type="text" name="record.lkey" id="j_input_lkey" value="${record.lkey }" placeholder="请输入KEY" >
	            </li> 
	            <li class="input_text">
	                <label>值(页面显示)</label>
	                <input type="text" name="record.lvalue" id="j_input_lvalue" value="${record.lvalue }" placeholder="请输入VALUE" >
	            </li>
				<li class="input_text">
					<label>父ID</label>
					<input type="text" name="record.pid" id="j_input_pid" value="${record.pid }" placeholder="请输入pid" >
				</li>
	            <li class="input_text">
	                <label>排序</label>
	                <input type="text" name="record.sort" id="j_input_sort" value="${record.sort }" placeholder="请输入排序" >
	            </li>
	            <li class="input_text">
	                <label>是否默认</label>
	                <select data-toggle="selectpicker" data-width="200" name="record.is_check"  data-size="10">
                    	<option value="true" <c:if test="${record.is_check }"> selected="selected" </c:if>>默认选中</option>
						<option value="false" <c:if test="${not record.is_check }"> selected="selected" </c:if> >默认不选中</option>
                    </select>
	            </li> 
	            <li class="input_text">
	                <label>备注</label>
	                <input type="text" name="record.remark" id="j_input_remark" value="${record.remark }" placeholder="请输入备注" >
	            </li> 
        	</ul>
			<div style="text-align:right; padding-top: 20px;">
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
				<button type="button" class="btn btn-close" style="background: red; color: white;">取消</button>
			</div>
		</form>
		<div class="clearfix"></div>
	</div>
</div>
<script type="text/javascript">
initForm();
</script>