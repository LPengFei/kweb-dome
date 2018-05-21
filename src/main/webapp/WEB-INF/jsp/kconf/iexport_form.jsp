<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  

<style>
	
</style> 

<%@ include file="../taglib.jsp" %> 
	<div class="bjui-pageContent">
	<div class="ess-form">
		<form action="${ctx}/kconf/iexport/save"  data-toggle="validate" data-alertmsg="false">
			<input type="hidden" name="record.ieid" value="${record.ieid }">
			<ul>
					<li>
						<label>配置名称</label>
						<input type="text" name="record.iename" value="${record.iename }" id="j_iename" value="" placeholder="请输入导入导出配置名称" class="form-control">
					</li> 
				 	
					<li>
						<label>类型(导入、导出)</label>
						<select data-toggle="selectpicker" data-width="200" name="record.ietype"  data-size="10" data-val="${record.ietype }">
	                    	<option value="导出" selected="selected">导出</option>
							<option value="导入" <c:if test="${record.ietype == '导入' }"> selected="selected" </c:if> >导入</option>
	                    </select>
					</li> 
				 
					<li>
						<label>操作数据库表</label>
						<input type="text" name="record.ietable"  value="${record.ietable }" id="j_ietable" value="" placeholder="请输入表名称" class="form-control">
					</li>
					<li>
						<label>关联ModelID</label>
						<select data-toggle="selectpicker" data-width="200" name="record.modelid"  data-size="10">
							<option value="">---请选择---</option>
							<c:forEach items="${models }" var="m">
	                    		<option value="${m.mid }" <c:if test="${record.modelid ==m.mid  }"> selected="selected" </c:if>>${m.mname}</option>
	                    	</c:forEach>
	                    </select>
					</li>
					<li>
						<label>Excel导出标题(可以采用#key#等设置动态参数，并在Controller中设置值)</label>
						<input type="text" name="record.title" value="${record.title }" id="j_title" value="" placeholder="请输入Excel标题名称" class="form-control">
					</li> 
			</ul>
			<div class="clearfix"></div>
			<!-- 操作按钮 -->
			<div style="text-align:right; padding-top: 20px;">
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
				<button type="button" class="btn-close" style="background: red; color: white;">取消</button>
			</div>
		</form>
	</div>
</div>
