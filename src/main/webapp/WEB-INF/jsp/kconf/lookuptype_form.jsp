<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %> 
	<div class="bjui-pageContent">
	<div class="ess-form">
		<form action="${ctx }/kconf/lookup/typeSave" data-toggle="validate">
			<ul>
				<li>
					<label>类型ID</label>
					<input type="text" name="record.ltid" id="j_ltid" value="${record.ltid }" placeholder="请输入类型ID">
				</li> 
				<li>
					<label>类型名称</label>
					<input type="text" name="record.tname" id="j_tname" value="${record.tname }" placeholder="请输入类型名称">
				</li>
				<li>
					<label>常量类型</label>
					<select name="record.type" value="${record.type }" class="form-control" id="j_type" style="width: 210px;height: 30px;">
						<option value="公有常量" >公有常量</option>
						<option value="私有常量" <c:if test="${record.type eq '私有常量'}">selected</c:if>>私有常量</option>
					</select>
				</li>
				<li>
					<label>备注</label>
					<input type="text" name="record.remark" id="j_remark" value="${record.remark }" placeholder="请输入备注">
				</li> 
			</ul>
		
		<div class="clearfix"></div>
		
		<div style="text-align:right; padding-top: 20px;">
			<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
			<button type="button" class="btn btn-close" style="background: red; color: white;">取消</button>
		</div>
		</form>
		
	</div>
</div>
 