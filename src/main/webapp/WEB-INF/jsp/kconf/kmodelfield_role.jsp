<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %> 
	<div class="bjui-pageContent">
	<div class="ess-form">
		<form action="${ctx}/kconf/field/saveRole?mid=${mid}"  data-toggle="validate" data-alertmsg="false">

			<!-- 角色菜单配置 -->
			<c:forEach items="${roles }" var="role">
				<div style="margin:5px 0px; font-size:12px; border-top:1px solid #ccc; clear: both;">
					<span onclick="selectAllNextUl(this)" style="font-weight: bold;line-height:40px;cursor: pointer;">角色-${role.rname } </span>
					<ul>
					 	<c:forEach items="${fields}" var="field">
							<li style="width:150px;margin-right:15px; line-height:30px;height:auto;">
								<input style="width:12px;float:left;vertical-align: middle;margin-top:-1px; margin-right: 4px;" type="checkbox" name="role-${role.id }" value="${field.mfid}" <c:if test="${fn:contains(role.fields,field.mfid)}"> checked="checked"</c:if>  />
								<span <c:if test="${field.list_view =='false' }">style="color:red;"</c:if> >
								<c:if test="${empty field.field_alias }">${field.field_name }</c:if>
								<c:if test="${not empty field.field_alias }">${field.field_alias }</c:if>
								
							</li>	
						</c:forEach>
					</ul>
				</div>
			</c:forEach>
			<div class="clearfix"></div>
			<!-- 操作按钮 -->
			<div style="text-align:right; padding-top: 20px;"  >
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
				<button type="button" class="btn-close" style="background: red; color: white;">取消</button>
			</div>
		</form>
	</div>
</div>
