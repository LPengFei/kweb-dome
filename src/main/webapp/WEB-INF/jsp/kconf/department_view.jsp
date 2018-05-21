
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<script type="text/javascript">


</script>
<!-- 基本信息 -->
<div class="section verify" >
	<div class="tile-header">
		<%-- <a href="javascript:;" data-toggle="lookupback" data-args="{zg_dept:'${record.dname }',audit_deptid:'${record.id }',audit_deptname:'${record.short_name }',work_dept:'${record.dname }'}" style="float:right" class="btn btn-blue" title="选择本项" data-icon="check">选择</a> --%>
		<%-- <a href="javascript:;" data-toggle="lookupback" data-args="{zg_dept:'${record.dname }',audit_deptid:'${record.id }',audit_deptname:'${record.short_name }',work_dept:'${record.dname }'}" style="float:right" class="btn btn-blue" title="选择本项" data-icon="check">选择</a> --%>
		<!-- 选择按钮 -->
		<a class="selecting_department_name" style="float:right;cursor:pointer;" name="${record.dname }" mytestid="${record.id }">
			<input type="hidden" name="record.dname" value="${record.dname}" id="dept_view_dname">
			<input type="hidden" name="record.id" value="${record.id}" id="dept_view_id">
			<input type="button" value="带回" class="btn deep-blue ess_btn fr get_back" data-icon="check"></a>
	</div>
	<div class="tile-body ess-form borderB" style="padding: 0px;">

		<div class="short-filed-container">
			<ul class="section_ul">
				<li>
					<label class="filed-label">上级部门</label>
					<span class="filed-content" name="record.pid">${record.pid}</span>
				</li>
				<li>
					<label class="filed-label">部门名称</label>
					<span class="filed-content" name = "record.dname">${record.dname}</span>
				</li>
				<li>
					<label class="filed-label">单位简称</label>
					<span class="filed-content" name = "record.short_name">${record.short_name}</span>
				</li>
				<li>
					<label class="filed-label">单位类型</label>
					<span class="filed-content" name="record.type">${record.type}</span>
				</li>
				<li>
					<label class="filed-label">单位属性</label>
					<span class="filed-content" name = "record.nature">${record.nature}</span>
				</li>
				<li>
					<label class="filed-label">归口部门</label>
					<span class="filed-content" name="record.relevant">${record.relevant}</span>
				</li>
			</ul>
		</div>


		<span class="clearfix"></span>
	</div>
</div>
<script>

$(function(){
	
	 $(".selecting_department_name",$.CurrentDialog).click(function(){
		 var name =  $('#dept_view_dname',$.CurrentDialog).val();
		 var mytestid =  $('#dept_view_id',$.CurrentDialog).val();
		 $("input[name='verify_dept_id']").val(mytestid);
		 $("input[name='verify_dept_name']").val(name);
         $("input[name='record.verify_dept_id']").val(mytestid);
         $("input[name='record.verify_dept_name']").val(name);
         $(this).dialog('closeCurrent');
	    }); 

});
 
</script>
