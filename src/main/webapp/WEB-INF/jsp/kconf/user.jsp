<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %>
<script type="text/javascript">
	//删除回调
	$('#tabledit1').on('afterdelete.bjui.tabledit', function(e) {
	    var $tbody = $(e.relatedTarget)
	    
	    console.log('你删除了一条数据，还有['+ $tbody.find('> tr').length +']条数据！')
	});
	
	//页面初始化之后，重新绑定 按钮 点击事件
	$.CurrentNavtab.on(BJUI.eventType.afterInitUI, function(e){
		$(e.target).find("a[data-toggle-old]").each(function(){
			$(this).attr("data-toggle", $(this).attr("data-toggle-old"));
		});
	});
	
	//删除用户
	function delUser(uid,that){
		$(that).alertmsg('confirm', '确定要删除该行信息吗？', { displayPosition:'topcenter', okName:'确定', cancelName:'取消', title:'确认信息',okCall: function(){
			$.ajax({
				type: "post",
				url: "${ctx }/kconf/user/delete?uid="+uid, 
				success: function(data){
					if(data.statusCode == 200){
						$(that).parent().parent().remove();
					}
				}
			});
		}});
	}
	
</script>
<div class="white bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/kconf/user/user" method="post">
		<input type="hidden" name="pageNumber" value="${query.pageNumber }" /> 
		<input type="hidden" name="pageSize" value="${query.pageSize }" /> 
		<input type="hidden" name="orderField" value="${query.orderField }" />
		<input type="hidden" name="orderDirection" value="${query.orderDirection}">
		<input type="hidden" name="deptid" value="${deptid}">
		<div class="bjui-searchBar ess-searchBar ">
			<%--动态生成查询条件 start --%>
			<c:set var="queryCount" value="0"></c:set>

			<c:forEach items="${searchFields }" var="f">
				<div>
				<c:if test="${!empty(f.list_search_op) and item.form_view_type ne 'hidden' }">
					<c:set var="queryCount" value="${queryCount + 1 }"></c:set>

					<label>${f.field_alias }：</label>
					<c:if test="${not empty f.list_format and fn:containsIgnoreCase(f.type, 'date')}">
						<fmt:formatDate value="${fvalue }" pattern="${f.list_format }" var="fvalue" />
					</c:if>
					<input type="text"
						   value="${requestScope[f.field_name] }" name="${f.field_name }"
						   data-width="${f.form_search_width  }"
						   size="12"
						   data-ds="${ctx }${f.form_data_source }"
						   data-toggle="${f.form_view_type }"
						   data-chk-style="${f.chk-style }"
						   data-live-search='${f.form_is_search }'
						   data-select-empty-text="-全部-"
						   <c:if test="${not empty f.list_format }">data-pattern="${f.list_format }"</c:if>
					>&nbsp;


				</c:if>
				</div>
			</c:forEach>
				<button type="submit" class="btn-orange" style="background: #FF6600; color: white;" data-icon="search">筛选</button>


				<button type="button"  data-url="${ctx}/kconf/user/edit?deptid=${deptid}" data-icon="plus" data-toggle="navtab" data-id="${modelName }-edit" data-title="新增人员" style="background: #14CAB4; color: white; float: right;"  >新增</button>

			<div class="pull-right">
 				<c:if test="${!empty(exports) or !empty(imports) }">
	 			<div class="btn-group">
	                <button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
	                <ul class="dropdown-menu right" role="menu">
	                	<c:forEach items="${exports }" var="e">
	                    	<li><a href="${ctx}/kconf/user/export?xlsid=${e.ieid}&export=true&deptid=${deptid}" class="export" data-toggle="dialog" data-confirm-msg="确定要${e.iename }吗？" class="blue">${e.iename }-导出</a></li>
	                    </c:forEach>
	                    <c:if test="${!empty(imports) }">
	                    <li class="divider"></li>
	                   	<c:forEach items="${imports }" var="e">
	                   		<c:if test="${e.ieid!=291 }">
	                    		<li><a href="${ctx}/kconf/user/importxls?xlsid=${e.ieid}"  data-toggle="dialog" data-width="500" data-height="200" data-id="dialog-normal" class="green">${e.iename }-导入</a></li>
	                   		</c:if>
	                   		<c:if test="${e.ieid==291 }">
	                    		<li><a href="${ctx}/kconf/user/_importqualificationxls?xlsid=${e.ieid}"  data-toggle="dialog" data-width="500" data-height="200" data-id="dialog-normal" class="green">${e.iename }-导入</a></li>
	                   		</c:if>
	                    </c:forEach>
	                    </c:if>
	                    <!--
	                    <li><a href="book1.xlsx" data-toggle="doexportchecked" data-confirm-msg="确定要导出选中项吗？" data-idname="expids" data-group="ids">导出<span style="color: red;">选中</span></a></li>
	                    <li class="divider"></li>
	                    <li><a href="ajaxDone2.html" data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="delids" data-group="ids">删除选中</a></li>
	                    -->
	                </ul>
	            </div>
	            </c:if>
           </div>


		</div>
	</form>
</div>
<div class=" white bjui-pageContent tableContent " >

	<table data-toggle="tablefixed" data-width="100%">
		<thead>
			<tr>
				<th width="40px"  style="background: #DBF1ED; color: #03B9A0; text-align: center">序号</th>
				<%@include file="../common/common_list_thead.jsp" %>
				<th style="background: #DBF1ED; color: #03B9A0;width:130px;">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="r" varStatus="s">
				<tr >
					<td style="text-align: center">${s.count}</td>
					<c:forEach items="${fields }" var="f">  
						<c:if test="${f.list_view =='true' }">
							<c:choose> 
									<c:when test="${f.field_name eq 'rname' }">
										<td>${r.rname }</td>
									</c:when>
									<c:otherwise>
										<kval:val model="${r }" field="${f }" />
									</c:otherwise>
							</c:choose> 
						</c:if>
					</c:forEach>  
					<td style="width:130px;">
						<div style="height:32px;line-height: 32px;">
						<c:forEach items="${links }" var="link">
							 <klink:link model="${r }" link="${link }"/> &nbsp;
						</c:forEach>
							<c:if test="${isAdminUser && r.status eq 'lock'}">
								<a href="${ctx}/kconf/user/unlock/${r.id}" class="" data-toggle="doajax" data-confirm-msg="解除锁定">解锁</a>
							</c:if>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="bjui-pageFooter" style="margin-bottom:1px;">
	<div class="pages">
		<span>每页&nbsp;</span>
		<div class="selectPagesize">
			<select data-toggle="selectpicker" data-toggle-change="changepagesize">
				<option value="30">30</option>
				<option value="60">60</option>
				<option value="120">120</option>
				<option value="150">150</option>
			</select>
		</div>
		<span>&nbsp;条，共 ${page.totalRow } 条</span>
	</div>
	<div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>
