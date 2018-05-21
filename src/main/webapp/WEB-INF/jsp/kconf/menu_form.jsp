<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %> 
	<div class="bjui-pageContent">
	<div class="ess-form">
		<form action="${ctx}/kconf/menu/save"  data-toggle="validate" data-alertmsg="false">
			<input type="hidden" value="${type}" name="record.type">
			<ul>
					<li class="input_text">
		                <label>菜单ID</label>
		               <input type="text" name="record.menuid" id="j_input_menuid" value="${record.menuid }" placeholder="请输入菜单ID" >
		            </li> 
		            <li class="input_text">
		                <label>菜单名称</label>
		               <input type="text" name="record.mname" id="j_input_mname" value="${record.mname }" placeholder="请输入菜单名称" >
		            </li> 
		            <li class="input_text">
		                <label>菜单地址</label>
		               <input type="text" name="record.murl" id="j_input_murl" value="${record.murl }" placeholder="请输入菜单地址" >
		            </li> 
		          	
		          	<!-- 判断是否有父级菜单,module类型没有父级菜单，parent级别查询module，child菜单查询parent级菜单 -->
		          	 <c:if test="${!empty(pmenus) }">
		            <li class="input_text">
		                <label>父级菜单</label>
		          <%--      <input type="text" name="record.pmenuid" id="j_input_pmenuid" value="${record.pmenuid }" placeholder="请输入父级菜单" > --%>
		               <select name="record.pmenuid" data-toggle="selectpicker">
		               		<c:forEach items="${pmenus }" var="p">
		               			<option value="${p.menuid }" <c:if test="${p.menuid eq record.pmenuid  }">selected=selected</c:if> >${p.mname }</option>
		               		</c:forEach>
		               </select>
		            </li> 
		              </c:if>
		              
		              <c:if test="${not empty(models)}">
				<li class="input_text">
						  <label>模型</label>
						  <select name="mid" data-toggle="selectpicker">
							  <option value="">---请选择---</option>
							  <c:forEach items="${models }" var="m">
								  <option value="${m.mid }" <c:if test="${fn:contains(m.tabid, record.menuid)  }">selected=selected</c:if> >${m.mname }</option>
							  </c:forEach>
						  </select>
				</li>
					  </c:if>
		            <li class="input_text">
		                <label>排序</label>
		               <input type="text" name="record.ord" id="j_input_ord" value="${record.ord }" placeholder="菜单显示顺序" >
		            </li> 
		            
		            <li class="input_text">
		                <label>菜单图标</label>
		               <input type="text" name="record.icon" id="j_input_icon" value="${record.icon }" placeholder="菜单图标名称" >
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

